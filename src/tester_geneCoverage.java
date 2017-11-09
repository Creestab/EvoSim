/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.0.1
 * @date 11/5/17
 */

public class tester_geneCoverage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String genes = "";
		int[] eleCount = new int[26];
		
		Creature c;
		String cGenes = "";
		int l = 0;
		char ele = '?';
		int creatures = 0;
		while(/*(genes.length() < 26) && */(creatures < 100000)){
			c = new Creature((int)(Math.random() * 3) + 1, (int)((Math.random() * 4) + 1));
			cGenes = c.getGeneticCode();
			l = cGenes.length();
			for(int i = 0; i < l; i++){
				ele = cGenes.charAt(i);
				if(genes.indexOf(ele) == -1) genes += ele;
				
				if(ele == 'A') eleCount[0] += 1;		
				else if(ele == 'B') eleCount[1] += 1;	
				else if(ele == 'C') eleCount[2] += 1;
				else if(ele == 'D') eleCount[3] += 1;
				else if(ele == 'E') eleCount[4] += 1;
				else if(ele == 'F') eleCount[5] += 1;
				else if(ele == 'G') eleCount[6] += 1;
				else if(ele == 'H') eleCount[7] += 1;
				else if(ele == 'I') eleCount[8] += 1;
				else if(ele == 'J') eleCount[9] += 1;
				else if(ele == 'K') eleCount[10] += 1;
				else if(ele == 'L') eleCount[11] += 1;
				else if(ele == 'M') eleCount[12] += 1;
				else if(ele == 'N') eleCount[13] += 1;
				else if(ele == 'O') eleCount[14] += 1;
				else if(ele == 'P') eleCount[15] += 1;
				else if(ele == 'Q') eleCount[16] += 1;
				else if(ele == 'R') eleCount[17] += 1;
				else if(ele == 'S') eleCount[18] += 1;
				else if(ele == 'T') eleCount[19] += 1;
				else if(ele == 'U') eleCount[20] += 1;
				else if(ele == 'V') eleCount[21] += 1;
				else if(ele == 'W') eleCount[22] += 1;
				else if(ele == 'X') eleCount[23] += 1;
				else if(ele == 'Y') eleCount[24] += 1;
				else if(ele == 'Z') eleCount[25] += 1;
				else{
					eleCount[26] += 1;
				}
			}
			creatures++;
		}
		System.out.println("Creatures made: " + creatures + '\n');
		
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < eleCount.length; i++){
			System.out.println(letters.charAt(i) + ": " + eleCount[i]);
		}
	}

}
