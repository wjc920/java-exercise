package algorithm;

import java.util.Random;

import org.junit.Test;

public class RBTTest {
	
	private RBT<Integer, Integer> rbt=new RBT<>();
	Random r=new Random();
	
	@Test
	public void testInsert() {
		int size=300;
		while(size-->0) {
			int tmp=r.nextInt(1000);
			rbt.put(tmp, tmp);
			if(!rbt.isRBT()) {
				System.out.println(true);
			}
		}
	}
	
	@Test
	public void testDelete() {
		testInsert();
		int size=300;
		while(size-->0) {
			int tmp=r.nextInt(1000);
			rbt.remove(tmp);
			if(!rbt.isRBT()) {
				System.out.println(false);
			}
		}
	}
	

	
}
