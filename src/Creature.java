/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.0.1
 * @date 11/5/17
 */
public class Creature {
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

	public Creature(){
		geneComplexity = 1;
		geneSize = (byte)((Math.random() * 3) + 2);
		geneticCode = geneGen(geneSize);
		nutrition = "" + eleGen(geneToSeed(geneticCode));
		level = 1;
		xpCurrent = 0;
		xpUntilLevel = xpCurve(level);
		digestPower = 1;
		hungerMax = 10;
		hungerCurrent = hungerMax;
		hungerRate = 1;
		ageCurrent = 1;
		ageMax = (sumString(geneticCode) / 3) + 1;
	}
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

		ageCurrent = 1;
		ageMax = (sumString(geneticCode) / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;
	}
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

		ageCurrent = 1;
		ageMax = (sumString(geneticCode) / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;
	}
	public Creature(String parent1, String parent2){

	}

	public void tick(){
		if(xpCurrent >= xpUntilLevel) levelUp();
		hungerCurrent -= hungerRate;
		ageCurrent++;
	}

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
			hungerCurrent += nutrients;
			if(hungerCurrent > hungerMax) hungerCurrent = hungerMax;

			xpCurrent += nutrients * (preyComplexity + 1) * 10;
		}
	}

	public String addDiet(String genes, String diet){
		char cur = eleGen(geneToSeed(genes));
		if(diet.indexOf(cur) == -1) return diet + cur;
		else return diet;
	}

	public int geneToSeed(String genes){
		return (int)(Math.pow(productString(genes), (sumString(genes) % 11)) + sumString(genes));
	}

	public String geneGen(int length){
		String gene = "";

		for(int i = 0; i < length; i++){
			gene += eleGen();
		}

		return gene;
	}

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

	public void levelUp()
	{
		level++;
		xpUntilLevel = xpCurve(level + 1);
		ageMax = (sumString(geneticCode) / 4) + (int)(3 * Math.pow(geneComplexity, 2)) 
				- (int)(Math.pow(geneSize * geneticCode.length(), .5)) + 5;

		if(geneToSeed(geneticCode) % 5 == 0) digestPower++;
		if(geneToSeed(geneticCode) % 5 == 1) hungerMax++;
		if(geneToSeed(geneticCode) % 5 == 2) hungerRate -= .1;
		if(geneToSeed(geneticCode) % 5 == 3) addDiet(geneticCode, nutrition);
		if(geneToSeed(geneticCode) % 5 == 4) {
			geneComplexity++;
			geneticCode += geneGen((int)Math.pow(geneSize, geneComplexity) - geneticCode.length());
		}
	}

	public void xpGain(int gain){
		xpCurrent += gain;
	}

	public int xpCurve(int lvl){
		return ((int)(5000 * Math.pow(1.03, lvl))) -5050;
	}

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

	public String getGeneticCodeFormatted(){
		String form = "[";
		for(int i = 0; i < geneticCode.length(); i++){
			form += geneticCode.substring(i, i + 1);
			if((i + 1) % geneSize == 0) form += "][";
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

	public int getAgeMax(){
		return ageMax;
	}

	public int getAgeCurrent(){
		return ageCurrent;
	}

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
}