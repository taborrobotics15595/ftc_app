package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;


public class MecanumDriveTrain {

    ArrayList<DcMotor> motors = new ArrayList<DcMotor>();

    double[] forward = {1,-1,1,-1};
    double[] right = {-1,-1,1,1};

    double[] turn = {1,1,1,1};



    public MecanumDriveTrain(HardwareMap hardwareMap, String ... motorNames){
        for (int i = 0;i<motorNames.length;i++){
            DcMotor currentMotor = hardwareMap.get(DcMotor.class,motorNames[i]);
            currentMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

    public void turn(double power){
        double[] outputPower = multiplyLists(turn,power);
        applyPower(outputPower);
    }
    public void setPower(double powerY,double powerX){
        double[] powersForward = multiplyLists(forward,powerY);
        double[] powersRight = multiplyLists(right,powerX);
        double[] outputPower = addLists(powersForward,powersRight);
        applyPower(outputPower);

    }

    private void applyPower(double[] power){
        for(int index = 0;index<motors.size();index++){
            motors.get(index).setPower(power[index]);
        }
    }

    private double[] addLists(double[] ... lists){
        double[] sum =new double[lists[0].length];
        for(int i=0;i<lists[0].length;i++){
            double s = 0;
            for (double[] list:lists){
                s -= list[i];
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
