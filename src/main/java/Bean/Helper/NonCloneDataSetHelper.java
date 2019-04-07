package Bean.Helper;

/**
 * Created by garen on 2019/3/25.
 */
public class NonCloneDataSetHelper extends DataSetInfoHelper{
    private String lable;
    public NonCloneDataSetHelper(){
        this.lable = "NON_CLONE";
    }

    public String getLable() {
        return lable;
    }
}
