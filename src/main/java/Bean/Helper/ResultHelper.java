package Bean.Helper;

import Bean.MethodPairSimVector;

import java.util.List;

/**
 * Created by garen on 2019/3/27.
 */
public class ResultHelper {
    private List<MethodPairSimVector> testMethodPairSimVectors;
    private List<MethodPairSimVector> benchmarks;
    private String test_file_path;
    private FeatureHelper nonFeature;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    private String modelName;

    public void setNonFeature(FeatureHelper nonFeature) {
        this.nonFeature = nonFeature;
    }

    public FeatureHelper getNonFeature() {
        return nonFeature;
    }

    private String benchmarkFileXMLpath;

    public List<MethodPairSimVector> getTestMethodPairSimVectors() {
        return testMethodPairSimVectors;
    }

    public String getTest_file_path() {
        return test_file_path;
    }

    public void setTestMethodPairSimVectors(List<MethodPairSimVector> testMethodPairSimVectors) {
        this.testMethodPairSimVectors = testMethodPairSimVectors;
    }

    public void setTest_file_path(String test_file_path) {
        this.test_file_path = test_file_path;
    }

    public List<MethodPairSimVector> getBenchmarks() {
        return benchmarks;
    }

    public void setBenchmarks(List<MethodPairSimVector> benchmarks) {
        this.benchmarks = benchmarks;
    }

    public String getBenchmarkFileXMLpath() {
        return benchmarkFileXMLpath;
    }

    public void setBenchmarkFileXMLpath(String benchmarkFileXMLpath) {
        this.benchmarkFileXMLpath = benchmarkFileXMLpath;
    }
}
