package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

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
        m1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        m2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m2.setDirection(DcMotorSimple.Direction.REVERSE);

        m3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public void setup(){
        m3.setTargetPosition(96);
        m3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(m3.isBusy()){
            m3.setPower(0.5);
        }
        m3.setPower(0);
        m3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    public boolean gradualChange(boolean increasing, boolean moving,double maxPower){
        int pos = m1.getCurrentPosition();
        int pos2 = m2.getCurrentPosition();

        int f = (increasing)?1:-1;

        boolean done1 = false;
        boolean done2 = false;

        if ((pos%interval == 0) && moving && ((0 <= currentPower1) || (currentPower1 <= maxPower))){
            currentPower1 = Range.clip(currentPower1 + increase*f,0,maxPower);
            m1.setPower(currentPower1);
        }
        else{
            done1 = true;
        }

        if ((pos2%interval == 0) && moving && ((0 <= currentPower2) || (currentPower2 <= maxPower))){
            currentPower2 = Range.clip(currentPower2 + increase*f,0,maxPower);
            m2.setPower(currentPower2);
        }
        else{
            done2 = true;
        }

        return done1&&done2;


    }

    public void setPower(double power){
        m1.setPower(power);
        m2.setPower(power);
    }


}
