package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class EncoderReading extends LinearOpMode {
    DcMotor motor;

    @Override
    public void runOpMode(){
        motor = hardwareMap.get(DcMotor.class,"Motor");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        String message;
        int target = 0;
        int current;

        waitForStart();
        while(opModeIsActive()){

            double power = Range.clip(gamepad1.left_stick_y,-0.3,0.3);
            motor.setPower(power);

            current = motor.getCurrentPosition();

            if (gamepad1.a){
                target = current;
                motor.setTargetPosition(target);
            }

            message = "Power: " + String.valueOf(power) + " Current Posiiton: " + String.valueOf(current) + " Target: " + String.valueOf(target);

            if (gamepad1.x){
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setPower(0.2);
                telemetry.addData("State","Motor is moving to position");
                while(motor.isBusy()){
                    telemetry.update();

                }
                motor.setPower(0);
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            telemetry.addData("State",message);
            telemetry.update();




        }
    }
}
