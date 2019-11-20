package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MecanumTeleOP extends LinearOpMode {
    MecanumDriveTrain robot;
    DcMotor arm;
    ElapsedTime runtime;

    private double maxPower = 0.75;


    double powerY,powerX,turn;


    @Override
    public void runOpMode() {
        robot = new MecanumDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //arm = hardwareMap.get(DcMotor.class,"Arm_Motor");

        runtime = new ElapsedTime();

        waitForStart();

        while (opModeIsActive()) {
            powerY = Range.clip(gamepad1.left_stick_y, -maxPower, maxPower);
            powerX = Range.clip(gamepad1.left_stick_x, -maxPower, maxPower);
            turn = Range.clip(gamepad1.right_stick_x, -maxPower, maxPower);

            robot.setPower(maxPower,powerY,powerX,turn);

            /*
            if (gamepad1.a){
                arm.setPower(1);
            }
            else{
                arm.setPower(0);
            }

            if (gamepad1.b){
                arm.setPower(-1);
            }
            else{
                arm.setPower(0);
            }
*/


        }
    }
}
