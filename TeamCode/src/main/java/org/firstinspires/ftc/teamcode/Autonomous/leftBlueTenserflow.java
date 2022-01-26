package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

@Autonomous(name = "blueTenserFlow")
public class leftBlueTenserflow extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";


    Robot bsgRobot = new Robot();

    /*
    *
    VARIABLES FOR ENCODERS
    *
    */
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 537.7;    // Neverest 40
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.78;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
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

    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

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

    @Override
    public void runOpMode() throws InterruptedException {
        initVuforia();
        bsgRobot.init(hardwareMap);



        waitForStart();

    }
    /*
      *
      *
      *
      FUNCTIONS FOR ENCODERS
      *
      *
      *
      *
       */
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
        bsgRobot.frontRight.setPower(0);
        bsgRobot.frontLeft.setPower(0);
        bsgRobot.backRight.setPower(0);
        bsgRobot.backLeft.setPower(0);
        return;
    }
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


}
