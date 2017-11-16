import java.util.*;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.1.3
 * @date 11/16/17
 */
public class tester_breeding {
	public static void main(String[] args) {
		SimFuncs sim = new SimFuncs();
		
		Creature c1 = new Creature("AA", 2, 1);
		Creature c2 = new Creature("BB", 2, 1);
		Creature c3 = new Creature(c1, c2);
		
		c1.print(); System.out.println();
		c2.print(); System.out.println();
		c3.print();
	}
}
