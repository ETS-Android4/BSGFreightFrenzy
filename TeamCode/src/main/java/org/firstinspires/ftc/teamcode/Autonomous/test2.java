package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Disabled
@Autonomous (name = "test2")
public class test2 extends LinearOpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        //rotate right for 2.5 seconds
        waitForStart();
        frontLeft.setPower(.1);
        frontRight.setPower(-.1);
        backLeft.setPower(.1);
        backRight.setPower(-.1);
        sleep(5000);

        //stop for 1 second
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        sleep(5000);

        //rotate left for 4 seconds
        frontLeft.setPower(-.1);
        frontRight.setPower(.1);
        backLeft.setPower(-.1);
        backRight.setPower(.1);
        sleep(5000);

    }
}
