import java.util.Arrays;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.1.1
 * @date 11/9/17
 */
public class Creature implements Comparable<Creature>, Cloneable {
	protected byte geneSize;
	protected int geneComplexity;
	protected String geneticCode;
	protected String nutrition;
	protected int level;
	protected int xpCurrent;
	protected int xpUntilLevel;
	protected int digestPower;
	protected int hungerMax;
	protected double hungerCurrent;
	protected double hungerRate;
	protected int ageMax;
	protected int ageCurrent;

	/**
	 * The default constructor. Creates a level 1 Creature with a gene complexity of 1 and a random size and code.
	 */
	public Creature(){
		geneComplexity = 1;
		geneSize = (byte)((Math.random() * 3) + 2);
		geneticCode = geneGen(geneSize);
		nutrition = sortString("" + eleGen(geneToSeed(geneticCode)));
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);
		digestPower = 1;
		hungerMax = 10;
		hungerCurrent = hungerMax;
		hungerRate = 1;
		ageCurrent = 0;
		ageMax = (sumString(geneticCode) / 3) + 1;
	}
	/**
	 * Creates a level 1 Creature with a defined gene complexity and size but a random code.
	 * @param complex this Creatures geneComplexity.
	 * @param size this Creatures geneSize.
	 */
	public Creature(int complex, int size){
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);
		digestPower = 1;
		hungerMax = 10;
		hungerCurrent = hungerMax;
		hungerRate = 1;

		geneComplexity = complex;
		geneSize = (byte)size;
		geneticCode = geneGen(geneSize);

		nutrition = "" + eleGen(geneToSeed(geneticCode));
		for(int i = 1; i < geneComplexity; i++){
			geneticCode += geneGen(((int)Math.pow(geneSize, i + 1)) - geneticCode.length());
			nutrition = addDiet(geneticCode, nutrition);
		}
		nutrition = sortString(nutrition);

		ageCurrent = 0;
		ageMax = (sumString(geneticCode) / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;
	}
	/**
	 * Creates a level 1 Creature with a defined gene code, complexity, and size.
	 * @param genes this Creatures geneticCode.
	 * @param complex this Creatures geneComplexity.
	 * @param size this Creatures geneSize.
	 */
	public Creature(String genes, int complex, int size){
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);
		digestPower = 1;
		hungerMax = 10;
		hungerCurrent = hungerMax;
		hungerRate = 1;

		geneComplexity = complex;
		geneSize = (byte)size;
		geneticCode = genes;

		nutrition = "" + eleGen(geneToSeed(geneticCode));
		for(int i = 1; i < geneComplexity; i++){
			geneticCode += geneGen(((int)Math.pow(geneSize, i + 1)) - geneticCode.length());
			nutrition = addDiet(geneticCode, nutrition);
		}
		nutrition = sortString(nutrition);

		ageCurrent = 0;
		ageMax = (sumString(geneticCode) / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;
	}
	/**
	 * Creates a level 1 Creature based off the genetics and stats of two other Creatures. Used for breeding.
	 * @param p1 one of the Creatures used to create this Creature.
	 * @param p2 the other Creature used to create this Creature.
	 */
	public Creature(Creature p1, Creature p2){
		double p1Val = (int)Math.pow(((geneToSeed(p1.getGeneticCode()) % 10) + sumString(p1.getGeneticCode())) * p2.getAgeMax(), Math.log(p1.getLevel()));
		double p2Val = (int)Math.pow(((geneToSeed(p2.getGeneticCode()) % 10) + sumString(p2.getGeneticCode())) * p2.getAgeMax(), Math.log(p2.getLevel()));
		double p1Weight = p1Val / (p1Val + p2Val);
		double p2Weight = p2Val / (p1Val + p2Val);

		geneSize  = p1.getGeneSize();
		geneComplexity = p1.getGeneComplexity();
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);

		geneticCode = "";
		int l = p1.getGeneticCode().length();
		for(int i = 0; i < l; i++){
			if(Math.random() < p1Weight) geneticCode += p1.getGeneticCode().charAt(i);
			else geneticCode += p2.getGeneticCode().charAt(i);
		}

		nutrition = "";
		l = p1.getNutrition().length();
		for(int i = 0; i < l; i++){
			if(p2.getNutrition().indexOf(p1.getNutrition().charAt(i)) != -1) nutrition += p1.getNutrition().charAt(i);
		}
		
		int n1 = 0;
		int n2 = 0;
		int extraNutrition = (int)Math.floor((p1Weight * (double)l) + (p2Weight * (double)p2.getNutrition().length())) + nutrition.length();
		while(nutrition.length() < extraNutrition){
			if(Math.random() < p1Weight && n1 < p1.getNutrition().length()){
				if(nutrition.indexOf(p1.getNutrition().charAt(n1)) == -1){
					nutrition += p1.getNutrition().charAt(n1);
				}
				n1++;
			}
			
			else if(nutrition.indexOf(p2.getNutrition().charAt(n2)) == -1){
				nutrition += p2.getNutrition().charAt(n2);
				n2++;
			}
			
			else{
				n2++;
				while(nutrition.length() < extraNutrition){
					char ele = eleGen();
					if(nutrition.indexOf(ele) == -1) nutrition += ele;
				}
			}
		}
		
		digestPower = (int)Math.floor(((p1Weight * p1.getDigestPower()) + (p2Weight * p2.getDigestPower())) * (1 / ((p1.getLevel() + p2.getLevel()) / 2)));
		hungerMax = (int)Math.floor(((p1Weight * p1.getHungerMax()) + (p2Weight * p2.getHungerMax())) * (1 / ((p1.getLevel() + p2.getLevel()) / 2)));
		hungerCurrent = hungerMax;
		hungerRate = (int)Math.floor((p1Weight * p1.getHungerRate()) + (p2Weight * p2.getHungerRate()));
		
		ageCurrent = 0;
		ageMax = (sumString(geneticCode) / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;
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
	 * Attempts to add a new element into a Creatures nutrition.
	 * @param genes the Creatures geneticCode.
	 * @param diet the Creatures nutrition.
	 * @return the Creatures updated nutrition.
	 */
	public String addDiet(String genes, String diet){
		char cur = eleGen(geneToSeed(genes));
		if(diet.indexOf(cur) == -1) return diet + cur;
		else return diet;
	}
	
	/**
	 * Uses this creatures geneticCode to generate a seed.
	 * @return the seed.
	 */
	public int geneToSeed(){
		return (int)(Math.pow(productString(geneticCode), (sumString(geneticCode) % 11)) + sumString(geneticCode));
	}
	
	/**
	 * Uses a genetic code to generate a seed.
	 * @param genes the genetic code used to generate the seed.
	 * @return the seed.
	 */
	public int geneToSeed(String genes){
		return (int)(Math.pow(productString(genes), (sumString(genes) % 11)) + sumString(genes));
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
	 * Used to increase this Creatures level and stats accordingly.
	 */
	public void levelUp()
	{
		level++;
		xpUntilLevel = xpCurve(level + 1);
		ageMax = (sumString(geneticCode) / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;

		if(geneToSeed(geneticCode) % 5 == 0) digestPower++;
		if(geneToSeed(geneticCode) % 5 == 1) hungerMax++;
		if(geneToSeed(geneticCode) % 5 == 2 && hungerRate >= .6) hungerRate -= .05;
		else hungerMax++;
		if(geneToSeed(geneticCode) % 5 == 3) addDiet(geneticCode, nutrition);
		if(geneToSeed(geneticCode) % 5 == 4) {
			geneComplexity++;
			geneticCode += geneGen((int)Math.pow(geneSize, geneComplexity) - geneticCode.length());
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
	
	/**
	 * Multiplies the character values of a String [A = 1, B = 2, C = 3, ext].
	 * @param s the String
	 * @return the product.
	 */
	public int productString(String s){
		int prod = 0;

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
	}

	@Override
	public String toString(){
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
				"ageCurrent: " + ageCurrent + '\n';
	}
	public int compareTo(Creature c) {
		return getWorth() - c.getWorth();
	}
}