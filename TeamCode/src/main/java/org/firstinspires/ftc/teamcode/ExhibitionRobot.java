package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

@TeleOp
public class ExhibitionRobot extends LinearOpMode {

    //HolonomicDriveTrain robot;

    double maxPower = 1;

    WiffleLauncher launcher;

    RevBlinkinLedDriver driver;

    double currentMax = 0;
    double increaseFactor = 0.1;

    boolean increasing = false;
    boolean finished = false;
    boolean moving = false;

    double currentTime = 0;




    @Override
    public void runOpMode(){
        //driver = hardwareMap.get(RevBlinkinLedDriver.class,"Driver");

        //robot = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        launcher = new WiffleLauncher(hardwareMap,"Launch1","Launch2","Spinner");

        //robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();

        while(opModeIsActive()){
            double yPower = Range.clip(gamepad1.left_stick_y,-maxPower,maxPower);
            double xPower = -Range.clip(gamepad1.left_stick_x,-maxPower,maxPower);

            double rotate = -Range.clip(gamepad1.right_stick_x,-maxPower,maxPower);

            //robot.setPower(maxPower,yPower,xPower,rotate);


            if (gamepad1.left_trigger > 0){
                currentMax = Range.clip(currentMax + increaseFactor,-1,1);
                increasing = true;
                moving = true;

                //driver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_LAVA_PALETTE);

            }
            else{
                currentMax = 0;
                increasing = false;
                moving = false;

                //'driver.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_BLUE);

            }



            if(gamepad1.right_trigger > 0){
                launcher.setup();
            }


            if (finished){
                launcher.setPower(currentMax);
            }

            finished = launcher.gradualChange(increasing,moving,currentMax);


            String message = "Max Power: " + Double.toString(currentMax) + "Finished: " +  Boolean.toString(finished) + "Time: " + Double.toString(currentTime);
            telemetry.addData("Status:",message);
            telemetry.update();

        }
    }
}
