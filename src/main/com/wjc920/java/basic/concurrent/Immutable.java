package wjc920.java.basic.concurrent;

import java.util.stream.IntStream;

/*
 * 不可变对象保证线程安全
 * @author: wjc
 * @date: 2018/11/13 21:47
 */
public final class Immutable {
    private final int value;

    public Immutable(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Immutable add(int newValue){
        return new Immutable(this.getValue() + newValue);
    }

    private static volatile Immutable immutable = new Immutable(0);
    public static void main(String[] args) {
        IntStream.range(1, 3).forEach(i -> new Thread(() -> {
            int inc = 0;
            while (true) {
                int oldValue = immutable.getValue();
                immutable = immutable.add(inc);
                int newValue = immutable.getValue();
                System.out.println(oldValue + "+" + inc + "=" + newValue);
                if (oldValue + inc != newValue) {
                    System.err.println("oldValue: " + oldValue + ",inc: " + inc + ",newValue: " + newValue);
                }
                inc++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start());
    }
}
