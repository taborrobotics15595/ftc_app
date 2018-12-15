package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;

public class HolonomicDriveTrain {
    public ArrayList<DcMotor> motors = new ArrayList<DcMotor>();

    double[] forward = {1,-1,1,-1};
    double[] right = {1,1,-1,-1};
    double[] turn = {1,1,1,1};

    double returnPower = 0.3;

    public HolonomicDriveTrain(HardwareMap hardwareMap,String ... names){
        for(String name:names){
            DcMotor currentMotor = hardwareMap.get(DcMotor.class,name);
            motors.add(currentMotor);
        }
    }

    public void setMode(DcMotor.RunMode mode){
        for(DcMotor motor: motors){
            motor.setMode(mode);
        }
    }

    public void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior behaviour){
        for(DcMotor motor:motors){
            motor.setZeroPowerBehavior(behaviour);
        }
    }

    public void setPower(double powerY,double powerX) {
        double[] f = multiplyLists(forward, powerY);
        double[] r = multiplyLists(right, powerX);
        double[] powers = addLists(f, r);
        applyPower(powers, 1);
    }

    public void rotate(double power){
        applyPower(turn,power);
    }

    public int[] getCurrentPositions(){
        int[] positions = new int[motors.size()];
        for(DcMotor motor:motors){
            int p = motor.getCurrentPosition();
            positions[motors.indexOf(motor)] = p;
        }
        return positions;
    }

    public void moveToPositions(int[] targetPositions){
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
        }



    }

    private  void applyPower(double[] config,double power){
        for(int index = 0;index < motors.size();index++){
            double p = config[index] * power;
            motors.get(index).setPower(p);
        }
    }

    private double[] addLists(double[] ... lists){
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

    private double[] multiplyLists(double[] list, double power){
        double[] sum = new double[list.length];
        for(int i = 0;i<list.length;i++){
            double p = list[i]*power;
            sum[i] = p;
        }
        return sum;
    }
}
