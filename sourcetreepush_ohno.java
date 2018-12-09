package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class sourcetreepush_ohno extends LinearOpMode {

    public DcMotor Motor1;

    @Override
    public void runOpMode() {
        Motor1 = hardwareMap.get(DcMotor.class, "Motor1");

        waitForStart();
        while (opModeIsActive()) {
            double power = Range.clip(gamepad1.left_stick_y, -1, 1);
            Motor1.setPower(power);

            telemetry.addData("Test", power);
            telemetry.update();

        }
    }
}

