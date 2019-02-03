package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class FInalRobot extends LinearOpMode {
    HolonomicDriveTrain robot;
    ElapsedTime runtime;

    private double maxPower = 0.3;

    double powerY = 0;
    double powerX = 0;


    @Override
    public void runOpMode() {
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        runtime = new ElapsedTime();

        waitForStart();

        while (opModeIsActive()) {
            powerY = -Range.clip(gamepad1.left_stick_y, -maxPower, maxPower);
            powerX = Range.clip(gamepad1.left_stick_x, -maxPower, maxPower);

            double turn = Range.clip(gamepad1.right_stick_x, -maxPower, maxPower);

            if (turn != 0) {
                robot.turn(turn);
            } else {
                robot.setPower(powerY, powerX,maxPower);
            }



            String message = Double.toString(runtime.seconds());

            telemetry.addData("Time:", message);
            telemetry.update();

            idle();

        }
    }

}
