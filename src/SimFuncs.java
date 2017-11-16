import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.1.2
 * @date 11/16/17
 */
public class SimFuncs {
	
	int curTick;
	
	public SimFuncs() {
		curTick = -1;
	}
	public SimFuncs(int tick) {
		curTick = tick;
	}

	/**
	 * Creates an array of random Creatures of length 50.
	 * @return the array of Creatures.
	 */
	public ArrayList<Creature> populate(){
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		if(curTick == -1){
			for(int i = 0; i < 50; i++){
				creatures.add(new Creature());
			}
		}
		else{
			for(int i = 0; i < 50; i++){
				creatures.add(new Creature(curTick));
			}
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
		if(curTick == -1){
			for(int i = 0; i < l; i++){
				creatures.add(new Creature());
			}
		}
		else{
			for(int i = 0; i < l; i++){
				creatures.add(new Creature(curTick));
			}
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
		return Math.pow(c1.getLevel(), c1.getGeneComplexity()) * ((c1.geneToSeed() % 3) + 1) * Math.floor(c1.getHungerCurrent() / c1.getHungerMax()) 
				> Math.pow(c2.getLevel(), c2.getGeneComplexity()) * ((c2.geneToSeed() % 3) + 1) * Math.floor(c2.getHungerCurrent() / c2.getHungerMax());
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
	 * Generates a random element.
	 * @return the element.
	 */
	public char eleGen(){
		int rand = (int)(Math.random() * 351);

		if(rand < 26) return 'A';		//26
		else if(rand < 51) return 'B';	//25
		else if(rand < 75) return 'C';	//24
		else if(rand < 98) return 'D';	//23
		else if(rand < 120) return 'E'; //22
		else if(rand < 141) return 'F'; //21
		else if(rand < 161) return 'G'; //20
		else if(rand < 180) return 'H'; //19
		else if(rand < 198) return 'I'; //18
		else if(rand < 215) return 'J'; //17
		else if(rand < 231) return 'K'; //16
		else if(rand < 246) return 'L'; //15
		else if(rand < 260) return 'M'; //14
		else if(rand < 273) return 'N'; //13
		else if(rand < 285) return 'O'; //12
		else if(rand < 296) return 'P'; //11
		else if(rand < 306) return 'Q'; //10
		else if(rand < 315) return 'R'; //9
		else if(rand < 323) return 'S'; //8
		else if(rand < 330) return 'T'; //7
		else if(rand < 336) return 'U'; //6
		else if(rand < 341) return 'V'; //5
		else if(rand < 345) return 'W'; //4
		else if(rand < 348) return 'X'; //3
		else if(rand < 350) return 'Y'; //2
		else if(rand < 351) return 'Z'; //1
		else{
			return '?';
		}
	}

	/**
	 * Generates a seeded element.
	 * @param seed the seed used to generate the element.
	 * @return the element.
	 */
	public char eleGen(int seed){
		seed = (int)(seed % 351);

		if(seed < 26) return 'A';		//26
		else if(seed < 51) return 'B';	//25
		else if(seed < 75) return 'C';	//24
		else if(seed < 98) return 'D';	//23
		else if(seed < 120) return 'E'; //22
		else if(seed < 141) return 'F'; //21
		else if(seed < 161) return 'G'; //20
		else if(seed < 180) return 'H'; //19
		else if(seed < 198) return 'I'; //18
		else if(seed < 215) return 'J'; //17
		else if(seed < 231) return 'K'; //16
		else if(seed < 246) return 'L'; //15
		else if(seed < 260) return 'M'; //14
		else if(seed < 273) return 'N'; //13
		else if(seed < 285) return 'O'; //12
		else if(seed < 296) return 'P'; //11
		else if(seed < 306) return 'Q'; //10
		else if(seed < 315) return 'R'; //9
		else if(seed < 323) return 'S'; //8
		else if(seed < 330) return 'T'; //7
		else if(seed < 336) return 'U'; //6
		else if(seed < 341) return 'V'; //5
		else if(seed < 345) return 'W'; //4
		else if(seed < 348) return 'X'; //3
		else if(seed < 350) return 'Y'; //2
		else if(seed < 351) return 'Z'; //1
		else{
			return '?';
		}
	}
	
	public void tick(){curTick++;}
	
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
