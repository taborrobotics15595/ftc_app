package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.util.ArrayList;

@TeleOp
public class OmniFlynn extends LinearOpMode {

    public ArrayList<DcMotor> motors = new ArrayList<DcMotor>();

    String names[] = {"Motor1","Motor2","Motor3","Motor4"};

    int[] front = {0,1};
    int[] back = {2,3};
    int[] left = {0,2};
    int[] right = {1,3};

    double maxPower = 0.5;

    int[] currentPositions = {0,0,0,0};
    int[] targetPositions = {0,0,0,0};

    double returnPower = 0.5;

    @Override
    public void runOpMode(){
        for(String name:names){
            DcMotor motor = hardwareMap.get(DcMotor.class,name);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motors.add(motor);

        }

        waitForStart();
        while(opModeIsActive()){
            double rightP = Range.clip(gamepad1.left_stick_y + gamepad1.left_stick_x,-maxPower,maxPower);
            double leftP = Range.clip(gamepad1.left_stick_y - gamepad1.left_stick_x,-maxPower,maxPower);

            double slide = Range.clip(gamepad1.right_stick_x,-maxPower,maxPower);

            if (slide != 0){
                actionToGroup(front,slide);
                actionToGroup(back,-slide);
            }else{
                actionToGroup(right,rightP);
                actionToGroup(left,-leftP);
            }

            currentPositions = getCurrentPositions();

            targetPositions = (gamepad1.a)?currentPositions:targetPositions;

            if(gamepad1.x){
                moveToPositions();
            }

            String message = "Current: " + Integer.toString(currentPositions[0]) + " Target: " + Integer.toString(targetPositions[0]);
            telemetry.addData("Status:",message);
            telemetry.update();
        }
    }

    public void actionToGroup(int[] group,double power){
        for(int index:group){
            motors.get(index).setPower(power);
        }
    }

    public int[] getCurrentPositions(){
        int[] positions = currentPositions;
        for(int i = 0;i<motors.size();i++){
            int p = motors.get(i).getCurrentPosition();
            positions[i] = p;
        }
        return positions;
    }

    public void moveToPositions(){
        for(int i = 0;i<motors.size();i++){
            DcMotor motor = motors.get(i);
            int targetPosition = targetPositions[i];
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setTargetPosition(targetPosition);
        }

        boolean keep  = true;
        while (keep) {
            for (int i = 0; i < motors.size(); i++) {
                DcMotor motor = motors.get(i);
                boolean busy = motor.isBusy();
                if (busy) {
                    motor.setPower(returnPower);
                }
                else{
                    motor.setPower(0);
                    motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                keep = keep && busy;
            }
            telemetry.addData("Status:","in loop");
            telemetry.update();
        }



    }




}
