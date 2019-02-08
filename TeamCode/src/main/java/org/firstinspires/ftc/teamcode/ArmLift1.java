package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
//yay
@TeleOp
//@Disabled
public class ArmLift1 extends LinearOpMode {

    DcMotor motor1,motor2;




    @Override
    public void runOpMode(){
        motor1 = hardwareMap.get(DcMotor.class,"Motor1");

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        double power = 0.75;


        waitForStart();


        while(opModeIsActive()) {

            if (gamepad1.a){
                power = (power ==0.75)?1:0.75;
            }
            double y = Range.clip(gamepad1.left_stick_y, -power,power);
            motor1.setPower(y);

            telemetry.addData("Power:",Double.toString(power));
            telemetry.update();

        }

    }

}
