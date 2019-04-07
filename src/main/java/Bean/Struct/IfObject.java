package Bean.Struct;

import StaticValue.StructureStaticValue;

/**
 * Created by garen on 2019/2/28.
 */
public class IfObject extends StructParentObject{
    private String name;

    public String getName() {
        return name;
    }

    public IfObject()
    {
        this.name = StructureStaticValue.IF;
    }
}
