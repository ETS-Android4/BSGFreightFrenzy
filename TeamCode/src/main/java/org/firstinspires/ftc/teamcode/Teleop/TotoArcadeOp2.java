package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TotoArcadeOp2 extends LinearOpMode {
    DcMotor leftMotor, rightMotor, lift;
    Servo clamp;
    float   leftPower, rightPower, xValue, yValue;

    @Override
    public void runOpMode() throws InterruptedException {
        {
            leftMotor = hardwareMap.dcMotor.get("left_motor");
            rightMotor = hardwareMap.dcMotor.get("right_motor");

            leftMotor.setDirection(DcMotor.Direction.REVERSE);

            telemetry.addData("Mode", "waiting");
            telemetry.update();

            // wait for start button.

            waitForStart();

            while (opModeIsActive()) {
                yValue = gamepad1.right_stick_y * -1;
                xValue = gamepad1.right_stick_x * -1;

                leftPower = yValue - xValue;
                rightPower = yValue + xValue;

                leftMotor.setPower(Range.clip(leftPower, -1.0, 1.0));
                rightMotor.setPower(Range.clip(rightPower, -1.0, 1.0));

                telemetry.addData("Mode", "running");
                telemetry.addData("stick", "  y=" + yValue + "  x=" + xValue);
                telemetry.addData("power", "  left=" + leftPower + "  right=" + rightPower);
                telemetry.update();

                idle();
            }
        }
        if (gamepad1.a)
            clamp.setPosition(50);
        else if (gamepad1.b) {
            clamp.setPosition(-50);
        } else {
            clamp.setPosition(0);
        }
        //moving the pulley up/down
        if (gamepad1.dpad_up) {
            lift.setPower(1);
        } else if (gamepad1.dpad_down) {
            lift.setPower(-1);
        } else {
            lift.setPower(0);
        }
    }
}
