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


    public boolean foundGold(List<Recognition> recognitions){
        boolean found = false;
        for(Recognition r:recognitions){
            if ((r.getLabel() == LABEL_GOLD_MINERAL) && r.getConfidence() > 0.7){
                found = true;
            }
        }
        return found;
    }





    public String getGoldPosition(List<Recognition> r){
        String message = "";
        if (r.size() == 3){
            float g = -1;
            float s1 = -1;
            float s2 = -1;
            for(Recognition rec:r){
                if (rec.getLabel() == "Gold Mineral"){
                    g = rec.getRight();
                }
                else{
                    if (s1 == -1){
                        s1 = rec.getRight();
                    }else{
                        s2 = rec.getRight();
                    }
                }
            }
            if ((g > s1)&&(g>s2)){
                message = "right";
            }
            else if ((g < s1) && (g<s2)){
                message = "left";
            }
            else{
                message = "middle";
            }
        }
        else if(r.size() == 2){
            if (foundGold(r)){
                float g = -1;
                float s = -1;
                for(Recognition rec:r){
                    if (rec.getLabel() == "Gold Mineral"){
                        g = rec.getRight();
                    }
                    else{
                        s = rec.getRight();
                    }
                }

                if ((g > s) && (g>900)){
                    message = "right";
                }
                else if (g>s){
                    message = "middle";
                }
                else if (s>900){
                    message = "middle";
                }
                else{
                    message = "left";
                }
                if (g == -1){
                    message = "unknown";
                }
            }
        }
        else if (r.size() == 1){
            float g = r.get(0).getRight();
            if (foundGold(r) && (g>900)){
                message = "middle";
            }
            else{
                message = "turn";
            }
        }
        else{
            message = "unknown";
        }
        return message;

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
