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

        telemetry.addData("Move forward", "L/R sticks");
        telemetry.update();

        bsgRobot.dtSpeed = 0.8;


    }

    @Override
    public void loop() {
        if (Math.abs(-gamepad1.left_stick_y)>.1){
            bsgRobot.drive(-gamepad1.left_stick_y);
        }
        else if ((Math.abs(gamepad1.right_stick_x) > .1) || Math.abs(gamepad1.right_stick_x)>-.1){
            bsgRobot.drive(gamepad1.right_stick_x, -gamepad1.right_stick_x, gamepad1.right_stick_x, -gamepad1.right_stick_x);
        }
        else if(Math.abs(gamepad1.left_stick_x) > .1 || Math.abs(gamepad1.left_stick_x)> .1){
            bsgRobot.drive(-gamepad1.left_stick_x, gamepad1.left_stick_x, gamepad1.left_stick_x, -gamepad1.left_stick_x);
        }
        else{
           bsgRobot.stopWheels();
        }
        if (gamepad1.dpad_left) {
            bsgRobot.carousel.setPower(-0.65);
        }
        if(gamepad1.dpad_right){
            bsgRobot.carousel.setPower(0.65);
        }
        else {
            bsgRobot.carousel.setPower(0);
        }
        telemetry.addData("Front Right Value: ", bsgRobot.frontRight.getPower());
        telemetry.addData("Back Right Value: ", bsgRobot.backRight.getPower());
        telemetry.addData("Front Left Value: ", bsgRobot.frontLeft.getPower());
        telemetry.addData("Back  Left Value: ", bsgRobot.backLeft.getPower());
        telemetry.update();

//prone to change in the future 100% cause like robot not done yet
        //opening and closing clamp
        if (gamepad2.right_bumper)
        bsgRobot.clamp.setPower(1);
        else {
            bsgRobot.clamp.setPower(0);
        }
        //moving the pulley up/down
        if (gamepad2.dpad_up) {
            bsgRobot.lift.setPower(1);
        } else if (gamepad2.dpad_down) {
            bsgRobot.lift.setPower(-1);
        }
        else{
            bsgRobot.lift.setPower(0);
        }
        //moving claw up and down
        if (gamepad2.x){
            bsgRobot.motion.setPower(.5);
        }
        else if (gamepad2.a){
            bsgRobot.motion.setPower(-.2);
        }
        else{
            bsgRobot.motion.setPower(0);
        }
    }
}
