package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class TestEncoder extends LinearOpMode{

    DcMotor motor;
    int currentPosition;
    int targetPosition;

    @Override
    public void runOpMode(){
        motor = hardwareMap.get(DcMotor.class,"Motor1");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        currentPosition = motor.getCurrentPosition();
        targetPosition = currentPosition;

        waitForStart();
        while(opModeIsActive()){
            double power = Range.clip(gamepad1.left_stick_y,-0.2,0.2);

            motor.setPower(power);

            currentPosition = motor.getCurrentPosition();

            targetPosition = (gamepad1.a)?currentPosition:targetPosition;

            if (gamepad1.x){
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while(motor.isBusy()) {
                    power = (targetPosition - currentPosition)<0?0.2:-0.2;
                    motor.setPower(power);
                    telemetry.addData("Status","In loop");
                    telemetry.update();
                }
                motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                motor.setPower(0);
            }

            String message = "Current: " + Integer.toString(currentPosition) + " Going to: " + Integer.toString(targetPosition);
            telemetry.addData("Encoder Data:",message);
            telemetry.update();

        }
    }
}
