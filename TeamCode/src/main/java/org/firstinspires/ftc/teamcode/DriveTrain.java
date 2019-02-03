package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

public class DriveTrain {
    ArrayList<DcMotor> motors = new ArrayList<>();

    double[] forward;
    double[] sideways;
    double[] turn;

    public DriveTrain(HardwareMap hardwareMap,String ... names){
        for(String name:names){
            DcMotor motor = hardwareMap.get(DcMotor.class,name);
            this.motors.add(motor);
        }

    }

    public void setPower(double powerY,double powerX,double max){
        double[] yComp = multiplyLists(forward,powerY);
        double[] xComp = multiplyLists(sideways,powerX);
        double[] total = addLists(yComp,xComp,max);
        applyPower(total);
    }

    public void turn(double power){
        double[] confic = multiplyLists(turn,power);
        applyPower(confic);
    }

    public void applyPower(double [] power){
        for(int i = 0;i<motors.size();i++){
            motors.get(i).setPower(power[i]);
        }
    }

    public double[] multiplyLists(double[] list,double scalar){
        double[] newList = new double[list.length];
        for(int i = 0;i<list.length;i++){
            double value = list[i]*scalar;
            newList[i] = value;
        }
        return newList;
    }

    public double[] addLists(double[] list1,double[] list2,double max){
        double[] newList = new double[list1.length];
        for(int i = 0;i<list1.length;i++){
            double value = Range.clip(list1[i] + list2[i],-max,max);
            newList[i] = value;
        }
        return newList;
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


}
