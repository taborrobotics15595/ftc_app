package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Joystick_Output extends LinearOpMode {

    @Override
    public void runOpMode(){
        waitForStart();
        while (opModeIsActive()){
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            String messge = "X output " + Double.toString(x) + "Y output " + Double.toString(y);
            telemetry.addData("Info:",messge);
            telemetry.update();



        }

    }

}