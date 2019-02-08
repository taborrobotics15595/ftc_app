package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous
public class GetPosition extends LinearOpMode {
    MineralFinder finder;
    ElapsedTime runtime;

    boolean hasTurned = false;
    boolean found = false;
    int position = 1;
    String[] positionNames = {"left","middle","right"};
    String message = "";

    @Override
    public void runOpMode(){
        finder = new MineralFinder(hardwareMap);
        runtime = new ElapsedTime();

        waitForStart();
        finder.activate();
        finder.getRecognitions();

        while (opModeIsActive()){
            if (runtime.seconds() > 1) {
                if (!found) {
                    List<Recognition> r = finder.getRecognitions();
                    message = finder.getGoldPosition(r);

                    if (message == "turn") {
                        if (!hasTurned) {
                            position = (position + 1) % 3;
                        }
                        hasTurned = true;
                    } else if (message == "other turn") {
                        if (hasTurned) {
                            position = (position + 1) % 3;
                            message = positionNames[position];
                            found = true;
                        }
                    } else if (message != "unknown") {
                        found = true;
                    }
                    telemetry.addData("Data:", " Size:" + r.size() + " Position: " + message + " RObot: " + positionNames[position]);
                }else{
                    finder.stop();
                    telemetry.addData("Data:","Mineral position: " + message + " RObot:" + positionNames[position]);

                }

                telemetry.update();
            }
        }
        finder.stop();
    }
}
