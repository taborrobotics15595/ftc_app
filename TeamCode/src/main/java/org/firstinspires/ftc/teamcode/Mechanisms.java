package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Mechanisms {
    public DcMotor liftArmMotor,grabberArmMotor;
    public Servo bucket;
    public CRServo grabber;

    public int liftMaxPosition = 180;
    public int liftMinPosition = 0;
    public double liftPower = 0.5;

    public int grabberMaxPosition = 300;
    public int grabberMinPosition = 0;
    public double grabberPower = 0.5;

    public double bucketMaxPos = 1;
    public double bucketMinPos = 0;
    public double currentPos = bucketMinPos;
    public double interval = 0.1;

    public double spinningPower = 1;



    public Mechanisms(HardwareMap hardwareMap,String liftName,String grabberName,String bucketServoName,String grabberServoName){
        liftArmMotor = hardwareMap.get(DcMotor.class,liftName);
        grabberArmMotor = hardwareMap.get(DcMotor.class,grabberName);
        bucket = hardwareMap.get(Servo.class,bucketServoName);
        grabber = hardwareMap.get(CRServo.class,grabberServoName);

        liftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        grabberArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabberArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void lift(DcMotor m,int maxPos,double power){
        m.setTargetPosition(maxPos);
        m.setPower(power);
    }

    public void drop(DcMotor m,int maxPos,double power){
        m.setTargetPosition(maxPos);
        m.setPower(-power);
    }

    public void stopLift(DcMotor m){
        m.setPower(0);
    }

    public boolean isLifting(DcMotor m){
        return m.isBusy();
    }

    public void openBucket(){
        for(double pos=currentPos;pos <= bucketMaxPos;pos+=interval){
            bucket.setPosition(pos);
        }
        currentPos = bucketMaxPos;
    }

    public void closeBucket(){
        for(double pos=currentPos;pos >= bucketMinPos;pos-=interval){
            bucket.setPosition(pos);
        }
        currentPos = bucketMinPos;
    }

    public void startSpinning(){
        grabber.setPower(spinningPower);
    }

    public void stopSpinning(){
        grabber.setPower(0);
    }

}
