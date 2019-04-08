package DataTrain;

import DataTrain.FLDLTrainer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by garen on 2019/3/28.
 */
public class ParameterStaticValue {
//    public static  Double Clone_Threshold =0.99;
//    public static  Integer nEpochs = 300;
//    public static final Integer numInputs = 12; //特征个数 固定
//    public static final Integer numOutputs = 2; //输出层个数 固定 二分类 克隆与非克隆
//    public static final Integer numHiddenNodes= 16;
//    public static final Double learningRate = 0.001;
//    public static final String train_data_path="TrainDataFiles\\newTrainData.csv";
//    public static final Integer batchsize= 45326;
    public  String training_file;
    public  String model_name;

    public  String output_dir;

    public  int seed;
    public  double learningRate;
    public  int batchsize;
    public  int nEpochs;

    public  int numInputs;
    public  int numOutputs;
    public  int numHiddenNodes;

    public  Double x;
    public  int DT;
    public  Double Clone_Threshold;

        public  void Load_Config(){
        try {
            String config_file = ".\\ConfigFiles\\parameters.properties";
            Properties prop = new Properties();
            InputStream is = new FileInputStream(config_file);

            prop.load(is);
           // ParameterStaticValue p = new ParameterStaticValue();

            output_dir = prop.getProperty("output_dir");

            training_file = prop.getProperty("training_file");
            model_name=prop.getProperty("model_name");

            seed = Integer.valueOf(prop.getProperty("training_seed"));
            learningRate = Double.valueOf(prop.getProperty("training_learningRate"));
            batchsize = Integer.valueOf(prop.getProperty("training_batchSize"));
            nEpochs = Integer.valueOf(prop.getProperty("training_iteration"));

            numInputs = Integer.valueOf(prop.getProperty("training_input_num"));
            numOutputs = Integer.valueOf(prop.getProperty("training_output_num"));
            numHiddenNodes = Integer.valueOf(prop.getProperty("training_hidden_num"));

            x = Double.valueOf(prop.getProperty("x"));
            DT = Integer.valueOf(prop.getProperty("DT"));
            Clone_Threshold = Double.valueOf(prop.getProperty("CloneThreshold"));

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParameterStaticValue(){
            Load_Config();
    }




}
