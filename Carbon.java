package molecule;

public class Carbon extends Thread {
	
	private static int carbonCounter = 0;
	private int id;
	private Propane sharedPropane;
	public static boolean alreadyExecuted = false;
	
	public Carbon(Propane propane_obj) {
		Carbon.carbonCounter+=1;
		id=carbonCounter;
		this.sharedPropane = propane_obj;
	}
	
	public void run() {
		try {	 
						/**
						 *  1. Acquire mutex semaphore. And ensure its run once.
						 */
						sharedPropane.mutex.acquire(); 
							if(!alreadyExecuted) {							
							sharedPropane.carbonQ.release(3);
							sharedPropane.hydrogensQ.release(8);
							alreadyExecuted = true;
						}
						/**
						 * 1. Release mutex semaphore.
						 * 2. Acquire the hydrogenQ.
						 * 3. Ensure only 8 hydrogens and 3 carbons reach point.
						 * 4. Acquire mutex semaphore.
						 */
						sharedPropane.mutex.release();
						sharedPropane.carbonQ.acquire();
						sharedPropane.barrier.phase1();
						sharedPropane.mutex.acquire();

						if (sharedPropane.getCarbon() == 0 && sharedPropane.getHydrogen() == 0){
							System.out.println("---Group Ready for bonding---");
						}
						/**
						 * 1. Add Carbonn molecule.
						 * 2. Bond Carbon molecule.
						 * 3. Release mutex semaphore.
						 * 4. Allow formed ATOM to pass barrier. Reset barrier.
						 * 5. Release the carbonQ to all new threads
						 */
						sharedPropane.addCarbon();		
						sharedPropane.bond("C"+this.id);
						sharedPropane.mutex.release();
						sharedPropane.barrier.phase2();
						sharedPropane.carbonQ.release(); 
						
						
	    	  	   	 
	    }
	    catch (InterruptedException ex) { /* not handling this  */}
	   // System.out.println(" ");
	}
}
