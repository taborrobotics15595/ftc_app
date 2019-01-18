package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Mechanisms {
    public static DcMotor liftArmMotor,grabberArmMotor,turnMotor;
    public Servo bucket;
    public CRServo grabber;

    public static  int liftMaxPosition = 500;
    public static int liftMinPosition = 0;
    public static double liftPower = 0.5;
    public static boolean liftExtended = false;

    public static int grabberMaxPosition = 1500;
    public static int grabberMinPosition = 0;
    public static double grabberPower = 0.5;
    public static boolean grabberExtended = false;

    public static int turnMotorMax = 100;
    public static int turnMotorMin = 0;
    public static double turnPower = 0.5;
    public static boolean turnExtended = false;

    public double bucketMaxPos = 1;
    public double bucketMinPos = 0.5;
    public double currentPos = bucketMaxPos;
    public double interval = 0.1;

    public double spinningPower = 1;
    public boolean isSpinning = false;

    public boolean bucketOpened = false;


    public Mechanisms(HardwareMap hardwareMap,String liftName){
        liftArmMotor = hardwareMap.get(DcMotor.class,liftName);

        liftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public Mechanisms(HardwareMap hardwareMap,String liftName,String grabberName,String turnName,String bucketServoName,String grabberServoName){
        liftArmMotor = hardwareMap.get(DcMotor.class,liftName);
        grabberArmMotor = hardwareMap.get(DcMotor.class,grabberName);
        turnMotor = hardwareMap.get(DcMotor.class,turnName);
        bucket = hardwareMap.get(Servo.class,bucketServoName);
        grabber = hardwareMap.get(CRServo.class,grabberServoName);

        liftArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        turnMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turnMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turnMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        grabberArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabberArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        grabberArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabberArmMotor.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public void lift(DcMotor m, int maxPos, double power){
        m.setTargetPosition(maxPos);
        m.setPower(power);
        m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void drop(DcMotor m,int minPos, double power){
        m.setTargetPosition(minPos);
        m.setPower(power);
        m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        isSpinning = true;
    }

    public void stopSpinning(){
        isSpinning = false;
        grabber.setPower(0);
    }

    public void moveMotor(boolean buttonPressed, Mechanisms.MotorConstants motorData){
        DcMotor motor = motorData.motor;
        int maxPos = motorData.max;
        int minPos = motorData.min;
        double power = motorData.power;
        boolean extended = motorData.extended;

        if (buttonPressed){
            if(extended) {
                this.drop(motor,minPos,power);
            }else{
                this.lift(motor,maxPos,power);
            }
            extended = !extended;
        }

        if (!this.isLifting(motor)){
            this.stopLift(motor);
        }

        motorData.extended = extended;

    }

    enum MotorConstants{
        LIFT(Mechanisms.liftArmMotor,Mechanisms.liftMaxPosition,Mechanisms.liftMinPosition,Mechanisms.liftPower,Mechanisms.liftExtended),
        GRAB(Mechanisms.grabberArmMotor,Mechanisms.grabberMaxPosition,Mechanisms.grabberMinPosition,Mechanisms.grabberPower,Mechanisms.grabberExtended),
        TURN(Mechanisms.turnMotor,Mechanisms.turnMotorMax,Mechanisms.turnMotorMin,Mechanisms.turnPower,Mechanisms.turnExtended);

        public DcMotor motor;
        public int max;
        public int min;
        public double power;
        public boolean extended;
        MotorConstants(DcMotor motor, int max, int min,double power,boolean extended){
            this.motor = motor;
            this.max = max;
            this.min = min;
            this.power = power;
            this.extended = extended;
        }
    }



}
