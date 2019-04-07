package Features;

import StaticValue.StructureStaticValue;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2019/3/11.
 */
public class SimAlgorithm {
    public static Double getCosineSim(List<Integer> score1,List<Integer> score2)
    {
        if (score1 == null || score2==null || score1.size()<1 || score2.size()<1)
        {
            return null;
        }
        int size =score1.size();
        Double fenzi = 0.0;
        for (int i=0;i<size;i++)
        {
            fenzi = fenzi + (double)score1.get(i) * score2.get(i);
        }
        Double sum1 =0.0;
        Double sum2 =0.0;
        for (int i=0;i<size;i++)
        {
            sum1 = sum1 + (double)score1.get(i)*score1.get(i);
        }
        for (int i=0;i<size;i++)
        {
            sum2 = sum2 + (double)score2.get(i)*score2.get(i);
        }
        Double fenmu = Math.sqrt(sum1) * Math.sqrt(sum2);
        Double sim = fenzi / fenmu;
        return sim;
    }

    public static Double getModifiedSim(Integer num1,Integer num2,Integer lineNum1,Integer lineNum2)
    {
        int fenzi = 0;
        if (num2>num1)
        {
            fenzi = num2 -num1;
        }else
            fenzi = num1-num2;
        int fenmu = 0;
        if (lineNum1>lineNum2)
        {
            fenmu = lineNum2;
        }else
            fenmu = lineNum1;
        Double sim = 0.5*(1-fenzi/fenmu);
        return sim;
    }


    public static Double getManhattanSim(List<Integer> list,List<Integer> list1)
    {
        int size = list.size();
        int sum=0;
        for (int i=0;i<size;i++)
        {
            sum = sum + Math.abs(list.get(i)-list1.get(i));
        }
        Double sim = (double)1/(1+sum);
        return sim;
    }

    public static Double getEuclideanSim(List<Integer> list1,List<Integer> list2){
        int sum=0;
        if (list1 == null || list2 == null)
        {
            return null;
        }
        int size = list1.size();
        int size1 = list2.size();
        if (size != size1)
        {
            return null;
        }
        for (int i=0;i<size;i++){
            sum = sum + Math.abs(list1.get(i) - list2.get(i))*Math.abs(list1.get(i)-list2.get(i));
        }
        double sqrtValue = Math.sqrt((double)sum);
        double sim = 1/(1+sqrtValue);
        return sim;
    }


    public static Double[][] getEuclideanDistanceMatrix(List<Map<String,Integer>> s, List<Map<String,Integer>> t){
        if (s == null || t == null)
        {
            return null;
        }
        int size = s.size();
        int size1 = t.size();
        Double[][] psdm = new Double[size][size1];
       for (int i=0;i<size;i++){
           Map<String,Integer> map = s.get(i);
           Integer x = map.get(StructureStaticValue.XINDEX);
           Integer y = map.get(StructureStaticValue.YINDEX);
           Integer z = map.get(StructureStaticValue.ZINDEX);
           for(int j=0;j<size1;j++){
               Map<String,Integer> map1 = t.get(i);
               Integer x1 = map1.get(StructureStaticValue.XINDEX);
               Integer y1 = map1.get(StructureStaticValue.YINDEX);
               Integer z1 = map1.get(StructureStaticValue.ZINDEX);
               psdm[i][j] = getEuclideanDistance(x,y,z,x1,y1,z1);

           }
       }
        return psdm;
    }

    public static Double getEuclideanDistance(int x,int y,int z,int x1,int y1,int z1){
        int sum =0 ;
        sum = (x-x1)*(x-x1) + (y-y1)*(y-y1) + (z-z1)*(z-z1);
        Double distance = Math.sqrt((double)sum);
        return distance;
    }


    public static Integer[][] getManhattanDisMatrix(List<Map<String,Integer>> s, List<Map<String,Integer>> t){
        if (s == null || t == null)
        {
            return null;
        }
        int size = s.size();
        int size1 = t.size();
        Integer[][] psdm = new Integer[size][size1];
        for (int i=0;i<size;i++){
            Map<String,Integer> map = s.get(i);
            Integer x = map.get(StructureStaticValue.XINDEX);
            Integer y = map.get(StructureStaticValue.YINDEX);
            Integer z = map.get(StructureStaticValue.ZINDEX);
            for(int j=0;j<size1;j++){
                Map<String,Integer> map1 = t.get(j);
                Integer x1 = map1.get(StructureStaticValue.XINDEX);
                Integer y1 = map1.get(StructureStaticValue.YINDEX);
                Integer z1 = map1.get(StructureStaticValue.ZINDEX);
                psdm[i][j] = getManhattanDistance(x,y,z,x1,y1,z1);

            }
        }
        return psdm;

    }


    private static Integer getManhattanDistance(int x,int y,int z,int x1,int y1,int z1){
        return Math.abs(x-x1) + Math.abs(y-y1) + Math.abs(z-z1);
    }

}
