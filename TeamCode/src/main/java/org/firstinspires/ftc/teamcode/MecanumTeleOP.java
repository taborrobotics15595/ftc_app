package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MecanumTeleOP extends LinearOpMode {
    MecanumDriveTrain robot;
    Servo servo;
    ElapsedTime runtime;

    private double maxPower = 1,minPosition = 0,maxPosition = 0.7,position = minPosition;


    double powerY,powerX,turn;


    @Override
    public void runOpMode() {
        robot = new MecanumDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        //robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        servo = hardwareMap.get(Servo.class,"Servo");

        runtime = new ElapsedTime();

        waitForStart();

        while (opModeIsActive()) {
            powerY = Range.clip(gamepad1.left_stick_y, -maxPower, maxPower);
            powerX = Range.clip(gamepad1.left_stick_x, -maxPower, maxPower);
            turn = Range.clip(gamepad1.right_stick_x, -maxPower, maxPower);

            robot.setPower(maxPower,powerY,powerX,turn);

            if (gamepad1.a){
                servo.setPosition(position);
                position = (position == maxPosition)?minPosition:maxPosition;
                try{
                    Thread.sleep(400);
                }catch(InterruptedException e){

                }
            }




        }
    }
}
