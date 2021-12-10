//bro like half of this im referencing from tylaop lmfao
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotserver.internal.webserver.RobotControllerWebHandlers;
import org.firstinspires.ftc.teamcode.Hardware.Robot;


@TeleOp (name = "TotoOpArcade")
public class TotoOpArcade extends OpMode {
    Robot bsgRobot = new Robot();

    double xValue, yValue, leftPower, rightPower;

    @Override
    public void init() {
        bsgRobot.init(hardwareMap);;

        bsgRobot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bsgRobot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bsgRobot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bsgRobot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {

        telemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
        telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
        telemetry.addData("Left Stick Y:", gamepad1.left_stick_y);
        telemetry.addData("Right Stick Y:", gamepad1.right_stick_y);


        telemetry.update();

        if (Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.left_stick_x) < .1) {
           yValue = gamepad1.left_stick_y;
           xValue = gamepad1.left_stick_x;
           leftPower = yValue - xValue;
           rightPower = yValue + xValue;
           bsgRobot.frontLeft(Range.clip(leftPower, -1.0, 1.0));
           bsgRobot.backLeft(Range.clip(leftPower, -1.0,1.0));
           bsgRobot.frontRight(Range.clip(rightPower, -1.0, 1.0));
           bsgRobot.backRight(Range.clip(rightPower,-1.0,1.0));
        } if (gamepad1.dpad_left){
            bsgRobot.strafeLeft(1);
        }
        else if (gamepad1.dpad_right){
            bsgRobot.strafeRight(1);
        }
        else{
            bsgRobot.stopWheels();
        }
        if (gamepad1.left_bumper) {
            bsgRobot.carousel.setPower(-0.5);
        } else {
            bsgRobot.carousel.setPower(0);
        }
        if (gamepad1.right_bumper) {
            bsgRobot.carousel.setPower(0.5);
        } else {
            bsgRobot.carousel.setPower(0);
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
        if(gamepad1.a){
            bsgRobot.openClamp();
        }
        else if (gamepad1.b) {
            bsgRobot.closeClamp();
        }
    }
}


