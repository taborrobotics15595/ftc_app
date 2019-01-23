package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class TankDriveTeleOp extends LinearOpMode {

    TankDrive robot;

    double maxPower = 0.5;

    int[] currentPositions = {0,0,0,0};
    int[] targetPositions = {0,0,0,0};

    @Override
    public void runOpMode(){
        robot = new TankDrive(hardwareMap,"Distance","Motor1","Motor2","Motor3","Motor4");

        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while(opModeIsActive()){
            robot.setPower(gamepad1.left_stick_x,gamepad1.left_stick_y,maxPower);

            currentPositions = robot.getCurrentPositions();
            targetPositions = (gamepad1.a)?currentPositions:targetPositions;

            if (gamepad1.x){
                robot.goToPositions(targetPositions,maxPower);
            }

            double distance = robot.getDistance(DistanceUnit.CM);

            String message = "";
            for(int i = 0;i<currentPositions.length;i++){
                int current = currentPositions[i];
                int target = targetPositions[i];
                message += " Current: " + Integer.toString(current) + " Target: " + Integer.toString(target);
            }
            message += " Distance: " + Double.toString(distance);

            telemetry.addData("Data:",message);
            telemetry.update();

        }
    }
}
