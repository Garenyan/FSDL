package Bean.Struct;

import StaticValue.StructureStaticValue;

/**
 * Created by garen on 2019/2/28.
 */
public class ForObject extends StructParentObject{
    private String name;

    public String getName() {
        return name;
    }

    public ForObject()
    {
        this.name = StructureStaticValue.FOR;
    }
}



