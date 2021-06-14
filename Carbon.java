package molecule;

public class Carbon extends Thread {
	
	private static int carbonCounter =0;
	private int id;
	private Propane sharedPropane;
	
	public Carbon(Propane propane_obj) {
		Carbon.carbonCounter+=1;
		id=carbonCounter;
		this.sharedPropane = propane_obj;
	}
	
	public void run() {
		try {
			sharedPropane.mutex.acquire();
			sharedPropane.addCarbon(); 

			if (sharedPropane.getHydrogen() >= 8) 

			{

				System.out.println("---Group ready for bonding---"); 

				sharedPropane.carbonQ.release(3); 
				sharedPropane.removeCarbon(3);  

				sharedPropane.hydrogensQ.release(8); 
				sharedPropane.removeHydrogen(8); 
				
				
			} 


			else
			{
				sharedPropane.mutex.release(); 
			}

			sharedPropane.carbonQ.acquire(); 
			sharedPropane.bond("C" + this.id); 
			sharedPropane.barrier.b_wait(); 
			sharedPropane.mutex.release(); 

		} 
	    catch (InterruptedException ex) { }
	   
	}
}
