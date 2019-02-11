package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
//@Disabled
public class FInalRobot extends LinearOpMode {
    HolonomicDriveTrain robot;
    Mechanisms mechs;
    ElapsedTime runtime;

    private double maxPower = 0.4;
    private double autoPower = 0.2;

    double powerY,powerX,turn,lift,swing;

    int[] currentPositions = new int[4];
    int[] targetPositions = new int[4];


    @Override
    public void runOpMode() {
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        runtime = new ElapsedTime();

        mechs = new Mechanisms(hardwareMap,"Lift_Motor","Extend_Motor","Drop_Motor","Swing_Motor","Flip_Servo");

        waitForStart();

        while (opModeIsActive()) {
            powerY = -Range.clip(gamepad1.left_stick_y, -maxPower, maxPower);
            powerX = Range.clip(gamepad1.left_stick_x, -maxPower, maxPower);
            turn = Range.clip(gamepad1.right_stick_x, -maxPower, maxPower);
            lift = Range.clip(gamepad2.left_stick_y,-mechs.liftPower,mechs.liftPower);
            swing = Range.clip(gamepad2.right_stick_y,-mechs.swingPower,mechs.swingPower);

            robot.setPower(maxPower,powerY,powerX,turn);
            mechs.setPower(mechs.liftMotor,lift);
            mechs.setPower(mechs.swingArmMotor,swing);

            if (gamepad2.b){
                int pos = (mechs.extendExtended)?mechs.extendMin:mechs.extendMax;
                mechs.toggleMotor(mechs.extendArmMotor,pos,mechs.extendPower);
                mechs.extendExtended = !mechs.extendExtended;
            }
            if (gamepad2.right_trigger > 0){
                double power = mechs.dropPower;
                mechs.setPower(mechs.dropMineralMotor,power);
                mechs.dropSpinning = !mechs.dropSpinning;
            }

            else{
                double power = 0;
                mechs.setPower(mechs.dropMineralMotor,power);
            }
            if (gamepad2.x){
                mechs.flipBucket();
            }

            mechs.checkBusy();

            currentPositions = robot.getCurrentPositions();
            targetPositions = (gamepad1.a)?currentPositions:targetPositions;



            String message = formatString(" Position: ",currentPositions) + formatString(" Target: ",targetPositions);
            message += " Arm: " + mechs.liftMotor.getCurrentPosition() + " Extend: " + mechs.swingArmMotor.getCurrentPosition();
            telemetry.addData("Status:", message);
            telemetry.update();
        }
    }

    private String formatString(String pre,int[] positions){
        String m = "";
        for(int p:positions){
            m += pre + p;
        }
        return m;
    }
}
