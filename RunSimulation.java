package molecule;

import molecule.Carbon;
import molecule.Propane;
import molecule.Hydrogen;

public class RunSimulation {

	/**
	 * This class is sent the number of hydrogen and carbon atoms.
	 */
	public static void main(String[] args) {
		int no_hydrogens = Integer.parseInt(args[0]);
		int no_carbons = Integer.parseInt(args[1]);
		
		System.out.println("Starting simulation with "+no_hydrogens+" H and "+no_carbons + " C");
		
		Carbon[] carbons = new Carbon[no_carbons];
		Hydrogen[] hydrogens = new Hydrogen[no_hydrogens];
		Propane sharedPropane = new Propane();
		
		for (int i=0;i<no_carbons;i++) {
			carbons[i]=new Carbon(sharedPropane); // call constructor
			carbons[i].start(); // start thread
		}
		for (int i=0;i<no_hydrogens;i++) {
			hydrogens[i]= new Hydrogen(sharedPropane);
			hydrogens[i].start();
		}
	}
}
