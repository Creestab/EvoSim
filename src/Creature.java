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
	protected char[] nutrition;
	protected short level;
	protected int xpCurrent;
	protected int xpUntillLevel;
	protected short geneComplexity;
	protected byte geneSize;
	protected short digestPower;
	protected short hungerCurrent;
	protected short hungerRate;
	
	public Creature(){

	}
	public Creature(String parent1, String parent2){

	}
	
	public String geneGen(short comp, byte size){
		int length = size ^ comp;
		String gene = "";
		
		for(int i = 0; i < length; i++){
			gene += eleGen();
		}
		
		return gene;
	}
	
	public char eleGen(){
		short rand = (short)((Math.random() * 351));
		
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
}