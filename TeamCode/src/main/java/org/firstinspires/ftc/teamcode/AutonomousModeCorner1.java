package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous
public class AutonomousModeCorner1 extends LinearOpMode {
    //MecanumDriveTrain robot = new MecanumDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");


    String pos;
    double angle;
    @Override
    public void runOpMode(){
        //robot.runWithEncoders();

        MineralFinder finder = new MineralFinder(hardwareMap);
        finder.activate();
        waitForStart();

        while (opModeIsActive()){
            List<Recognition> recognitions = finder.getRecognitions();
            pos = finder.getGoldPosition(recognitions);
            angle = finder.getGoldAngle(recognitions);

            String message = "Position: " + pos + " Angle: " + Double.toString(angle);
            telemetry.addData("Info:",message);
            telemetry.update();
        }
    }
}
