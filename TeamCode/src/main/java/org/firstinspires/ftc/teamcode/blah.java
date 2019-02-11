package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous
public class blah extends LinearOpMode {
    HolonomicDriveTrain robot;
    Mechanisms mechs;
    MineralFinder finder;
    ElapsedTime runtime;

    boolean hasTurned = false;
    boolean found = false;
    int position = 1;
    String[] positionNames = {"left","middle","right"};
    String message = "";

    int[] position0 = {-300,300,-300,300};
    int[] position1 = {-300,-300,300,300};
    int[] right = {230,230,230,230};
    int[] left = {-460,-460,-460,-460};

    boolean on = false;
    boolean lifed = false;
    boolean attacking = false;
    double attackStart;
    boolean canFInish = false;

    @Override
    public void runOpMode(){
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        mechs = new Mechanisms(hardwareMap,"Lift_Motor","Extend_Motor","Drop_Motor","Swing_Motor","Flip_Servo");
        runtime = new ElapsedTime();
        finder = new MineralFinder(hardwareMap);

        DcMotor lift = mechs.liftMotor;

        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        finder.activate();
        runtime.reset();
        while(opModeIsActive()){
            double time = runtime.seconds();
            if (time < 2.6) {
                double power = mechs.liftPower;
                lift.setPower(power);
            }else {
                lift.setPower(0);
                lifed = true;
            }

            if (lifed && !on) {
                robot.goToPositions(position0, 0.3);
                robot.goToPositions(position1,0.3);
                on = true;
            }
            if (on && (time < (attackStart + 3))) {
                robot.setPower(0.3,0,0.3,0);
                canFInish = true;
            }else{
                robot.setPower(0,0,0,0);
                if (canFInish) {
                    this.stop();
                }
            }

        }
    }
}
