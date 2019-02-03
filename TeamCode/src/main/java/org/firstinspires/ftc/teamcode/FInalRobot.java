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
    MechanismsHolonomic mechanisms;
    ElapsedTime runtime;

    private double maxPower1 = 0.3;

    private int conditional = 1;

    private double maxPower = maxPower1 + 0.2 * conditional;

    double powerY = 0;
    double powerX = 0;

    int[] currentPositions = {0, 0, 0, 0};
    int[] targetPosition = {0, 0, 0, 0};


    @Override
    public void runOpMode() {
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        runtime = new ElapsedTime();

        mechanisms = new MechanismsHolonomic(hardwareMap, "Lift_Motor","Extend_Motor","Drop_Motor","Swing_Motor","Flip_Servo");
        mechanisms.setMotorZeroPowerBehaviour(MechanismsHolonomic.MotorConstants.SWING.motor,DcMotor.ZeroPowerBehavior.BRAKE);
        mechanisms.setMotorZeroPowerBehaviour(MechanismsHolonomic.MotorConstants.LIFT.motor,DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            powerY = -Range.clip(gamepad1.left_stick_y, -maxPower, maxPower);
            powerX = Range.clip(gamepad1.left_stick_x, -maxPower, maxPower);

            double turn = Range.clip(gamepad1.right_stick_x, -maxPower, maxPower);

            if (turn != 0) {
                robot.turn(turn);
            } else {
                robot.setPower(powerY, powerX);
            }


            maxPower = maxPower1 + (gamepad1.right_trigger * 0.2);


            double liftP = Range.clip(gamepad2.left_stick_y,-1,1);
            MechanismsHolonomic.liftMotor.setPower(liftP);

            double swingP = Range.clip(gamepad2.right_stick_y,-0.7,0.7);
            MechanismsHolonomic.swingArmMotor.setPower(swingP);

            moveMotor(gamepad2.b,MechanismsHolonomic.MotorConstants.EXTEND);

            if (gamepad2.y){
                double power = (mechanisms.dropSpinning)?-0.5:0;
                MechanismsHolonomic.dropMineralMotor.setPower(-0.5);
                mechanisms.dropSpinning = !mechanisms.dropSpinning;
            }

            mechanisms.flip(gamepad2.x);



            String message = Double.toString(runtime.seconds());

            telemetry.addData("Motor Data:", message);
            telemetry.update();

            idle();

        }
    }

    public void moveMotor(boolean buttonPressed, MechanismsHolonomic.MotorConstants motorData){
        DcMotor motor = motorData.motor;
        int maxPos = motorData.max;
        int minPos = motorData.min;
        double power = motorData.power;
        boolean extended = motorData.extended;

        if (buttonPressed){
            int pos = (extended)?minPos:maxPos;
            mechanisms.setPosition(motor,pos,power);
            extended = !extended;
        }

        if (!motor.isBusy()){
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        motorData.extended = extended;

    }
}
