package org.firstinspires.ftc.teamcode.Testing.Intake;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp (name = "servoTest")
public class servoTest extends OpMode {
    Robot bsgBot = new Robot();
    CRServo spinningFunction;
    @Override
    public void init() {
        spinningFunction = hardwareMap.crservo.get("spinningFunction");
    }

    @Override
    public void loop() {
        if(gamepad1.right_bumper) //rotating servo for hilarious grabbing mechanic xd
        {
            spinningFunction.setPower(1);
        }
        else
        {
            spinningFunction.setPower(0);
        }
    }
}
