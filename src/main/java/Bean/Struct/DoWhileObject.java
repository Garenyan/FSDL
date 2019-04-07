package Bean.Struct;

import StaticValue.StructureStaticValue;

/**
 * Created by garen on 2019/2/28.
 */
public class DoWhileObject extends StructParentObject{
    private String name;

    public String getName() {
        return name;
    }

    public DoWhileObject()
    {
        this.name = StructureStaticValue.DOWHILE;
    }
}
