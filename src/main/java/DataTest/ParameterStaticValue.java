package DataTest;

import DataTrain.FLDLTrainer;

/**
 * Created by garen on 2019/3/28.
 */
public class ParameterStaticValue {
    public static Double Clone_Threshold =0.99;
    public static Integer nEpochs = 1500;
    public static Integer numInputs = 12; //特征个数 固定
    public static Integer numOutputs = 2; //输出层个数 固定 二分类 克隆与非克隆
    public static Integer numHiddenNodes= 16;

    public static String model_name = "model";

    public static void main(String[] args){
        for (int i=6;i<18;i++){
            nEpochs = nEpochs+100;
            //FLDLTrainer.main();
        }

    }


    private static void createTrainModel(){

    }



}
