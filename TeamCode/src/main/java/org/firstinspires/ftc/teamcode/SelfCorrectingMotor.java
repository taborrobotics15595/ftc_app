package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class SelfCorrectingMotor extends LinearOpMode {

    DcMotor m;

    int last = 0;
    int target = 0;
    int current;

    double power = 0.5;
    double constant = -0.2;
    double correctingPower;
    double difference;

    boolean stay = false;

    @Override
    public void runOpMode(){
        m = hardwareMap.get(DcMotor.class,"Motor1");
        m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        while (opModeIsActive()){
            current = m.getCurrentPosition();

            double apower = Range.clip(gamepad1.left_stick_y,-power,power);
            m.setPower(apower);

            if (gamepad1.a){
                target = current;
                stay = !stay;
                if (stay){
                    m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    m.setTargetPosition(current);
                }
                else{
                    m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            }


            if (stay){
                difference = last - current;
                correctingPower = Range.clip(constant * difference,-1,1);
                if ((correctingPower == 0) && (m.isBusy())) {
                    correctingPower = 1;
                }
                m.setPower(correctingPower);

            }

            last = current;


            String message = "Encoder Difference: " + Double.toString(difference) + " Correcting Power: " + Double.toString(correctingPower);
            telemetry.addData("Status:",message);
            telemetry.update();
        }
    }




}
