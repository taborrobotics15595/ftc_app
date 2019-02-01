package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous
public class IterativeAutonomousCorner1 extends OpMode {
    MineralFinder finder;
    HolonomicDriveTrain driveTrain;
    MechanismsHolonomic mechanisms;

    int[] start = {580,-580,580,-580};
    int[] slideRight = {570,570,-570,-570};
    int[] middle = {0,0,0,0};
    int[] slideLeft = {-600,-600,600,600};
    int[] park = {200,-200,200,-200};

    int[][] positions = {slideLeft,middle,slideRight};

    double returnPower = 0.3;

    boolean hasFound = false;

    int wiggleCount = 0;

    int f = 50;

    int index = 1;

    @Override
    public void init(){
        finder = new MineralFinder(hardwareMap);
        driveTrain = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        mechanisms = new MechanismsHolonomic(hardwareMap,"Lift_Motor","Extend_Motor","Drop_Motor","Swing_motor","Flip_Servo");

        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        mechanisms.setMotorZeroPowerBehaviour(MechanismsHolonomic.MotorConstants.LIFT.motor,DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void init_loop(){


    }

    @Override
    public void start(){
        mechanisms.dropRobot();

    }

    @Override
    public void loop(){
        List<Recognition> r = finder.getRecognitions();
        String message = "";
        if(!hasFound) {
            if (finder.foundGold(r)) {
                hasFound = true;
                message = "found";
            } else {
                if (wiggleCount != 2) {
                    int[] tPos = wiggle(f - wiggleCount * 2*f);
                    driveTrain.goToPositions(tPos,returnPower);
                    wiggleCount += 1;
                    message = "wiggle" + Integer.toString(wiggleCount);
                }
                else{
                    wiggleCount = 0;
                    int[] p = positions[index];
                    driveTrain.goToPositions(p,returnPower);
                    index  = (index + 1)%positions.length;
                    message = "turning" + Integer.toString(index);

                }
            }
        }
        else{
            driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            driveTrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveTrain.goToPositions(start,returnPower);
            driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            driveTrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveTrain.goToPositions(park,returnPower);

            this.stop();

        }
        telemetry.addData("Stats:",message);
        telemetry.update();

    }



    @Override
    public void stop(){

    }

    public int[] wiggle(int f){
        int n[] = driveTrain.getCurrentPositions();
        for(int i = 0;i<n.length;i++){
            n[i] += (f*slideRight[i]/Math.abs(slideRight[i]));

        }
        return n;
    }

}
