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
	public static void main(String[] args) throws InterruptedException {


		int numCreatures		=25;		//the number of Creatures to start your simulation with.
		int minCreatures		=10;		//if the number of Creatures falls below this number, new ones will be created to match it
		int maxCreatures		=50;		//if the number of Creatures is higher than this number, the weakest creatures will be removed to match it
		int numSteps			=100;		//the number of steps this simulation will run while setSteps is =true.
		boolean setSteps 		=true;		//set to false for unlimited simulation. (NOTE: only do this in activityLog mode)
		boolean printCreatures	=true;		//If true, prints all of your Creatures every certain number of steps.
		int stepsPerPrint		=25;		//The number of steps in between each Creature set printing.
		boolean printToFile		=false;		//If true, saves Creature printings to a file instead of to Terminal.
		boolean activityLog		=true;		//Prints activity within each tick like breeding, eating, and other stats. Good for debugging.


		SimFuncs sim = new SimFuncs();			//Helper Functions
		ArrayList<Creature> creatures = sim.populate(numCreatures);		//Create a bunch of random creatures
			if(activityLog) System.out.println("Number of Creatures on Initialization:" + creatures.size() + '\n');
		ArrayList<Creature> shufCreatures1;		//Array for one half the creatures
		ArrayList<Creature> shufCreatures2;		//Array for the other half of the creatures
		Creature HS_XP = creatures.get(0);
		Creature HS_Age = creatures.get(0);
		Creature tempCreature1 = null;			//Used for creature comparisons
		Creature tempCreature2 = null;			//Used for creature comparisons
		int numC = creatures.size();			//The amount of creatures in our current population
		int step = 0;							//The step we are currently on
		while(!setSteps || step < numSteps){	//Runs the loop a set amount of steps			
			//Beginning of step code.
			step++;							//Increases current step
			for(int i = 0; i < numC; i++){	//Calls the basic tick actions for each creature
				if(creatures.get(i).getXP() > HS_XP.getXP() && !sim.RIP(creatures.get(i))) HS_XP = creatures.get(i);
				if(creatures.get(i).getAgeCurrent() > HS_Age.getAgeCurrent() && !sim.RIP(creatures.get(i))) HS_Age = creatures.get(i);
				creatures.get(i).tick();
			}
			//if(activityLog) System.out.println("Tick #" + step + "_ Current XP High Score: "); HS_XP.print();
			//if(activityLog) System.out.println('\n' + "Tick #" + step + "_ Current Age High Score: "); HS_Age.print();
			/*
			 * TODO Write your simulation below this comment:
			 */


			Collections.shuffle(creatures);		//Randomize the creatures indices in the List
				if(activityLog) System.out.println("Tick #" + step + "_ Number of Creatures before pairing: " + numC);
			if(numC % 2 == 1){					//If there is an odd amount of creatures, set one aside
				tempCreature1 = creatures.get(numC - 1);	//Stores the odd creature in a placeholder
				numC--;							//Reduces the variable for amount of creatures by 1, making it even
			}
			shufCreatures1 = new ArrayList<Creature>(creatures.subList(0, numC / 2));	//Splits the first half of the List into a separate one
				if(activityLog) System.out.println("Tick #" + step + "_ Number of Creatures in Array 1: " + shufCreatures1.size());
			shufCreatures2 = new ArrayList<Creature>(creatures.subList(numC/2, numC));	//Splits the second half of the List into a separate one
				if(activityLog) System.out.println("Tick #" + step + "_ Number of Creatures in Array 2: " + shufCreatures2.size() + '\n');


			creatures.clear();					//Empties the main Creature List
			if(!(tempCreature1 == null) && !sim.RIP(tempCreature1)) creatures.add(tempCreature1);	//If there is an odd creature that isn't set to die, add it back to the main List

			for(int i = 0; i < numC / 2; i++){			//Runs a loop for each creature pairing
				tempCreature1 = shufCreatures1.get(i);	//Gets the first creature of the pair
				tempCreature2 = shufCreatures2.get(i);	//Gets the second creature of the pair

				if(sim.breedable(tempCreature1, tempCreature2) && tempCreature1.getHungerRatio() > .2 && tempCreature2.getHungerRatio() > .2){	//If both creatures are compatible to breed and not starving
						if(activityLog) System.out.println("Tick #" + step + "_ " + tempCreature1.getGeneticCode() + " and " + tempCreature2.getGeneticCode() + " breed.");
					tempCreature1.xpGain(tempCreature2.getLevel() * tempCreature2.sumGenes());	//Gain XP
					if(!sim.RIP(tempCreature1)) creatures.add(tempCreature1);					//Add creature back into the main List if it isn't set to die

					tempCreature2.xpGain(tempCreature1.getLevel() * tempCreature1.sumGenes());	//Gain XP
					if(!sim.RIP(tempCreature2)) creatures.add(tempCreature2);					//Add creature back into the main List if it isn't set to die

					creatures.add(new Creature(tempCreature1, tempCreature2));					//Add the offspring of the pair to the list
				}
				
				else if(tempCreature1.getHungerRatio() < .75 && sim.whoEatsWho(tempCreature1, tempCreature2)){		//If creature 1 is hungry and is "stronger" than creature 2, it eats it
						if(activityLog) System.out.println("Tick #" + step + "_ " + tempCreature1.getGeneticCode() + " eats " + tempCreature2.getGeneticCode() + ".");
					tempCreature1.eat(tempCreature2.getGeneticCode(), tempCreature2.getGeneComplexity());		//Creature 1 eats Creature 2
					if(!sim.RIP(tempCreature1)) creatures.add(tempCreature1);					//Add creature back into the main List if it isn't set to die
				}
				
				else if(tempCreature2.getHungerRatio() < .75){									//If creature 2 is hungry and is "stronger" than creature 1, it eats it
						if(activityLog) System.out.println("Tick #" + step + "_ " + tempCreature2.getGeneticCode() + " eats " + tempCreature1.getGeneticCode() + ".");
					tempCreature2.eat(tempCreature1.getGeneticCode(), tempCreature1.getGeneComplexity());		//Creature 2 eats Creature 1
					if(!sim.RIP(tempCreature2)) creatures.add(tempCreature2);					//Add creature back into the main List if it isn't set to die
				}
				
				else{															//If neither Creature is hungry, they eat a basic random meal and get added back
					tempCreature1.eat(""+sim.eleGen((int)(Math.random() * step) + 1), 1 + (int)Math.log(step));	//Eats some basic nutrition
					tempCreature2.eat(""+sim.eleGen((int)(Math.random() * step) + 1), 1 + (int)Math.log(step));	//Eats some basic nutrition
					
					if(!sim.RIP(tempCreature1)) creatures.add(tempCreature1);		//Add creature back into the main List if it isn't set to die
					if(!sim.RIP(tempCreature2)) creatures.add(tempCreature2);		//Add creature back into the main List if it isn't set to die
					}
			}
			if(activityLog) System.out.println('\n' + "Tick #" + step + "_ Number of Creatures after pairing but before purge/repopulation: " + creatures.size() + '\n');

			Collections.sort(creatures);											//Resorts the creatures
			while(creatures.size() > maxCreatures) creatures.remove(0);				//If there are too many creatures, remove the weakest ones
			while(creatures.size() < minCreatures) creatures.add(new Creature());	//If there are not enough creatures, make new random ones



			//End of step code.
			numC = creatures.size();
			if(printCreatures && (step % stepsPerPrint) == 0){
				if(printToFile){

				}
				else{
					System.out.println("Step #: " + step);
					System.out.println("Number of Creatures: " + numC +'\n');
					sim.printCreatures(creatures);
					System.out.println("-----------------------------------------------------------------");
					System.out.println('\n');
				}
			}
			Thread.sleep(50);
		}
		//Simulation Overview
		System.out.println("Highest Level Creature Recorded:"); HS_XP.print();
		System.out.println('\n' + "Oldest Creature Recorded:"); HS_Age.print();
	}
}
