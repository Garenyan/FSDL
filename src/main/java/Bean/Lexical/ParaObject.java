package Bean.Lexical;

import org.eclipse.jdt.core.dom.Type;

/**
 * Created by garen on 2019/3/8.
 */
public class ParaObject {
    private String parameterTypeName;
    private Type parameterType;
    private Integer typeIndex;
    private Integer score;


    public String getParameterTypeName() {
        return parameterTypeName;
    }

    public void setParameterTypeName(String parameterTypeName) {
        this.parameterTypeName = parameterTypeName;
    }

    public void setTypeIndex(Integer typeIndex) {
        this.typeIndex = typeIndex;
    }

    public Integer getTypeIndex() {
        return typeIndex;
    }



    public Type getParameterType() {
        return parameterType;
    }

    public void setParameterType(Type parameterType) {
        this.parameterType = parameterType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
