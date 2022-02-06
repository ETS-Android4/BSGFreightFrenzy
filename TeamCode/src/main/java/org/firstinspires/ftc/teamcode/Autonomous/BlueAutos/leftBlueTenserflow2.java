package org.firstinspires.ftc.teamcode.Autonomous.BlueAutos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

import java.util.List;
/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * This 2020-2021 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the Freight Frenzy game elements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@Autonomous @Disabled //(name = "TenserFlowRightBlue", group = "Blue")
public class leftBlueTenserflow2 extends LinearOpMode {
    Robot bsgRobot = new Robot();

    /*
    *
    VARIABLES FOR ENCODERS
    *
    */
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 537.7;    // Neverest 40
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.78;       // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    //For turning with encoders
    Integer cpr = 28; //counts per rotation originally 28
    Integer gearratio = (((1 + (46 / 17))) * (1 + (46 / 11))); //IDK IT WAS ORIGINALLY 40
    Double diameter = 4.0;
    Double cpi = (cpr * gearratio) / (Math.PI * diameter); //counts per inch, 28cpr * gear ratio / (2 * pi * diameter (in inches, in the center))
    Double bias = 0.8;//default 0.8
    Double meccyBias = 0.9;//change to adjust only strafing movement (was .9)
    //
    Double conversion = cpi * bias;
    Boolean exit = false;

    static final double COUNTS_PER_ARM_MOTOR_REV = 1440.0;  // eg: TETRIX Motor Encoder //2150.8
    static final double ARM_GEAR_REDUCTION = 0.3;        // This is < 1.0 if geared UP
    static final double SPROCKET_DIAMETER_INCHES = 3.0;     // For figuring circumference

    static final double ARM_PER_INCH = (COUNTS_PER_ARM_MOTOR_REV * ARM_GEAR_REDUCTION) / (SPROCKET_DIAMETER_INCHES * 3.1415);
    static final double LVL_1_INCHES = 5;
    static final double LVL_2_INCHES = 14;
    static final double LVL_3_INCHES = 19.5;

    public static double liftHeight = 0.0;
    /* Note: This sample uses the all-objects Tensor Flow model (FreightFrenzy_BCDM.tflite), which contains
     * the following 4 detectable objects
     *  0: Ball,
     *  1: Cube,
     *  2: Duck,
     *  3: Marker (duck location tape marker)
     *
     *  Two additional model assets are available which only contain a subset of the objects:
     *  FreightFrenzy_BC.tflite  0: Ball,  1: Cube
     *  FreightFrenzy_DM.tflite  0: Duck,  1: Marker
     */
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };
    int level = 0;
    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AcXydGT/////AAABmaoOrVEo9U5MmkjGK046kUuJ1fe3bKCk91BjU60EiS60M1MJhK7KxxM5O9LptxzRZOgzxBIBC1NO6yj0SF2j9HxnmFbPTUu+MiMMVe31OCSpqqeueblLKonNgSNql1UOi9NHYM4yx2dxAbWKi9eCQ18YPHq5UC6WFTqvgrbKDYI1QgcBV9SBtiukvchFmBvXvC76/51Yfur4rAvdYIMmH/B9jea4jl0IlnLVci8C7zV/uEJfi2R+L2ogvLyaqbG7P6n4HKF1Zku5vnxyEEyvsQIDIWyy35rOCkyPE4D/n9MtNb7p+X/zy4BORz14RNPNxdOqqPqSiov2RcFcLKNFh95xxYFl5S2csn1+2R3kkyDH";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;
    private boolean isDuckDetected;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2.5, 16.0 / 9.0);
        }
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        //close claw
        bsgRobot.clamp.setPower(-1);
        sleep(250);

        //strafe towards markers
        strafeToPosition(4, 0.5);

        telemetry.addData("Height of Lift", liftHeight);
        telemetry.update();

        //scan for funny ducky or capstone on first marker
       if (isDuckDetected()) {

            liftHeight = LVL_1_INCHES;
            telemetry.addLine("Detected LVL 1, Proceed movement");
            //Turn 180
            encoderDrive(DRIVE_SPEED, 7.55, -7.55, 3);
            strafeToPosition(12, 0.3);
            encoderDrive(DRIVE_SPEED,2,2,3);
            bsgRobot.motion.setPower(-0.3);
            sleep(400);
            bsgRobot.stopMotors();
        } else { //no detection on marker 1

            //drive down
            encoderDrive(DRIVE_SPEED, -4, -4, 3);
            //wait like 0.7 sec ig
            sleep(700);

            if (isDuckDetected()) { //marker 2
                liftHeight = LVL_2_INCHES;
                telemetry.addLine("Detected LVL 2, Proceed movement");
                //Turn 180 then strafe
                encoderDrive(DRIVE_SPEED, 7.55, -7.55, 3);
                strafeToPosition(14, 0.3);
            } else { //no detection on marker 2
                liftHeight = LVL_3_INCHES;
                telemetry.addLine("LVL 3, Proceed movement");
                encoderDrive(DRIVE_SPEED, 7.55, -7.55, 3);
                strafeToPosition(16, 0.3);
                encoderDrive(DRIVE_SPEED,3,3,3);
                bsgRobot.motion.setPower(-0.3);
                sleep(400);
                bsgRobot.stopMotors();

            }
        }

//placing and leaving area
        bsgRobot.dropBlock();

        bsgRobot.motion.setPower(1);

        encoderDrive(0.4,-2,-2,3);

        bsgRobot.lift.setPower(liftHeight);


        bsgRobot.stopMotors();

        encoderDrive(0.4,-15,-15,3);

        //ducky :]

        bsgRobot.strafeRight(1000);
        sleep(400);

        encoderDrive(0.3,0,-6,3);

        bsgRobot.carousel.setPower(0.65);
        sleep(3000);

        bsgRobot.stopMotors();

        encoderDrive(0.3,-6,0,3);

        bsgRobot.strafeLeft(1000);
        sleep(200);


        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        boolean isDuckDetected = false;     //  ** ADDED **
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            i++;

                            // check label to see if the camera now sees a Duck         ** ADDED **
                            if (recognition.getLabel().equals("Duck")) {//  ** ADDED **
                                level = 1;                             //  ** ADDED **
                                telemetry.addData("Object Detected", "Duck");      //  ** ADDED **
                            }


                            //insert move right code here
                            if (recognition.getLabel().equals("Duck")) {            //  ** ADDED **
                                level = 2;                             //  ** ADDED **
                                telemetry.addData("Object Detected", "Duck");      //  ** ADDED **
                            }


                            if (recognition.getLabel().equals("Duck")) {            //  ** ADDED **
                                level = 3;                             //  ** ADDED **
                                telemetry.addData("Object Detected", "Duck");      //  ** ADDED **
                            }
                            telemetry.addData("Level Detected", level);
                            telemetry.update();
                            switch(level) {
                                case 1:
                                    //auto lvl 1
                                    break;
                                case 2: 
                                    //auto lvl 2
                                    break;
                                case 3:
                                    //auto lvl 3
                                    break;
                                case 0:
                                    //failsafe (assume this autonomous)
                            }
                            //  ** ADDED **
                            }                                                      //  ** ADDED **
                        }
                        telemetry.update();
                    }
                }
            }
        }

    private boolean isDuckDetected() {
        boolean isDetected = false;
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                // telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                int i = 0;

                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                    i++;
                    // check label to see if the camera now sees a Duck         ** ADDED **
                    if (recognition.getLabel().equals("Duck")){
                        // telemetry.addData("Object Detected!!!", "Duck");
                        isDetected = true ;

                    } else {
                        telemetry.addData("Object Not Detected!!!", "Not DUCK");
                        isDetected = false;
                    }
                }
                telemetry.update();
            }
        }

        return isDetected;
    }


    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = bsgRobot.frontLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newBackLeftTarget = bsgRobot.backLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newFrontRightTarget = bsgRobot.frontRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            newBackRightTarget = bsgRobot.backRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);

            bsgRobot.frontLeft.setTargetPosition(newFrontLeftTarget);
            bsgRobot.backLeft.setTargetPosition(newBackLeftTarget);
            bsgRobot.frontRight.setTargetPosition(newFrontRightTarget);
            bsgRobot.backRight.setTargetPosition(newBackRightTarget);


            // Turn On RUN_TO_POSITION
            bsgRobot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bsgRobot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bsgRobot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bsgRobot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();

            bsgRobot.frontLeft.setPower(Math.abs(speed));
            bsgRobot.backLeft.setPower(Math.abs(speed));
            bsgRobot.frontRight.setPower(Math.abs(speed));
            bsgRobot.backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (bsgRobot.frontLeft.isBusy() && bsgRobot.frontRight.isBusy() &&
                            bsgRobot.backLeft.isBusy() && bsgRobot.backRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newFrontLeftTarget, newFrontRightTarget,
                        newBackLeftTarget, newBackRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        bsgRobot.frontLeft.getCurrentPosition(),
                        bsgRobot.backLeft.getCurrentPosition(),
                        bsgRobot.frontRight.getCurrentPosition(),
                        bsgRobot.backRight.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            bsgRobot.stopWheels();

            // Turn off RUN_TO_POSITION
            bsgRobot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bsgRobot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bsgRobot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bsgRobot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    //strafing with encoders
    public void strafeToPosition(double inches, double speed) {
        //
        int move = (int) (Math.round(inches * cpi * meccyBias * 1.265));
        //
        bsgRobot.backLeft.setTargetPosition(bsgRobot.backLeft.getCurrentPosition() - move);
        bsgRobot.frontLeft.setTargetPosition(bsgRobot.frontLeft.getCurrentPosition() + move);
        bsgRobot.backRight.setTargetPosition(bsgRobot.backRight.getCurrentPosition() + move);
        bsgRobot.frontRight.setTargetPosition(bsgRobot.frontRight.getCurrentPosition() - move);
        //
        bsgRobot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bsgRobot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bsgRobot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bsgRobot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //
        bsgRobot.frontLeft.setPower(speed);
        bsgRobot.backLeft.setPower(speed);
        bsgRobot.frontRight.setPower(speed);
        bsgRobot.backRight.setPower(speed);
        //
        while (bsgRobot.frontLeft.isBusy() && bsgRobot.frontRight.isBusy() &&
                bsgRobot.backLeft.isBusy() && bsgRobot.backRight.isBusy()) {
        }
        bsgRobot.stopWheels();
        return;
    }

}
