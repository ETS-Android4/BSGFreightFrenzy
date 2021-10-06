package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@Autonomous (name = "test")
public class test extends LinearOpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    Servo servoBoi;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        servoBoi = hardwareMap.servo.get("servoBoi");

        //forward for 3 seconds
        waitForStart();
        frontLeft.setPower(.25);
        frontRight.setPower(.25);
        backLeft.setPower(.25);
        backRight.setPower(.25);
        sleep(5000);

        //servo set position .79
        servoBoi.setPosition(.75);
        sleep(5000);

        //stop for 2 seconds
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        sleep(5000);

        //backwards for 3 seconds
        frontLeft.setPower(-.12);
        frontRight.setPower(-.12);
        backLeft.setPower(-.12);
        backRight.setPower(-.12);
        sleep(5000);

        //

    }


}
