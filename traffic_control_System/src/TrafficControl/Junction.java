package TrafficControl;
import java.util.ArrayList;


// Junction class
public class Junction extends Point {

	// Junction variable definitions
	private String name;
	public static int junctionID=1;
	public int thisJunctionID;
	private ArrayList<Road> EnterRoads;
	private ArrayList<Road> ExitRoads;
	public int LightTypeNumVal= 1 + (int)(Math.random() * ((8 - 1) + 1));
	private int junctionLightType;
	private Light thisJunctionLight;
	private boolean visited = false;
    private Junction previousJunction = null;
    private double juncDistance = Double.MAX_VALUE;
    private ArrayList<Junction> junctionNeighbors;
    private double prevDist = 0.0;
    private ArrayList<Junction> shortestPathToMeJunctions;
    private ArrayList<Road> shortestPathToMeRoads;
    private Junction lowestCostNeighbor;
    private Road previousRoad = null;
    private boolean onPath = false;
    private Junction lastJunctionInTrack = null;
    private boolean isLast = false;

	// This function is used to create a junction object 
	public Junction(double x, double y) {
		super(x, y);		
		thisJunctionID = junctionID++;
		EnterRoads = new ArrayList<Road>();
		ExitRoads = new ArrayList<Road>();
		
		// Lights definitions
		thisJunctionLight = null;
		junctionLightType = LightTypeNumVal;
		
		// Dijkstra lists definition
		junctionNeighbors = new ArrayList<Junction>();
		shortestPathToMeJunctions = new ArrayList<Junction>();
		shortestPathToMeRoads = new ArrayList<Road>();		
	}
	
	// This function returns the name of the junction 
	public String getName(){
		this.name = "Junction " + thisJunctionID;
		return this.name;
	}
	
	// This returns the junction ID number
	public int getJuncID() {
		return thisJunctionID;
	}
	
	// This function prints the Junction
	public void printJunction(){
		System.out.println( getName() + " at point " + "("+ df.format(x) +","+ df.format(y) +")");
	}

	// Gets the entering roads 
	public ArrayList<Road> getEnter(){
		return EnterRoads;
	}
	
	// Sets the entering roads list
	public void setEnterroad(ArrayList<Road> EnterRoads){
		this.EnterRoads = EnterRoads;
	}
	
	// Gets the exiting roads 
	public ArrayList<Road> getExit(){
		return ExitRoads;
	}
	
	// Sets the exiting roads list
	public void setExitroad(ArrayList<Road> ExitRoads){
		this.ExitRoads = ExitRoads;
	}	
	
	// 
	public  ArrayList<Junction> getNeighbors(){
		return junctionNeighbors;
	}
	
	// This sets the junction neighbors (fills the neighbors list), and their values,
	// if this neighbor was not visited (shortest path to it was not found yet)
	public void setNeighbors(){
		if ((this.getExit().size()>=1)) {
			for (int i = 0; i <= ExitRoads.size()-1; i++) {
				
				// Adds all neighbor junctions
				junctionNeighbors.add(ExitRoads.get(i).getEndJunction());

				// Checks if the path to this neighbor is shorter than the one we already have.
				// Two cases:
				// In case it is shorter
				if (((ExitRoads.get(i).getRoadLength()+ junctionNeighbors.get(i).getPrevDistance()) <
						(junctionNeighbors.get(i).getJuncDistance())) && (junctionNeighbors.get(i).isVisited() == false)) {

					// sets neighbors distances from start point
					junctionNeighbors.get(i).setJuncDistance(ExitRoads.get(i).
							getRoadLength()+ junctionNeighbors.get(i).getPrevDistance());

						// Sets the junction as the previous junction for the neighbor 
						junctionNeighbors.get(i).setPreviousJunction(this);
						
						// Sets the previous road as the previous road for the neighbor 
						junctionNeighbors.get(i).setPreviousRoad();
				}
				
				// In case it is not shorter
				else {
					;
				}
			}
		}
		else {
			this.setJuncDistance(Double.MAX_VALUE);
		}

		this.setVisited(true);
	}
	
	@SuppressWarnings("unused")
	public void createShortestTrack() {
		
			// Replaces the junctions 
		    shortestPathToMeJunctions = new ArrayList<Junction>();
			ArrayList<Junction> tempJsForTest = new ArrayList<Junction>();
			shortestPathToMeJunctions.clear();
			Junction tempJ;
	    	tempJ = this;
	    	while (!(tempJ == null)) {
	    		if (tempJ.isLastJunc()){
	    			shortestPathToMeJunctions.add(0, tempJ);
	    			tempJsForTest.add(tempJ);
	    			tempJ = tempJ.getPreviousJunction();
	    		}
	    		else if (tempJ.getJuncDistance() == 0.0){
	    			shortestPathToMeJunctions.add(0, tempJ);
	    		    tempJsForTest.add(tempJ);
	    			tempJ = null;
	    		}
	    		else if (!(tempJ == null)) {
	    			if (tempJsForTest.contains(tempJ)) {
						tempJ = null;
					}
	    			else {
	    				shortestPathToMeJunctions.add(0, tempJ);
	    				tempJsForTest.add(tempJ);
	    				tempJ = tempJ.getPreviousJunction();
	    			}
	    		} 
	    		else if (tempJ == null) {
	    			;
	    		}
	    	}
	    	
	    	this.setShortestJunctions(shortestPathToMeJunctions);
	    	this.getShortestJunctions();

	    	// Replaces the Roads
	    	shortestPathToMeRoads = new ArrayList<Road>();
	    	ArrayList<Road> tempRsForTest = new ArrayList<Road>();
	    	shortestPathToMeRoads.clear();
	    	this.getShortestRoads().clear();

	    	Junction tempJuncForRoad = this;
	    	for (int i = 0; i < this.getShortestJunctions().size()-1; i++) {
				Junction tempJunc = this.getShortestJunctions().get((this.getShortestJunctions().size()-1)-i);
				tempJunc.setPreviousRoad();
				shortestPathToMeRoads.add(0, tempJunc.getPreviousRoad());
			}
	    	this.setShortestRoads(shortestPathToMeRoads);
	    	this.getShortestRoads();		
	}
	
	//
	public Junction getLowestCostNeighbor(){
		
		// Neighbors costs list 
		ArrayList<Double> costs = new ArrayList<Double>();
		
		// Gets the costs of the neighbors to a list
		for (int i = 0; i <= junctionNeighbors.size()-1; i++) {
			costs.add(junctionNeighbors.get(i).getJuncDistance());
		}
		
		// Compares the costs of the neighbors
		if (costs.size()>=1) {
			double minValue = costs.get(0); 
		    for(int i=0 ; i <=costs.size()-1; i++){ 
		      if((costs.get(i) < minValue) && (junctionNeighbors.get(i).isVisited() == false)){ 
		        minValue = costs.get(i); 
		      } 
		      else {
		    	  ;
		      }
		    } 
		    
		    //
		    for (int i = 0; i <= junctionNeighbors.size()-1; i++) {
				if ((junctionNeighbors.get(i).getJuncDistance() == minValue)) {
					lowestCostNeighbor = junctionNeighbors.get(i);
				}
				else {
					;
				
				}
		    }
		}
		else {
			this.setJuncDistance(Double.MAX_VALUE);
		}
	    
	    //
	    return lowestCostNeighbor;		
	}
	
	
	// Clears the former path values of this junction
	public void clearPathValues() {
		setVisited(false);
		junctionNeighbors.clear();
		prevDist = 0.0;
		juncDistance = Double.MAX_VALUE;
		previousJunction = null;
		setPrevRoadAsNull();
		lowestCostNeighbor = null;
		shortestPathToMeJunctions.clear();
		shortestPathToMeRoads.clear();
		onPath = false;
		isLast = false;
	}
	
	// Gets the junction light
	public Light getLight(){
		return thisJunctionLight;
	}
	
	// Sets the junction light to a value of: Random, Permanent or Null.
	// The decision of which value the junction would get, is decided randomly
	// with only 25% chance of getting a value other than null,
	// out of the lights that are not null there is 50% chance of getting random, and 50% chance of getting Permanent
	public void setLight(Light thisJunctionLight){
		
		// Sets the light type decider number value to a random value in range 1-8 
		junctionLightType = LightTypeNumVal;
		
		// Got any number in range 1-6 -> Junction's light gets null type
		if ((1 <= junctionLightType && junctionLightType <= 6) || (this.getEnter().size()== 0)) {
			thisJunctionLight = null;
		}
		
		// Got 7 -> Junction's light gets permanent type
		else if (junctionLightType == 7 && this.getEnter().size()> 0) {
			thisJunctionLight = new PermanentLight(this);
			System.out.println("Permanent light created at - "+ getName() + " , Delay = " + thisJunctionLight.getDelay()
			+ " , " + "\r\n" + "Current green road : " + thisJunctionLight.getCurrentGreen().getName());
		}
		
		// Got 8 -> Junction's light gets random type
		else if (junctionLightType == 8 && this.getEnter().size()> 0) {
			thisJunctionLight = new RandomLight(this);
			System.out.println("Random light created at - "+ getName() + " , Delay = " + thisJunctionLight.getDelay()
			+ " , " +  "\r\n" + "Current green road : " + thisJunctionLight.getCurrentGreen().getName());
		}
		this.thisJunctionLight = thisJunctionLight;
	}	
	
	//
    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public boolean isOnPath(Junction pathJunction) {
    	Junction temp;
    	temp = pathJunction;
    	while (!(temp == null)) {
    		temp = temp.getPreviousJunction();
    		if (temp == pathJunction) {
    			onPath = true;
    		}
    		else {
    			onPath = false;
    		}
    	}
    	
    	return onPath;
    }

    
    public Junction getPreviousJunction() {
        return previousJunction;
    }

    public void setPreviousJunction(Junction previousJunction) {
        this.previousJunction = previousJunction;
    }
    
    public void setPreviousRoad() {
    	if (this.getPreviousJunction() == null) {
    		previousRoad = null;
    	}
    	else {
    		for (int i = 0; i <= this.getEnter().size()-1; i++) {
				Road tempRoad = this.getEnter().get(i);
				if((tempRoad.getStartJunction()==this.getPreviousJunction()) 
						&& (tempRoad.getEndJunction() == this)) {
						previousRoad = tempRoad;
				}
				else {
					;
				}
			}
    	}
    	//return previousRoad;
    }
    
    public Road getPreviousRoad(){
    	return previousRoad;
    }
    
	public void setJuncDistance(Double dist) {
		this.juncDistance = dist;
	} 
	
	public double getJuncDistance() {
		return this.juncDistance;
	}
	
	public void setPrevDistance(Double prevDist) {
		this.prevDist = prevDist;
	} 
	
	public double getPrevDistance() {
		return this.prevDist;
	}

	//
	public ArrayList<Junction> getShortestJunctions(){
		return this.shortestPathToMeJunctions;
	}
	
	// maybe delete
	public void setShortestJunctions(ArrayList<Junction> shortestPathToMeJunctions){
		this.shortestPathToMeJunctions = shortestPathToMeJunctions;
	}	
	
	public ArrayList<Road> getShortestRoads(){
		return this.shortestPathToMeRoads;
	}
	
	// maybe delete
	public void setShortestRoads(ArrayList<Road> shortestPathToMeRoads){
		this.shortestPathToMeRoads = shortestPathToMeRoads;
	}		
	
	public void setAsLastJunctionInTrack() {
		lastJunctionInTrack = this;
	}
	
	public Junction getLastJunctionInTrack(){
		return lastJunctionInTrack;
	}
	
	public void setAsLast() {
		isLast = true;
	}
	
	public boolean isLastJunc() {
		return isLast;
	}
	
	public void setPrevRoadAsNull() {
		this.previousRoad = null;
	}
}