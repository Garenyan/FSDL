package Bean.Struct;

import StaticValue.StructureStaticValue;

/**
 * Created by garen on 2019/2/28.
 */
public class WhileObject extends StructParentObject{
    private String name;

    public String getName() {
        return name;
    }

    public WhileObject()
    {
        this.name = StructureStaticValue.WHILE;
    }
}
