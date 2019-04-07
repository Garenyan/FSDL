package Bean.Helper;

/**
 * Created by garen on 2019/3/23.
 */
public class MeasureResultHelper {
    private Double precision;
    private Double recall;
    private Double F_measure;


    public Double getF_measure() {
        return F_measure;
    }

    public Double getPrecision() {
        return precision;
    }

    public Double getRecall() {
        return recall;
    }

    public void setF_measure(Double f_measure) {
        F_measure = f_measure;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    public void setRecall(Double recall) {
        this.recall = recall;
    }
}
