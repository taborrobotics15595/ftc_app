package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class MechanismsHolonomicTeleOp extends LinearOpMode {
    MechanismsHolonomic mechs;

    ElapsedTime runtime;

    @Override
    public void runOpMode(){
        mechs = new MechanismsHolonomic(hardwareMap,"Lift_Motor","Extend_Motor","Drop_Motor","Swing_Motor","Flip_Servo");
        runtime = new ElapsedTime();
        waitForStart();

        while(opModeIsActive()){
            moveMotor(gamepad2.a,MechanismsHolonomic.MotorConstants.LIFT);
            moveMotor(gamepad2.b,MechanismsHolonomic.MotorConstants.EXTEND);
            moveMotor(gamepad2.right_bumper,MechanismsHolonomic.MotorConstants.SWING);
            mechs.spin(gamepad2.y);
            mechs.flip(gamepad2.x);

            telemetry.addData("TIme:",Double.toString(runtime.seconds()));
            telemetry.update();
        }
    }

    public void moveMotor(boolean buttonPressed, MechanismsHolonomic.MotorConstants motorData){
        DcMotor motor = motorData.motor;
        int maxPos = motorData.max;
        int minPos = motorData.min;
        double power = motorData.power;
        boolean extended = motorData.extended;

        if (buttonPressed){
            int pos = (extended)?maxPos:minPos;
            motor.setTargetPosition(pos);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setPower(power);
            extended = !extended;
        }

        if (!motor.isBusy()){
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        motorData.extended = extended;

    }
}




