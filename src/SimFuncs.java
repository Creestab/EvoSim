/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.1.1
 * @date 11/9/17
 */
public class SimFuncs {
	public SimFuncs() {
		
	}

	/**
	 * Determines which creature will eat the other in a paring.
	 * @param c1 is a creature in a pair.
	 * @param c2 is the other creature in a pair.
	 * @return if true, c1 eats c2. If false, c2 eats c1.
	 */
	public boolean whoEatsWho(Creature c1, Creature c2){
		return c1.getLevel() * ((c1.geneToSeed() % 3) + 1) * Math.floor(c1.getHungerCurrent() / c1.getHungerMax()) 
				> c2.getLevel() * ((c2.geneToSeed() % 3) + 1) * Math.floor(c2.getHungerCurrent() / c2.getHungerMax());
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
}
