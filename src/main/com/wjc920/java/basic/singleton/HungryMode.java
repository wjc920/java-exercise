package wjc920.java.basic.singleton;

import java.util.Random;
import java.util.stream.IntStream;
/**
 * 饿汉模式，懒汉模式见Holder
 * @author: wjc
 * @date: 2018/11/16 16:05
 */
public class HungryMode {
    private static HungryMode hungryMode = new HungryMode();
    public int value = new Random().nextInt(10);
    private HungryMode(){
        System.out.println("inited...");
    }

    public static HungryMode getInstance(){
        return hungryMode;
    }

    /** 只初始化一次，满足并发要求
     * @param: [args]
     * @return: void
     * @author: wjc
     * @date: 2018/11/16 15:23
     */
    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(i -> new Thread(() -> System.out.println(HungryMode.getInstance().value)).start());
    }
}
