package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MecanumTeleOp extends LinearOpMode {

    private double maxPower1 = 0.5;

    private int conditional = 1;

    private double maxPower = maxPower1 + 0.2*conditional;

    private MecanumDriveTrain robot;


    @Override
    public void runOpMode(){

        robot = new MecanumDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while(opModeIsActive()){
            double powerY = Range.clip(gamepad1.left_stick_y,-maxPower,maxPower);
            double powerX = Range.clip(gamepad1.left_stick_x,-maxPower,maxPower);

            double turn = Range.clip(gamepad1.right_stick_x,-maxPower,maxPower);

            if (turn != 0){
                robot.turn(turn);
            }
            else{
                robot.setPower(powerY,powerX);
            }

            conditional = gamepad1.right_bumper?1:0;
            maxPower = maxPower1 + conditional*0.2;

        }
    }

}
