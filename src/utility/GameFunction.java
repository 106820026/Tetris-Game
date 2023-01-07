package utility;

public class GameFunction {

    // 計算下落軌跡方法
    public static int sleepTime(int level){
        int sleep = (-100 * level + 900);
        sleep = sleep  < 100 ? 100 : sleep;
        return sleep;
    }
}
