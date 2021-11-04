package org.firstinspires.ftc.teamcode.Testing.Intake;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp (name = "test")
public class test extends OpMode {
    Robot bsgBot = new Robot();

    @Override
    public void init() {
        bsgBot.init(hardwareMap);
        bsgBot.carousel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgBot.carousel2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    @Override
    public void loop() {
        if (gamepad1.right_bumper){
            bsgBot.carousel.setPower(1);
        }
        else{
            bsgBot.carousel.setPower(0);
        }
        if (gamepad1.left_bumper){
            bsgBot.carousel2.setPower(1);
        }
        else {
            bsgBot.carousel2.setPower(0);
        }

    }
}
