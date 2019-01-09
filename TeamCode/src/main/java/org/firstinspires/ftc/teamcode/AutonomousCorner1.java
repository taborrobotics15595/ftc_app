package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous
public class AutonomousCorner1 extends LinearOpMode {

    MineralFinder finder;
    MecanumDriveTrain robot;

    int[] start = {580,-580,580,-580};
    int[] slideRight = {570,570,-570,-570};
    int[] middle = {0,0,0,0};
    int[] slideLeft = {-600,-600,600,600};
    int[] park = {200,-200,200,-200};

    int[][] positions = {slideLeft,middle,slideRight};

    double returnPower = 0.3;

    boolean hasFound = false;

    int wiggleCount = 0;

    int f = 50;

    int index = 1;

    @Override
    public void runOpMode(){
        finder = new MineralFinder(hardwareMap);
        robot = new MecanumDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");

        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        robot.goToPositions(start,returnPower);
        robot.resetEncoders();

        while(opModeIsActive()){
            List<Recognition> r = finder.getRecognitions();
            String message = "";
            if(!hasFound) {
                if (finder.foundGold(r)) {
                    hasFound = true;
                    message = "found";
                } else {
                    if (wiggleCount != 2) {
                        int[] tPos = wiggle(f - wiggleCount * 2*f);
                        robot.goToPositions(tPos,returnPower);
                        wiggleCount += 1;
                        message = "wiggle" + Integer.toString(wiggleCount);
                    }
                    else{
                        wiggleCount = 0;
                        int[] p = positions[index];
                        robot.goToPositions(p,returnPower);
                        index  = (index + 1)%positions.length;
                        message = "turning" + Integer.toString(index);

                    }
                }
            }
            else{
                robot.resetEncoders();
                robot.goToPositions(start,returnPower);
                robot.resetEncoders();
                robot.goToPositions(park,returnPower);
                break;
            }
            telemetry.addData("Stats:",message);
            telemetry.update();

        }
    }

    public int[] wiggle(int f){
        int n[] = robot.getCurrentPositions();
        for(int i = 0;i<n.length;i++){
            n[i] += (f*slideRight[i]/Math.abs(slideRight[i]));

        }
        return n;
    }
}
