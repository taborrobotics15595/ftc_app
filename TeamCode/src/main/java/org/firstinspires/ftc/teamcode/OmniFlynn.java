package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

@TeleOp
public class OmniFlynn extends LinearOpMode {

    HolonomicDriveTrain robot;

    double maxPower = 1;

    WiffleLauncher launcher;

    double maxLaunch1 = 0.7;
    double maxLaunch2 = 0.9;
    double currentMax = 0;
    double increaseFactor = 0.1;

    boolean increasing = false;
    boolean finished = false;
    boolean moving = false;

    double currentTime = 0;




    @Override
    public void runOpMode(){
        ElapsedTime runtime = new ElapsedTime();
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        launcher = new WiffleLauncher(hardwareMap,"Launch1","Launch2","Spinner");

        robot.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();

        while(opModeIsActive()){
            double yPower = Range.clip(gamepad1.left_stick_x,-maxPower,maxPower);
            double xPower = -Range.clip(gamepad1.left_stick_y,-maxPower,maxPower);

            double rotate = -Range.clip(gamepad1.right_stick_x,-maxPower,maxPower);

            if (rotate != 0){
                robot.rotate(rotate);
            }
            else{
                robot.setPower(yPower,xPower);
            }

            if (gamepad1.left_trigger > 0){
                currentMax = Range.clip(currentMax + increaseFactor,-1,1);
                increasing = true;
                moving = true;

            }
            else{
                currentMax = 0;
                increasing = false;
                moving = false;

            }

            if (gamepad1.a){
                currentMax = maxLaunch1;
                increasing = true;
                moving = true;
                runtime.reset();

            }
            if(gamepad1.x){
                currentMax = maxLaunch2;
                increasing = true;
                moving = true;
                runtime.reset();
            }


            if(gamepad1.b){
                increasing = false;
            }

            if(gamepad1.y || (gamepad1.right_trigger > 0)){
                launcher.setup();
            }

            currentTime = runtime.seconds();
            if(currentTime < 15) {
                if (finished){
                    launcher.setPower(currentMax);
                }
            }else{
                //increasing = false;
            }

            finished = launcher.gradualChange(increasing,moving,currentMax);


            String message = "Max Power: " + Double.toString(currentMax) + "Finished: " +  Boolean.toString(finished) + "Time: " + Double.toString(currentTime);
            telemetry.addData("Status:",message);
            telemetry.update();

        }
    }
}
