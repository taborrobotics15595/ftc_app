package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.ArrayList;
import java.util.List;

@Autonomous
public class IterativeAutonomousCorner1 extends OpMode {
    MineralFinder finder;
    HolonomicDriveTrain driveTrain;
    MechanismsHolonomic mechanisms;

    int[] turnRight = {150,150,150,160};
    int[] straight = {150,-150,150,-150};
    double power = 0.3;

    boolean hasTurned = false;
    boolean hasFound = false;

    int p = 1;
    ArrayList<String> positions = new ArrayList<>();



    @Override
    public void init(){
        positions.add("left");
        positions.add("middle");
        positions.add("right");
        finder = new MineralFinder(hardwareMap);

        driveTrain = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        mechanisms = new MechanismsHolonomic(hardwareMap,"Lift_Motor","Extend_Motor","Drop_Motor","Swing_Motor","Flip_Servo");

        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        mechanisms.setMotorZeroPowerBehaviour(MechanismsHolonomic.MotorConstants.SWING.motor,DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void init_loop(){


    }

    @Override
    public void start(){
        //mechanisms.dropRobot();
        finder.activate();

        int p[] = {50,-50,50,-50};
        driveTrain.goToPositions(p,power);

    }

    @Override
    public void loop(){


        List<Recognition> r = finder.getRecognitions();
        String message = finder.getGoldPosition(r);

        if (!hasFound) {
            if (message == "turn") {
                if (p == 1) {
                    driveTrain.goToPositions(turnRight, power);
                } else if (p == 2) {
                    driveTrain.goToPositions(multiplyLists(turnRight, -2), power);
                }
                p = (p + 1) % 3;
            }
            } else if (message != "unknown") {
                int difference = positions.indexOf(message) - p;
                driveTrain.goToPositions(multiplyLists(turnRight, difference), power);
                hasFound = true;
            }

        else{
            int[] fullTurn = {-700,-700,-700,-700};
            driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            driveTrain.goToPositions(fullTurn,power);
            driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            driveTrain.goToPositions(straight,power);
            mechanisms.extendMotor(MechanismsHolonomic.MotorConstants.SWING);
            mechanisms.extendMotor(MechanismsHolonomic.MotorConstants.EXTEND);
            this.stop();

        }

        telemetry.addData("Stats:", message);
        telemetry.update();

    }



    @Override
    public void stop(){
        driveTrain.halt();
    }

    public int[] multiplyLists(int[] list,int number){
        int[] n = new int[list.length];
        for(int i = 0;i<list.length;i++){
            int a = list[i]*number;
            n[i] = a;
        }
        return n;
    }

}
