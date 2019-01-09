package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

@TeleOp
public class OmniFlynn extends LinearOpMode {

    HolonomicDriveTrain robot;

    double maxPower = 1;

    WiffleLauncher launcher;

    double maxLaunch1 = 0.7;
    double maxLaunch2 = 0.9;
    double currentMax = maxLaunch1;
    boolean increasing = false;




    @Override
    public void runOpMode(){
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        launcher = new WiffleLauncher(hardwareMap,"Launch1","Launch2","Spinner");

        robot.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int[] currentPositions;
        int[] targetPositions = new int[robot.motors.size()];

        waitForStart();

        while(opModeIsActive()){
            double yPower = -Range.clip(gamepad1.left_stick_x,-maxPower,maxPower);
            double xPower = Range.clip(gamepad1.left_stick_y,-maxPower,maxPower);

            double rotate = -Range.clip(gamepad1.right_stick_x,-maxPower,maxPower);

            if (rotate != 0){
                robot.rotate(rotate);
            }
            else{
                robot.setPower(yPower,xPower);
            }

            if (gamepad1.a){
                currentMax = maxLaunch1;
                increasing = true;
            }

            if(gamepad1.x){
                currentMax = maxLaunch2;
                increasing = true;
            }

            if(gamepad1.b){
                increasing = false;
            }

            if(gamepad1.y){
                launcher.setup();
            }

            if(increasing){
                launcher.gradualChange(currentMax);
            }
            else{
                launcher.stop();
            }



            //currentPositions = robot.getCurrentPositions();
            //targetPositions = (gamepad1.a)?currentPositions:targetPositions;
            //if(gamepad1.x){
                //robot.moveToPositions(targetPositions);
            //}
            //String message = "";
            //for(int index = 0;index < robot.motors.size();index ++){
                //int current = currentPositions[index];
                //int target = targetPositions[index];
                //message += " Current: " + Integer.toString(current) + "Target: " + Integer.toString(target);
            //}
            //telemetry.addData("Status:",message);
            //telemetry.update();
        }
    }
}
