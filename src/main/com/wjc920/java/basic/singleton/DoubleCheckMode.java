package wjc920.java.basic.singleton;
import java.util.stream.IntStream;

/**在《Java高并发编程详解》-汪文君版 中，说该模式在高并发时，可能会产生：doubleCheckMode已经初始化，但是value1，value2，value3
 * 三个变量未初始化的情况，其实在我看来，这种情况是可以避免的，对doubleCheckMode初始化的唯一入口就是注释1处，而在对doubleCheckMode
 * 赋值之前，构造器中的对三个value的赋值必须先完成，这样就不存在并发问题了。
 * @author wjc
 * @date 2018/11/1616:08
 */
public class DoubleCheckMode {
    private Integer value1;
    private Integer value2;
    private Integer value3;
    private static DoubleCheckMode doubleCheckMode;
    private DoubleCheckMode(){
        value1 = new Integer(1);
        value1 = new Integer(2);
        value1 = new Integer(3);
        System.out.println("inited...");
    }
    public static DoubleCheckMode getInstance(){
        if(doubleCheckMode==null){
            synchronized (DoubleCheckMode.class){
                if(doubleCheckMode==null){
                    //1
                    doubleCheckMode = new DoubleCheckMode();
                }
            }
        }
        return doubleCheckMode;
    }
    public static void main(String[] args) {
        IntStream.range(0,10).forEach(i->DoubleCheckMode.getInstance());
    }
}
