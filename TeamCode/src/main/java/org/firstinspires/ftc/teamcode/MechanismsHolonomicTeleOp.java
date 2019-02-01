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
            mechs.moveMotor(gamepad2.a,MechanismsHolonomic.MotorConstants.LIFT);
            mechs.moveMotor(gamepad2.b,MechanismsHolonomic.MotorConstants.EXTEND);
            mechs.spin(gamepad2.y);
            mechs.flip(gamepad2.x);

            telemetry.addData("TIme:",Double.toString(runtime.seconds()));
            telemetry.update();
        }
    }
}




