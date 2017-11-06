/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.0.1
 * @date 11/5/17
 */
public class tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Creature c1 = new Creature();
		Creature c2 = new Creature(2,(byte)2);
		Creature c3 = new Creature(2, (byte)3);
		Creature c4 = new Creature(3, (byte)2);
		
		System.out.println(c1.getGeneticCode());
		System.out.println(c1.getGeneticCodeFormatted());
		System.out.println(c1.getNutrition());
		System.out.println(c1.getLevel());
		System.out.println(c1.getXP());
		System.out.println(c1.getDigestPower());
		System.out.println(c1.getHungerMax());
		System.out.println(c1.getHungerCurrent());
		System.out.println(c1.getHungerRate());
		
		System.out.println(c2.getGeneticCode());
		System.out.println(c2.getGeneticCodeFormatted());
		System.out.println(c2.getNutrition());
		System.out.println(c2.getLevel());
		System.out.println(c2.getXP());
		System.out.println(c2.getDigestPower());
		System.out.println(c2.getHungerMax());
		System.out.println(c2.getHungerCurrent());
		System.out.println(c2.getHungerRate());
		
		System.out.println(c3.getGeneticCode());
		System.out.println(c3.getGeneticCodeFormatted());
		System.out.println(c3.getNutrition());
		System.out.println(c3.getLevel());
		System.out.println(c3.getXP());
		System.out.println(c3.getDigestPower());
		System.out.println(c3.getHungerMax());
		System.out.println(c3.getHungerCurrent());
		System.out.println(c3.getHungerRate());
		
		System.out.println(c4.getGeneticCode());
		System.out.println(c4.getGeneticCodeFormatted());
		System.out.println(c4.getNutrition());
		System.out.println(c4.getLevel());
		System.out.println(c4.getXP());
		System.out.println(c4.getDigestPower());
		System.out.println(c4.getHungerMax());
		System.out.println(c4.getHungerCurrent());
		System.out.println(c4.getHungerRate());
	}

}
