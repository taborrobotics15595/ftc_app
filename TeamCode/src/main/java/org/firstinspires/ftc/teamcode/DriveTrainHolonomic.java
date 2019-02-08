package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class DriveTrainHolonomic extends LinearOpMode {
    HolonomicDriveTrain robot;
    ElapsedTime runtime;

    private double maxPower = 0.3;

    double powerY,powerX,turn;


    @Override
    public void runOpMode() {
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        runtime = new ElapsedTime();

        waitForStart();

        while (opModeIsActive()) {
            powerY = -Range.clip(gamepad1.left_stick_y, -maxPower, maxPower);
            powerX = Range.clip(gamepad1.left_stick_x, -maxPower, maxPower);
            turn = Range.clip(gamepad1.right_stick_x, -maxPower, maxPower);


            robot.setPower(maxPower,powerY,powerX,turn);


            String message = Double.toString(runtime.seconds()) + "power X: " + Double.toString(powerX) + "power y: " + Double.toString(powerY) + "Turn:" + Double.toString(turn);

            telemetry.addData("Time:", message);
            telemetry.update();
        }
    }
}
