package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class WiffleLauncher {
    public DcMotor m1,m2,m3;

    int interval = 100;
    double increase = 0.05;
    double currentPower1 = 0;
    double currentPower2 = 0;

    public WiffleLauncher(HardwareMap hardwareMap,String name1,String name2,String name3){
        m1 = hardwareMap.get(DcMotor.class,name1);
        m2 = hardwareMap.get(DcMotor.class,name2);
        m3 = hardwareMap.get(DcMotor.class,name3);

        m1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        m2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m2.setDirection(DcMotorSimple.Direction.REVERSE);

        m3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void launch(double power){
        m1.setPower(power);
        m2.setPower(power);
    }

    public void setup(){
        m3.setTargetPosition(120);
        m3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(m3.isBusy()){
            m3.setPower(0.1);
        }
        m3.setPower(0);
        m3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void gradualChange(double maxPower){
        int pos = m1.getCurrentPosition();
        int pos2 = m2.getCurrentPosition();

        if ((pos%interval == 0) && (currentPower1 < maxPower)){
            currentPower1 += increase;
            m1.setPower(currentPower1);
        }

        if((pos2%interval == 0) &&(currentPower2 < maxPower)){
            currentPower2 += increase;
            m2.setPower(currentPower2);
        }
    }

    public void stop(){
        m1.setPower(0);
        m2.setPower(0);
    }
}
