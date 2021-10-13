//bro like half of this im referencing from tylaop lmfao
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp (name = "TotoOp")
public class TotoOp extends OpMode {
    Robot bsgBot = new Robot();
    @Override
    public void init() {
        bsgBot.init(hardwareMap);

        bsgBot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgBot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgBot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgBot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgBot.carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgBot.carousel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    @Override
    public void loop() {
        if(Math.abs(gamepad1.right_stick_y)>.1){
            bsgBot.frontRight.setPower(-gamepad1.right_stick_y);
            bsgBot.backRight.setPower(-gamepad1.right_stick_y);
        }
        else{
            bsgBot.frontRight.setPower(0);
            bsgBot.backRight.setPower(0);
        }
        if(Math.abs(gamepad1.left_stick_y)>.1){
            bsgBot.frontLeft.setPower(-gamepad1.left_stick_y);
            bsgBot.backLeft.setPower(-gamepad1.left_stick_y);
        }
        if(gamepad1.left_bumper){
            bsgBot.carousel.setPower(1);
        }
        else{
            bsgBot.carousel.setPower(0);
        }
        if(gamepad1.right_bumper){
            bsgBot.carousel2.setPower(1);
        }
        else{
            bsgBot.carousel2.setPower(0);
        }
        telemetry.addData("Front Right Value: ", bsgBot.frontRight.getPower());
        telemetry.addData("Back Right Value: ", bsgBot.backRight.getPower());
        telemetry.addData("Front Left Value: ", bsgBot.frontLeft.getPower());
        telemetry.addData("Back  Left Value: ", bsgBot.backLeft.getPower());
        telemetry.update();

        if(gamepad1.b) //rotating servo for hilarious grabbing mechanic xd
        {
            bsgBot.spinningFunction.setPower(1);
        }
        else
        {
            bsgBot.spinningFunction.setPower(0);
        }
//prone to change in the future 100% cause like robot not done yet

        //moving the pulley up/down
        if(gamepad1.dpad_up) {
            bsgBot.lift.setPower(-1);
        }
        else if(gamepad1.dpad_down){
            bsgBot.lift.setPower(1);
        }
        else{
            bsgBot.lift.setPower(0);
        }
    }
}
