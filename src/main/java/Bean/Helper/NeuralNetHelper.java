package Bean.Helper;

/**
 * Created by garen on 2019/3/28.
 */
public class NeuralNetHelper {
    private String modelName;

    private Double learningRate;
    private Integer batchSize;
    private Integer numHiddenNodes;
    private Integer numInputs;
    private Integer numOutputs;
    private Integer nEpochs;

    public Double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(Double learningRate) {
        this.learningRate = learningRate;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Integer getNumHiddenNodes() {
        return numHiddenNodes;
    }

    public void setNumHiddenNodes(Integer numHiddenNodes) {
        this.numHiddenNodes = numHiddenNodes;
    }

    public Integer getNumInputs() {
        return numInputs;
    }

    public void setNumInputs(Integer numInputs) {
        this.numInputs = numInputs;
    }

    public Integer getNumOutputs() {
        return numOutputs;
    }

    public void setNumOutputs(Integer numOutputs) {
        this.numOutputs = numOutputs;
    }

    public Integer getnEpochs() {
        return nEpochs;
    }

    public void setnEpochs(Integer nEpochs) {
        this.nEpochs = nEpochs;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
