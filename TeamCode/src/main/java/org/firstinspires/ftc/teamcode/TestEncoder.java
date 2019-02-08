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

    double[] powers = {0.75,1};
    double powerA = 0.75;
    int i = 0;

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
            double power = Range.clip(gamepad1.left_stick_y,-powerA,powerA);

            if (gamepad1.a){
                i = (i + 1)%powers.length;
                powerA = powers[i];
            }

            motor.setPower(power);

            currentPosition = motor.getCurrentPosition();

            targetPosition = (gamepad1.a)?currentPosition:targetPosition;

            if (gamepad1.x){
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while(motor.isBusy()) {
                    motor.setPower(powerA);
                    telemetry.addData("Status","In loop");
                    telemetry.update();
                }
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor.setPower(0);
            }

            String message = "Power: " + Double.toString(powerA) + " Current: " + Integer.toString(currentPosition) + " Going to: " + Integer.toString(targetPosition);
            telemetry.addData("Encoder Data:",message);
            telemetry.update();

        }
    }
}
