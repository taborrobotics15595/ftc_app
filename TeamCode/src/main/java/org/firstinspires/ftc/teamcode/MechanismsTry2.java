package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MechanismsTry2 {
    DcMotor liftMotor,extendArmMotor,dropMineralMotor;

    int liftMax = 100;
    int liftMin = 0;

    public MechanismsTry2(HardwareMap hardwareMap,String liftName,String extendName,String dropName){
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


    public void moveMotor(boolean buttonPressed, MechanismsTry2.MotorConstants motorData){
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
        LIFT(Mechanisms.liftArmMotor,Mechanisms.liftMaxPosition,Mechanisms.liftMinPosition,Mechanisms.liftPower,Mechanisms.liftExtended),
        EXTEND(Mechanisms.grabberArmMotor,Mechanisms.grabberMaxPosition,Mechanisms.grabberMinPosition,Mechanisms.grabberPower,Mechanisms.grabberExtended),
        DROP(Mechanisms.turnMotor,Mechanisms.turnMotorMax,Mechanisms.turnMotorMin,Mechanisms.turnPower,Mechanisms.turnExtended);

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
