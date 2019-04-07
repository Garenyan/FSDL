package Test;

import Features.SimAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garen on 2019/3/13.
 */
public class SimTest {
    public static void main(String args[])
    {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> list1 = new ArrayList<Integer>();
//
//        list.add(4);
//        list.add(3);
//        list.add(5);
//        list.add(0);
//        list.add(5);
//        list.add(1);
//        for (int i=0;i<100;i++)
//        {
//            list.add(1);
//            list1.add(2);
//        }
        list.add(10);
        list.add(10);
        list.add(90);

        list1.add(100);
        list1.add(90);
        list1.add(10);
//
//        list.add(20);
//        list.add(0);
//        list1.add(200);
//        list1.add(0);


//        list1.add(0);
//        list1.add(3);
//        list1.add(4);
//        list1.add(1);
//        list1.add(1);
//        list1.add(5);

        Double sim1 = SimAlgorithm.getCosineSim(list,list1);



        System.out.println(sim1);
    }
}
