package org.firstinspires.ftc.teamcode.Practice.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@Autonomous (name = "Hoodini")
public class Hoodini extends LinearOpMode{
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft.setDirection(frontLeft.getDirection());

        //forward
        frontLeft.setPower(-.5);
        frontRight.setPower(.5);
        backLeft.setPower(-.5);
        backRight.setPower(.5);
        sleep(5000);

        //backward
        frontLeft.setPower(.5);
        frontRight.setPower(-.5);
        backLeft.setPower(.5);
        backRight.setPower(-.5);
        sleep(5000);

        //pivot turn left
        frontLeft.setPower(-.5);
        frontRight.setPower(0);
        backLeft.setPower(-.5);
        backRight.setPower(0);
        sleep(5000);

        //pivot turn right
        frontLeft.setPower(0);
        frontRight.setPower(.5);
        backLeft.setPower(0);
        backRight.setPower(.5);

    }
}




