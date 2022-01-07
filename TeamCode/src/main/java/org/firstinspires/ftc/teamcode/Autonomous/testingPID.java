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

    ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        bsgRobot.init(hardwareMap);

        waitForStart();

        time.reset();
        pidMotion = new PIDMotion(0.007,0.000035,0.0007,20);

        while (time.milliseconds() < 3000) {
            bsgRobot.motion.setPower(pidMotion.output(150, bsgRobot.motion.getCurrentPosition()));
        }

        while (time.milliseconds() < 6000) {
                bsgRobot.motion.setPower(pidMotion.output(0, bsgRobot.motion.getCurrentPosition()));
        }



    }
}
