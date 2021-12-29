package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.PIDMotion;
import org.firstinspires.ftc.teamcode.Hardware.Robot;

@Autonomous (name = "PIDTesting")
public class testingPID extends LinearOpMode {
    Robot bsgRobot = new Robot();
    PIDMotion pidMotion;

    ElapsedTime time;

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        time.reset();
        pidMotion = new PIDMotion(0.07,0.00035,0.007,20);

        while (time.milliseconds() < 3000) {
            bsgRobot.motion.setPower(pidMotion.output(300, bsgRobot.motion.getCurrentPosition()));
        }

        while (time.milliseconds() < 6000) {
                bsgRobot.motion.setPower(pidMotion.output(0, bsgRobot.motion.getCurrentPosition()));
        }



    }
}
