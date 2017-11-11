import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.1.1
 * @date 11/9/17
 */
public class SimFuncs {
	public SimFuncs() {
		
	}

	/**
	 * Creates an array of random Creatures of length 50.
	 * @return the array of Creatures.
	 */
	public ArrayList<Creature> populate(){
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		for(int i = 0; i < 50; i++){
			creatures.add(new Creature());
		}
		return creatures;
	}
	
	/**
	 * Creates an array of random Creatures of length 50.
	 * @param l the number of Creatures to create.
	 * @return the array of Creatures.
	 */
	public ArrayList<Creature> populate(int l){
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		for(int i = 0; i < l; i++){
			creatures.add(new Creature());
		}
		return creatures;
	}
	
	/**
	 * Determines which creature will eat the other in a paring.
	 * @param c1 is a creature in a pair.
	 * @param c2 is the other creature in a pair.
	 * @return if true, c1 eats c2. If false, c2 eats c1.
	 */
	public boolean whoEatsWho(Creature c1, Creature c2){
		return c1.getLevel() * ((c1.geneToSeed() % 3) + 1) * Math.floor(c1.getHungerCurrent() / c1.getHungerMax()) 
				> c2.getLevel() * ((c2.geneToSeed() % 3) + 1) * Math.floor(c2.getHungerCurrent() / c2.getHungerMax());
	}
	
	/**
	 * Determines if two creatures are compatible for breeding.
	 * @param c1 is a creature in a pair.
	 * @param c2 is the other creature in a pair.
	 * @return if true, the pair is compatible for breeding.
	 */
	public boolean breedable(Creature c1, Creature c2){
		return (c1.getGeneComplexity() == c2.getGeneComplexity()) && (c1.getGeneSize() == c2.getGeneSize());
	}
	
	public boolean RIP(Creature c) {
		return (c.getAgeCurrent() >= c.getAgeMax() || c.getHungerCurrent() <= 0);
	}
	
	/**
	 * Go's through a set of Creatures and prints out all of their info in a neat and concise fassion.
	 * @param creatures the array of Creatures.
	 */
	public void printCreatures(ArrayList<Creature> creatures){
		int l = creatures.size();
		for(int i = 0; i < l; i++) {creatures.get(i).print(); 	System.out.println();}
	}
	
	/**
	 * NOT CURRENTLY OPERATIONAL
	 * @param creatures the array of Creatures.
	 */
	public void printCreaturesToFile(ArrayList<Creature> creatures){
		int l = creatures.size();
		for(int i = 0; i < l; i++) {creatures.get(i).print();	System.out.println();}
	}
}
