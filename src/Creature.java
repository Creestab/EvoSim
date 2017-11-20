/**
 * @author Chris Berghoff
 * @version 0.2.1
 * @date 11/19/17
 */
public class Creature implements Comparable<Creature>, Cloneable {

	private SimFuncs sim;
	private int geneSize;
	private int geneComplexity;
	private String geneCode;
	private int geneSeed;
	private int geneSum;
	private int geneProduct;
	private int level;
	private int xpCurrent;
	private int xpNext;
	private String diet;
	private int digestion;
	private int hungerMax;
	private double hungerCurrent;
	private double hungerRate;
	private Creature[] parents;
	private int generation;
	private int lineageValue;
	private int birthtick;
	private int ageMax;
	private int ageCurrent;

	public Creature(){
		sim = new SimFuncs();
		geneSize = (int)(Math.random() * 3) + 2;		//A random number between 2 and 4 inclusive
		geneComplexity = 1;
		geneCode = sim.geneGen((int)Math.pow(geneSize, geneComplexity));
		geneSeed = sim.geneToSeed(geneCode);
		geneSum = sim.sumString(geneCode);
		geneProduct = sim.productString(geneCode);
		level = 1;
		xpCurrent = 0;
		xpNext = sim.xpCurve(level);
		diet = "" + sim.eleGen(geneSeed);
		digestion = 1;
		hungerMax = 10 + sim.funkyString(diet);
		hungerCurrent = Math.ceil(hungerMax / 2);
		hungerRate = 1;
		parents = null;
		generation = 1;
		lineageValue = (int)((geneSum / geneCode.length()) * 645277);
		birthtick = -1;
		ageMax = (3 * ((geneSeed % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		ageCurrent = 0;
	}

	public Creature(int btick){
		sim = new SimFuncs(btick);
		geneSize = (int)(Math.random() * 3) + 2;		//A random number between 2 and 4 inclusive
		geneComplexity = 1;
		geneCode = sim.geneGen((int)Math.pow(geneSize, geneComplexity));
		geneSeed = sim.geneToSeed(geneCode);
		geneSum = sim.sumString(geneCode);
		geneProduct = sim.productString(geneCode);
		level = 1;
		xpCurrent = 0;
		xpNext = sim.xpCurve(level);
		diet = "" + sim.eleGen(geneSeed);
		digestion = 1;
		hungerMax = 10 + sim.funkyString(diet);
		hungerCurrent = Math.ceil(hungerMax / 2);
		hungerRate = 1;
		parents = null;
		generation = 1;
		lineageValue = (int)((geneSum / geneCode.length()) * 645277);
		birthtick = btick;
		ageMax = (3 * ((geneSeed % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		ageCurrent = 0;
	}

	public Creature(int gSize, int gComp){
		sim = new SimFuncs();
		geneSize = gSize;
		geneComplexity = gComp;
		geneCode = sim.geneGen((int)Math.pow(geneSize, geneComplexity));
		geneSeed = sim.geneToSeed(geneCode);
		geneSum = sim.sumString(geneCode);
		geneProduct = sim.productString(geneCode);
		level = 1;
		xpCurrent = 0;
		xpNext = sim.xpCurve(level);
		diet = "" + sim.eleGen(geneSeed);
		digestion = 1;
		hungerMax = 10 + sim.funkyString(diet);
		hungerCurrent = Math.ceil(hungerMax / 2);
		hungerRate = 1;
		parents = null;
		generation = 1;
		lineageValue = (int)((geneSum / geneCode.length()) * 645277);
		birthtick = -1;
		ageMax = (3 * ((geneSeed % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		ageCurrent = 0;
	}

	public Creature(int gSize, int gComp, int btick){
		sim = new SimFuncs(btick);
		geneSize = gSize;
		geneComplexity = gComp;
		geneCode = sim.geneGen((int)Math.pow(geneSize, geneComplexity));
		geneSeed = sim.geneToSeed(geneCode);
		geneSum = sim.sumString(geneCode);
		geneProduct = sim.productString(geneCode);
		level = 1;
		xpCurrent = 0;
		xpNext = sim.xpCurve(level);
		diet = "" + sim.eleGen(geneSeed);
		digestion = 1;
		hungerMax = 10 + sim.funkyString(diet);
		hungerCurrent = Math.ceil(hungerMax / 2);
		hungerRate = 1;
		parents = null;
		generation = 1;
		lineageValue = (int)((geneSum / geneCode.length()) * 645277);
		birthtick = btick;
		ageMax = (3 * ((geneSeed % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		ageCurrent = 0;
	}

	public Creature(String gCode, int gSize, int gComp){
		sim = new SimFuncs();
		geneSize = gSize;
		geneComplexity = gComp;
		geneCode = gCode;
		geneSeed = sim.geneToSeed(geneCode);
		geneSum = sim.sumString(geneCode);
		geneProduct = sim.productString(geneCode);
		level = 1;
		xpCurrent = 0;
		xpNext = sim.xpCurve(level);
		diet = "" + sim.eleGen(geneSeed);
		digestion = 1;
		hungerMax = 10 + sim.funkyString(diet);
		hungerCurrent = Math.ceil(hungerMax / 2);
		hungerRate = 1;
		parents = null;
		generation = 1;
		lineageValue = (int)((geneSum / geneCode.length()) * 645277);
		birthtick = -1;
		ageMax = (3 * ((geneSeed % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		ageCurrent = 0;
	}

	public Creature(String gCode, int gSize, int gComp, int btick){
		sim = new SimFuncs(btick);
		geneSize = gSize;
		geneComplexity = gComp;
		geneCode = gCode;
		geneSeed = sim.geneToSeed(geneCode);
		geneSum = sim.sumString(geneCode);
		geneProduct = sim.productString(geneCode);
		level = 1;
		xpCurrent = 0;
		xpNext = sim.xpCurve(level);
		diet = "" + sim.eleGen(geneSeed);
		digestion = 1;
		hungerMax = 10 + sim.funkyString(diet);
		hungerCurrent = Math.ceil(hungerMax / 2);
		hungerRate = 1;
		parents = null;
		generation = 1;
		lineageValue = (int)((geneSum / geneCode.length()) * 645277);
		birthtick = btick;
		ageMax = (3 * ((geneSeed % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		ageCurrent = 0;
	}

	public Creature(Creature p1, Creature p2){
		sim = new SimFuncs();
		geneSize = p1.getGeneSize();
		geneComplexity = p1.getGeneComplexity();
		geneCode = sim.geneMerge(p1, p2, (int)Math.pow(geneSize, geneComplexity));
		geneSeed = sim.geneToSeed(geneCode);
		geneSum = sim.sumString(geneCode);
		geneProduct = sim.productString(geneCode);
		level = 1;
		xpCurrent = 0;
		xpNext = sim.xpCurve(level);
		diet = "" + sim.addDiet(p1, p2);
		digestion = 1;
		hungerMax = 10 + sim.funkyString(diet);
		hungerCurrent = Math.ceil(hungerMax / 2);
		hungerRate = 1;
		parents = new Creature[]{p1, p2};
		if(p1.getGeneration() > p2.getGeneration()) generation = p1.getGeneration() + 1;
		else generation = p2.getGeneration() + 1;
		lineageValue = (int)((((p1.getLineageValue() + (p1.getGeneSum() / p1.getGeneticCode().length()) / 2) + 
				(p2.getLineageValue() + (p2.getGeneSum() / p2.getGeneticCode().length()) / 2)) / 2) * 645277);
		birthtick = -1;
		ageMax = (3 * ((geneSeed % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		ageCurrent = 0;
	}

	public Creature(Creature p1, Creature p2, int btick){
		sim = new SimFuncs(btick);
		geneSize = p1.getGeneSize();
		geneComplexity = p1.getGeneComplexity();
		geneCode = sim.geneMerge(p1, p2, (int)Math.pow(geneSize, geneComplexity));
		geneSeed = sim.geneToSeed(geneCode);
		geneSum = sim.sumString(geneCode);
		geneProduct = sim.productString(geneCode);
		level = 1;
		xpCurrent = 0;
		xpNext = sim.xpCurve(level);
		diet = "" + sim.addDiet(p1, p2);
		digestion = 1;
		hungerMax = 10 + sim.funkyString(diet);
		hungerCurrent = Math.ceil(hungerMax / 2);
		hungerRate = 1;
		parents = new Creature[]{p1, p2};
		if(p1.getGeneration() > p2.getGeneration()) generation = p1.getGeneration() + 1;
		else generation = p2.getGeneration() + 1;
		lineageValue = (int)((((p1.getLineageValue() + (p1.getGeneSum() / p1.getGeneticCode().length()) / 2) + 
				(p2.getLineageValue() + (p2.getGeneSum() / p2.getGeneticCode().length()) / 2)) / 2) * 645277);
		birthtick = btick;
		ageMax = (3 * ((geneSeed % 9) + 1)) + (int)(4 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneCode.length(), .5)) + 5;		//Determines how long this creature will live based on their genetic code
		ageCurrent = 0;
	}

	public void tick(){
		if(xpCurrent >= xpNext) levelUp();
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
		int len = preyGenes.length();
		for(int i = 0; i < len; i++){
			char ele = preyGenes.charAt(i);
			if(diet.indexOf(ele) != -1){
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
		if(digestion < preyComplexity){
			hungerCurrent += nutrients * (1 / (Math.pow(2, preyComplexity - digestion)));
			if(hungerCurrent > hungerMax) hungerCurrent = hungerMax;

			xpCurrent += Math.ceil((nutrients * (Math.floor(preyComplexity / level) + 1) * 10) * (1 / (Math.pow(2, preyComplexity - digestion))));
		}
		else{
			hungerCurrent += nutrients * 3;
			if(hungerCurrent > hungerMax) hungerCurrent = hungerMax;

			xpCurrent += nutrients * (preyComplexity + 1) * 10;
		}
	}
	
	public void levelUp(){
		level++;						//Increases current level
		xpNext += sim.xpCurve(level);	//Calculates the amount of XP needed for the next level
		ageMax = (geneSum / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 	//Determines this creatures new max age
				- (int)(Math.pow(geneSize * geneCode.length(), .5)) + (5 * level);

		if(((geneSeed * level) + xpCurrent) % 5 == 0) digestion++;	//
		if(((geneSeed * level) + xpCurrent) % 5 == 1) hungerMax++;
		if(((geneSeed * level) + xpCurrent) % 5 == 2){
			if(hungerRate >= .45) hungerRate -= .05;
			else hungerMax++;
		}
		if(((geneSeed * level) + xpCurrent) % 5 == 3) diet += sim.addDiet(geneCode, diet, level);
		if(((geneSeed * level) + xpCurrent) % 5 == 4) {
			geneComplexity++;
			geneCode += sim.geneGen((int)Math.pow(geneSize, geneComplexity) - geneCode.length());		//Adds new elements to the genetic code to catch up to new complexity
		}
	}
	
	public void xpGain(int gain){
		xpCurrent += gain;
	}
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
	
	public String getGeneticCode(){
		return geneCode;
	}
	
	/**
	 * Returns this Creatures geneticCode seperated into chunks of length geneSize enclosed by brackets.
	 * @return the geneticCode String parsed into sections inclosed in brackets.
	 */
	public String getGeneticCodeFormatted(){
		String form = "[";
		for(int i = 0; i < geneCode.length(); i++){
			form += geneCode.substring(i, i + 1);
			if((i + 1) % geneSize == 0 && (i+1) < geneCode.length()) form += "][";
		}
		return form + "]";
	}

	public String getDiet(){
		return diet;
	}

	public int getGeneSize(){
		return geneSize;
	}

	public int getGeneComplexity(){
		return geneComplexity;
	}

	public int getGeneSeed() {
		return geneSeed;
	}
	
	public int getGeneSum(){
		return geneSum;
	}
	
	public int getGeneProduct(){
		return geneProduct;
	}
	
	public int getLevel(){
		return level;
	}

	public int getXP(){
		return xpCurrent;
	}

	public int getDigestion(){
		return digestion;
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
	
	public double getAgeRatio(){
		return ageCurrent / ageMax;
	}

	public int getGeneration(){
		return generation;
	}

	public Creature[] getParents(){
		return parents;
	}
	
	public double getLineageValue(){
		return lineageValue;
	}
	
	public String getLineageValueFormatted(){
		return Integer.toHexString(lineageValue);
	}

	public int getBirthtick(){
		return birthtick;
	}

	public int getWorth() {
		return (int)Math.ceil((((geneSize + (5 * geneComplexity)) * level) + sim.sumString(diet) + (int)(hungerCurrent / 2)) * (((double)(ageMax - ageCurrent) / (double)ageMax) + .5));
	}

	
	/**
	 * Prints the stats and attributes of this Creature to the Terminal.
	 */
	public void print(){
		System.out.println("Genetic Code: " + getGeneticCodeFormatted());
		System.out.println("Gene Size: " + geneSize);
		System.out.println("Code Complexity: " + geneComplexity);
		System.out.println("Seed: " + geneSeed);
		System.out.println("Sum Gene String: " + geneSum);
		System.out.println("Product Gene String: " + geneProduct);
		System.out.println("Level: " + level);
		System.out.println("Total XP: " + xpCurrent);
		System.out.println("XP Needed for Next Level: " + xpNext);
		System.out.println("Diet: " + diet);
		System.out.println("Digestion Power: " + digestion);
		System.out.println("Current Hunger: " + hungerCurrent);
		System.out.println("Hunger when Full: " + hungerMax);
		System.out.println("Rate of Hunger: " + hungerRate);
		System.out.println("Current Age: " + ageCurrent);
		System.out.println("Maximum Lifespan: " + ageMax);
		if(birthtick != -1) System.out.println("Birthtick: " + birthtick);
		if(parents != null) System.out.println("Parents: " + parents[0].getGeneticCodeFormatted() + ", " + parents[1].getGeneticCodeFormatted());
		System.out.println("Generation: " + generation);
		if(generation != 1) System.out.println("Lineage Value (Hex): " + getLineageValueFormatted());
	}

	@Override
	public String toString(){
		if(parents == null){
			return 	"geneSize: " + geneSize + '\n' +
					"geneComplexity: " + geneComplexity + '\n' +
					"geneCode: " + geneCode + '\n' +
					"geneSeed: " + geneSeed + '\n' +
					"geneSum: " + geneSum + '\n' +
					"geneProduct: " + geneProduct + '\n' +
					"level: " + level + '\n' +
					"xpCurrent: " + xpCurrent + '\n' +
					"xpNext: " + xpNext + '\n' +
					"diet: " + diet + '\n' +
					"digestion: " + digestion + '\n' +
					"hungerMax: " + hungerMax + '\n' + 
					"hungerCurrent: " + hungerCurrent + '\n' +
					"hungerRate: " + hungerRate + '\n' +
					"generation: " + generation + '\n' +
					"birthtick: " + birthtick + '\n' +
					"ageMax: " + ageMax + '\n' +
					"ageCurrent: " + ageCurrent + '\n';
		}
		else{
			return 	"geneSize: " + geneSize + '\n' +
					"geneComplexity: " + geneComplexity + '\n' +
					"geneCode: " + geneCode + '\n' +
					"geneSeed: " + geneSeed + '\n' +
					"geneSum: " + geneSum + '\n' +
					"geneProduct: " + geneProduct + '\n' +
					"level: " + level + '\n' +
					"xpCurrent: " + xpCurrent + '\n' +
					"xpNext: " + xpNext + '\n' +
					"diet: " + diet + '\n' +
					"digestion: " + digestion + '\n' +
					"hungerMax: " + hungerMax + '\n' + 
					"hungerCurrent: " + hungerCurrent + '\n' +
					"hungerRate: " + hungerRate + '\n' +
					"parent 1: " + parents[0].getGeneticCodeFormatted() + '\n' +
					"parent 2: " + parents[1].getGeneticCodeFormatted() + '\n' +
					"generation: " + generation + '\n' +
					"lineageValue " + lineageValue + '\n' +
					"birthtick: " + birthtick + '\n' +
					"ageMax: " + ageMax + '\n' +
					"ageCurrent: " + ageCurrent + '\n';
		}
	}
	
	public int compareTo(Creature c) {
		return getWorth() - c.getWorth();
	}
}