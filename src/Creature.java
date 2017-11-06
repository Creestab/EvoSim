/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.0.1
 * @date 11/5/17
 */
public class Creature {
	protected String geneticCode;
	protected String nutrition;
	protected int level;
	protected int xpCurrent;
	protected int xpUntillLevel;
	protected int geneComplexity;
	protected byte geneSize;
	protected int digestPower;
	protected int hungerMax;
	protected int hungerCurrent;
	protected int hungerRate;

	public Creature(){
		geneComplexity = 1;
		geneSize = (byte)((Math.random() * 3) + 1);
		geneticCode = geneGen(geneSize);
		nutrition = "" + eleGen(geneToSeed(geneticCode));
		level = 1;
		xpCurrent = 0;
		xpUntillLevel = xpCurve(level);
		digestPower = 1;
		hungerMax = 10;
		hungerCurrent = hungerMax;
		hungerRate = 1;
	}
	public Creature(int complex, byte size){
		level = 1;
		xpCurrent = 0;
		xpUntillLevel = xpCurve(level);
		digestPower = 1;
		hungerMax = 10;
		hungerCurrent = hungerMax;
		hungerRate = 1;
		
		geneComplexity = complex;
		geneSize = size;
		
		geneticCode = geneGen(size ^ complex);
		
		nutrition = "";
		char cur;
		int failsafe;
		for(int i = 0; i < geneComplexity; i++){
			cur = eleGen(geneToSeed(geneticCode));
			while(nutrition.indexOf(cur) != -1){
				failsafe = 0;
				cur = eleGen(geneToSeed(geneticCode + cur) + failsafe);
				failsafe++;
			}
			nutrition += cur;
		}
	}
	public Creature(String parent1, String parent2){

	}

	public int geneToSeed(String genes){
		return (int)(((((Math.sqrt(2 ^ (sumString(genes) / (genes.length() + 1)))) 
							* ((((sumString(genes) / genes.length())+ 1)^ 2) 
							/ (3 * (sumString(genes) / (genes.length() - 1))) + 10))
							+ productString(genes)) % 350) + 1);
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
		else if(rand < 245) return 'W'; //4
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
		else if(seed < 245) return 'W'; //4
		else if(seed < 348) return 'X'; //3
		else if(seed < 350) return 'Y'; //2
		else if(seed < 351) return 'Z'; //1
		else{
			return '?';
		}
	}
	
	public int xpCurve(int lvl){
		return (5000 * (1.03 ^ lvl)) -5050;
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
}