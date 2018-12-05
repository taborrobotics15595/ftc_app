package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;

public class TankDrive {
    ArrayList<DcMotor> motors = new ArrayList<DcMotor>();

    int[] all = {0,1,2,3};
    int[] left = {0,2};
    int[] right = {1,3};



    public TankDrive(HardwareMap hardwareMap,String ... names){
        for(int index = 0;index < names.length;index++){
            DcMotor motor = hardwareMap.get(DcMotor.class,names[index]);
            motors.add(motor);
        }
    }

    public void setPower(double power){
        actionToGroup(all,power);
    }

    public void setPower(double powerR,double powerL){
        actionToGroup(right,powerR);
        actionToGroup(left,powerL);
    }



    private void actionToGroup(int[] group,double power){
        for(int index:group){
            motors.get(index).setPower(power);
        }
    }
}
