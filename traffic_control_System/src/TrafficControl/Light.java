package TrafficControl;

import TrafficControl.Junction; 

// Light class
public abstract class Light {
	
	// Variables definition
	public int DelayVal= 2 + (int)(Math.random() * ((4 - 2) + 1));
	private int delayTime;
	private int bit;
	private Road currentGreenRoad;
	private Junction newJunction;
	
	// This function creates a light at the requested junction
	public Light(Junction junction) {
		 delayTime = DelayVal;
		 bit=0;
		 this.newJunction=junction;
		 greenLight();			
	}
	
	// Checks if delay time is over performing 
	public void check() {
		//System.out.println("Light at " + getJunction().getName() + " is performing check");
		if (bit%delayTime==0) {			
			greenLight();
		}
	}
	
	// Gets delay time for this light
	public int getDelay() {
		return delayTime;
	}
	
	// Sets delay time for this light
	public void setDelay(int delayTime) {
		this.delayTime = delayTime;
	}
	
	// Gets bit
	public int getBit() {
		return bit;
	}
	
	// Sets bit
	public void setBit(int bit) {
		this.bit = bit;
	}
	
	// Returns the road that is currently green
	public Road getCurrentGreen() {
		return currentGreenRoad;
	}
	
	// Sets the green road to the requested road
	public void setCurrentGreen(Road currentGreenRoad) {
		this.currentGreenRoad = currentGreenRoad;
	}
	
	// Gets the light's junction
	public Junction getJunction() {
		return newJunction;
	}
	
	// Sets the light's junction to the requested junction
	public void setJunction(Junction junction) {
		this.newJunction = junction;
	}
	
	// Green light abstract definition
	public abstract void greenLight();
}