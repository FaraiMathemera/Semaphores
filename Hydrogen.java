package molecule;

public class Hydrogen extends Thread {

	private static int carbonCounter =0;
	private int id;
	private Propane sharedPropane;
	
	
	public Hydrogen(Propane propane_obj) {
		Hydrogen.carbonCounter+=1;
		id=carbonCounter;
		this.sharedPropane = propane_obj;
		
	}
	
	public void run() {
		try {
			sharedPropane.mutex.acquire(); 
			sharedPropane.addHydrogen();   
			
			
			if (sharedPropane.getHydrogen() >= 8 && sharedPropane.getCarbon() >= 3) {

				System.out.println("---Group ready for bonding---"); 

				sharedPropane.hydrogensQ.release(8);
				sharedPropane.removeHydrogen(8);   

				sharedPropane.carbonQ.release(3);    
				sharedPropane.removeCarbon(3);     

			}

			else{
				sharedPropane.mutex.release(); 
			    }

			sharedPropane.hydrogensQ.acquire(); 

			sharedPropane.bond("H" + this.id);

			sharedPropane.barrier.b_wait();  
			

		}
	   catch (InterruptedException ex) { }
	
	}
}
