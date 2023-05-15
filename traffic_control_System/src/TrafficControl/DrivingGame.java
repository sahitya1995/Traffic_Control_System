package TrafficControl;

import java.util.ArrayList;

// Driving Game class
public class DrivingGame {
	
	// Variables definitions
	private Map GameMap;
	private ArrayList<Vehicle> finishVehicles;
	private boolean stillPlaying;
	private GameTimer timerOfGame;
	
	// Game object
    public DrivingGame(Map GameMap) {
    	this.GameMap = GameMap;
    	finishVehicles = new ArrayList<Vehicle>();
    	stillPlaying = true;
    	timerOfGame = new GameTimer(this);
    }
    
    // Plays the game
    public void play () {
    	
    	// Starts the timer
    	timerOfGame.start();
    	
        // Plays till all vehicles finish their tracks
        while (stillPlaying) {
 
        	// Thread sleep 
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

        	// Sets and prints all lights statuses for this turn
        	for (int i = 0; i <= GameMap.getMapJunctions().size()-1; i++) {        		
    			if (GameMap.getMapJunctions().get(i).getLight() == null) {
    				;
    			}
    			else{
    				GameMap.getMapJunctions().get(i).getLight().check();
    			}    			
    		}
        	
        	// Sets and prints all vehicles statuses for this turn
        	for (int h = 0; h <= GameMap.getMapVehicles().size()-1; h++) {
        		GameMap.getMapVehicles().get(h).move();
        		
        		// If the vehicle finished it's track, adds it to the finished vehicles list
        		if((GameMap.getMapVehicles().get(h).getFinishVal()==true) && 
        				(!(finishVehicles.contains(GameMap.getMapVehicles().get(h))))) {
        			finishVehicles.add(GameMap.getMapVehicles().get(h));
        		}
        		getFinishVehicles();
        		setFinishVehicles(finishVehicles);
    		}
        	
        	// End of the game - all vehicles finished their tracks
        	if (GameMap.getMapVehicles().size() == finishVehicles.size()) {
        		System.out.println("All vehicles have arrived to their destination!");
        		stillPlaying = false;
        	}
        	else {
        		;
        	}
        }
    }
    
	// Gets the finished vehicles list
	public ArrayList<Vehicle> getFinishVehicles(){
		return finishVehicles;
	}
	
	// Sets the finished vehicles list
	public void setFinishVehicles(ArrayList<Vehicle> finishVehicles){
		this.finishVehicles = finishVehicles;
	}	
	
	// boolean for checking if the game is finished or not
	public boolean isStillPlaying() {
		return this.stillPlaying;
	}
}