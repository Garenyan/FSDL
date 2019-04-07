package Bean.Helper;

import java.util.List;
import java.util.Set;

/**
 * Created by garen on 2019/3/14.
 */
public class VectorHelperObject {
    private List<String> sStrlist;
    private Set<String> strings;
    private List<Integer> sIntegerList;
    private List<String> tStrlist;
    private List<Integer> tIntegerList;

    public List<Integer> getsIntegerList() {
        return sIntegerList;
    }

    public List<String> getsStrlist() {
        return sStrlist;
    }

    public Set<String> getStrings() {
        return strings;
    }

    public void setsIntegerList(List<Integer> sIntegerList) {
        this.sIntegerList = sIntegerList;
    }

    public void setsStrlist(List<String> sStrlist) {
        this.sStrlist = sStrlist;
    }

    public void setStrings(Set<String> strings) {
        this.strings = strings;
    }

    public List<String> gettStrlist() {
        return tStrlist;
    }

    public void settStrlist(List<String> tStrlist) {
        this.tStrlist = tStrlist;
    }

    public List<Integer> gettIntegerList() {
        return tIntegerList;
    }

    public void settIntegerList(List<Integer> tIntegerList) {
        this.tIntegerList = tIntegerList;
    }
}
