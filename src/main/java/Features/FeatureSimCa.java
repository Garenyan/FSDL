package Features;

import Bean.*;
import Bean.Helper.VectorHelperObject;
import Bean.Lexical.*;
import Bean.Struct.*;
import MyTools.FileUtils;
import StaticValue.StructureStaticValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by garen on 2019/3/8.
 */
public class FeatureSimCa {
    public Double getLeSim1(List<VariableName> sVariableNames, List<VariableName> tVariableNames)
    {
        Double leSim1 = 0.0;
        if (checkLe(sVariableNames,tVariableNames) == 0)
        {
            leSim1 = 0.5;
        }else {
            List<String> svList = new ArrayList<String>();
            List<String> tvList = new ArrayList<String>();
            List<Integer> svIList = new ArrayList<Integer>();
            List<Integer> tvIList = new ArrayList<Integer>();
            for (VariableName sv : sVariableNames) {
                svList.add(sv.getName());
                svIList.add(sv.getNum());
            }
            for (VariableName tv : tVariableNames) {
                tvList.add(tv.getName());
                tvIList.add(tv.getNum());
            }
            Set<String> stringSet = getUnion(svList, tvList);
            //List<String> inteStrlist = getIntersection(svList,tvList);
            VectorHelperObject vectorHelperObject = new VectorHelperObject();
            vectorHelperObject.setsIntegerList(svIList);
            vectorHelperObject.settStrlist(tvList);
            vectorHelperObject.settIntegerList(tvIList);
            vectorHelperObject.setsStrlist(svList);
            vectorHelperObject.setStrings(stringSet);

            List<Integer> score1 = getScoreVector(vectorHelperObject);

            VectorHelperObject vectorHelperObject1 = new VectorHelperObject();
            vectorHelperObject1.setsIntegerList(tvIList);
            vectorHelperObject1.settStrlist(svList);
            vectorHelperObject1.setsStrlist(tvList);
            vectorHelperObject1.setStrings(stringSet);
            vectorHelperObject1.settIntegerList(svIList);
            List<Integer> score2 = getScoreVector(vectorHelperObject1);
            leSim1 = SimAlgorithm.getCosineSim(score1, score2);
        }
        return leSim1;
    }

    public Double getLeSim2(List<VariableType> s,List<VariableType> t)
    {
        Double leSim2 = 0.0;
        if (checkLe(s,t) == 0)
        {
            leSim2 = 0.5;
        }else {
            List<String> svList = new ArrayList<String>();
            List<String> tvList = new ArrayList<String>();
            List<Integer> svIList = new ArrayList<Integer>();
            List<Integer> tvIList = new ArrayList<Integer>();
            for (VariableType sv : s) {
                svList.add(sv.getTypeName());
                svIList.add(sv.getNum());
            }
            for (VariableType tv : t) {
                tvList.add(tv.getTypeName());
                tvIList.add(tv.getNum());
            }
            Set<String> stringSet = getUnion(svList, tvList);
            List<String> inteStrlist = getIntersection(svList,tvList);
            VectorHelperObject vectorHelperObject = new VectorHelperObject();
            vectorHelperObject.setsIntegerList(svIList);
            vectorHelperObject.settStrlist(tvList);
            vectorHelperObject.settIntegerList(tvIList);
            vectorHelperObject.setsStrlist(svList);
            vectorHelperObject.setStrings(stringSet);

            List<Integer> score1 = getScoreVector(vectorHelperObject);

            VectorHelperObject vectorHelperObject1 = new VectorHelperObject();
            vectorHelperObject1.setsIntegerList(tvIList);
            vectorHelperObject1.settStrlist(svList);
            vectorHelperObject1.setsStrlist(tvList);
            vectorHelperObject1.setStrings(stringSet);
            vectorHelperObject1.settIntegerList(svIList);
            List<Integer> score2 = getScoreVector(vectorHelperObject1);
            leSim2 = SimAlgorithm.getCosineSim(score1, score2);
        }
        return leSim2;
    }

    public Double getLeSim3(List<OperateType> s, List<OperateType> t)
    {
        Double leSim3 = 0.0;
        if (checkLe(s,t) == 0)
        {
            leSim3 = 0.5;
        }else {
            List<String> svList = new ArrayList<String>();
            List<String> tvList = new ArrayList<String>();
            List<Integer> svIList = new ArrayList<Integer>();
            List<Integer> tvIList = new ArrayList<Integer>();
            for (OperateType sv : s) {
                svList.add(sv.getOperateTypeStr());
                svIList.add(sv.getNum());
            }
            for (OperateType tv : t) {
                tvList.add(tv.getOperateTypeStr());
                tvIList.add(tv.getNum());
            }
            Set<String> stringSet = getUnion(svList, tvList);
            List<String> inteStrlist = getIntersection(svList,tvList);
            VectorHelperObject vectorHelperObject = new VectorHelperObject();
            vectorHelperObject.setsIntegerList(svIList);
            vectorHelperObject.settStrlist(tvList);
            vectorHelperObject.settIntegerList(tvIList);
            vectorHelperObject.setsStrlist(svList);
            vectorHelperObject.setStrings(stringSet);

            List<Integer> score1 = getScoreVector(vectorHelperObject);

            VectorHelperObject vectorHelperObject1 = new VectorHelperObject();
            vectorHelperObject1.setsIntegerList(tvIList);
            vectorHelperObject1.settStrlist(svList);
            vectorHelperObject1.setsStrlist(tvList);
            vectorHelperObject1.setStrings(stringSet);
            vectorHelperObject1.settIntegerList(svIList);
            List<Integer> score2 = getScoreVector(vectorHelperObject1);
            leSim3 = SimAlgorithm.getCosineSim(score1, score2);
        }
        return leSim3;
    }

    public Double getLeSim4(List<MyMethodInvocationName> s, List<MyMethodInvocationName> t)
    {
        Double leSim4 = 0.0;
        if (checkLe(s,t) == 0)
        {
            leSim4 = 0.5;
        }else {
            List<String> svList = new ArrayList<String>();
            List<String> tvList = new ArrayList<String>();
            List<Integer> svIList = new ArrayList<Integer>();
            List<Integer> tvIList = new ArrayList<Integer>();
            for (MyMethodInvocationName sv : s) {
                svList.add(sv.getMethodname());
                svIList.add(sv.getNameNum());
            }
            for (MyMethodInvocationName tv : t) {
                tvList.add(tv.getMethodname());
                tvIList.add(tv.getNameNum());
            }
            Set<String> stringSet = getUnion(svList, tvList);
            VectorHelperObject vectorHelperObject = new VectorHelperObject();
            vectorHelperObject.setsIntegerList(svIList);
            vectorHelperObject.settStrlist(tvList);
            vectorHelperObject.settIntegerList(tvIList);
            vectorHelperObject.setsStrlist(svList);
            vectorHelperObject.setStrings(stringSet);

            List<Integer> score1 = getScoreVector(vectorHelperObject);

            VectorHelperObject vectorHelperObject1 = new VectorHelperObject();
            vectorHelperObject1.setsIntegerList(tvIList);
            vectorHelperObject1.settStrlist(svList);
            vectorHelperObject1.setsStrlist(tvList);
            vectorHelperObject1.setStrings(stringSet);
            vectorHelperObject1.settIntegerList(svIList);
            List<Integer> score2 = getScoreVector(vectorHelperObject1);
            leSim4 = SimAlgorithm.getCosineSim(score1, score2);
        }
        return leSim4;
    }

    public Double getLeSim5(List<MyMethodInvocationPara> s, List<MyMethodInvocationPara> t)
    {

        Double leSim5 = 0.0;
        LexicalFeature lexicalFeature = new LexicalFeature();
        if (checkLe(s,t) == 0)
        {
            leSim5 = 0.5;
        }else {
            List<String> svList = new ArrayList<String>();
            List<String> tvList = new ArrayList<String>();
            List<Integer> svIList = new ArrayList<Integer>();
            List<Integer> tvIList = new ArrayList<Integer>();
            for (MyMethodInvocationPara sv : s) {
                svList.add(sv.getParaName());
                svIList.add(sv.getNum());
            }
            for (MyMethodInvocationPara tv : t) {
                tvList.add(tv.getParaName());
                tvIList.add(tv.getNum());
            }
            Set<String> stringSet = getUnion(svList, tvList);
            VectorHelperObject vectorHelperObject = new VectorHelperObject();
            vectorHelperObject.setsIntegerList(svIList);
            vectorHelperObject.settStrlist(tvList);
            vectorHelperObject.settIntegerList(tvIList);
            vectorHelperObject.setsStrlist(svList);
            vectorHelperObject.setStrings(stringSet);

            List<Integer> score1 = getScoreVector(vectorHelperObject);

            VectorHelperObject vectorHelperObject1 = new VectorHelperObject();
            vectorHelperObject1.setsIntegerList(tvIList);
            vectorHelperObject1.settStrlist(svList);
            vectorHelperObject1.setsStrlist(tvList);
            vectorHelperObject1.setStrings(stringSet);
            vectorHelperObject1.settIntegerList(svIList);
            List<Integer> score2 = getScoreVector(vectorHelperObject1);
            leSim5 = SimAlgorithm.getCosineSim(score1, score2);
        }
        return leSim5;
    }

    public Double getStructSim1(List<ForObject> s, List<ForObject> t)
    {
        Double sim =0.5;
        List<StructParentObject> sparentObjects = new ArrayList<StructParentObject>();
        sparentObjects.addAll(s);
        List<StructParentObject> tparentObjects = new ArrayList<StructParentObject>();
        tparentObjects.addAll(t);
        if (check(sparentObjects,tparentObjects)) {
            sim = getStructSim(sparentObjects,tparentObjects);
        }
        return sim;
    }

    public Double getStructSim2(List<WhileObject> s, List<WhileObject> t)
    {
        Double sim =0.5;
        List<StructParentObject> sparentObjects = new ArrayList<StructParentObject>();
        sparentObjects.addAll(s);
        List<StructParentObject> tparentObjects = new ArrayList<StructParentObject>();
        tparentObjects.addAll(t);
        if (check(sparentObjects,tparentObjects)) {
            sim = getStructSim(sparentObjects,tparentObjects);
        }
        return sim;
    }

    public Double getStructSim3(List<SwitchObject> s, List<SwitchObject> t)
    {
        Double sim =0.5;
        List<StructParentObject> sparentObjects = new ArrayList<StructParentObject>();
        sparentObjects.addAll(s);
        List<StructParentObject> tparentObjects = new ArrayList<StructParentObject>();
        tparentObjects.addAll(t);
        if (check(sparentObjects,tparentObjects)) {
            sim = getStructSim(sparentObjects,tparentObjects);
        }
        return sim;
    }

    public Double getStructSim4(List<DoWhileObject> s, List<DoWhileObject> t)
    {
        Double sim =0.5;
        List<StructParentObject> sparentObjects = new ArrayList<StructParentObject>();
        sparentObjects.addAll(s);
        List<StructParentObject> tparentObjects = new ArrayList<StructParentObject>();
        tparentObjects.addAll(t);
        if (check(sparentObjects,tparentObjects)) {
            sim = getStructSim(sparentObjects,tparentObjects);
        }
        return sim;
    }

    public Double getStructSim5(List<IfObject> s, List<IfObject> t)
    {
        Double sim =0.5;
        List<StructParentObject> sparentObjects = new ArrayList<StructParentObject>();
        sparentObjects.addAll(s);
        List<StructParentObject> tparentObjects = new ArrayList<StructParentObject>();
        tparentObjects.addAll(t);
        if (check(sparentObjects,tparentObjects)) {
            sim = getStructSim(sparentObjects,tparentObjects);
        }
        return sim;
    }



    private List<Integer> getScoreVector(VectorHelperObject vectorHelperObject)
    {
        Set<String> strings = vectorHelperObject.getStrings();
        List<String> slist = vectorHelperObject.getsStrlist();
        List<Integer> sIntegerList = vectorHelperObject.getsIntegerList();
        List<Integer> list = new ArrayList<Integer>();
        List<String> tlist = vectorHelperObject.gettStrlist();
        List<Integer> tIntegerList = vectorHelperObject.gettIntegerList();
        for (String s:strings)
        {
            Boolean b = false;
            for (int i=0;i<slist.size();i++)
            {
                if (s.equals(slist.get(i)))
                {
                    list.add(sIntegerList.get(i));
                    b=true;
                    break;
                }
            }
            if (!b)
            {
                list.add(0);
            }
        }
        int sn=0;
        int dn=0;
        if (slist.size() != 0 && tlist.size() !=0  && strings.size() == 1) {
                if (sIntegerList.get(0) < tIntegerList.get(0)) {
                    dn = tIntegerList.get(0) - sIntegerList.get(0);
                } else {
                    sn = sIntegerList.get(0) - tIntegerList.get(0);
                }
        }
      for (int i=0;i<slist.size();i++)
      {
          Boolean isContain = false;
          for (int j=0;j<tlist.size();j++)
          {
              if (slist.get(i).equals(tlist.get(j)))
              {
                  isContain= true;
                  break;
              }
          }
          if (!isContain)
          {
              sn = sn +sIntegerList.get(i);
          }
      }
        for (int i=0;i<tlist.size();i++)
        {
            Boolean isContain = false;
            for (int j=0;j<slist.size();j++)
            {
                if (tlist.get(i).equals(slist.get(j)))
                {
                    isContain= true;
                    break;
                }
            }
            if (!isContain)
            {
                dn = dn +tIntegerList.get(i);
            }
        }

      list.add(sn);
      list.add(dn);
        return list;
    }

    private Set<String> getUnion(List<String> stringList,List<String> stringList1)
    {
        Set<String> stringSet = new HashSet<String>();
        for (String s:stringList)
        {
            stringSet.add(s);
        }
        for (String s1:stringList1)
        {
            stringSet.add(s1);
        }
        return stringSet;
    }

    /**
     * 得到功能相似度
     * @param s
     * @param t
     * @return
     */
    public Double getFunSim(List<ParaObject> s, List<ParaObject> t)
    {
        Double sim =0.0;
        Double x = 0.3;
        sim = x*getfirstSim(s,t)+(1-x)*getSecondSim(s,t);
        return sim;
    }

    private Double getSecondSim(List<ParaObject> s, List<ParaObject> t) {
        Double sim=0.0;
        List<String> svList = new ArrayList<String>();
        List<String> tvList = new ArrayList<String>();
        List<Integer> svIList = new ArrayList<Integer>();
        List<Integer> tvIList = new ArrayList<Integer>();
        for (ParaObject sv:s)
        {
            svList.add(sv.getParameterTypeName());
        }
        for (ParaObject tv:t)
        {
            tvList.add(tv.getParameterTypeName());
        }
        Map<String,Integer> map = deleteDuplicate(svList);
        Map<String,Integer> map1 = deleteDuplicate(tvList);
        svList.clear();
        tvList.clear();
        FunctionFeature functionFeature = new FunctionFeature();
        for (String str:map.keySet())
        {
            int value = map.get(str);
            Integer typeIndex=0;
            for (ParaObject paraObject:s)
            {
                if (paraObject.getParameterTypeName().equals(str))
                {
                    typeIndex = paraObject.getTypeIndex();
                    break;
                }
            }
            int score = functionFeature.getParaScore(typeIndex);
            svList.add(str);
            svIList.add(value*score);
        }
        for (String str1:map1.keySet())
        {
            int value = map1.get(str1);
            Integer typeIndex1 = 0;
            for (ParaObject paraObject:t)
            {
                if (paraObject.getParameterTypeName().equals(str1))
                {
                    typeIndex1 = paraObject.getTypeIndex();
                    break;
                }
            }
            int score = functionFeature.getParaScore(typeIndex1);
            tvList.add(str1);
            tvIList.add(value*score);
        }
        Set<String> stringSet = getUnion(svList,tvList);
        VectorHelperObject vectorHelperObject = new VectorHelperObject();
        vectorHelperObject.setsIntegerList(svIList);
        vectorHelperObject.settStrlist(tvList);
        vectorHelperObject.settIntegerList(tvIList);
        vectorHelperObject.setsStrlist(svList);
        vectorHelperObject.setStrings(stringSet);

        List<Integer> score1 = getScoreVector(vectorHelperObject);

        VectorHelperObject vectorHelperObject1 = new VectorHelperObject();
        vectorHelperObject1.setsIntegerList(tvIList);
        vectorHelperObject1.settStrlist(svList);
        vectorHelperObject1.setsStrlist(tvList);
        vectorHelperObject1.setStrings(stringSet);
        vectorHelperObject1.settIntegerList(svIList);
        List<Integer> score2 = getScoreVector(vectorHelperObject1);
        sim = SimAlgorithm.getCosineSim(score1,score2);
        return sim;
    }

    private Double getfirstSim(List<ParaObject> s, List<ParaObject> t) {
        Double sim=0.0;
        List<String> svList = new ArrayList<String>();
        List<String> tvList = new ArrayList<String>();
        List<Integer> svIList = new ArrayList<Integer>();
        List<Integer> tvIList = new ArrayList<Integer>();
        for (ParaObject sv:s)
        {
            svList.add(String.valueOf(sv.getTypeIndex()));
        }
        for (ParaObject tv:t)
        {
            tvList.add(String.valueOf(tv.getTypeIndex()));
        }
        Map<String,Integer> map = deleteDuplicate(svList);
        Map<String,Integer> map1 = deleteDuplicate(tvList);
        svList.clear();
        tvList.clear();
        FunctionFeature functionFeature = new FunctionFeature();
        for (String str:map.keySet())
        {
            int value = map.get(str);
            int score = functionFeature.getParaScore(Integer.valueOf(str));
            svList.add(str);
            svIList.add(value*score);
        }
        for (String str:map1.keySet())
        {
            int value = map1.get(str);
            int score = functionFeature.getParaScore(Integer.valueOf(str));
            tvList.add(str);
            tvIList.add(value*score);
        }
        Set<String> stringSet = getUnion(svList,tvList);
        VectorHelperObject vectorHelperObject = new VectorHelperObject();
        vectorHelperObject.setsIntegerList(svIList);
        vectorHelperObject.settStrlist(tvList);
        vectorHelperObject.settIntegerList(tvIList);
        vectorHelperObject.setsStrlist(svList);
        vectorHelperObject.setStrings(stringSet);

        List<Integer> score1 = getScoreVector(vectorHelperObject);

        VectorHelperObject vectorHelperObject1 = new VectorHelperObject();
        vectorHelperObject1.setsIntegerList(tvIList);
        vectorHelperObject1.settStrlist(svList);
        vectorHelperObject1.setsStrlist(tvList);
        vectorHelperObject1.setStrings(stringSet);
        vectorHelperObject1.settIntegerList(svIList);
        List<Integer> score2 = getScoreVector(vectorHelperObject1);
        sim = SimAlgorithm.getCosineSim(score1,score2);
        return sim;
    }

    public  Double getWholeStructSim(WholeStructObject s,WholeStructObject t)
    {
        Double sim6=0.0;

        List<Integer> svIList = new ArrayList<Integer>();
        List<Integer> tvIList = new ArrayList<Integer>();



        svIList.add(s.getMaxDepth());
        //svIList.add(s.getSecondLevelNum());
        svIList.add(s.getAllNodesNum());
        svIList.add(s.getLeafNum());

        tvIList.add(t.getMaxDepth());
        //tvIList.add(t.getSecondLevelNum());
        tvIList.add(t.getAllNodesNum());
        tvIList.add(t.getLeafNum());
        sim6 = SimAlgorithm.getCosineSim(svIList,tvIList);
        return sim6;
    }

    /**
     * 去重，并统计个数
     * @param list
     * @return
     */
    private Map<String,Integer> deleteDuplicate(List<String> list)
    {
        Map<String,Integer> map = new HashMap<String, Integer>();
        for (String s:list)
        {
            if (map.containsKey(s))
            {
                int value = map.get(s);
                value++;
                map.put(s,value);
            }
            else
            {
                map.put(s,1);
            }
        }
        return map;
    }

    private Boolean check(List s,List t)
    {
        if (s == null && t == null)
        {
            return false;
        }
        else if (s == null || t == null)
        {
            return true;
        }
        else if (s.size()==0&&t.size()==0)
        {
            return false;
        }
        else
            return true;

    }

    private void printToFile(String content)
    {
        Date date = new Date();
        DateFormat df3 = new SimpleDateFormat("yyy-MM-dd HH-mm-ss");
        String filepath = ".\\TrainDataFiles\\"+df3.format(date);
        FileUtils.writeToFiles(content,filepath);
    }

    private Integer checkLe(List s,List t)
    {
        if (s  == null && t ==null)
            return 0;
        else if (s==null || t==null)
        {
            return 1;
        }
        else if (s.size()==0 && t.size()==0)
        {
            return 0;
        }
        else if (s.size() == 0 || t.size()==0)
        {
            return 2;
        }
        else
            return 3;

    }

    private List<String> getIntersection(List<String> s,List<String> t)
    {
        List<String> list = new ArrayList<String>();
        list.addAll(s);

        List<String> list1 = new ArrayList<String>();
        list1.addAll(t);

        if (list.size()>list1.size())
        {
            list.retainAll(list1);
            return list;
        }else {
            list1.retainAll(list);
            return list1;
        }
    }

    private List<Map<String,Integer>> convertToMap(List<StructParentObject> structParentObjects){
        List<Map<String,Integer>> list = new ArrayList<Map<String, Integer>>();
        for (int i=0;i<structParentObjects.size();i++){
            Map<String,Integer> smap = new HashMap<String, Integer>();
            smap.put(StructureStaticValue.XINDEX,structParentObjects.get(i).getX());
            smap.put(StructureStaticValue.YINDEX,structParentObjects.get(i).getY());
            smap.put(StructureStaticValue.ZINDEX,structParentObjects.get(i).getZ());
            list.add(smap);
        }
        return list;
    }

    private Boolean[][] isNearOrEqual(List<Map<String,Integer>> smap,List<Map<String,Integer>> tmap){
        Integer[][] psdm = SimAlgorithm.getManhattanDisMatrix(smap,tmap);
        Boolean[][] booleans = new Boolean[psdm.length][psdm[0].length];//???
        for (int i=0;i<psdm.length;i++){
            int min=psdm[i][0];
            List<Integer> minlist = new ArrayList<Integer>();
            for (int j=0;j<psdm[i].length;j++){
                if (min>psdm[i][j]){
                    min = psdm[i][j];
                }
            }
            for (int j=0;j<psdm[i].length;j++){
                if (min == psdm[i][j]){
                    minlist.add(j);
                }
            }
            if (minlist.size() == 1){
                Integer index = minlist.get(0);
                for (int m=0;m<psdm[i].length;m++){
                    if (m==index && psdm[i][m]<=StructureStaticValue.DT){
                        booleans[i][m] = true;
                    }else {
                        booleans[i][m] = false;
                    }
                }
            }else{
                int sy = smap.get(i).get(StructureStaticValue.YINDEX);
                int flag=-1;
                for (int m=0;m<minlist.size();m++){
                    int index = minlist.get(m);
                    Map<String,Integer> map = tmap.get(index);
                    int ty = map.get(StructureStaticValue.YINDEX);
                    if (sy==ty && psdm[i][index]<=StructureStaticValue.DT){
                        flag = index;
                        break;
                    }
                }
                if (flag != -1) {
                    for (int j = 0; j < psdm[i].length; j++) {
                        if (j == flag){
                            booleans[i][j] = true;
                        }else {
                            booleans[i][j] = false;
                        }
                    }
                }else{
                    int mintemp= Math.abs(i-minlist.get(0));
                    int indextemp = 0;
                    for (int m=0;m<minlist.size();m++){
                        if (mintemp>Math.abs(i-minlist.get(m))){
                            indextemp = m;
                            mintemp = Math.abs(i-minlist.get(m));
                        }
                    }
                    int flag2 = -1;
                    if (psdm[i][indextemp]<=StructureStaticValue.DT){
                        flag2 = indextemp;
                    }
                    if (flag2 != -1) {
                        for (int j = 0; j < psdm[i].length; j++) {
                            if (j == flag2){
                                booleans[i][j] = true;
                            }else {
                                booleans[i][j] = false;
                            }
                        }
                    }else{
                        for (int j=0;j<psdm[i].length;j++){
                            booleans[i][j] = false;
                        }
                    }
                }
            }
        }
        for (int j=0;j<psdm[0].length;j++){
            int count =0 ;
            List<Integer> trueList = new ArrayList<Integer>();
            for (int i=0;i<psdm.length;i++){
                if (booleans[i][j]){
                    count++;
                    trueList.add(i);
                }
            }
            if (count>1){
                int mintemp = psdm[trueList.get(0)][j];
                List<Integer> list = new ArrayList<Integer>();
                for (int m=0;m<trueList.size();m++) {
                    int index = trueList.get(m);
                    if (mintemp>psdm[index][j]){
                        mintemp = psdm[index][j];
                    }
                }
                for (int i=0;i<psdm.length;i++){
                    if (psdm[i][j] == mintemp){
                        list.add(i);
                    }
                }
                if (list.size() == 1){
                    int index = list.get(0);
                    for (int i=0;i<psdm.length;i++){
                        if (booleans[i][j]) {
                            if (i != index) {
                                booleans[i][j] = false;
                            }
                        }
                    }
                }else{
                    int mintemp2 = Math.abs(j-list.get(0));
                    int indexFlag = 0 ;
                    for (int m=0;m<list.size();m++){
                        if (mintemp2>Math.abs(j-list.get(m))){
                            indexFlag = m;
                            mintemp2 = Math.abs(j-list.get(m));
                        }
                    }
                    for (int i=0;i<psdm.length;i++){
                        if (booleans[i][j]) {
                            if (i != indexFlag) {
                                booleans[i][j] = false;
                            }
                        }
                    }
                }
            }
        }
        return booleans;
    }

    private Double getStructSim(List<StructParentObject> sparentObjects,List<StructParentObject> tparentObjects){
        List<String> svList = new ArrayList<String>();
        List<String> tvList = new ArrayList<String>();
        List<Integer> svIList = new ArrayList<Integer>();
        List<Integer> tvIList = new ArrayList<Integer>();
        if (sparentObjects.size() !=0 && tparentObjects.size()!= 0) {

            Boolean[][] booleans = isNearOrEqual(convertToMap(sparentObjects), convertToMap(tparentObjects));
            Map<Integer, Integer> integerIntegerMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < booleans.length; i++) {
                for (int j = 0; j < booleans[i].length; j++) {
                    if (booleans[i][j]) {
                        integerIntegerMap.put(i, j);
                    }
                }
            }
            for (int i = 0; i < sparentObjects.size(); i++) {
                if (integerIntegerMap.containsKey(i)) {
                    svList.add(String.valueOf(i));
                } else {
                    svList.add(sparentObjects.get(i).getX() + "," + sparentObjects.get(i).getY() + "," + sparentObjects.get(i).getZ());
                }
                svIList.add(sparentObjects.get(i).getNum());
            }
            for (int j = 0; j < tparentObjects.size(); j++) {
                Boolean isValueExist = false;
                for (Integer i : integerIntegerMap.keySet()) {
                    if (j == integerIntegerMap.get(i)) {
                        tvList.add(String.valueOf(i));
                        isValueExist = true;
                        break;
                    }
                }
                if (!isValueExist) {
                    tvList.add(tparentObjects.get(j).getX() + "," + tparentObjects.get(j).getY() + "," + tparentObjects.get(j).getZ());
                }
                svIList.add(tparentObjects.get(j).getNum());
            }
        }else{
            for (StructParentObject structParentObject:sparentObjects){
                svList.add(structParentObject.getX()+","+structParentObject.getY()+","+structParentObject.getZ());
                svIList.add(structParentObject.getNum());
            }

            for (StructParentObject structParentObject:tparentObjects){
                tvList.add(structParentObject.getX()+","+structParentObject.getY()+","+structParentObject.getZ());
                tvIList.add(structParentObject.getNum());
            }
        }
        if (svList.size() == svIList.size() && tvList.size() == tvIList.size()) {
            Set<String> stringSet = getUnion(svList, tvList);
            VectorHelperObject vectorHelperObject = new VectorHelperObject();
            vectorHelperObject.setsIntegerList(svIList);
            vectorHelperObject.settStrlist(tvList);
            vectorHelperObject.settIntegerList(tvIList);
            vectorHelperObject.setsStrlist(svList);
            vectorHelperObject.setStrings(stringSet);

            List<Integer> score1 = getScoreVector(vectorHelperObject);

            VectorHelperObject vectorHelperObject1 = new VectorHelperObject();
            vectorHelperObject1.setsIntegerList(tvIList);
            vectorHelperObject1.settStrlist(svList);
            vectorHelperObject1.setsStrlist(tvList);
            vectorHelperObject1.setStrings(stringSet);
            vectorHelperObject1.settIntegerList(svIList);
            List<Integer> score2 = getScoreVector(vectorHelperObject1);
            Double sim = SimAlgorithm.getCosineSim(score1, score2);
            return sim;
        }else{
            return 0.0;
        }
    }




}