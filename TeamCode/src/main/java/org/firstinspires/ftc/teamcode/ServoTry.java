package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTry extends LinearOpMode {

    @Override
    public void runOpMode(){

        //CRServo servo = hardwareMap.get(CRServo.class,"Servo");
        Servo servo = hardwareMap.get(Servo.class,"Servo");

        boolean rotate = false;
        double maxPos = 1;
        double minPos = 0;
        double interval = 0.1;
        double currentPos = minPos;

        waitForStart();

        servo.setPosition(minPos);
        while(opModeIsActive()){
            if (gamepad1.a){
                rotate = !rotate;
            }

           if (rotate){
                for(double p = currentPos;p<maxPos;p += interval){
                    servo.setPosition(p);
                }
                currentPos = maxPos;
           }
           else{
               for(double p = currentPos;p > minPos;p -= interval){
                   servo.setPosition(p);
               }
               currentPos = minPos;
           }





        }
    }
}
