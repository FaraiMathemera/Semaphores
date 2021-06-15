package molecule;

import molecule.Carbon;
import molecule.Propane;
import molecule.Hydrogen;

public class RunSimulation {

	/**
	 * This class is sent the number of hydrogen and carbon atoms.
	 */
	static void createThreads(int carbon, int hydrogen){
		Carbon[] carbons = new Carbon[carbon];
		Hydrogen[] hydrogens = new Hydrogen[hydrogen];
		Propane sharedPropane = new Propane();
		
		for (int i=0;i<carbon;i++) {
			carbons[i]=new Carbon(sharedPropane); // call constructor
			carbons[i].start(); // start thread
		}
		for (int i=0;i<hydrogen;i++) {
			hydrogens[i]= new Hydrogen(sharedPropane);
			hydrogens[i].start();
		}	
	}
	
	public static void main(String[] args) {
		int no_hydrogens = Integer.parseInt(args[0]);
		int no_carbons = Integer.parseInt(args[1]);
		/**
		 * Validation: Ensure we have correct number of molecules. discard redundant molecules
		 */
		if (no_hydrogens < 8 || no_carbons < 3){
			System.out.println("Insufficient amount of molecules. You need a minimum of 8 Hydrogen molecules and 3 carbon.");
			System.out.println("Simulation Terminated");
			System.exit(1);
		}else {
			
			System.out.println("Starting simulation with "+no_hydrogens+" H and "+no_carbons + " C");
			if(no_hydrogens % 8 == 0 && no_carbons % 3 == 0 && no_hydrogens / 8 == no_carbons / 3) {
				createThreads(no_carbons, no_hydrogens);
			}else {
				System.out.println("The amount of carbon molecules should be a multiple 3 and hydrogen a multiple of 8 respectively.");
				System.out.println("The extra molecules will be discarded in the simulation.");
				no_carbons = no_carbons - (no_carbons % 3);
				no_hydrogens = no_hydrogens - (no_hydrogens % 8);
				createThreads(no_carbons, no_hydrogens);
			}
		}
		
	}
}
