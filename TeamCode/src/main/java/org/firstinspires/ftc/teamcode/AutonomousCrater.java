package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous
public class AutonomousCrater extends OpMode {
    HolonomicDriveTrain robot;
    Mechanisms mechs;
    MineralFinder finder;

    ElapsedTime runtime;

    double power = 0.4;

    boolean hasTurned = false;
    boolean found = false;
    int position = 1;
    String[] positionNames = {"left","middle","right"};
    String message = "";

    int[] rotate = {60,60,60,60};
    int[] returnRotate = {-120,-120,-120,-120};
    int[] forward = {500,500,-500,-500};

    @Override
    public void init(){
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        mechs = new Mechanisms(hardwareMap,"Lift_Motor","Extend_Motor","Drop_Motor","Swing_Motor","Flip_Servo");
        finder = new MineralFinder(hardwareMap);
        runtime = new ElapsedTime();

        mechs.liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void init_loop(){

    }

    @Override
    public void start(){

        finder.activate();
        finder.getRecognitions();

        runtime.reset();
    }

    @Override
    public void loop(){


        if (runtime.seconds() > 1) {
            if (!found) {
                List<Recognition> r = finder.getRecognitions();
                message = finder.getGoldPosition(r);

                if (message == "turn") {
                    if (!hasTurned) {
                        position = (position + 1) % 3;
                        robot.goToPositions(rotate,0.3);
                    }
                    hasTurned = true;
                } else if (message == "other turn") {
                    if (hasTurned) {
                        position = (position + 1) % 3;
                        message = positionNames[position];
                        robot.goToPositions(returnRotate,0.3);
                        //found = true;
                    }
                } else if (message != "unknown") {
                    found = true;
                }
                telemetry.addData("Data:", " Size:" + r.size() + " Position: " + message + " Robot: " + positionNames[position]);
                telemetry.update();
            } else {
                finder.stop();
                telemetry.addData("Data:", "Mineral position: " + message + " Robot:" + positionNames[position]);
                telemetry.update();
                robot.goToPositions(forward,0.3);

            }

            telemetry.update();
        }
    }

    @Override
    public void stop(){
        finder.stop();
    }
}
