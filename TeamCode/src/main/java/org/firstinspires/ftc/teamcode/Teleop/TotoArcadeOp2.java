package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp @Disabled
public class TotoArcadeOp2 extends LinearOpMode {
    private Robot bsgBot = new Robot();
    double  leftPower, rightPower, xValue, yValue;


    @Override
    public void runOpMode() throws InterruptedException {
        bsgBot.init(hardwareMap);
        {

            bsgBot.frontLeft.setDirection(DcMotor.Direction.REVERSE);
            bsgBot.backLeft.setDirection(DcMotor.Direction.REVERSE);


            telemetry.addData("Mode", "waiting");
            telemetry.update();

            // wait for start button.

            waitForStart();

            while (opModeIsActive()) {
                if (Math.abs(gamepad1.left_stick_y)> 1 || Math.abs(gamepad1.left_stick_x)> .1)
                yValue = gamepad1.left_stick_y ;
                xValue = gamepad1.left_stick_x ;

                leftPower = yValue - xValue;
                rightPower = yValue + xValue;

                bsgBot.frontLeft.setPower(Range.clip(leftPower, -1.0, 1.0));
                bsgBot.backLeft.setPower(Range.clip(leftPower,-1.0,1.0));
                bsgBot.frontRight.setPower(Range.clip(rightPower, 1.0, -1.0));
                bsgBot.backRight.setPower(Range.clip(rightPower,1.0,-1.0));

                

                telemetry.addData("Mode", "running");
                telemetry.addData("stick", "  y=" + yValue + "  x=" + xValue);
                telemetry.addData("power", "  left=" + leftPower + "  right=" + rightPower);
                telemetry.update();

                idle();
            }
        }
       /* if (gamepad1.a)
            bsgBot.clamp.setPower(1);
        else if (gamepad1.b) {
            bsgBot.clamp.setPosition(-1);
        } else {
            bsgBot.clamp.setPosition(0);
        }*/
        //moving the pulley up/down
        if (gamepad1.dpad_up) {
            bsgBot.lift.setPower(1);
        } else if (gamepad1.dpad_down) {
            bsgBot.lift.setPower(-1);
        } else {
            bsgBot.lift.setPower(0);
        }
    }
}
