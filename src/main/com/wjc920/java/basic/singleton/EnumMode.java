package wjc920.java.basic.singleton;

import java.util.EmptyStackException;
import java.util.Random;
import java.util.stream.IntStream;

/**饿汉模式
 * @author wjc
 * @date 2018/11/16 16:23
 */
public enum EnumMode {
    INSTANCE;
    private EnumMode(){
        System.out.println("inited...");
    }
    public static EnumMode getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        IntStream.range(0,10).forEach(i->EnumMode.getInstance());
    }
}
