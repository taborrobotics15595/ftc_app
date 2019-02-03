package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class autonmous extends LinearOpMode {

    HolonomicDriveTrain robot;
    MechanismsHolonomic mechs;
    ElapsedTime runtime;

    boolean swinging = false;


    @Override
    public void runOpMode(){
        robot = new HolonomicDriveTrain(hardwareMap,"Motor1","Motor2","Motor3","Motor4");
        mechs = new MechanismsHolonomic(hardwareMap,"Lift_Motor","Extend_Motor","Drop_Motor","Swing_Motor","Flip_Servo");
        runtime = new ElapsedTime();

        robot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        mechs.setMotorZeroPowerBehaviour(MechanismsHolonomic.swingArmMotor,DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        runtime.reset();
        while(opModeIsActive()) {


            if (runtime.seconds() < 1) {
                robot.setPower(0, 0);
                if (!swinging) {
                    swinging = true;
                }

                this.stop();
            } else {
                robot.setPower(0.3, 0);
            }

            if (runtime.seconds() < 1.2) {
                if (swinging) {
                    mechs.swingArmMotor.setPower(0.5);
                }
                else{
                    mechs.swingArmMotor.setPower(0);
                }
            }
        }



    }
}
