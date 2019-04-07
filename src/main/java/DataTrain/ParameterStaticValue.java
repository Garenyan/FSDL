package DataTrain;

import DataTrain.FLDLTrainer;

/**
 * Created by garen on 2019/3/28.
 */
public class ParameterStaticValue {
    public static final Double Clone_Threshold =0.99;
    public static final Integer nEpochs = 1500;
    public static final Integer numInputs = 12; //特征个数 固定
    public static final Integer numOutputs = 2; //输出层个数 固定 二分类 克隆与非克隆
    public static final Integer numHiddenNodes= 16;
    public static final Double learningRate = 0.001;
    public static final String train_data_path="TrainDataFiles\\newTrainData.csv";
    public static final Integer batchsize= 45326;

}
