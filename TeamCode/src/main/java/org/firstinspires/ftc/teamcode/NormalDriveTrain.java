package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class NormalDriveTrain extends LinearOpMode {
    DcMotor right,left;

    double max = 0.75;

    @Override
    public void runOpMode(){
        right = hardwareMap.get(DcMotor.class,"Motor1");
        left = hardwareMap.get(DcMotor.class,"Motor2");

        waitForStart();
        while(opModeIsActive()){
            double leftP = gamepad1.left_stick_y;
            double rightP = -gamepad1.left_stick_x;

            double powerRight = Range.clip(leftP + rightP ,-max,max);
            double powerLeft = -Range.clip(leftP - rightP ,-max,max);

            right.setPower(powerRight);
            left.setPower(powerLeft);
        }
    }
}
