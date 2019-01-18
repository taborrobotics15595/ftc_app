package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Lights extends LinearOpMode {

    RevBlinkinLedDriver driver;

    @Override
    public void runOpMode(){
        driver = hardwareMap.get(RevBlinkinLedDriver.class,"Lights");

        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.a) {
                driver.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_RAINBOW_PALETTE);
            }

            if (gamepad1.x){
                driver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_LAVA_PALETTE);
            }

            if (gamepad1.y){
                driver.setPattern(RevBlinkinLedDriver.BlinkinPattern.LARSON_SCANNER_RED);
            }

            if (gamepad1.b){
                driver.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_BLUE);
            }
        }
    }
}
