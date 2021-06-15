package molecule;
import java.util.concurrent.atomic.AtomicBoolean;

public class Hydrogen extends Thread {

	private static int hydrogenCounter =0;
	private int id;
	private Propane sharedPropane;
	public static boolean flag = true;

	
	
	public Hydrogen(Propane propane_obj) {
		Hydrogen.hydrogenCounter+=1;
		id=hydrogenCounter;
		this.sharedPropane = propane_obj;
		
	}
	
	public void run() {
		try {	 
			sharedPropane.mutex.acquire();
			sharedPropane.mutex.release();
			sharedPropane.hydrogensQ.acquire();
			sharedPropane.barrier.phase1();
			sharedPropane.mutex.acquire();
			System.out.println("Hy__Carbon: " + sharedPropane.getCarbon() +"--- Hydrogen: "+sharedPropane.getHydrogen());	
			if (sharedPropane.getCarbon() == 0 && sharedPropane.getHydrogen() == 0){
				System.out.println("---Group Ready for bonding---");
			}
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
