import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 */

/**
 * @author Chris Berghoff
 * @version 0.0.1
 * @date 11/9/17
 */

public class Simulation{
	public static void main(String[] args) {


		int numCreatures		=10;		//the number of Creatures to start your simulation with.
		int maxCreatures		=100;		//
		int numSteps			=100;		//the number of steps this simulation will run while setSteps is =true.
		boolean setSteps 		=true;		//set to false for unlimited simulation. (NOTE: only do this in debug mode)
		boolean printCreatures	=true;		//If true, prints all of your Creatures every certain number of steps.
		int stepsPerPrint		=10;		//The number of steps in between each Creature set printing.
		boolean printToFile		=false;		//If true, saves Creature printings to a file instead of to Terminal.


		SimFuncs sim = new SimFuncs();
		ArrayList<Creature> creatures = sim.populate(numCreatures);
		ArrayList<Creature> shufCreatures1;
		ArrayList<Creature> shufCreatures2;
		Creature tempCreature1 = null;
		Creature tempCreature2 = null;
		int numC = creatures.size();
		int step = 0;
		while(!setSteps || step < numSteps){
			//Beginning of step code.
			step++;
			for(int i = 0; i < numC; i++){
				creatures.get(i).tick();
			}
			/*
			 * TODO Write your simulation below this comment:
			 */


			Collections.shuffle(creatures);
			if(numC % 2 == 1){
				tempCreature1 = creatures.get(numC - 1);
				numC--;
			}
			shufCreatures1 = new ArrayList<Creature>(creatures.subList(0, numC / 2));
			shufCreatures2 = new ArrayList<Creature>(creatures.subList(numC/2, numC));

			creatures.clear();
			if(!(tempCreature1 == null) && !sim.RIP(tempCreature1)) creatures.add(tempCreature1);

			for(int i = 0; i < numC / 2; i++){
				tempCreature1 = shufCreatures1.get(i);
				tempCreature2 = shufCreatures2.get(i);

				if(sim.breedable(tempCreature1, tempCreature2) && tempCreature1.getHungerRatio() > .2 && tempCreature2.getHungerRatio() > .2){
					tempCreature1.xpGain(tempCreature2.getLevel() * tempCreature2.sumGenes());
					if(!sim.RIP(tempCreature1)) creatures.add(tempCreature1);

					tempCreature2.xpGain(tempCreature1.getLevel() * tempCreature1.sumGenes());
					if(!sim.RIP(tempCreature2)) creatures.add(tempCreature2);

					creatures.add(new Creature(tempCreature1, tempCreature2));
				}
				else if(tempCreature1.getHungerRatio() < .75 && sim.whoEatsWho(tempCreature1, tempCreature2)){
					tempCreature1.eat(tempCreature2.getGeneticCode(), tempCreature2.getGeneComplexity());
					if(!sim.RIP(tempCreature1)) creatures.add(tempCreature1);
				}
				else if(tempCreature2.getHungerRatio() < .75){
					tempCreature2.eat(tempCreature1.getGeneticCode(), tempCreature1.getGeneComplexity());
					if(!sim.RIP(tempCreature2)) creatures.add(tempCreature2);
				}
				else{
					if(!sim.RIP(tempCreature1)) creatures.add(tempCreature1);
					if(!sim.RIP(tempCreature2)) creatures.add(tempCreature2);
				}
			}

			Collections.sort(creatures);
			while(creatures.size() > maxCreatures) creatures.remove(0);



			//End of step code.
			numC = creatures.size();
			if(printCreatures && (step % stepsPerPrint) == 0){
				if(printToFile){

				}
				else{
					System.out.println("Step #: " + step);
					System.out.println("Number of Creatures: " + numC +'\n');
					sim.printCreatures(creatures);
					System.out.println('\n');
				}
			}
		}
	}
}
