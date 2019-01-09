package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Autonomous
public class AutonomousTry extends LinearOpMode {

    MineralFinder finder;

    public ArrayList<DcMotor> motors = new ArrayList<DcMotor>();

    String names[] = {"Motor1","Motor2","Motor3","Motor4"};


    double returnPower = 0.3;

    int wiggle = 0;
    boolean hasFound = false;


    int[] start = {580,-580,580,-580};
    int[] slideRight = {570,570,-570,-570};
    int[] middle = {0,0,0,0};
    int[] slideLeft = {-600,-600,600,600};
    int[] park = {200,-200,200,-200};

    int[][] positions = {slideLeft,middle,slideRight};

    int f = 50;

    int index = 1;


    @Override
    public void runOpMode(){

        for(String name:names){
            DcMotor motor = hardwareMap.get(DcMotor.class,name);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motors.add(motor);

        }

        finder = new MineralFinder(hardwareMap);
        finder.activate();

        waitForStart();

        moveToPositions(start);
        resetEncoders();

        while(opModeIsActive()){
            List<Recognition> r = finder.getRecognitions();
            String message = "";
            if(!hasFound) {
                if (finder.foundGold(r)) {
                    hasFound = true;
                    message = "found";
                } else {
                    if (wiggle != 2) {
                        int[] tPos = wiggle(f - wiggle * 2*f);
                        moveToPositions(tPos);
                        wiggle += 1;
                        message = "wiggle" + Integer.toString(wiggle);
                    }
                    else{
                        wiggle = 0;
                        int[] p = positions[index];
                        moveToPositions(p);
                        index  = (index + 1)%positions.length;
                        message = "turning" + Integer.toString(index);

                    }
                }
            }
            else{
                resetEncoders();
                moveToPositions(start);
                resetEncoders();
                moveToPositions(park);
                break;
            }
            telemetry.addData("Stats:",message);
            telemetry.update();

        }

    }

    public void resetEncoders(){
        for(DcMotor motor:motors){
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public  void applyPower(double[] config,double power){
        for(int index = 0;index < motors.size();index++){
            double p = config[index] * power;
            motors.get(index).setPower(p);
        }
    }

    public int[] wiggle(int f){
        int[] n = getCurrentPositions();
        for(int i = 0;i<n.length;i++){
            n[i] += (f*slideRight[i]/Math.abs(slideRight[i]));

        }
        return n;
    }

    public int[] getCurrentPositions(){
        int n[]= new int[motors.size()];
        for(int i = 0;i<motors.size();i++){
            DcMotor m = motors.get(i);
            n[i] = m.getCurrentPosition();
        }
        return n;
    }

    public void moveToPositions(int[] target) {
        for (int i = 0; i < motors.size(); i++) {
            DcMotor motor = motors.get(i);
            int targetPosition = target[i];
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setTargetPosition(targetPosition);
        }

        int motorsBusy = 4;
        while (motorsBusy > 0) {
            motorsBusy = 0;
            for (int i = 0; i < motors.size(); i++) {
                DcMotor motor = motors.get(i);
                boolean busy = motor.isBusy();
                if (busy) {
                    motor.setPower(returnPower);
                } else {
                    motor.setPower(0);
                    motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                motorsBusy = (busy) ? (motorsBusy + 1) : motorsBusy;
            }


        }
    }
}
