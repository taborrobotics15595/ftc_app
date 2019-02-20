package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.ArrayList;
import java.util.List;


@Autonomous
public class AutonomousCrater extends LinearOpMode {
    HolonomicDriveTrain robot;
    MineralFinder finder;

    ElapsedTime runtime;

    double power = 0.4;
    boolean active = true;
    boolean hasTurned = false;
    boolean found = false;
    int position = 1;
    String[] positionNames = {"left", "middle", "right"};
    String message = "";

    int[] rotate = {220, 220, 220, 220};
    int[] returnRotate = {-440, -440, -440, -440};
    int[] forward = {1200, -1200, 1200, -1200};
    int[] back = {-400,400,-400,400};

    @Override
    public void runOpMode() {
        robot = new HolonomicDriveTrain(hardwareMap, "Motor1", "Motor2", "Motor3", "Motor4");
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        finder = new MineralFinder(hardwareMap);
        runtime = new ElapsedTime();

        waitForStart();

        finder.activate();
        finder.getRecognitions();

        runtime.reset();

        sleep(500);
        while (opModeIsActive() && !found) {
            List<Recognition> r = finder.getRecognitions();
            message = finder.getGoldPosition(r);

            if (message == "turn") {
                if (!hasTurned) {
                    position = (position + 1) % 3;
                    robot.goToPositions(rotate, 0.3);
                }
                else{
                    position = (position + 1) % 3;
                    message = positionNames[position];
                    robot.goToPositions(returnRotate, 0.3);
                    found = true;
                }
                hasTurned = true;
            } else if (message == "other turn") {
                if (hasTurned) {
                    position = (position + 1) % 3;
                    message = positionNames[position];
                    robot.goToPositions(returnRotate, 0.3);
                    found = true;
                } else {
                    position = (position + 1) % 3;
                    robot.goToPositions(rotate, 0.3);
                    hasTurned = true;
                }
            } else if (message != "unknown") {
                found = true;
            }
            telemetry.addData("Data:", " Size:" + r.size() + " Position: " + message + " Robot: " + positionNames[position]);
            telemetry.update();
            sleep(250);

        }
        finder.stop();
        robot.goToPositions(forward, 0.3);
        robot.goToPositions(back,0.3);

    }

}
