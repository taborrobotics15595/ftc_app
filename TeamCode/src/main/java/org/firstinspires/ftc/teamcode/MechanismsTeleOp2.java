package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class MechanismsTeleOp2 extends LinearOpMode {
    MechanismsTry2 mechs;

    @Override
    public void runOpMode(){
        mechs = new MechanismsTry2(hardwareMap,"LiftMotor","ExtendMotor","DropMotor");

        waitForStart();

        while(opModeIsActive()){
            mechs.moveMotor(gamepad1.a,MechanismsTry2.MotorConstants.LIFT);
            mechs.moveMotor(gamepad1.x,MechanismsTry2.MotorConstants.EXTEND);
            mechs.moveMotor(gamepad1.y,MechanismsTry2.MotorConstants.DROP);

        }
    }
}




