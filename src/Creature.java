import java.util.*;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.1.3
 * @date 11/16/17
 */
public class Creature implements Comparable<Creature>, Cloneable {
	protected SimFuncs sim;
	protected byte geneSize;			//The length of this Creatures gene strand
	protected int geneComplexity;		//The height of the gene strand "tree"
	protected String geneticCode;		//The sequence of elements representing this creatures genes, with length = size ^ complexity
	protected String nutrition;			//This creatures diet, aka the elements it needs to eat
	protected int level;				//This creatures current level
	protected int xpCurrent;			//The amount of total xp this creature has amassed
	protected int xpUntilLevel;			//The amount of xp needed to reach the next level
	protected int digestPower;			//The complexity level of prey that this creature can process at 100% efficiency
	protected int hungerMax;			//The capacity of this creatures stomach, its hunger can not surpass this
	protected double hungerCurrent;		//This creatures current hunger. The higher it is, the fuller its stomach is
	protected double hungerRate;		//The rate at which this creatures hunger depletes per tick
	protected int ageMax;				//The amount of ticks this creature will live for
	protected int ageCurrent;			//The current amount ticks this creature has lived
	protected int generation;			//The amount of ancestors to this Creature + 1
	protected Creature[] parents;
	protected int birthtick;			//The step that this creature was born on. -1 if not applicable.

	/**
	 * The default constructor. Creates a level 1 Creature with a gene complexity of 1 and a random size and code.
	 */
	public Creature(){
		sim = new SimFuncs();
		geneComplexity = 1;
		geneSize = (byte)((Math.random() * 3) + 2);		//A random number between 2 and 4 inclusive
		geneticCode = sim.geneGen(geneSize);				//Sets this creatures genes to a random string of elements of length size
		nutrition = sim.sortString("" + sim.eleGen(sim.geneToSeed(geneticCode)));	//Sets this creatures diet to a sorted array of elements based off of its genetic seed
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);		//Calls the xp curve equation to determine the xp requirement for the next level
		digestPower = 1;
		hungerMax = 25;
		hungerCurrent = hungerMax;
		hungerRate = 1;
		ageCurrent = 0;
		ageMax = (3 * ((geneToSeed() % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;	 	//Determines how long this creature will live based on their genetic code
		generation = 1;
		parents = null;
		birthtick = -1;
	}
	/**
	 * A full random Creature with a birthtick.
	 * @param the tick this Creature was born on.	 
	 * */
	public Creature(int tick){
		sim = new SimFuncs();
		geneComplexity = 1;
		geneSize = (byte)((Math.random() * 3) + 2);		//A random number between 2 and 4 inclusive
		geneticCode = sim.geneGen(geneSize);				//Sets this creatures genes to a random string of elements of length size
		nutrition = sim.sortString("" + sim.eleGen(geneToSeed()));	//Sets this creatures diet to a sorted array of elements based off of its genetic seed
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);		//Calls the xp curve equation to determine the xp requirement for the next level
		digestPower = 1;
		hungerMax = 25;
		hungerCurrent = hungerMax;
		hungerRate = 1;
		ageCurrent = 0;
		ageMax = (3 * ((geneToSeed() % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;	 	//Determines how long this creature will live based on their genetic code
		generation = 1;
		parents = null;
		birthtick = tick;
	}
	/**
	 * Creates a level 1 Creature with a defined gene complexity and size but a random code.
	 * @param size this Creatures geneSize.
	 * @param complex this Creatures geneComplexity.
	 */
	public Creature(int size, int complex){
		sim = new SimFuncs();
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);		//Calls the xp curve equation to determine the xp requirement for the next level
		digestPower = 1;
		hungerMax = 25;
		hungerCurrent = hungerMax;
		hungerRate = 1;

		geneComplexity = complex;
		geneSize = (byte)size;
		geneticCode = sim.geneGen(geneSize);	//Sets this creatures genes to a random string of elements of length size

		nutrition = "" + sim.eleGen(geneToSeed());		//Sets this creatures diet to an array of elements based off of its genetic seed
		for(int i = 1; i < geneComplexity; i++){				//Runs a loop for each level of complexity besides the first
			geneticCode += sim.geneGen(((int)Math.pow(geneSize, i + 1)) - geneticCode.length());	//Increases the size of the complexity tree of elements, adding seeded elements up to length size ^ (i+1)
			nutrition = sim.addDiet(geneticCode, nutrition, level);		//Adds a new diet option (if unique element determined) for each complexity
		}
		nutrition = sim.sortString(nutrition);	//Sorts the diet alphabetically

		ageCurrent = 0;
		ageMax = (3 * ((geneToSeed() % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		generation = 1;
		parents = null;
		birthtick = -1;
	}
	/**
	 * Creates a level 1 Creature with a defined gene code, complexity, and size.
	 * @param genes this Creatures geneticCode.
	 * @param size this Creatures geneSize.
	 * @param complex this Creatures geneComplexity.
	 */
	public Creature(String genes, int size, int complex){
		sim = new SimFuncs();
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);		//Calls the xp curve equation to determine the xp requirement for the next level
		digestPower = 1;
		hungerMax = 25;
		hungerCurrent = hungerMax;
		hungerRate = 1;

		geneComplexity = complex;
		geneSize = (byte)size;
		geneticCode = genes;

		nutrition = "";
		for(int i = 0; i < geneComplexity; i++){		//Runs a loop for each level of complexity
			nutrition = sim.addDiet(geneticCode.substring(0, (int)Math.pow(geneSize, i + 1)), nutrition, level);	//Calculates its diet for each level of complexity, using gene substring of length size ^ i
		}
		nutrition = sim.sortString(nutrition);		//Sorts the diet alphabetically

		ageCurrent = 0;
		ageMax = (3 * ((geneToSeed() % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		generation = 1;
		parents = null;
		birthtick = -1;
	}
	/**
	 * Creates a level 1 Creature based off the genetics and stats of two other Creatures. Used for breeding.
	 * @param p1 one of the Creatures used to create this Creature.
	 * @param p2 the other Creature used to create this Creature.
	 */
	public Creature(Creature p1, Creature p2){
		sim = new SimFuncs();
		double p1Val = (int)Math.pow(((p1.geneToSeed() % 10) + sim.sumString(p1.getGeneticCode())) * p2.getAgeMax(), Math.log(p1.getLevel())); 		//Equations to determine genetic prowess when deciding on offspring genes
		double p2Val = (int)Math.pow(((p2.geneToSeed() % 10) + sim.sumString(p2.getGeneticCode())) * p2.getAgeMax(), Math.log(p2.getLevel()));
		double p1Weight = p1Val / (p1Val + p2Val);		//A ration of this creatures prowess compared to the others
		double p2Weight = p2Val / (p1Val + p2Val);		//A ration of this creatures prowess compared to the others

		geneSize  = p1.getGeneSize();					//Gene size of child is equal to parents
		geneComplexity = p1.getGeneComplexity();		//Gene complexity of a child is equal to parents
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);		//Calls the xp curve equation to determine the xp requirement for the next level

		geneticCode = "";
		int l = (int) Math.pow(geneSize, geneComplexity);		//The length of this creatures genes	
		for(int i = 0; i < l; i++){						//Runs a loop for each element in this creatures genes
			if(Math.random() < p1Weight) geneticCode += p1.getGeneticCode().charAt(i);		//If a random double x:{0 >= x < 1} is less than parent 1's prowess ratio, the child's element at gene index i will be parent 1's element at gene index i
			else geneticCode += p2.getGeneticCode().charAt(i);			//If a random double x:{0 >= x < 1} is greater than parent 1's prowess ratio, the child's element at gene index i will be parent 2's element at gene index i
		}

		nutrition = "";
		l = p1.getNutrition().length();		//The amount of elements in parent 1's diet
		for(int i = 0; i < l; i++){			//Loops for each element in parent 1's diet
			if(p2.getNutrition().indexOf(p1.getNutrition().charAt(i)) != -1) nutrition += p1.getNutrition().charAt(i);	//Compares each element of parent 1s diet to parent 2's diet. If any elements are in both, they are added to the childs diet
		}

		int n1 = 0;
		int n2 = 0;
		l = (int)Math.floor((p1Weight * (double)getNutrition().length()) + (p2Weight * (double)p2.getNutrition().length())) + nutrition.length();		//The amount of elements in this creatures diet based on the lengths of the 2 parents diets
		if(l >= 26) nutrition = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		else{
			while(nutrition.length() < l){											//Loop runs while the childs current diet isnt as long as it should be
				if(Math.random() < p1Weight && n1 < p1.getNutrition().length()){	//If a random double x:{0 >= x < 1} is less than parent 1's prowess ratio and not all of parent 1's diet elements have been looked at
					if(nutrition.indexOf(p1.getNutrition().charAt(n1)) == -1){		//If the current element in parent 1's diet isn't already in the childs diet
						nutrition += p1.getNutrition().charAt(n1);					//Add the current element being looked at in parent 1's diet to the childs diet
					}
					n1++;			//Increase the index of the current element in parent 1's diet being look at
				}

				else if(n2 < p2.getNutrition().length() && nutrition.indexOf(p2.getNutrition().charAt(n2)) == -1){		//If a random double x:{0 >= x < 1} is greater than parent 1's prowess ratio or all of parent 1's diet elements have been looked at
					nutrition += p2.getNutrition().charAt(n2);		//Add the current element being looked at in parent 1's diet to the childs diet
					n2++;			//Increase the index of the current element in parent 1's diet being look at
				}
				else if(nutrition.length() < 26){
					char ele = sim.eleGen();		//Generates a random element
					if(nutrition.indexOf(ele) == -1) nutrition += ele;			//If the random element isn't in the childs diet already, add it
				}
			}
		}

		digestPower = (int)Math.ceil(((p1Weight * p1.getDigestPower()) + (p2Weight * p2.getDigestPower())) * (1 / ((p1.getLevel() + p2.getLevel()) / 2)));		//Determines digest power based on the parents, scaled back depending on their level
		hungerMax = (int)Math.ceil(((p1Weight * p1.getHungerMax()) + (p2Weight * p2.getHungerMax())) * (1 / ((p1.getLevel() + p2.getLevel()) / 2)));			//Determines max hunger based on the parents, scaled back depending on their level
		hungerCurrent = hungerMax;
		hungerRate = 1;

		ageCurrent = 0;
		ageMax = (sumGenes() / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		if(p1.getGeneration() > p2.getGeneration()) generation = p1.getGeneration() + 1;
		else generation = p2.getGeneration() + 1;
		parents = new Creature[]{p1, p2};
		birthtick = -1;
	}
	/**
	 * Creates a level 1 Creature based off the genetics and stats of two other Creatures. Used for breeding.
	 * @param p1 one of the Creatures used to create this Creature.
	 * @param p2 the other Creature used to create this Creature.
	 */
	public Creature(Creature p1, Creature p2, int tick){
		sim = new SimFuncs();
		double p1Val = (int)Math.pow(((p1.geneToSeed() % 10) + p1.sumGenes()) * p2.getAgeMax(), Math.log(p1.getLevel())); 		//Equations to determine genetic prowess when deciding on offspring genes
		double p2Val = (int)Math.pow(((p2.geneToSeed() % 10) + p2.sumGenes()) * p2.getAgeMax(), Math.log(p2.getLevel()));
		double p1Weight = p1Val / (p1Val + p2Val);		//A ration of this creatures prowess compared to the others
		double p2Weight = p2Val / (p1Val + p2Val);		//A ration of this creatures prowess compared to the others

		geneSize  = p1.getGeneSize();					//Gene size of child is equal to parents
		geneComplexity = p1.getGeneComplexity();		//Gene complexity of a child is equal to parents
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);		//Calls the xp curve equation to determine the xp requirement for the next level

		geneticCode = "";
		int l = (int) Math.pow(geneSize, geneComplexity);		//The length of this creatures genes	
		for(int i = 0; i < l; i++){						//Runs a loop for each element in this creatures genes
			if(Math.random() < p1Weight) geneticCode += p1.getGeneticCode().charAt(i);		//If a random double x:{0 >= x < 1} is less than parent 1's prowess ratio, the child's element at gene index i will be parent 1's element at gene index i
			else geneticCode += p2.getGeneticCode().charAt(i);			//If a random double x:{0 >= x < 1} is greater than parent 1's prowess ratio, the child's element at gene index i will be parent 2's element at gene index i
		}

		nutrition = "";
		l = p1.getNutrition().length();		//The amount of elements in parent 1's diet
		for(int i = 0; i < l; i++){			//Loops for each element in parent 1's diet
			if(p2.getNutrition().indexOf(p1.getNutrition().charAt(i)) != -1) nutrition += p1.getNutrition().charAt(i);	//Compares each element of parent 1s diet to parent 2's diet. If any elements are in both, they are added to the childs diet
		}

		int n1 = 0;
		int n2 = 0;
		l = (int)Math.floor((p1Weight * (double)getNutrition().length()) + (p2Weight * (double)p2.getNutrition().length())) + nutrition.length();		//The amount of elements in this creatures diet based on the lengths of the 2 parents diets
		if(l >= 26) nutrition = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		else{
			while(nutrition.length() < l){											//Loop runs while the childs current diet isnt as long as it should be
				if(Math.random() < p1Weight && n1 < p1.getNutrition().length()){	//If a random double x:{0 >= x < 1} is less than parent 1's prowess ratio and not all of parent 1's diet elements have been looked at
					if(nutrition.indexOf(p1.getNutrition().charAt(n1)) == -1){		//If the current element in parent 1's diet isn't already in the childs diet
						nutrition += p1.getNutrition().charAt(n1);					//Add the current element being looked at in parent 1's diet to the childs diet
					}
					n1++;			//Increase the index of the current element in parent 1's diet being look at
				}

				else if(n2 < p2.getNutrition().length() && nutrition.indexOf(p2.getNutrition().charAt(n2)) == -1){		//If a random double x:{0 >= x < 1} is greater than parent 1's prowess ratio or all of parent 1's diet elements have been looked at
					nutrition += p2.getNutrition().charAt(n2);		//Add the current element being looked at in parent 1's diet to the childs diet
					n2++;			//Increase the index of the current element in parent 1's diet being look at
				}
				else if(nutrition.length() < 26){
					char ele = sim.eleGen();		//Generates a random element
					if(nutrition.indexOf(ele) == -1) nutrition += ele;			//If the random element isn't in the childs diet already, add it
				}
			}
		}

		digestPower = (int)Math.ceil(((p1Weight * p1.getDigestPower()) + (p2Weight * p2.getDigestPower())) * (1 / ((p1.getLevel() + p2.getLevel()) / 2)));		//Determines digest power based on the parents, scaled back depending on their level
		hungerMax = (int)Math.ceil(((p1Weight * p1.getHungerMax()) + (p2Weight * p2.getHungerMax())) * (1 / ((p1.getLevel() + p2.getLevel()) / 2)));			//Determines max hunger based on the parents, scaled back depending on their level
		hungerCurrent = hungerMax;
		hungerRate = 1;

		ageCurrent = 0;
		ageMax = (sumGenes() / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		if(p1.getGeneration() > p2.getGeneration()) generation = p1.getGeneration() + 1;
		else generation = p2.getGeneration() + 1;
		parents = new Creature[]{p1, p2};
		birthtick = tick;
	}


	/**
	 * Updates some basic stats for each step in the simulation. Should be done for each Creature at the beginning of each step.
	 */
	public void tick(){
		if(xpCurrent >= xpUntilLevel) levelUp();
		hungerCurrent -= hungerRate;
		ageCurrent++;
	}

	/**
	 * This Creature eats the other Creature. NOTE: this function does not effect the eaten Creature object, removing from your Creature array is advised.
	 * @param preyGenes the geneticCode of the Creature getting eaten.
	 * @param preyComplexity the geneComplexity of the Creature getting eaten.
	 */
	public void eat(String preyGenes, int preyComplexity){
		int nutrients = 0;
		int l = preyGenes.length();
		for(int i = 0; i < l; i++){
			char ele = preyGenes.charAt(i);
			if(nutrition.indexOf(ele) != -1){
				if(ele == 'A') nutrients += 1;	
				else if(ele == 'B') nutrients += 2;	
				else if(ele == 'C') nutrients += 3;	
				else if(ele == 'D') nutrients += 4;	
				else if(ele == 'E') nutrients += 5;	
				else if(ele == 'F') nutrients += 6;
				else if(ele == 'G') nutrients += 7;
				else if(ele == 'H') nutrients += 8;
				else if(ele == 'I') nutrients += 9;
				else if(ele == 'J') nutrients += 10;
				else if(ele == 'K') nutrients += 11;
				else if(ele == 'L') nutrients += 12;
				else if(ele == 'M') nutrients += 13;
				else if(ele == 'N') nutrients += 14;
				else if(ele == 'O') nutrients += 15;
				else if(ele == 'P') nutrients += 16;
				else if(ele == 'Q') nutrients += 17;
				else if(ele == 'R') nutrients += 18;
				else if(ele == 'S') nutrients += 19;
				else if(ele == 'T') nutrients += 20;
				else if(ele == 'U') nutrients += 21;
				else if(ele == 'V') nutrients += 22;
				else if(ele == 'W') nutrients += 23;
				else if(ele == 'X') nutrients += 24;
				else if(ele == 'Y') nutrients += 25;
				else if(ele == 'Z') nutrients += 26;
			}
		}
		if(digestPower < preyComplexity){
			hungerCurrent += nutrients * (1 / (Math.pow(2, preyComplexity - digestPower)));
			if(hungerCurrent > hungerMax) hungerCurrent = hungerMax;

			xpCurrent += Math.ceil((nutrients * (Math.floor(preyComplexity / level) + 1) * 10) * (1 / (Math.pow(2, preyComplexity - digestPower))));
		}
		else{
			hungerCurrent += nutrients * 3;
			if(hungerCurrent > hungerMax) hungerCurrent = hungerMax;

			xpCurrent += nutrients * (preyComplexity + 1) * 10;
		}
	}

	/**
	 * Uses this creatures geneticCode to generate a seed.
	 * @return the seed.
	 */
	public int geneToSeed(){
		return (int)(Math.pow(Math.log(productGenes()), (sumGenes() % 3)) + sumGenes());
	}

	/**
	 * Used to increase this Creatures level and stats accordingly.
	 */
	public void levelUp() 
	{
		level++;						//Increases current level
		xpUntilLevel += xpCurve(level);	//Calculates the amount of XP needed for the next level
		ageMax = (sumGenes() / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 	//Determines this creatures new max age
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + (5 * level);

		if((int)(geneToSeed() + (Math.random() * (level + 5))) % 5 == 0) digestPower++;	//
		if((int)(geneToSeed() + (Math.random() * (level + 5))) % 5 == 1) hungerMax++;
		if((int)(geneToSeed() + (Math.random() * (level + 5))) % 5 == 2){
			if(hungerRate >= .45) hungerRate -= .05;
			else hungerMax++;
		}
		if((int)(geneToSeed() + (Math.random() * (level + 5))) % 5 == 3) nutrition += sim.addDiet(geneticCode, nutrition, level);
		if((int)(geneToSeed() + (Math.random() * (level + 5))) % 5 == 4) {
			geneComplexity++;
			geneticCode += sim.geneGen((int)Math.pow(geneSize, geneComplexity) - geneticCode.length());		//Adds new elements to the genetic code to catch up to new complexity
		}
	}

	/**
	 * Increases this Creatures xp by a set amount.
	 * @param gain the amount to increase this Creatures xp by.
	 */
	public void xpGain(int gain){
		xpCurrent += gain;
	}

	/**
	 * Determines the amount of total xp needed to reach a certain level.
	 * @param lvl the target level.
	 * @return the amount of xp needed.
	 */
	public int xpCurve(int lvl){
		return ((int)(5000 * Math.pow(1.03, lvl))) -5050;
	}

	/**
	 * Sums the character values of this creatures geneticCode [A = 1, B = 2, C = 3, ext].
	 * @return the sum.
	 */
	public int sumGenes(){
		int sum = 0;

		int length = geneticCode.length();
		char cur;
		for(int i = 0; i < length; i++){
			cur = geneticCode.charAt(i);

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
	 * Multiplies the character values of this Creatures geneticCode [A = 1, B = 2, C = 3, ext].
	 * @param s the String
	 * @return the product.
	 */
	public int productGenes(){
		int prod = 0;

		int length = geneticCode.length();
		char cur;
		for(int i = 0; i < length; i++){
			cur = geneticCode.charAt(i);

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

	public String getGeneticCode(){
		return geneticCode;
	}

	/**
	 * Returns this Creatures geneticCode seperated into chunks of length geneSize enclosed by brackets.
	 * @return the geneticCode String parsed into sections inclosed in brackets.
	 */
	public String getGeneticCodeFormatted(){
		String form = "[";
		for(int i = 0; i < geneticCode.length(); i++){
			form += geneticCode.substring(i, i + 1);
			if((i + 1) % geneSize == 0 && (i+1) < geneticCode.length()) form += "][";
		}
		return form + "]";
	}

	public String getNutrition(){
		return nutrition;
	}

	public byte getGeneSize(){
		return geneSize;
	}

	public int getGeneComplexity(){
		return geneComplexity;
	}

	public int getLevel(){
		return level;
	}

	public int getXP(){
		return xpCurrent;
	}

	public int getDigestPower(){
		return digestPower;
	}

	public int getHungerMax(){
		return hungerMax;
	}

	public double getHungerCurrent(){
		return hungerCurrent;
	}

	public double getHungerRate(){
		return hungerRate;
	}

	public double getHungerRatio(){
		return hungerCurrent / hungerMax;
	}

	public int getAgeMax(){
		return ageMax;
	}

	public int getAgeCurrent(){
		return ageCurrent;
	}
	public int getGeneration(){
		return generation;
	}
	public Creature[] getParents(){
		return parents;
	}
	public int getBirthtick(){
		return birthtick;
	}
	public int getWorth() {
		return (int) (level * Math.sqrt(sumGenes()) * ((hungerCurrent / hungerMax) + .5));
	}

	/**
	 * Prints the stats and attributes of this Creature to the Terminal.
	 */
	public void print(){
		System.out.println("Genetic Code: " + getGeneticCodeFormatted());
		System.out.println("Gene Size: " + geneSize);
		System.out.println("Code Complexity: " + geneComplexity);
		System.out.println("Level: " + level);
		System.out.println("Total XP: " + xpCurrent);
		System.out.println("XP until Next Level: " + xpUntilLevel);
		System.out.println("Diet: " + nutrition);
		System.out.println("Digestion Power: " + digestPower);
		System.out.println("Current Hunger: " + hungerCurrent);
		System.out.println("Hunger when Full: " + hungerMax);
		System.out.println("Rate of Hunger: " + hungerRate);
		System.out.println("Current Age: " + ageCurrent);
		System.out.println("Age of Death: " + ageMax);
		System.out.println("Generation: " + generation);
		if(parents != null) System.out.println("Parents: " + parents[0].getGeneticCodeFormatted() + ", " + parents[0].getGeneticCodeFormatted());
		if(birthtick != -1) System.out.println("Birthtick: " + birthtick);
	}

	@Override
	public String toString(){
		if(parents == null){
			return 	"geneSize: " + geneSize + '\n' +
					"geneComplexity: " + geneComplexity + '\n' +
					"geneticCode: " + geneticCode + '\n' +
					"nutrition: " + nutrition + '\n' +
					"level: " + level + '\n' +
					"xpCurrent: " + xpCurrent + '\n' +
					"xpUntilLevel: " + xpUntilLevel + '\n' +
					"digestPower: " + digestPower + '\n' +
					"hungerMax: " + hungerMax + '\n' + 
					"hungerCurrent: " + hungerMax + '\n' +
					"hungerRate: " + hungerRate + '\n' +
					"ageMax: " + ageMax + '\n' +
					"ageCurrent: " + ageCurrent + '\n' +
					"generation: " + generation + '\n' +
					"birthtick: " + birthtick + '\n';
		}
		else{
			return 	"geneSize: " + geneSize + '\n' +
					"geneComplexity: " + geneComplexity + '\n' +
					"geneticCode: " + geneticCode + '\n' +
					"nutrition: " + nutrition + '\n' +
					"level: " + level + '\n' +
					"xpCurrent: " + xpCurrent + '\n' +
					"xpUntilLevel: " + xpUntilLevel + '\n' +
					"digestPower: " + digestPower + '\n' +
					"hungerMax: " + hungerMax + '\n' + 
					"hungerCurrent: " + hungerMax + '\n' +
					"hungerRate: " + hungerRate + '\n' +
					"ageMax: " + ageMax + '\n' +
					"ageCurrent: " + ageCurrent + '\n' +
					"generation: " + generation + '\n' +
					"parent 1: " + parents[0].getGeneticCodeFormatted() + '\n' +
					"parent 2: " + parents[1].getGeneticCodeFormatted() + '\n' +
					"birthtick: " + birthtick + '\n';
		}
		
	}
	public int compareTo(Creature c) {
		return getWorth() - c.getWorth();
	}
}