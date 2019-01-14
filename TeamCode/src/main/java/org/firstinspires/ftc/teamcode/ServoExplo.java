package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoExplo extends LinearOpMode {

    Servo s;


    double maxPos = 1;
    double minPos = 0;

    double currentPos = 0;

    double increase = 0.1;

    @Override
    public void runOpMode(){

        s = hardwareMap.get(Servo.class,"Servo");

        waitForStart();
        while(opModeIsActive()){
            if (gamepad1.a){
                for(double pos = minPos; pos <= maxPos; pos += increase){
                    s.setPosition(pos);
                    currentPos = pos;
                }
            }

            if (gamepad1.x){
                for(double pos = maxPos; pos >= minPos; pos -= increase){
                    s.setPosition(pos);
                    currentPos = pos;
                }
            }
        }
    }
}
