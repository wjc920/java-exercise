package wjc920.java.basic.concurrent;

public class VolatileTest {
    private volatile static int initValue;
    private final static int MAX=5;

    public static void main(String[] args) {
        new Thread(() -> {
            int cacheInitValue = initValue;
            while (cacheInitValue < MAX) {
                if (cacheInitValue != initValue) {
                    System.out.println("initValue updated to:" + initValue);
                    cacheInitValue = initValue;
                }
            }
        }, "Reader").start();
        new Thread(() -> {
            int cacheInitValue = initValue;
            while (cacheInitValue < MAX) {
                System.out.println("initValue is changed to:" + ++cacheInitValue);
                initValue = cacheInitValue;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Writer").start();
    }
//    initValue is changed to:1
//    initValue updated to:1
//    initValue is changed to:2
//    initValue updated to:2
//    initValue is changed to:3
//    initValue updated to:3
//    initValue is changed to:4
//    initValue updated to:4
//    initValue is changed to:5
//    initValue updated to:5
}
