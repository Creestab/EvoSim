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

public class Simulation_Skeleton{
	public static void main(String[] args) {


		int numCreatures		=10;		//the number of Creatures to start your simulation with.
		int numSteps			=1;			//the number of steps this simulation will run while setSteps is =true.
		boolean setSteps 		=true;		//set to false for unlimited simulation. (NOTE: only do this in debug mode)
		boolean printCreatures	=true;		//If true, prints all of your Creatures every certain number of steps.
		int stepsPerPrint		=1;			//The number of steps in between each Creature set printing.
		boolean sortPrint		=false;		//If true, sorts the creatures by total XP before printing.
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







			//End of step code.
			numC = creatures.size();
			if(printCreatures && (step % stepsPerPrint) == 0){
				if(sortPrint){
					Collections.sort(creatures);
				}
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