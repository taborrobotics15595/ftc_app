package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class Test extends LinearOpMode {
    DcMotor m1;
    ElapsedTime runtime;

    double max1 = 0.3;
    double max2 = 0.5;
    double currentMax = 0;

    double currentPower = 0;

    int interval = 100;
    double increase = 0.05;

    boolean increasing = false;
    boolean moving = false;
    boolean finished = false;

    double currentTime = 0;

    @Override
    public void runOpMode(){

        runtime = new ElapsedTime();
        m1 = hardwareMap.get(DcMotor.class,"Motor1");
        m1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while(opModeIsActive()){
            if (gamepad1.a){
                currentMax = max1;
                increasing = true;
                moving = true;
                runtime.reset();
            }
            if(gamepad1.x){
                currentMax = max2;
                increasing = true;
                moving = true;
                runtime.reset();
            }

            if (gamepad1.b){
                increasing = false;
                currentMax = 0;
            }

            currentTime = runtime.seconds();

            if (currentTime < 8){
                if (finished){
                    m1.setPower(currentMax);
                }
            }
            else{
                increasing = false;
                currentMax = 0;
            }
            finished = steadyIncrease(increasing,moving,currentMax);


            String message = "Max Power: " + Double.toString(currentMax) + "Finished: " +  Boolean.toString(finished) + "Current POwer: " + Double.toString(currentPower) + "Time: " + Double.toString(currentTime);
            telemetry.addData("Statys",message);
            telemetry.update();


        }
    }

    public boolean steadyIncrease(boolean positive,boolean moving,double max){
        int p = m1.getCurrentPosition();

        int f = (positive)?1:-1;
        if ((p % interval == 0) && moving && ((currentPower < (currentMax - increase)) || (currentPower > increase))) {
            currentPower = Range.clip(currentPower + f*increase,0,max);
            m1.setPower(currentPower);
        }else{
            return true;
        }
        return false;

    }
}
