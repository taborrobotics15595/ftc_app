package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class TestTry1 extends LinearOpMode {

    @Override
    public void runOpMode(){

        ElapsedTime runtime = new ElapsedTime();

        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Time",Double.toString(runtime.seconds()));
            telemetry.update();
        }
    }
}
