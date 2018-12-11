package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MecanumDriveTrain  {

    ArrayList<DcMotor> motors = new ArrayList<DcMotor>();
    int[] right = {1,3};
    int[] left = {0,2};
    int[] front = {0,1};
    int[] back = {2,3};

    public  MecanumDriveTrain(HardwareMap hardwareMap,String ... motorNames){
        for (int i = 0;i<motorNames.length;i++){
            DcMotor currentMotor = hardwareMap.get(DcMotor.class,motorNames[i]);
            currentMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motors.add(currentMotor);
        }

        for(int index:right){
            motors.get(index).setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }

    public void runWithEncoders(){
        for(DcMotor motor:motors){
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void setPower(double power){
        for (DcMotor motor:motors){
            motor.setPower(power);
        }
    }

    public void setPower(double powerR,double powerL){
        powerTOGroup(right,powerR);
        powerTOGroup(left,powerL);
    }

    public void setPower(double ... powers){
        for(int index = 0;index < powers.length; index++){
            motors.get(index).setPower(powers[index]);
        }
    }

    public void rotate(double power){
        powerTOGroup(right,power);
        powerTOGroup(left,power);

    }

    public void slide(double power){
        powerTOGroup(front,-power);
        powerTOGroup(back,power);

    }



    private void powerTOGroup(int[] group,double power){
        for(int index:group){
            motors.get(index).setPower(power);
        }
    }

}
