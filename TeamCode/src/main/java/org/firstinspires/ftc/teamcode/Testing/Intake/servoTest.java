package org.firstinspires.ftc.teamcode.Testing.Intake;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp (name = "servoTest")
public class servoTest extends OpMode {
    Robot bsgBot = new Robot();
    @Override
    public void init() {


    }

    @Override
    public void loop() {
        if(gamepad1.right_bumper) //rotating servo for hilarious grabbing mechanic xd
        {
            bsgBot.spinningFunction.setPosition(1);
        }
        else
        {
            bsgBot.spinningFunction.setPosition(0);
        }
    }
}
