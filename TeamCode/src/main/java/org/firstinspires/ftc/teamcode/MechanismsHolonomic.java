package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MechanismsHolonomic {
    static DcMotor liftMotor,extendArmMotor,dropMineralMotor;

    static int liftMax = 100;
    static int liftMin = 0;
    static double liftPower = 0.5;
    static boolean liftExtended = false;

    static int extendMax = 100;
    static int extendMin = 0;
    static double extendPower = 0.5;
    static boolean extendExtended = false;

    static int dropMax = 100;
    static int dropMin = 0;
    static double dropPower = 0.5;
    static boolean dropExtended = false;


    public MechanismsHolonomic(HardwareMap hardwareMap,String liftName,String extendName,String dropName){
        liftMotor = hardwareMap.get(DcMotor.class,liftName);
        extendArmMotor = hardwareMap.get(DcMotor.class,extendName);
        dropMineralMotor = hardwareMap.get(DcMotor.class,dropName);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        extendArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendArmMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        dropMineralMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dropMineralMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void goToPosition(DcMotor m, int pos, double power){
        m.setTargetPosition(pos);
        m.setPower(power);
        m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void stopLift(DcMotor m){
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
            this.goToPosition(motor,pos,power);
            extended = !extended;
        }

        if (!this.isLifting(motor)){
            this.stopLift(motor);
        }

        motorData.extended = extended;

    }

    enum MotorConstants{
        LIFT(MechanismsHolonomic.liftMotor,MechanismsHolonomic.liftMax,MechanismsHolonomic.liftMin,MechanismsHolonomic.liftPower,MechanismsHolonomic.liftExtended),
        EXTEND(MechanismsHolonomic.extendArmMotor,MechanismsHolonomic.extendMax,MechanismsHolonomic.extendMin,MechanismsHolonomic.extendPower,MechanismsHolonomic.extendExtended),
        DROP(MechanismsHolonomic.dropMineralMotor,MechanismsHolonomic.dropMax,MechanismsHolonomic.dropMin,MechanismsHolonomic.dropPower,MechanismsHolonomic.dropExtended);

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
