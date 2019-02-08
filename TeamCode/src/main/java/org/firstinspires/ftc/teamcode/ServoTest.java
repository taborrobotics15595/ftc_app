package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTest extends LinearOpMode {
    Servo s;

    double p = 1;

    @Override
    public void runOpMode(){
        s = hardwareMap.get(Servo.class,"Servo");
        s.setPosition(p);

        waitForStart();
        while(opModeIsActive()){
            if (gamepad1.a){
                p = (p == 1)?0:1;
                s.setPosition(p);
            }
        }
    }
}
