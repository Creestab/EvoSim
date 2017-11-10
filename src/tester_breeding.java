import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.0.1
 * @date 11/9/17
 */

public class tester_breeding {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		creatures.add(new Creature("AA",1,2));
		creatures.add(new Creature("AB",1,2));
		creatures.add(new Creature("BC",1,2));
		creatures.add(new Creature("ABA",1,3));
		creatures.add(new Creature("ABAC",2,2));
		creatures.add(new Creature("BCBD",2,2));
		creatures.add(new Creature("ABACADAE",3,2));
		creatures.add(new Creature("ABACADAEA",2,3));
		creatures.add(new Creature("BCDEFGHIJ",2,3));

		System.out.println("Creature 1 breeds with creature 3");
		creatures.add(new Creature(creatures.get(0), creatures.get(2)));
		System.out.println("Creature 5 breeds with creature 6");
		creatures.add(new Creature(creatures.get(4), creatures.get(5)));
		System.out.println("Creature 8 breeds with creature 9");
		creatures.add(new Creature(creatures.get(7), creatures.get(8)));
		
		for(int i = 0; i < creatures.size(); i++){
			System.out.println("Creature #: " + (i + 1));
			creatures.get(i).print();
			System.out.println();
		}
	}
}
