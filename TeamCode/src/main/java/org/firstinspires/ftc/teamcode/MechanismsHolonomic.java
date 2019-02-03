package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class MechanismsHolonomic {
    static DcMotor liftMotor,extendArmMotor,dropMineralMotor,swingArmMotor;
    Servo flipBucket;

    double flipMax = 1;
    double flipMin = 0;
    double flipCurrent = 0;

    static int liftMax = 5000;
    static int liftMin = 0;
    static double liftPower = 1;
    static boolean liftExtended = false;

    static int extendMax = -3000;
    static int extendMin = 0;
    static double extendPower = 1;
    static boolean extendExtended = false;

    static int swingMax = -2257;
    static int swingMin = 0;
    static double swingPower = 0.5;
    static boolean swingExtended = false;

    double dropPower = -0.5;
    boolean dropSpinning = false;


    public MechanismsHolonomic(HardwareMap hardwareMap,String liftName,String extendName,String dropName,String swingArmName,String flipName){
        liftMotor = hardwareMap.get(DcMotor.class,liftName);
        extendArmMotor = hardwareMap.get(DcMotor.class,extendName);
        dropMineralMotor = hardwareMap.get(DcMotor.class,dropName);
        swingArmMotor = hardwareMap.get(DcMotor.class,swingArmName);

        flipBucket = hardwareMap.get(Servo.class,flipName);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        swingArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        swingArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        swingArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        extendArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }

    public void setMotorZeroPowerBehaviour(DcMotor motor,DcMotor.ZeroPowerBehavior behaviour){
        motor.setZeroPowerBehavior(behaviour);
    }

    public void goToPosition(MechanismsHolonomic.MotorConstants motorData){
        DcMotor motor = motorData.motor;
        int maxPos = motorData.max;
        int minPos = motorData.min;
        double power = motorData.power;
        boolean extended = motorData.extended;

        int pos = (extended)?minPos:maxPos;
        motor.setTargetPosition(pos);
        motor.setPower(power);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extended = !extended;

        motorData.extended = extended;
    }

    public void setPosition(DcMotor m, int pos, double power){
        m.setTargetPosition(pos);
        m.setPower(power);
        m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void stopLift(DcMotor m){
        m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        m.setPower(0);
    }

    public boolean isLifting(DcMotor m){
        return m.isBusy();
    }


    public void moveMotor(boolean buttonPressed, MechanismsHolonomic.MotorConstants motorData){
        DcMotor motor = motorData.motor;
        int maxPos = motorData.max;
        int minPos = motorData.min;
        double power = motorData.power;
        boolean extended = motorData.extended;

        if (buttonPressed){
            int pos = (extended)?minPos:maxPos;
            setPosition(motor,pos,power);
            extended = !extended;
        }

        if (!isLifting(motor)){
            stopLift(motor);
        }

        motorData.extended = extended;

    }

    public void extendMotor(MechanismsHolonomic.MotorConstants data){
        DcMotor motor = data.motor;
        int max = data.max;
        int min = data.min;
        boolean extended = data.extended;
        double power = data.power;

        int target = (extended)?min:max;

        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(motor.isBusy()){
            motor.setPower(power);
        }
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        data.extended = !extended;

    }

    public void spin(boolean buttonPressed){
        if (buttonPressed){
            double p = (dropSpinning)?0:dropPower;
            dropMineralMotor.setPower(p);
            dropSpinning = !dropSpinning;
        }
    }

    public void flip(boolean buttonPressed){
        if(buttonPressed){
            double target = (flipCurrent == flipMin)?flipMax:flipMin;
            flipBucket.setPosition(target);
            flipCurrent = target;
        }
    }

    public void dropRobot(){
        liftMotor.setTargetPosition(liftMax);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while(liftMotor.isBusy()){
            liftMotor.setPower(liftPower);
        }
        liftMotor.setPower(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    enum MotorConstants{
        LIFT(liftMotor,liftMax,liftMin,liftPower,liftExtended),
        EXTEND(extendArmMotor,extendMax,extendMin,extendPower,extendExtended),
        SWING(swingArmMotor,swingMax,swingMin,swingPower,swingExtended);

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
