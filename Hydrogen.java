package molecule;

public class Hydrogen extends Thread {

	private static int hydrogenCounter = 0;
	private int id;
	private Propane sharedPropane;
	
	public Hydrogen(Propane propane_obj) {
		Hydrogen.hydrogenCounter+=1;
		id=hydrogenCounter;
		this.sharedPropane = propane_obj;
	}
	
	public void run() {
		try {	
			/**
			 * 1. Acquire mutex semaphore.
			 * 2. Release mutex semaphore.
			 * 3. Acquire the hydrogenQ.
			 * 4. Ensure only 8 hydrogens and 3 carbons reach point.
			 * 5. Acquire mutex semaphore.
			 */
			sharedPropane.mutex.acquire();
			sharedPropane.mutex.release();
			sharedPropane.hydrogensQ.acquire();
			sharedPropane.barrier.phase1();
			sharedPropane.mutex.acquire();
	
			if (sharedPropane.getCarbon() == 0 && sharedPropane.getHydrogen() == 0){
				System.out.println("---Group Ready for bonding---");
			}
			/**
			 * 1. Add hydrogen molecule.
			 * 2. Bond Hydrogen molecule.
			 * 3. Release mutex semaphore.
			 * 4. Allow formed ATOM to pass barrier. Reset barrier.
			 * 5. Release the hydrogenQ to allow new threads
			 */
			sharedPropane.addHydrogen();
			sharedPropane.bond("H"+this.id);
			sharedPropane.mutex.release();
			sharedPropane.barrier.phase2();
			sharedPropane.hydrogensQ.release();
			
	    }
	    catch (InterruptedException ex) { /* not handling this  */}
	   // System.out.println(" ");
	}
}
