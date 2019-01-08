package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class ExhibitionBot extends LinearOpMode {

    WiffleLauncher launcher;

    double maxLaunch1 = 0.7;
    double maxLaunch2 = 0.9;
    double currentMax = maxLaunch1;
    boolean increasing = false;



    @Override
    public void runOpMode(){

        launcher = new WiffleLauncher(hardwareMap,"Motor1","Motor2","Motor3");
        waitForStart();
        while(opModeIsActive()){

            if (gamepad1.a){
                currentMax = maxLaunch1;
                increasing = true;
            }
            if(gamepad1.x){
                currentMax = maxLaunch2;
                increasing = true;
            }

            if(gamepad1.b){
                increasing = false;
                launcher.stop();

            }

            if(gamepad1.y){
                launcher.setup();
            }

            if(increasing){
                launcher.gradualChange(currentMax);
            }

        }
    }
}
