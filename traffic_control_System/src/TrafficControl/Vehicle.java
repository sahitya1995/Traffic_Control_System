package TrafficControl;

import java.util.ArrayList;

// Vehicle class
public class Vehicle {
	
	// Vehicle variables definitions
	public int speedVal=  30 + (int)(Math.random() * ((120 - 30) + 1));
	protected int vehicleSpeed;
	private String name;
	public static int VehicleID=1;
	public int thisVehicleID;
	private int counter;
	private int lastRoadIndex;
	private int currentRoadIndex;
	private int currentJunctionIndex;
	private ArrayList<Junction> trackJunctions;
	private ArrayList<Road> trackRoads;
	private Road currentRoad;
	private Junction currentJunction;
	private boolean finishedTrack;
	private boolean trackWasReplaced = false;
	private Map vehicleMap;
	
	// This function creates a vehicle according to the map
	public Vehicle(Map map) {
		
		// Sets vehicle ID
		thisVehicleID = VehicleID++;
		
		// Sets vehicle speed
		vehicleSpeed = speedVal;
		
		// Prints vehicle name and speed
		System.out.println("\r\n" + this.getName() + " was created, Speed of " + this.getName() + " is " + vehicleSpeed);
		
		//  Creates the vehicle's track and collects that track's necessary data
		thisVehicleTrack(map, this);
		setTrackJunctions(map);
		setTrackRoads(map);

		// Settings for the current and last road indexes that are later used for the "move" function 
		currentRoadIndex =0;
		lastRoadIndex = trackRoads.size()-1;
		
		// Settings for the current index that is later used for the "move" function
		currentJunctionIndex =0;

		// Set finished track to "false"
		finishedTrack = false;
	}
	
	// This function creates a track for the vehicle
	public void thisVehicleTrack(Map map, Vehicle vehicle) {
		map.createTrack(vehicle);
	}
	
	public void setVehicleMap(Map vehicleMap) {
		this.vehicleMap = vehicleMap;
	}
	
	public Map getVehicleMap() {
		return vehicleMap;
	}
	
	// Gets the list of junctions in this vehicle's track
	public ArrayList<Junction> getTrackJunctions(){
		return trackJunctions;
	}
	
	// Sets the list list of junctions in this vehicle's track
	public void setTrackJunctions(Map map){
		this.trackJunctions = map.getTrackJunctions();
	}
	
	// Sets the list list of junctions in this vehicle's track
	public void resetTrackJunctions(ArrayList<Junction> trackJunctions){
		this.trackJunctions = trackJunctions;
	}	
	
	
	// Gets the list of roads in this vehicle's track
	public ArrayList<Road> getTrackRoads(){
		return trackRoads;
	}
	
	// Sets the list of roads in this vehicle's track
	public void setTrackRoads(Map map){
		this.trackRoads = map.getTrackRoads();
	}	
	
	// Sets the list of roads in this vehicle's track
	public void resetTrackRoads(ArrayList<Road> trackRoads){
		this.trackRoads = trackRoads;
	}	

	
	// This function returns the name of the vehicle 
	public String getName(){
		this.name = "Vehicle " + thisVehicleID;
		return this.name;
	}

	// This function moves the vehicle on the track, and prints the vehicle's current position
	// in the track each time it is used
	public void move() {
		
		// Counter for progress in the road is up by one
		counter++;
						
		// Sets the current road the vehicle is on
		currentRoad = trackRoads.get(currentRoadIndex);
		
		// Sets the current junction the vehicle is on
		currentJunction = trackJunctions.get(currentJunctionIndex);
		
		// First junction deceleration 
		if (counter==1) {
			System.out.println("\r\n" + this.getName() + " is now on " + currentJunction.getName() + "\r\n" + 
					"Which is junction number " + (currentJunctionIndex+1) + " on it's track.");
		}
		else {
			;
		}
		
		// Waiting for green light 
		if((counter==1) && (!(currentJunction.getLight() == null)) && (!(currentJunction.getLight().getCurrentGreen().equals(currentRoad))) 
				&& (!(finishedTrack))) {
			System.out.println( this.getName() + " Is waiting in " + currentJunction.getName()  + "\r\n"
					+ "for a green light on " + currentRoad.getName());
		}
		
		// Acts according to the vehicle position in the track, according to 5 possible cases		
		else {
			
			// Case 1 - vehicle is in the middle of a road that is not the last road on it's track
			if ((((currentRoad.RoadLength)-(counter*vehicleSpeed))>0) && (currentRoadIndex < lastRoadIndex) && (!finishedTrack)) {
				
				// Prints the vehicle current position
				System.out.println("\r\n" + this.getName() + " is now on " + currentRoad.getName() + "\r\n" + 
						"Which is road number " + (currentRoadIndex+1) + " on it's track." + "\r\n" + 
						"The vehicle passed " + (counter*vehicleSpeed) + " of this road already, " + "\r\n" + 
						"And has " + ((currentRoad.RoadLength)-(counter*vehicleSpeed)) + " left to finish the road");
			}

			// Case 2 - vehicle finished a road that is not the last road on it's track
			else if ((((currentRoad.RoadLength)-(counter*vehicleSpeed))<=0) && (currentRoadIndex < lastRoadIndex) && (!finishedTrack)) {
				
				// Prints the vehicle current position
				System.out.println("\r\n" + this.getName() + " just finished " + currentRoad.getName() + "\r\n" + 
						"Which was road number " + (currentRoadIndex+1) + " on it's track." + "\r\n" +
						 "On the next turn, the vehicle would continue to road number "
						 + (currentRoadIndex+2) + " on it's track");
				
				// Sets the current junction index for the next junction
				currentJunctionIndex ++;
				
				// Sets the current road index for the next road
				currentRoadIndex++;
				
				// Sets the counter for progress in the road to zero
				counter=0;
			}

			// Case 3 - vehicle is in the middle of the last road on it's track
			else if ((((currentRoad.RoadLength)-(counter*vehicleSpeed))>0) && (currentRoadIndex == lastRoadIndex) && (!finishedTrack)) {
				
				// Prints the vehicle current position
				System.out.println("\r\n" + this.getName() + " is now on " + currentRoad.getName() + "\r\n" + 
						"Which is the last road on it's track." + "\r\n" + 
						"The vehicle passed " + (counter*vehicleSpeed) + " of this road already, " + "\r\n" + 
						"And has " + ((currentRoad.RoadLength)-(counter*vehicleSpeed)) + " left to finish the road");
			}
			
			// Case 4 - vehicle finished it's track in this turn
			else if ((((currentRoad.RoadLength)-(counter*vehicleSpeed))<=0) && (currentRoadIndex == lastRoadIndex) && (!finishedTrack)) {
				
				// Prints the vehicle current position
				System.out.println("\r\n" + this.getName() + " Finished it's track!");
				
				// Set this vehicle's track as finished 
				finishedTrack = true;
			}

			// Case 5 - vehicle finished it's in a previous turn
			else if (finishedTrack) {
				;
			}
		}
	}
	
	// Returns a boolean value that represents whether this vehicle finished it's track or not
	public boolean getFinishVal() {
		return this.finishedTrack;
	}
	
	public void setReplacedTrack() {
		trackWasReplaced = true;
	}
	public boolean getReplacedTrackVal() {
		return this.trackWasReplaced;
	}
	

}