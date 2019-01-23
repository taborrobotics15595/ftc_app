package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;

public class TankDrive {
    ArrayList<DcMotor> motors;
    DistanceSensor distanceSensor;

    double[] forward = {1,-1,1,-1};
    double[] turn = {1,1,1,1};

    public TankDrive(HardwareMap hardwareMap,String sensor, String ... names){
        distanceSensor = hardwareMap.get(DistanceSensor.class,sensor);
        for (int i = 0;i < names.length; i++){
            DcMotor motor = hardwareMap.get(DcMotor.class,names[i]);
            motors.add(motor);
        }
    }

    public double getDistance(DistanceUnit unit){
        return distanceSensor.getDistance(unit);
    }

    public void setMode(DcMotor.RunMode mode){
        for(DcMotor motor:motors){
            motor.setMode(mode);
        }
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        for(DcMotor motor:motors){
            motor.setZeroPowerBehavior(behavior);
        }
    }

    public void setPower(double powerX,double powerY,double max){
        double[] fwd = multiplyLists(forward,powerY);
        double[] trn = multiplyLists(turn,powerX);

        double[] toApply = addLists(fwd,trn,max);

        for(int i = 0;i<motors.size();i++){
            motors.get(i).setPower(toApply[i]);
        }
    }

    public double[] multiplyLists(double[] list, double scalar){
        double[] newList = new double[list.length];
        for(int i = 0;i<list.length;i++){
            double newValue = scalar * list[i];
            newList[i] = newValue;
        }

        return newList;
    }

    public double[] addLists(double[] list1,double[] list2,double maxValue){
        double[] newList = new double[list1.length];
        for(int i = 0;i < list1.length;i++){
            double newValue = Range.clip(list1[i] + list2[i],maxValue,maxValue);
            newList[i] = newValue;
        }
        return newList;
    }

    public int[] getCurrentPositions(){
        int[] positions = new int[motors.size()];
        for(DcMotor motor:motors){
            int p = motor.getCurrentPosition();
            positions[motors.indexOf(motor)] = p;
        }
        return positions;
    }

    public void goToPositions(int[] positions,double returnPower){
        for(int i = 0;i<motors.size();i++){
            motors.get(i).setTargetPosition(positions[i]);
        }
        this.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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
