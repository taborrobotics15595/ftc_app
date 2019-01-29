package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class MechanismsHolonomicTeleOp extends LinearOpMode {
    MechanismsHolonomic mechs;

    @Override
    public void runOpMode(){
        mechs = new MechanismsHolonomic(hardwareMap,"LiftMotor","ExtendMotor","DropMotor");

        waitForStart();

        while(opModeIsActive()){
            mechs.moveMotor(gamepad1.a,MechanismsHolonomic.MotorConstants.LIFT);
            mechs.moveMotor(gamepad1.x,MechanismsHolonomic.MotorConstants.EXTEND);
            mechs.moveMotor(gamepad1.y,MechanismsHolonomic.MotorConstants.DROP);

        }
    }
}




