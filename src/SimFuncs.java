import java.util.*;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.2.1
 * @date 11/19/17
 */
public class SimFuncs {
	
	int step;
	int minCreatures;
	int maxCreatures;
	ArrayList<Creature> HS;
	
	public SimFuncs() {
		step = -1;
		minCreatures = 0;
		maxCreatures = 100000;
		HS = new ArrayList<Creature>();
	}
	public SimFuncs(int tick) {
		step = tick;
		minCreatures = 0;
		maxCreatures = 100000;
		HS = new ArrayList<Creature>();
	}
	public SimFuncs(int tick, int minC, int maxC) {
		step = tick;
		minCreatures = minC;
		maxCreatures = maxC;
		HS = new ArrayList<Creature>();
	}

	/**
	 * Creates an array of random Creatures of length 50.
	 * @return the array of Creatures.
	 */
	public ArrayList<Creature> populate(){
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		if(step == -1){
			for(int i = 0; i < 50; i++){
				creatures.add(new Creature());
			}
		}
		else{
			for(int i = 0; i < 50; i++){
				creatures.add(new Creature(step));
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
		if(step == -1){
			for(int i = 0; i < l; i++){
				creatures.add(new Creature());
			}
		}
		else{
			for(int i = 0; i < l; i++){
				creatures.add(new Creature(step));
			}
		}
		return creatures;
	}
	
	public void tick(ArrayList<Creature> creatures){
		step++;
		int numC = creatures.size();
		for(int i = 0; i < numC; i++){	//Calls the basic tick actions for each creature
			if(creatures.get(i).getXP() > HS.get(0).getXP() && !RIP(creatures.get(i))) HS.set(0, creatures.get(i));
			if(creatures.get(i).getAgeCurrent() > HS.get(1).getAgeCurrent() && !RIP(creatures.get(i))) HS.set(1, creatures.get(i));
			//if(creatures.get(i).getNutrition().length() > HS.get().getNutrition().length() && !RIP(creatures.get(i))) HS.set(, creatures.get(i));
			if(creatures.get(i).getGeneticCode().length() > HS.get(2).getGeneticCode().length() && !RIP(creatures.get(i))) HS.set(2, creatures.get(i));
			creatures.get(i).tick();
		}
	}
	
	/**
	 * Determines which creature will eat the other in a paring.
	 * @param c1 is a creature in a pair.
	 * @param c2 is the other creature in a pair.
	 * @return if true, c1 eats c2. If false, c2 eats c1.
	 */
	public boolean whoEatsWho(Creature c1, Creature c2){
		return Math.pow(c1.getLevel(), c1.getGeneComplexity()) * c1.getHungerRatio() 
				> Math.pow(c2.getLevel(), c2.getGeneComplexity()) * c1.getHungerRatio();
	}
	
	/**
	 * Attempts to add a new element into a Creatures nutrition.
	 * @param genes the Creatures geneticCode.
	 * @param diet the Creatures nutrition.
	 * @return the Creatures updated nutrition.
	 */
	public String addDiet(String genes, String diet, int lvl){
		char cur = eleGen(geneToSeed(genes) + ((int)(Math.random() * lvl * 16) % 150));
		if(diet.indexOf(cur) == -1) return diet + cur;
		else return diet;
	}
	
	public String addDiet(Creature p1, Creature p2){
		double ratio = p1.getWorth() / (p1.getWorth() + p2.getWorth());
		int p1Len = p1.getDiet().length();
		int p2Len = p2.getDiet().length();
		
		int len = (p1Len + p2Len) / 2;
		String diet = "";
		for(int i = 0; i < p1Len; i++){
			for(int j = 0; j < p2Len; j++){
				if(p1.getDiet().charAt(i) == p2.getDiet().charAt(j)) diet += p1.getDiet().charAt(i);
			}
		}
		
		int i = 0;
		int j = 0;
		while(diet.length() < len){
			if(i < p1Len && Math.random() < ratio){
				if(diet.indexOf(p1.getDiet().charAt(i)) == -1) diet += p1.getDiet().charAt(i);
				i++;
			}
			else if(j < p2Len){
				if(diet.indexOf(p2.getDiet().charAt(i)) == -1) diet += p2.getDiet().charAt(i);
				j++;
			}
			else{
				char temp = eleGen();
				if(diet.indexOf(temp) == -1) diet += temp;
			}
		}
		
		return diet;
	}
	
	public String geneMerge(Creature p1, Creature p2, int len) {
		double ratio = p1.getWorth() / (p1.getWorth() + p2.getWorth());
		
		String genes = "";
		for(int i = 0; i < len; i++){						//Runs a loop for each element in this creatures genes
			if(Math.random() < ratio) genes += p1.getGeneticCode().charAt(i);		//If a random double x:{0 >= x < 1} is less than parent 1's prowess ratio, the child's element at gene index i will be parent 1's element at gene index i
			else genes += p2.getGeneticCode().charAt(i);			//If a random double x:{0 >= x < 1} is greater than parent 1's prowess ratio, the child's element at gene index i will be parent 2's element at gene index i
		}
		
		return genes;
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
	
	public ArrayList<Creature> popAdjust(ArrayList<Creature> creatures){
		Collections.sort(creatures);											//Resorts the creatures
		while(creatures.size() > maxCreatures) creatures.remove(0);				//If there are too many creatures, remove the weakest ones
		while(creatures.size() < minCreatures) creatures.add(new Creature(step));	//If there are not enough creatures, make new random ones
		Collections.sort(creatures);
		
		return creatures;
	}
	
	public int xpCurve(int lvl){
		return ((int)(5000 * Math.pow(1.03, lvl))) -5050;
	}
	
	/**
	 * Uses a genetic code to generate a seed.
	 * @param genes the genetic code used to generate the seed.
	 * @return the seed.
	 */
	public int geneToSeed(String genes){
		return (int)(Math.pow(Math.log(productString(genes)), (sumString(genes) % 3) + 1) + sumString(genes));
	}
	
	/**
	 * Generates a genetic code of a defined length.
	 * @param length the number of elements in this genetic code.
	 * @return the genetic code.
	 */
	public String geneGen(int length){
		String gene = "";

		for(int i = 0; i < length; i++){
			gene += eleGen();
		}

		return gene;
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
	
	/**
	 * Sorts the characters of a String.
	 * @param s the String.
	 * @return the sorted String.
	 */
	public String sortString(String s){
		char[] sChar = s.toCharArray();
		Arrays.sort(sChar);
		return new String(sChar);
	}
	
	/**
	 * Sums the character values of a String [A = 1, B = 2, C = 3, ext].
	 * @param s the String
	 * @return the sum.
	 */
	public int sumString(String s){
		int sum = 0;

		int length = s.length();
		char cur;
		for(int i = 0; i < length; i++){
			cur = s.charAt(i);

			if(cur == 'A') sum += 1;		
			else if(cur == 'B') sum += 2;	
			else if(cur == 'C') sum += 3;
			else if(cur == 'D') sum += 4;
			else if(cur == 'E') sum += 5;
			else if(cur == 'F') sum += 6;
			else if(cur == 'G') sum += 7;
			else if(cur == 'H') sum += 8;
			else if(cur == 'I') sum += 9;
			else if(cur == 'J') sum += 10;
			else if(cur == 'K') sum += 11;
			else if(cur == 'L') sum += 12;
			else if(cur == 'M') sum += 13;
			else if(cur == 'N') sum += 14;
			else if(cur == 'O') sum += 15;
			else if(cur == 'P') sum += 16;
			else if(cur == 'Q') sum += 17;
			else if(cur == 'R') sum += 18;
			else if(cur == 'S') sum += 19;
			else if(cur == 'T') sum += 20;
			else if(cur == 'U') sum += 21;
			else if(cur == 'V') sum += 22;
			else if(cur == 'W') sum += 23;
			else if(cur == 'X') sum += 24;
			else if(cur == 'Y') sum += 25;
			else if(cur == 'Z') sum += 26;
			else{
				return '?';
			}
		}

		return sum;
	}
	
	/**
	 * Multiplies the character values of a String [A = 1, B = 2, C = 3, ext].
	 * @param s the String
	 * @return the product.
	 */
	public int productString(String s){
		int prod = 1;

		int length = s.length();
		char cur;
		for(int i = 0; i < length; i++){
			cur = s.charAt(i);

			if(cur == 'A') prod *= 1;		
			else if(cur == 'B') prod *= 2;	
			else if(cur == 'C') prod *= 3;
			else if(cur == 'D') prod *= 4;
			else if(cur == 'E') prod *= 5;
			else if(cur == 'F') prod *= 6;
			else if(cur == 'G') prod *= 7;
			else if(cur == 'H') prod *= 8;
			else if(cur == 'I') prod *= 9;
			else if(cur == 'J') prod *= 10;
			else if(cur == 'K') prod *= 11;
			else if(cur == 'L') prod *= 12;
			else if(cur == 'M') prod *= 13;
			else if(cur == 'N') prod *= 14;
			else if(cur == 'O') prod *= 15;
			else if(cur == 'P') prod *= 16;
			else if(cur == 'Q') prod *= 17;
			else if(cur == 'R') prod *= 18;
			else if(cur == 'S') prod *= 19;
			else if(cur == 'T') prod *= 20;
			else if(cur == 'U') prod *= 21;
			else if(cur == 'V') prod *= 22;
			else if(cur == 'W') prod *= 23;
			else if(cur == 'X') prod *= 24;
			else if(cur == 'Y') prod *= 25;
			else if(cur == 'Z') prod *= 26;
			else{
				return '?';
			}
		}

		return prod;
	}
	
	public int funkyString(String s){
		int num = 0;

		int length = s.length();
		char cur;
		for(int i = 0; i < length; i++){
			cur = s.charAt(i);

			if(cur == 'A') num -= 1;		
			else if(cur == 'B') num += 1;	
			else if(cur == 'C') num += 1;
			else if(cur == 'D') num += 5;
			else if(cur == 'E') num += 5;
			else if(cur == 'F') num += 5;
			else if(cur == 'G') num += 5;
			else if(cur == 'H') num += 10;
			else if(cur == 'I') num += 10;
			else if(cur == 'J') num += 10;
			else if(cur == 'K') num += 10;
			else if(cur == 'L') num += 10;
			else if(cur == 'M') num += 10;
			else if(cur == 'N') num += 10;
			else if(cur == 'O') num -= 10;
			else if(cur == 'P') num += productString(s) / (Math.pow(num, 2) + 1);
			else if(cur == 'Q') num += 15;
			else if(cur == 'R') num += 15;
			else if(cur == 'S') num += sumString(s) / (num + 1);
			else if(cur == 'T') num += 20;
			else if(cur == 'U') num += 20;
			else if(cur == 'V') num += 20;
			else if(cur == 'W') num += 25;
			else if(cur == 'X') num -= 25;
			else if(cur == 'Y') num += num / 3;
			else if(cur == 'Z') num += num;
			else{
				return '?';
			}
		}

		if(num < 0) return 0;
		else return num;
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
