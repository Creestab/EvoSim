import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.0.1
 * @date 11/9/17
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
		creatures.add(new Creature("ABA",1,3));
		creatures.add(new Creature("ABAC",2,2));
		creatures.add(new Creature("BCBD",2,2));
		creatures.add(new Creature("ABACADAE",3,2));
		creatures.add(new Creature("ABACADAEA",2,3));

		System.out.println("Creature 1 Diet: " + creatures.get(0).getNutrition());
		System.out.println("Creature 2 Diet: " + creatures.get(1).getNutrition());
		System.out.println("Creature 3 Diet: " + creatures.get(2).getNutrition());
		System.out.println("Creature 4 Diet: " + creatures.get(3).getNutrition());
		System.out.println("Creature 5 Diet: " + creatures.get(4).getNutrition());
		System.out.println("Creature 6 Diet: " + creatures.get(5).getNutrition());
		System.out.println("Creature 7 Diet: " + creatures.get(6).getNutrition());
		System.out.println("Creature 8 Diet: " + creatures.get(7).getNutrition());
		System.out.println();

		for(int i = 0; i < creatures.size() -1; i++){
			creatures.get(i).eat(creatures.get(i+1).getGeneticCode(), creatures.get(i+1).getGeneComplexity());
			System.out.println("Creature " + (i + 1) + " eats creature " + (i + 2));
		}
		creatures.get(creatures.size() - 1).eat(creatures.get(0).getGeneticCode(), creatures.get(0).getGeneComplexity());
		System.out.println("Creature " + creatures.size() + " eats creature 1" + '\n');
		
		for(int i = 0; i < creatures.size(); i++){
			System.out.println("Creature #: " + (i + 1));
			creatures.get(i).print();
			System.out.println();
		}
	}
}
