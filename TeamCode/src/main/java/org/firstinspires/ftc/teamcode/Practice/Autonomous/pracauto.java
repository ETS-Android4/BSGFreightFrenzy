package org.firstinspires.ftc.teamcode.Practice.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous (name = "pracauto")
public class pracauto extends LinearOpMode {
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

        //Servo
        waitForStart();
        servoBoi.setPosition(.5);
        sleep(5000);

        //Point Turn Right
        frontLeft.setPower(0);
        frontRight.setPower(-1);
        backLeft.setPower(0);
        backRight.setPower(-1);
        sleep(2750);

        //Stop
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        sleep(1000);

        //Backwards
        frontLeft.setPower(-1);
        frontRight.setPower(1);
        backLeft.setPower(-1);
        backRight.setPower(1);
        sleep(2000);

        //Pivot Turn Left
        frontLeft.setPower(1);
        frontRight.setPower(1);
        backLeft.setPower(1);
        backRight.setPower(1);
        sleep(2000);
    }
}
