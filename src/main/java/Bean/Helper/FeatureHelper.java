package Bean.Helper;

/**
 * Created by garen on 2019/4/1.
 * 这个类是用来作对比实验
 * 如果要做缺少某个特征的实验效果时，则调用该类
 */
public enum FeatureHelper {
    VARIABLENAME, //表示缺省变量名特征，一下类同
    TYPE,
    OPERATETYPE,
    METHODINVOKENAME,
    METHODINVOKEPARA,
    FOR,
    WHILE,
    SWITCH,
    DOWHILE,
    IF,
    WHOLESTRUCTURE,
    FUNCTION, //当前只做了缺省功能特征
    NULLFEATURE;//表示完整的12个特征


    private  FeatureHelper(){}
}
