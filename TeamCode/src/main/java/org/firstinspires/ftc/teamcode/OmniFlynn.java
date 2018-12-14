package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

@TeleOp
public class OmniFlynn extends LinearOpMode {

    public ArrayList<DcMotor> motors = new ArrayList<DcMotor>();

    String names[] = {"Motor1","Motor2","Motor3","Motor4"};

    double[] forward = {1,-1,1,-1};
    double[] right = {1,1,-1,-1};
    double[] turn = {1,1,1,1};


    double maxPower = 0.3;

    int[] currentPositions;
    int[] targetPositions;

    double returnPower = 0.5;

    @Override
    public void runOpMode(){
        for(String name:names){
            DcMotor motor = hardwareMap.get(DcMotor.class,name);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motors.add(motor);

        }

        currentPositions = getCurrentPositions();
        targetPositions = currentPositions;

        waitForStart();
        while(opModeIsActive()){
            double yPower = Range.clip(gamepad1.left_stick_y,-maxPower,maxPower);
            double xPower = Range.clip(gamepad1.left_stick_x,-maxPower,maxPower);

            double rotate = Range.clip(gamepad1.right_stick_x,-maxPower,maxPower);

            if (rotate != 0){
                applyPower(turn,rotate);
            }
            else{
                double[] f = multiplyLists(forward,yPower);
                double[] r = multiplyLists(right,xPower);
                double[] powers = addLists(f,r);
                applyPower(powers,1);

            }

            currentPositions = getCurrentPositions();

            targetPositions = (gamepad1.a)?currentPositions:targetPositions;

            if(gamepad1.x){
                moveToPositions();
            }

            String message = "";
            for(int index = 0;index < motors.size();index ++){
                int current = currentPositions[index];
                int target = targetPositions[index];
                message += " Current: " + Integer.toString(current) + "Target: " + Integer.toString(target);
            }
            telemetry.addData("Status:",message);
            telemetry.update();
        }
    }

    public  void applyPower(double[] config,double power){
        for(int index = 0;index < motors.size();index++){
            double p = config[index] * power;
            motors.get(index).setPower(p);
        }
    }

    public double[] addLists(double[] ... lists){
        double[] sum =new double[lists[0].length];
        for(int i=0;i<lists[0].length;i++){
            double s = 0;
            for (double[] list:lists){
                s += list[i];
            }
            sum[i] = s;
        }
        return sum;
    }

    public double[] multiplyLists(double[] list, double power){
        double[] sum = new double[list.length];
        for(int i = 0;i<list.length;i++){
            double p = list[i]*power;
            sum[i] = p;
        }
        return sum;
    }



    public int[] getCurrentPositions(){
        int[] positions = new int[motors.size()];
        for(int i = 0;i<motors.size();i++){
            int p = motors.get(i).getCurrentPosition();
            positions[i] = p;
        }
        return positions;
    }

    public void moveToPositions(){
        for(int i = 0;i<motors.size();i++){
            DcMotor motor = motors.get(i);
            int targetPosition = targetPositions[i];
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setTargetPosition(targetPosition);
        }

        int motorsBusy  = 4;
        while (motorsBusy > 0) {
            motorsBusy = 0;
            for (int i = 0; i < motors.size(); i++) {
                DcMotor motor = motors.get(i);
                boolean busy = motor.isBusy();
                if (busy) {
                    motor.setPower(returnPower);
                }
                else{
                    motor.setPower(0);
                    motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                motorsBusy = (busy)?(motorsBusy + 1):motorsBusy;
            }
            telemetry.addData("Status:","in loop");
            telemetry.update();

        }



    }




}
