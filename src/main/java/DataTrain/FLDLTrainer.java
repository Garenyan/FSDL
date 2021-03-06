package DataTrain;

import Bean.Helper.NeuralNetHelper;
import MyTools.FileUtils;
import StaticValue.TrainDataFileStaticValue;
import org.canova.api.records.reader.RecordReader;
import org.canova.api.records.reader.impl.CSVRecordReader;
import org.canova.api.split.FileSplit;
import org.deeplearning4j.datasets.canova.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;

import org.deeplearning4j.datasets.iterator.DataSetIterator;

import org.nd4j.linalg.lossfunctions.LossFunctions;
import polyglot.ast.Do;

import java.io.*;
import java.sql.Time;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class FLDLTrainer {
    public static void main(String... args) throws java.io.IOException, InterruptedException {
        ParameterStaticValue parameterStaticValue = new ParameterStaticValue();
       // Load_Config();
//        String training_file = "TrainDataFiles\\newTrainData.csv";
        String modelName = parameterStaticValue.model_name+".mdl";
//        Double learningRate = 0.001;
//        int batchSize = 45326;
//        int nEpochs = 400;
//        int numInputs = 12; //特征个数 固定
//        int numOutputs = 2; //输出层个数 固定 二分类 克隆与非克隆
//        int numHiddenNodes= 16;
        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new File(parameterStaticValue.training_file)));//加载训练文件
        DataSetIterator trainIter = new RecordReaderDataSetIterator(rr, parameterStaticValue.batchsize, 0, 2);
        long start = System.nanoTime();
        int seed = parameterStaticValue.seed;

        String output_dir = "TrainDataFiles\\TrainDataSetOutput\\";
        System.out.println("开始模型训练.......");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder() //这里已经是输入层
                /**随机种子：因为模型的权重和偏置初始化是随机的
                 * 我们需要随机种子使得每次初始化权重和偏置是一样的
                 * **/
                .seed(seed)
                .iterations(1)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)//这些是神经网络模型中的随机梯度下降法
                .updater(Updater.NESTEROVS).momentum(0.9)
                .l2(1e-4)//正则化
                .list()
                //隐藏层参数设定（第一层隐藏层）
                .layer(0,new DenseLayer.Builder().nIn(parameterStaticValue.numInputs).nOut(parameterStaticValue.numHiddenNodes) // Number of input datapoints.
                        .activation("relu") // Activation function.
                        .weightInit(WeightInit.XAVIER) // Weight initialization.
                        .build())
                .layer(1,new DenseLayer.Builder().nIn(parameterStaticValue.numHiddenNodes).nOut(parameterStaticValue.numHiddenNodes)
                .weightInit(WeightInit.XAVIER)
                .activation("relu")
                .build())
//                .layer(2,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
//                        .weightInit(WeightInit.XAVIER)
//                        .activation("relu")
//                        .build())
//                .layer(3,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
//                        .weightInit(WeightInit.XAVIER)
//                        .activation("relu")
//                        .build())
//                .layer(4,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
//                        .weightInit(WeightInit.XAVIER)
//                        .activation("relu")
//                        .build())
//                .layer(5,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
//                        .weightInit(WeightInit.XAVIER)
//                        .activation("relu")
//                        .build())
//                .layer(6,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
//                        .weightInit(WeightInit.XAVIER)
//                        .activation("relu")
//                        .build())
                //输出层
                .layer(2,new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(parameterStaticValue.numHiddenNodes)
                        .nOut(parameterStaticValue.numOutputs)
                        .activation("softmax")
                        .weightInit(WeightInit.XAVIER)
                        .build())
                //pretrain -- 预训练  backprop--反向传播
                .pretrain(false).backprop(true)
                .build();

        // create the MLN
        MultiLayerNetwork network = new MultiLayerNetwork(conf);
        network.init();
        network.setListeners(Collections.singletonList((IterationListener) new ScoreIterationListener(10)));
        for (int n = 0; n < parameterStaticValue.nEpochs; n++) {
            network.fit(trainIter);
        }




        File model_File = new File(output_dir + modelName);
        ModelSerializer.writeModel(network, model_File, true);
        //输出网络配置信息（但是 如果仅仅是这么写的话 需要每次在文件夹中新建一个zip，并且要改名）
        //ModelSerializer.writeModel(network,new File(".\\TrainDataFiles\\TrainDataSetOutput\\model.zip"),true);
        //或者获得相关的配置信息，以自定义方式输出，这个是可用法，注意路径的修改等等。
        getConfigureInfoToJson(network,".\\TrainDataFiles\\TrainDataSetOutput\\modelParameterFile.json");
        long end = System.nanoTime();
        System.out.println("Time Cost:"+TimeUnit.MILLISECONDS.toMillis(end-start)+"ms");

        System.out.println("模型训练完毕，开始输出模型参数......");
        // 输出深度学习模型参数文件
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("model名称:");
        stringBuilder.append(modelName);
        stringBuilder.append("\n");
        stringBuilder.append("学习率:");
        stringBuilder.append(parameterStaticValue.learningRate);
        stringBuilder.append("\n");
        stringBuilder.append("迭代次数:");
        stringBuilder.append(parameterStaticValue.nEpochs);
        stringBuilder.append("\n");
        stringBuilder.append("Batchsize:");
        stringBuilder.append(parameterStaticValue.batchsize);
        stringBuilder.append("\n");
        stringBuilder.append("隐藏层结点个数:");
        stringBuilder.append(parameterStaticValue.numHiddenNodes);
        stringBuilder.append("\n");
        stringBuilder.append("输入层结点个数:");
        stringBuilder.append(parameterStaticValue.numInputs);
        stringBuilder.append("\n");
        stringBuilder.append("输出层结点个数:");
        stringBuilder.append(parameterStaticValue.numOutputs);
        stringBuilder.append("\n");
        FileUtils.writefiles(stringBuilder.toString(), TrainDataFileStaticValue.MODELPARAFILE);
        System.out.println("模型参数文件输出完毕......");
    }


    /**
     *
     * 这个方法我其实把上面的封装了一下，但其实没有用到
     * @param neuralNetHelper
     * @param train_file
     * @throws IOException
     * @throws InterruptedException
     */
    public static void train(NeuralNetHelper neuralNetHelper,String train_file) throws IOException, InterruptedException {
        int batchSize = neuralNetHelper.getBatchSize();
        int numInputs = neuralNetHelper.getNumInputs();
        int numHiddenNodes = neuralNetHelper.getNumHiddenNodes();
        int numOutputs = neuralNetHelper.getNumOutputs();
        int nEpochs = neuralNetHelper.getnEpochs();
        Double learningRate = neuralNetHelper.getLearningRate();
        String modelName = neuralNetHelper.getModelName();
        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new File(train_file)));
        DataSetIterator trainIter = new RecordReaderDataSetIterator(rr, batchSize, 0, 2);
        long start = System.nanoTime();
        int seed = 123;

        String output_dir = "TrainDataFiles\\TrainDataSetOutput\\";
//        NeuralNetConfiguration.Builder conf1 = new NeuralNetConfiguration.Builder();
//        NeuralNetConfiguration.Builder conf2 = conf1.seed(seed);
        //conf2.iterations(1);
        System.out.println("开始模型训练.......");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                /**随机种子：因为模型的权重和偏置初始化是随机的
                 * 我们需要随机种子使得每次初始化权重和偏置是一样的
                 * **/
                .seed(seed)
                .iterations(1)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(learningRate)
                .updater(Updater.NESTEROVS).momentum(0.9)
                .l2(1e-4)
                .list()
                //输入层
                .layer(0,new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes) // Number of input datapoints.
                        .activation("relu") // Activation function.
                        .weightInit(WeightInit.XAVIER) // Weight initialization.
                        .build())
                //隐藏层
                .layer(1,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .weightInit(WeightInit.XAVIER)
                        .activation("relu")
                        .build())
                .layer(2,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .weightInit(WeightInit.XAVIER)
                        .activation("relu")
                        .build())
//                .layer(3,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
//                        .weightInit(WeightInit.XAVIER)
//                        .activation("relu")
//                        .build())
//                .layer(4,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
//                        .weightInit(WeightInit.XAVIER)
//                        .activation("relu")
//                        .build())
//                .layer(5,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
//                        .weightInit(WeightInit.XAVIER)
//                        .activation("relu")
//                        .build())
//                .layer(6,new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
//                        .weightInit(WeightInit.XAVIER)
//                        .activation("relu")
//                        .build())
                //输出层
                .layer(3,new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(numHiddenNodes)
                        .nOut(numOutputs)
                        .activation("softmax")
                        .weightInit(WeightInit.XAVIER)
                        .build())
                //pretrain -- 预训练  backprop--反向传播
                .pretrain(false).backprop(true)
                .build();

        // create the MLN
        MultiLayerNetwork network = new MultiLayerNetwork(conf);
        network.init();
        network.setListeners(Collections.singletonList((IterationListener) new ScoreIterationListener(10)));
        for (int n = 0; n < nEpochs; n++) {
            network.fit(trainIter);
        }




        File model_File = new File(output_dir + modelName);
        ModelSerializer.writeModel(network, model_File, true);
        long end = System.nanoTime();
        System.out.println("Time Cost:"+TimeUnit.MILLISECONDS.toMillis(end-start)+"ms");

    }


    private static void getConfigureInfoToJson(MultiLayerNetwork model,String path){
        String json = model.getLayerWiseConfigurations().toJson();
       FileUtils.writefiles(json,path);
    }

}