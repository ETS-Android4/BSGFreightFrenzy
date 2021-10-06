package org.firstinspires.ftc.teamcode.Practice.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class Challenge12021 extends LinearOpMode {
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    Servo servoXd;
    @Override
    public void runOpMode() throws InterruptedException {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        servoXd = hardwareMap.servo.get("servoXd");

        //Set servo to 0.75
        waitForStart();
        servoXd.setPosition(0.75);
        sleep(5000);

        //Forward
        frontRight.setPower(-0.25);
        frontLeft.setPower(0.25);
        backRight.setPower(-0.25);
        backLeft.setPower(0.25);
        sleep(3000);

        //Stop for 2 seconds
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        sleep(2000);

        //Backwards
        frontRight.setPower(0.5);
        frontLeft.setPower(-0.5);
        backRight.setPower(0.5);
        backLeft.setPower(-0.5);
        sleep(3000);

        //Pivot Turn Left
        frontRight.setPower(0.5);
        frontLeft.setPower(0.5);
        backRight.setPower(0.5);
        backLeft.setPower(0.5);
        sleep(2500);

        //Stop for 1 second
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        sleep(1000);

        //Pivot Turn Right
        frontRight.setPower(-0.5);
        frontLeft.setPower(-0.5);
        backRight.setPower(-0.5);
        backLeft.setPower(-0.5);
        sleep(4000);

        //Point Turn Right
        frontRight.setPower(-0.5);
        frontLeft.setPower(0);
        backRight.setPower(-0.5);
        backLeft.setPower(0);

        //Point Turn Left
        frontRight.setPower(0);
        frontLeft.setPower(0.5);
        backRight.setPower(0);
        backLeft.setPower(0.5);


    }
}
