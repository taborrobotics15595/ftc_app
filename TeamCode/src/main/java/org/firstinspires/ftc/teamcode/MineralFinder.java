package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class MineralFinder {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";


    private static final String VUFORIA_KEY = "ASHHQIj/////AAAAGXYJVW7cAUFsgkCKi6Nhif9NSD8eT9qEc1ACRGnc4hTvEXGEXuyMiud30yKeNLWeVSRaauEwn7EsvmW3hLBIPAoP1O5tVZ3AKXoP7gtDsaqTX9zP457gxk4vDSBmO4vNsHgl4sa+ijZTb50UJ3nvA92U/4lPicHcyLYenOpWGXEe/4MJF1P6uQ/Hp4M0n2mrLRw8Q5xzjyV5CSY+Vv1H4/mCYH0zlwOl/ScN/ibmouIT6mOJfWQjlvx8xaIpbg5slN5j7L7CGrRs2284z1WF6aSn7Fo20IUe/FevV+ZLKmgAMZGmizpx91L7SskSWNSyjn6S/a/wDVPCScUm/iEEouKOEAa4yMkCYll62tn6VR8q";

    private VuforiaLocalizer vuforia;


    private TFObjectDetector tfod;

    public MineralFinder(HardwareMap hardwareMap){
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
        }
    }

    public void activate(){
        if(tfod != null) {
            tfod.activate();
        }
    }

    public List<Recognition> getRecognitions(){
        List<Recognition> recognitions = tfod.getRecognitions();
        return recognitions;
    }

    public String getGoldPosition(List<Recognition> recognitions){
        String pos = "";
        if (recognitions.size() == 3){
            float gold = -1;
            float silver1 = -1;
            float silver2 = -1;

            for(Recognition r:recognitions){
                if ((r.getLabel() == LABEL_GOLD_MINERAL)){
                    gold = r.getLeft();
                }
                else if(silver1 == -1){
                    silver1 = r.getLeft();
                }
                else{
                    silver2 = r.getLeft();

                }

            }
            if ((gold < silver1) &&(gold < silver2)){
                pos = "Left";
            }
            else if((gold > silver1) && (gold > silver2)){
                pos = "Right";
            }
            else{
                pos = "Center";
            }
        }
        return pos;

    }

    public double getGoldAngle(List<Recognition> recognitions){
        double angle = 90;
        for(Recognition r:recognitions) {
            if ((r.getLabel() == LABEL_GOLD_MINERAL)) {
                angle = r.estimateAngleToObject(AngleUnit.DEGREES);
            }
        }

        return angle;
    }


}
