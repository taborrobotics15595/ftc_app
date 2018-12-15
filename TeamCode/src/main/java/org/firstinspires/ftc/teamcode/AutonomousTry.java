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

    boolean hasTurned = false;
    boolean hasFound = false;


    int[] start = {580,-580,580,-580};
    int[] slideRight = {570,570,-570,-570};
    int[] slideLeft = {-600,-600,600,600};

    String pos = "center";



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
            if(!hasFound) {
                if (finder.foundGold(r)) {
                    hasFound = true;
                } else {
                    ArrayList<Float> rights = new ArrayList<>();
                    for (Recognition recognition : r) {
                        float right = recognition.getRight();
                        rights.add(right);
                    }
                    if (rights.size() == 1) {
                        if (hasTurned) {
                            moveToPositions(slideRight);
                            pos = "right";
                        } else {
                            moveToPositions(slideLeft);
                            pos = "left";
                        }
                        hasTurned = !hasTurned;
                    } else {
                        boolean left = false;
                        for (float rV : rights) {
                            if (rV < 800) {
                                left = true;
                            }
                        }
                        int[] tPos = (left) ? slideLeft : slideRight;
                        pos = (left) ? "left" : "right";
                        moveToPositions(tPos);
                    }
                }
            }
            else{
                resetEncoders();
                moveToPositions(start);

                telemetry.addData("Position",pos);
                telemetry.update();
                break;
            }




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
