package wjc920.java.basic.singleton;

import java.util.Random;
import java.util.stream.IntStream;

/** 懒汉，与HungryMode对应
 * @author: wjc
 * @date: 2018/11/16 16:03
 */
public class HolderMode {
    public int value = new Random().nextInt(10);
    private HolderMode(){
        System.out.println("inited...");
    }
    private static class Hodler{
        private static HolderMode holderMode = new HolderMode();
    }
    public static HolderMode getInstance(){
        return Hodler.holderMode;
    }

    public static void main(String[] args) {
        IntStream.range(0,10).forEach(i-> System.out.println(HolderMode.getInstance().value));
    }
}
