package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
//@Disabled
public class ArmLift1 extends LinearOpMode {

    DcMotor motor1;



    @Override
    public void runOpMode(){
        motor1 = hardwareMap.get(DcMotor.class,"Motor1");

        //motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();


        while(opModeIsActive()) {
            double y = Range.clip(gamepad1.left_stick_y, -0.9, 0.9);
            motor1.setPower(y);
            //int encoder = motor1.getCurrentPosition();
            //telemetry.addData("Encoder Value:",Integer.toString(encoder));
            //telemetry.update();
        }

    }

}
