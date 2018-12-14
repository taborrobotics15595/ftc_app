package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class MechanismsTeleOp extends LinearOpMode {
    Mechanisms mechs;



    boolean bucketOpened = false;

    @Override
    public void runOpMode(){
        mechs = new Mechanisms(hardwareMap,"LiftMotor","GrabberMotor","TurnMotor","BucketServo","GrabberServo");

        waitForStart();

        while(opModeIsActive()){
            moveMotor(gamepad1.a,Mechanisms.MotorConstants.LIFT);
            moveMotor(gamepad1.x,Mechanisms.MotorConstants.GRAB);
            moveMotor(gamepad1.y,Mechanisms.MotorConstants.TURN);


            if(gamepad1.b){
                if(bucketOpened){
                    mechs.openBucket();
                }
                else{
                    mechs.closeBucket();
                }
                bucketOpened = !bucketOpened;
            }

            if(gamepad1.right_bumper){
                if(mechs.isSpinning){
                    mechs.stopSpinning();
                }
                else{
                    mechs.startSpinning();
                }
            }

        }
    }

    private void moveMotor(boolean buttonPressed, Mechanisms.MotorConstants motorData){
        DcMotor motor = motorData.motor;
        int maxPos = motorData.max;
        int minPos = motorData.min;
        double power = motorData.power;
        boolean extended = motorData.extended;

        if (buttonPressed){
            if(extended) {
                mechs.drop(motor,minPos,power);
            }else{
                mechs.lift(motor,maxPos,power);
            }
            extended = !extended;
        }

        if (!mechs.isLifting(motor)){
            mechs.stopLift(motor);
        }

        motorData.extended = extended;

    }




}




