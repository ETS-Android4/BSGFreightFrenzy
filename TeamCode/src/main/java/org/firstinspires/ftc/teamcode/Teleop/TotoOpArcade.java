//bro like half of this im referencing from tylaop lmfao
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp (name = "TotoOpArcade")
public class TotoOpArcade extends OpMode {
    Robot bsgRobot = new Robot();

    @Override
    public void init() {
        bsgRobot.init(hardwareMap);

        bsgRobot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void loop() {

        telemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
        telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
        telemetry.addData("Left Stick Y:", gamepad1.left_stick_y);
        telemetry.addData("Right Stick Y:", gamepad1.right_stick_y);


        telemetry.update();

        if (Math.abs(gamepad1.left_stick_y) > .1 && Math.abs(gamepad1.left_stick_y) < .3) {
            bsgRobot.frontRight.setPower(-gamepad1.left_stick_y);
            bsgRobot.backRight.setPower(-gamepad1.left_stick_y);
            bsgRobot.frontLeft.setPower(-gamepad1.left_stick_y);
            bsgRobot.backLeft.setPower(-gamepad1.left_stick_y);
        } else {
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
        }
        if (gamepad1.left_stick_x < -.1 && Math.abs(gamepad1.left_stick_y) < .3) {
            bsgRobot.frontLeft.setPower(-gamepad1.left_stick_x);
            bsgRobot.backLeft.setPower(gamepad1.left_stick_x);
            bsgRobot.frontRight.setPower(gamepad1.left_stick_x);
            bsgRobot.backRight.setPower(-gamepad1.left_stick_x);
        } else {
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
        }
        if (gamepad1.left_stick_x < .1 && Math.abs(gamepad1.left_stick_y) < .3) {
            bsgRobot.frontLeft.setPower(-gamepad1.left_stick_x);
            bsgRobot.backLeft.setPower(gamepad1.left_stick_x);
            bsgRobot.frontRight.setPower(gamepad1.left_stick_x);
            bsgRobot.backRight.setPower(-gamepad1.left_stick_x);
        } else {
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
        }

        if (gamepad1.right_stick_x > -.1) {
            bsgRobot.frontLeft.setPower(gamepad1.right_stick_x);
            bsgRobot.backLeft.setPower(gamepad1.right_stick_x);
            bsgRobot.frontRight.setPower(-gamepad1.right_stick_x);
            bsgRobot.backRight.setPower(-gamepad1.right_stick_x);
        } else {
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
        }
        if (gamepad1.right_stick_x > .1) {
            bsgRobot.frontLeft.setPower(gamepad1.right_stick_x);
            bsgRobot.backLeft.setPower(gamepad1.right_stick_x);
            bsgRobot.frontRight.setPower(-gamepad1.right_stick_x);
            bsgRobot.backRight.setPower(-gamepad1.right_stick_x);
        } else {
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backRight.setPower(0);
            bsgRobot.frontRight.setPower(0);
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

        if (gamepad1.x) {
            bsgRobot.lift.setPower(1);
        } else {
            bsgRobot.lift.setPower(0);
        }
        if (gamepad1.y){
            bsgRobot.rotatingM.setPower(1);
        }
        else{
            bsgRobot.rotatingM.setPower(0);
        }
        if(gamepad1.a){
            bsgRobot.openClamp();
        }
        else if (gamepad1.b){
            bsgRobot.closeClamp();
        }
    }
}


