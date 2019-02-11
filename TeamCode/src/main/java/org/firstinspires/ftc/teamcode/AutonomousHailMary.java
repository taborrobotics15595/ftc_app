package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class AutonomousHailMary extends LinearOpMode {
    HolonomicDriveTrain robot;
    Mechanisms mechs;
    ElapsedTime runtime;

    boolean lifted = false;

    double controlTIme;
    boolean fwd = false;

    boolean turned = false;


    @Override
    public void runOpMode(){
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1", "Motor2", "Motor3", "Motor4");
        // robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        runtime = new ElapsedTime();

        mechs = new Mechanisms(hardwareMap,"Lift_Motor","Extend_Motor","Drop_Motor","Swing_Motor","Flip_Servo");

        waitForStart();
        while (opModeIsActive()){

            if (!fwd){
                if (time < (controlTIme + 3)){
                    robot.setPower(0.3,0,-0.3,0);
                }
                else{
                    robot.setPower(0,0,0,0);
                    fwd = true;
                    controlTIme = time;
                }
            }

            if (fwd){
                if (time < (controlTIme + 3)){
                    robot.setPower(0.3,0,0,-0.3);
                }
                else{
                   robot.setPower(0,0,0,0);
                   turned = true;
                   controlTIme = time;
                }
            }

            if (turned){
                if (time < controlTIme + 3) {
                    mechs.swingArmMotor.setPower(-0.5);
                }else{
                    mechs.swingArmMotor.setPower(0);
                    this.stop();
                }
            }
        }
    }
}
