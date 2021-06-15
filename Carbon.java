package molecule;

public class Carbon extends Thread {
	
	private static int carbonCounter =0;
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

						sharedPropane.mutex.acquire(); 
							if(!alreadyExecuted) {							
							sharedPropane.carbonQ.release(3);
							sharedPropane.hydrogensQ.release(8);
							alreadyExecuted = true;
						}
						
						sharedPropane.mutex.release();
						sharedPropane.carbonQ.acquire();
						sharedPropane.barrier.phase1();
						sharedPropane.mutex.acquire();
						
						if (sharedPropane.getCarbon() == 0 && sharedPropane.getHydrogen() == 0){
							System.out.println("---Group Ready for bonding---");
						}
							
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
