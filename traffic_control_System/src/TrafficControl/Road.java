package TrafficControl;

import java.text.DecimalFormat;
import java.util.Random;

// Road class 
public class Road {
	
	// Random definition
		Random r = new Random();
	
	// Decimal format 
	DecimalFormat df = new DecimalFormat("#.##");	

	// Variables definition
	private Junction startJunc;
	private Junction endJunc;
	public static int roadID=1;
	public int thisroadID;
	public String name;
	public double DefaultX = 800 * r.nextDouble();
	public double DefaultY = 600 * r.nextDouble();
	public double RoadLength;
	public Road enter;
	public Road exit;

	// This function creates a road instance 
	public Road(Junction startJunc, Junction endJunc) {
		thisroadID = roadID++;
		this.startJunc = startJunc;
		
		// If the road gets same junction twice, this creates a new random junction and sets the end junction to it
		if (endJunc.equals(startJunc)){	
			Junction JuncRand = new Junction(DefaultX, DefaultY);
			this.endJunc = JuncRand;
			System.out.println("Road got the same junction twice , this new end junction is created instead : ");
			((Junction) JuncRand).printJunction();
		}
		else {
			this.endJunc = endJunc;
		}		
		
		// Calculate road length
		this.RoadLength = this.startJunc.calcDistance(this.endJunc);
		
		// Adds the created road to entering and exiting roads in the junctions
		startJunc.getExit().add(this);
		endJunc.getEnter().add(this);
		
		// Prints the road
		System.out.println("Creating Road number " + thisroadID + " from " + this.startJunc.getName() + " to " + this.endJunc.getName() +
				" , Length : " + df.format(this.RoadLength));
		System.out.println(this.getName());
	}
	
	// This function returns the name of the road 
	public String getName(){
		this.name = "Road " + thisroadID + "- Road from " +this.startJunc.getName()+ " to " + this.endJunc.getName();
		return this.name;
	}
	
	// This function returns the end junction of the road 
	public Junction getEndJunction() {
		return this.endJunc;
	}
	
	// Sets the end junction
	public void setEndJunc(Junction endJunc){
		this.endJunc =endJunc;
	}	
	
	// Sets the start junction
	public void setStartJunc(Junction startJunc){
		this.startJunc = startJunc;
	}	
	
	// This function returns the start junction of the road 
	public Junction getStartJunction() {
		return this.startJunc;
	}
	
	// Returns the road length
	public double getRoadLength() {
		return this.RoadLength;
	}
}