package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class ExhibitionBot extends LinearOpMode {

    //TankDrive drive = new TankDrive(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
    WiffleLauncher launcher = new WiffleLauncher(hardwareMap,"Shooter1","Shooter2");

    double maxPower = 0.7;
    double[] launchPowers = {0.2,0.5,0.7,0.9};
    int currentIndex = 0;

    double maxLaunch1 = 0.7;
    double maxLaunch2 = 0.9;
    double currentMax = maxLaunch1;
    boolean increasing = false;


    @Override
    public void runOpMode(){

        waitForStart();
        while(opModeIsActive()){
            double rightPower = Range.clip(gamepad1.left_stick_y+gamepad1.left_stick_x,-maxPower,maxPower);
            double leftPower  = Range.clip(gamepad1.left_stick_y-gamepad1.left_stick_x,-maxPower,maxPower);


            if (gamepad1.a){
                currentMax = maxLaunch1;
                increasing = !increasing;
            }
            if(gamepad1.x){
                currentMax = maxLaunch2;
                increasing = !increasing;
            }

            if (increasing){
                launcher.gradualChange(currentMax);
            }

        }
    }
}