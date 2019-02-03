package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@TeleOp
public class FIndEncoderValues extends LinearOpMode {
    HolonomicDriveTrain driveTrain;
    MineralFinder finder;

    double power = 0.3;

    int[] currentPositions = {0,0,0,0};

    @Override
    public void runOpMode(){
        driveTrain = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        finder = new MineralFinder(hardwareMap);


        driveTrain.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        finder.activate();
        while(opModeIsActive()){
            double powerY = Range.clip(gamepad1.left_stick_y,-power,power);
            double powerX = Range.clip(gamepad1.left_stick_x,-power,power);

            double turn = Range.clip(gamepad1.right_stick_x,-power,power);

            if (turn == 0){
                driveTrain.setPower(powerY,powerX);
            }
            else{
                driveTrain.turn(turn);
            }

            List<Recognition> recognitions = finder.getRecognitions();
            String message = recognitions.size() + finder.getGoldPosition(recognitions);


            currentPositions = driveTrain.getCurrentPositions();
            for(int p:currentPositions){
                message += " Position: " + Integer.toString(p);
            }
            telemetry.addData("Data:",message);
            telemetry.update();
        }

    }
}
