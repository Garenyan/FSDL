package MyTools;

/**
 * Created by garen on 2019/4/2.
 */
public class MyThread {
    public static void waitForTime(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
