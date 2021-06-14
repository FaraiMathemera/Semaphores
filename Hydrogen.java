package molecule;

public class Hydrogen extends Thread {

	private static int hydrogenCounter =0;
	private int id;
	private Propane sharedPropane;
	
	
	public Hydrogen(Propane propane_obj) {
		Hydrogen.hydrogenCounter+=1;
		id=hydrogenCounter;
		this.sharedPropane = propane_obj;
		
	}
	
	public void run() {
		try {	 
						sharedPropane.mutex.acquire();
						sharedPropane.addHydrogen();

						
						if(sharedPropane.getHydrogen()<8 || sharedPropane.getCarbon()<3)
						{
							sharedPropane.mutex.release();
						}
						else if(sharedPropane.getHydrogen()>=8 && sharedPropane.getCarbon()>=3)
						{
							System.out.println("---Group ready for bonding---h");
							sharedPropane.hydrogensQ.release(8);
							sharedPropane.removeHydrogen(8);

							sharedPropane.carbonQ.release(3); 
							sharedPropane.removeCarbon(3);
						}

						sharedPropane.hydrogensQ.acquire();
						sharedPropane.bond("H"+ this.id); 
						sharedPropane.barrier.b_wait();
						sharedPropane.mutex.release();
	    }
	    catch (InterruptedException ex) { /* not handling this  */}
	   // System.out.println(" ");
	}
}
