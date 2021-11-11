//bro like half of this im referencing from tylaop lmfao
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp (name = "TotoOp")
public class TotoOp extends OpMode {
    Robot bsgRobot = new Robot();

    @Override
    public void init() {
        bsgRobot.init(hardwareMap);

        bsgRobot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.carousel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    @Override
    public void loop() {
        if (Math.abs(gamepad1.right_stick_y) > .1) {
            bsgRobot.frontRight.setPower(-gamepad1.right_stick_y);
            bsgRobot.backRight.setPower(-gamepad1.right_stick_y);
        } else {
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
        }
        if (Math.abs(gamepad1.left_stick_y) > .1) {
            bsgRobot.frontLeft.setPower(-gamepad1.left_stick_y);
            bsgRobot.backLeft.setPower(-gamepad1.left_stick_y);
        }
        else {
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
        }
        if (gamepad1.left_bumper) {
            bsgRobot.carousel.setPower(-0.5);
        } else {
            bsgRobot.carousel.setPower(0);
        }
        if (gamepad1.right_bumper) {
            bsgRobot.carousel2.setPower(-0.5);
        } else {
            bsgRobot.carousel2.setPower(0);
        }
        telemetry.addData("Front Right Value: ", bsgRobot.frontRight.getPower());
        telemetry.addData("Back Right Value: ", bsgRobot.backRight.getPower());
        telemetry.addData("Front Left Value: ", bsgRobot.frontLeft.getPower());
        telemetry.addData("Back  Left Value: ", bsgRobot.backLeft.getPower());
        telemetry.update();

        if(gamepad1.b) //rotating servo for hilarious grabbing mechanic xd
        {
            bsgRobot.spinningFunction.setPower(1);
        }
        if(gamepad1.a)
        {
            bsgRobot.spinningFunction.setPower(-0.5);
        }
        else
        {
            bsgRobot.spinningFunction.setPower(0);
        }
//prone to change in the future 100% cause like robot not done yet

        //moving the pulley up/down
        if(gamepad1.dpad_up) {
            bsgRobot.lift.setPower(1);
        }
        else if(gamepad1.dpad_down){
            bsgRobot.lift.setPower(-1);
        }
        else{
            bsgRobot.lift.setPower(0);
        }

    }
    }
