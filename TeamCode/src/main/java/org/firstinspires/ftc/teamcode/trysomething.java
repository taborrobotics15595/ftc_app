package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class trysomething extends LinearOpMode {

    boolean fwd = true;
    double p;
    @Override
    public void runOpMode(){



        waitForStart();
        while(opModeIsActive()){
            if (fwd){
                p = gamepad1.left_stick_y;
            }
            else{
                p = gamepad1.left_stick_x;
            }

            fwd = (gamepad1.a)?!fwd:fwd;



            telemetry.addData("p:",Double.toString(p));
            telemetry.update();
        }
    }
}
