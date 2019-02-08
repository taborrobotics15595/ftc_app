package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MechanismsTeleOp extends LinearOpMode {
    Mechanisms mechs;
    ElapsedTime runtime;

    private double maxPower = 0.3;

    double powerY,powerX,turn,lift,swing;


    @Override
    public void runOpMode() {
        runtime = new ElapsedTime();

        mechs = new Mechanisms(hardwareMap,"Lift_Motor","Extend_Motor","Drop_Motor","Swing_Motor","Flip_Servo");

        waitForStart();

        while (opModeIsActive()) {
            lift = Range.clip(gamepad2.left_stick_y,-mechs.liftPower,mechs.liftPower);
            swing = Range.clip(gamepad2.right_stick_y,-mechs.swingPower,mechs.swingPower);

            mechs.setPower(mechs.liftMotor,lift);
            mechs.setPower(mechs.swingArmMotor,swing);

            if (gamepad2.b){
                int pos = (mechs.extendExtended)?mechs.extendMin:mechs.extendMax;
                mechs.toggleMotor(mechs.extendArmMotor,pos,mechs.extendPower);
                mechs.extendExtended = !mechs.extendExtended;
            }
            if (gamepad2.y){
                double power = (mechs.dropSpinning)?0:mechs.dropPower;
                mechs.setPower(mechs.dropMineralMotor,power);
                mechs.dropSpinning = !mechs.dropSpinning;
            }
            if (gamepad2.x){
                mechs.flipBucket();
            }

            mechs.checkBusy();

            String message = Double.toString(runtime.seconds());

            telemetry.addData("Time:", message);
            telemetry.update();
        }
    }
}
