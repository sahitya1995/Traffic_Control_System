package TrafficControl;

import java.util.ArrayList;

// Map class
public class Map {
	
	// Map variables definitions
	public int junctionAmount;
	public int vehicleAmount;
	private ArrayList<Junction> mapJunctions;
	private ArrayList<Road> mapRoads;
	private ArrayList<Vehicle> mapVehicles;
	private ArrayList<Junction> trackJunctions;
	private ArrayList<Road> trackRoads;
	private boolean hasExits;
	private Vehicle vehicle;
	private ArrayList<Junction> visitedJunctions;
	private double trackLength = 0.0;
	private double newTrackLength = 0.0;
	private ArrayList<Junction> compareNeighboresJunctions;
	private Junction lowestCostUnvisited;

	// This function creates a map according to the requested amounts of junctions and vehicles
	public Map(int junctionAmount, int vehicleAmount) {
		
		// Prints announcement for the map that would be created
		System.out.println("\r\n" + "Creating new map with " + junctionAmount + " junctions ," + "\r\n" + 
		"And " + vehicleAmount + " vehicles : " + "\r\n");
		
		// creates random junctions according to the requested amount, 
		// and adds each one of them to the map junctions list
		mapJunctions = new ArrayList<Junction>();
		this.junctionAmount = junctionAmount;
		for (int i = 0; i < junctionAmount; i++) {
			Point randJunc = new Junction(-1,-1);
			mapJunctions.add((Junction) randJunc);
		}
		getMapJunctions();
		setMapJunctions(mapJunctions);
		
		// Prints the map junction list
		System.out.println("\r\n" + "Creating Map Junctions : ");
		for (int i = 0; i <= mapJunctions.size()-1; i++) {
			mapJunctions.get(i).printJunction();
		}
		
		// creates random roads between the map junctions, 
		// adds each one of them to the map roads list, and also prints them
		System.out.println("\r\n" + "Creating Map Roads : ");
		mapRoads = new ArrayList<Road>();
		for (int i = 0; i < ((mapJunctions.size()*2)-1); i++) {
			int myenterlistlength= mapJunctions.size()-1;
			int firstRandomJunctionIDX = (int)(Math.random()*myenterlistlength);
			int secondRandomJunctionIDX = (int)(Math.random()*myenterlistlength);
			Junction firstRandomJunction = mapJunctions.get(firstRandomJunctionIDX);
			Junction secondRandomJunction = mapJunctions.get(secondRandomJunctionIDX);
			Road randMapRoad = new Road(firstRandomJunction, secondRandomJunction);
			mapRoads.add(randMapRoad);
		}
		getMapRoads();
		setMapRoads(mapRoads);
		
		// Creates and prints the traffic lights for the junctions in this map
		System.out.println("\r\n"+ "Creating Map Traffic Lights : ");
		for (int i = 0; i <= mapJunctions.size()-1; i++) {
			Junction junctionToAddLight = mapJunctions.get(i);
			junctionToAddLight.setLight(junctionToAddLight.getLight());
		}

		// Creates and prints the vehicles in this map
		System.out.println("\r\n"+ "Creating Map Vehicles & their tracks : ");
		mapVehicles = new ArrayList<Vehicle>();
		for (int i = 0; i < vehicleAmount; i++) {
			vehicle = new Vehicle(this);
			mapVehicles.add(vehicle);
		}
		getMapVehicles();
		setMapVehicles(mapVehicles);

		// Prints announcement for the created map, after creation is finished
		System.out.println("\r\n" + "Your map was created Successfully!" + "\r\n");		
	}
	
	// This function creates a track for a vehicle by using elements from the map
	public void createTrack(Vehicle vehicle) {	
		
		// Clears path former path values for all map junctions 
		for (int i = 0; i <= mapJunctions.size()-1; i++) {
			mapJunctions.get(i).clearPathValues();
		}
		
		// Array variables definition for this track
		trackJunctions = new ArrayList<Junction>();
		trackRoads = new ArrayList<Road>();
		
		// This boolean would be used to determine whether a junction has exit roads or not
		hasExits = false;
				
		// Select the start random junction, after it makes sure it is indeed a junction with exit roads:
		// If the junction does have at least one exit road, it would add that junction as the first junction of the map.
		// And if it does'nt it would try again until it finds a junction that does.
		int myjunctionlistlength= mapJunctions.size()-1;
		while(!hasExits) {
			int TrackStartRandomJunctionIDX = (int)(Math.random()*myjunctionlistlength);
			Junction TrackStartJunction = mapJunctions.get(TrackStartRandomJunctionIDX);
			if (TrackStartJunction.getExit().size()-1>0) {
				trackJunctions.add(TrackStartJunction);
				hasExits = true;
			}
			else {
				;
			}
		}

		// Creates the track by adding roads and junctions
		for (int i = 0; i < 4; i++) {
			Junction currentJunction = trackJunctions.get(i);
			int myexitroadslistlength = currentJunction.getExit().size()-1;
			if (currentJunction.getExit().size()>0) {
				int selectedExitRoadIDX = (int)(Math.random()*myexitroadslistlength);
				Road selectedExitRoad = currentJunction.getExit().get(selectedExitRoadIDX);
				Junction endJuncOfExitRoad = selectedExitRoad.getEndJunction();
				trackJunctions.add(endJuncOfExitRoad);
				trackRoads.add(selectedExitRoad);
			}
			else {
				break;
			}
		}

		// Prints the track
		System.out.println("\r\n" + "Creating a new track for " + vehicle.getName() + " :");
		
		// Prints the track junctions
		System.out.println("Track junctions : ");
		for (int i = 0; i <= trackJunctions.size()-1; i++) {
			System.out.println("Junction number " + (i+1) +" in track : " + trackJunctions.get(i).getName());
		}
		
		// Prints the track roads
		System.out.println("Track roads : ");
		for (int i = 0; i <= trackRoads.size()-1; i++) {
			System.out.println("Road number " + (i+1) +" in track : " + trackRoads.get(i).getName());
		}		

		// Shortest track can be found once you take the command below out of note mode , but "move" (in vehicle) cannot read it 
	    // this.calcShortestTrack(vehicle);
		
		setTrackJunctions(trackJunctions);
		getTrackJunctions();
		setTrackRoads(trackRoads);
		getTrackRoads();
		
	}
	
	// 
	public void calcShortestTrack(Vehicle vehicle) {
		
		// Gets the first & last junctions before deleting the list
		Junction startJunction = trackJunctions.get(0);
		Junction targetJunction = trackJunctions.get(trackJunctions.size()-1);
		targetJunction.setAsLast();
		
		// circle track case 
		if (startJunction.equals(targetJunction)) {
			System.out.println("Circle track - cannot be replaced");
		}
		
		// one road track case
		else if (trackRoads.size() ==1) {
			System.out.println("One road track - no need to be replaced");
		}
		
		// Replaceable case
		else {
			
			//
			System.out.println("\r\n" + vehicle.getName() + " Track has been replaced with a shorter path." + "\r\n" + "New track:");
			
			visitedJunctions = new ArrayList<Junction>();
			
			vehicle.setReplacedTrack();
			
			
			// for me - print old track length
			for (int i = 0; i <= trackRoads.size()-1; i++) {
				trackLength = trackLength + trackRoads.get(i).getRoadLength();
			}
			
			// Prints the old track length
			System.out.println("Old track length : " + trackLength);
			
			// clear previous track
			trackRoads.clear();
			trackJunctions.clear();

			// Sets the start junction values
			startJunction.setJuncDistance(0.0);
			startJunction.setNeighbors();
			visitedJunctions.add(startJunction);
			
			// Start at the lowest cost neighbor
			Junction currentJunction = getLowestCostUnvisited();

			// count for staying in Dijkstra
			int countStay = 0;
			
			// Dijkstra activation
			while (countStay < mapJunctions.size()) {
				countStay = visitedJunctions.size();
				currentJunction.setNeighbors();
				visitedJunctions.add(currentJunction);
				lowestCostUnvisited = null;
				lowestCostUnvisited = getLowestCostUnvisited();
				if (!(lowestCostUnvisited==null)) {
					currentJunction = lowestCostUnvisited;
				}
				else {
					lowestCostUnvisited = null;
					countStay = mapJunctions.size();
				}
			}
			
			// 
			targetJunction.createShortestTrack();
			setTrackJunctions(targetJunction.getShortestJunctions());
			getTrackJunctions();
			setTrackRoads(targetJunction.getShortestRoads());
			getTrackRoads();
			getTrackJunctions();
			setTrackJunctions(trackJunctions);
			getTrackRoads();
			setTrackRoads(trackRoads);

			for (int i = 0; i <= trackRoads.size()-1; i++) {
				newTrackLength = newTrackLength + trackRoads.get(i).getRoadLength();
			}
			
			// Prints the new track length
			System.out.println("New track length : " + newTrackLength);

			// Clears the visited junctions list
			visitedJunctions.clear();

		}

		// Prints the track junctions
		System.out.println("New track junctions : ");
		for (int i = 0; i <= trackJunctions.size()-1; i++) {
			System.out.println("Junction number " + (i+1) +" in track : " + trackJunctions.get(i).getName());
		}
		
		// Prints the track roads
		System.out.println("New track roads : ");
		for (int i = 0; i <= trackRoads.size()-1; i++) {
			System.out.println("Road number " + (i+1) +" in track : " + trackRoads.get(i).getName());
		}	
		
	
		
	}
	
	// Map lists
	
	// Gets the map junctions list
	public ArrayList<Junction> getMapJunctions(){
		return mapJunctions;
	}
	
	// Sets the map junctions list
	public void setMapJunctions(ArrayList<Junction> mapJunctions){
		this.mapJunctions = mapJunctions;
	}	
	
	// Gets map roads list
	public ArrayList<Road> getMapRoads(){
		return mapRoads;
	}
	
	// Sets the map roads list
	public void setMapRoads(ArrayList<Road> mapRoads){
		this.mapRoads = mapRoads;
	}

	// Track lists

	// Gets the track junctions list
	public ArrayList<Junction> getTrackJunctions(){
		return trackJunctions;
	}
	
	// Sets the track junctions list
	public void setTrackJunctions(ArrayList<Junction> trackJunctions){
		this.trackJunctions = trackJunctions;
	}	
	
	// Gets the track roads list
	public ArrayList<Road> getTrackRoads(){
		return trackRoads;
	}
	
	// Sets the track roads list
	public void setTrackRoads(ArrayList<Road> trackRoads){
		this.trackRoads = trackRoads;
	}	
	
	// Gets the map vehicles list
	public ArrayList<Vehicle> getMapVehicles(){
		return mapVehicles;
	}
	
	// Sets the map vehicles list
	public void setMapVehicles(ArrayList<Vehicle> mapVehicles){
		this.mapVehicles = mapVehicles;
	}	

	// Gets the visited junctions list
	public ArrayList<Junction> getVisitedJunctions(){
		return visitedJunctions;
	}
	
	// Sets the visited junctions list
	public void setVisitedJunctions(ArrayList<Junction> visitedJunctions){
		this.visitedJunctions = visitedJunctions;
	}	
	
	// Gets the track junctions list
	public ArrayList<Junction> getCompareNeighboresJunctions(){
		return compareNeighboresJunctions;
	}
	
	// Sets the track junctions list
	public void setCompareNeighboresJunctions(ArrayList<Junction> compareNeighboresJunctions){
		this.compareNeighboresJunctions = compareNeighboresJunctions;
	}	
	
	// Returns the lowest cost unvisited junction
	public Junction getLowestCostUnvisited() {
		
		// Unvisited list creation
		ArrayList<Junction> unvisitedJunctions = new ArrayList<Junction>();
		for (int i = 0; i <= mapJunctions.size()-1; i++) {
			Junction toAddJunc = mapJunctions.get(i);
			if(visitedJunctions.contains(toAddJunc)) {
				;
			}
			else {
				unvisitedJunctions.add(toAddJunc);
			}
		}
		
		// Compares the junctions costs to get the lowest cost unvisited
		double minVal = Double.MAX_VALUE;
		for (int i = 0; i <= unvisitedJunctions.size()-1; i++) {
			double juncCost = unvisitedJunctions.get(i).getJuncDistance();
			if (juncCost<minVal) {
				lowestCostUnvisited = unvisitedJunctions.get(i);
				minVal = juncCost;
			}
			else {
				;
			}
		}
		
		// Returns the lowest cost unvisited junction 
		return lowestCostUnvisited;
	}
}