package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class ExhibitionBot extends LinearOpMode {

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
        launcher = new WiffleLauncher(hardwareMap,"Motor1","Motor2","Motor3");
        waitForStart();
        while(opModeIsActive()){
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
                increasing = false;
            }

            finished = launcher.gradualChange(increasing,moving,currentMax);


            String message = "Max Power: " + Double.toString(currentMax) + "Finished: " +  Boolean.toString(finished) + "Time: " + Double.toString(currentTime);
            telemetry.addData("Status:",message);
            telemetry.update();

        }
    }
}
