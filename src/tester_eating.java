import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.0.1
 * @date 11/5/17
 */

public class tester_eating {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		creatures.add(new Creature("AA",1,2));
		creatures.add(new Creature("AB",1,2));
		creatures.add(new Creature("BC",1,2));
		creatures.add(new Creature("ABC",1,3));
		creatures.add(new Creature("ABCD",2,2));
		creatures.add(new Creature("BCDE",2,2));
		creatures.add(new Creature("ABCDEFGH",3,2));
		creatures.add(new Creature("ABCDEFGHI",2,3));

		
	}

}
