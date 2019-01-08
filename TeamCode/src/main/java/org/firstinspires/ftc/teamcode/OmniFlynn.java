package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

@TeleOp
public class OmniFlynn extends LinearOpMode {

    HolonomicDriveTrain robot;

    double maxPower = 0.3;




    @Override
    public void runOpMode(){
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");

        robot.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int[] currentPositions;
        int[] targetPositions = new int[robot.motors.size()];



        waitForStart();

        while(opModeIsActive()){
            double yPower = Range.clip(gamepad1.left_stick_y,-maxPower,maxPower);
            double xPower = -Range.clip(gamepad1.left_stick_x,-maxPower,maxPower);

            double rotate = Range.clip(gamepad1.right_stick_x,-maxPower,maxPower);

            if (rotate != 0){
                robot.rotate(rotate);
            }
            else{
                robot.setPower(yPower,xPower);

            }

            currentPositions = robot.getCurrentPositions();

            targetPositions = (gamepad1.a)?currentPositions:targetPositions;

            if(gamepad1.x){
                robot.moveToPositions(targetPositions);
            }

            String message = "";
            for(int index = 0;index < robot.motors.size();index ++){
                int current = currentPositions[index];
                int target = targetPositions[index];
                message += " Current: " + Integer.toString(current) + "Target: " + Integer.toString(target);
            }
            telemetry.addData("Status:",message);
            telemetry.update();
        }
    }





}
