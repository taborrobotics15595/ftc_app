package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.ArrayList;
import java.util.List;

@Autonomous
public class AutonomousTry extends LinearOpMode {

    MineralFinder finder;

    public ArrayList<DcMotor> motors = new ArrayList<DcMotor>();

    String names[] = {"Motor1","Motor2","Motor3","Motor4"};

    double[] forward = {1,-1,1,-1};
    double[] right = {1,1,-1,-1};
    double[] turn = {1,1,1,1};


    double maxPower = 0.5;
    double slidePower = 0.2;

    @Override
    public void runOpMode(){
        for(String name:names){
            DcMotor motor = hardwareMap.get(DcMotor.class,name);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motors.add(motor);

        }

        finder = new MineralFinder(hardwareMap);
        finder.activate();

        waitForStart();
        while(opModeIsActive()){
            List<Recognition> r = finder.getRecognitions();
            double angle = finder.getGoldAngle(r);
            String message ;
            if (finder.foundGold(r)){
                if (angle > 1) {
                    applyPower(right, -slidePower);
                    message = "right";
                } else if (angle < -1) {
                    applyPower(right, slidePower);
                    message = "left";
                } else {
                    message = "done";
                }
            }
            else{
                message = "nothing found";
            }

            telemetry.addData("status",message);
            telemetry.update();



        }
    }

    public  void applyPower(double[] config,double power){
        for(int index = 0;index < motors.size();index++){
            double p = config[index] * power;
            motors.get(index).setPower(p);
        }
    }
}
