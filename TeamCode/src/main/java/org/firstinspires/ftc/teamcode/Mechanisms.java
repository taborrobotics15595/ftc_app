package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;

public class Mechanisms {
    DcMotor liftMotor,extendArmMotor,dropMineralMotor,swingArmMotor;
    Servo flipBucket;

    double flipMax = 1;
    double flipMin = 0.3;
    double flipCurrent = 0;

    double liftPower = 0.5;
    int liftMax = -5300;

    int extendMax = 11000;
    int extendMin = 0;
    double extendPower = 1;
    boolean extendExtended = false;

    double swingPower = 0.5;

    double dropPower = -0.6;
    boolean dropSpinning = false;

    ArrayList<DcMotor> busyMotors = new ArrayList<>();


    public Mechanisms(HardwareMap hardwareMap, String liftName, String extendName, String dropName, String swingArmName, String flipName){
        liftMotor = hardwareMap.get(DcMotor.class,liftName);
        extendArmMotor = hardwareMap.get(DcMotor.class,extendName);
        dropMineralMotor = hardwareMap.get(DcMotor.class,dropName);
        swingArmMotor = hardwareMap.get(DcMotor.class,swingArmName);

        flipBucket = hardwareMap.get(Servo.class,flipName);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        swingArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        swingArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        swingArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        swingArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        extendArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void flipBucket(){
        double target = (flipCurrent == flipMax)?flipMin:flipMax;
        flipCurrent = target;
        flipBucket.setPosition(target);
    }
    public void setPower(DcMotor motor,double power){
        motor.setPower(power);
    }

    public void toggleMotor(DcMotor motor,int pos,double power){
        motor.setTargetPosition(pos);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
        busyMotors.add(motor);

    }

    public void checkBusy(){
        ArrayList<DcMotor> toRemove = new ArrayList<>();
        for(DcMotor motor:busyMotors){
            if (!motor.isBusy()){
                motor.setPower(0);
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                toRemove.add(motor);
            }
        }
        for(DcMotor motor:toRemove){
            busyMotors.remove(motor);
        }
    }

   


}
