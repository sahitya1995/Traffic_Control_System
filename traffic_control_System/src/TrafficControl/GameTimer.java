package TrafficControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

// Game Timer class
public class GameTimer {
	
	// Variables definitions
	private Timer timer;
	private int secCounter=0;
	private int minCounter=0;
	private String strMin = "00";
	private String strSec = "00";
	private DrivingGame drvGame;
	
	// GameTimer object definitions
	public GameTimer(DrivingGame drvGame) {
		this.drvGame = drvGame;
		 timer = new Timer(1000, new TimerActionListener());
		 
	}
	
    // A function that starts the timer
    public void start()
    {
    	timer.start();
    }
    
    // A function that is called after every second. 
    // It updates the timer and takes other necessary actions
    class TimerActionListener implements ActionListener
    {
    	
    	// Performs action
        public void actionPerformed(ActionEvent e)
        {
        	
        	// Adds 1 to the seconds counter
        	secCounter++;
        	
        	// Checks if the second counter equals to 60 - if it is, it sets the seconds counter to 0
        	// and add 1 to the minute counter
        	if (secCounter == 60) {
        		minCounter++;
        		secCounter = 0;
        	}
        	else {
        		;
        	}
        	
        	 // Sets the seconds string, and if the seconds counter is smaller than 10, adds "0" to the string before the value 
        	 if (secCounter < 10) {
        		 strSec = "0" + secCounter;
        	 }
        	 else {
        		 strSec = String.valueOf(secCounter);
        	 }
        	 
        	// Sets the minutes string, and if the minutes counter is smaller than 10, adds "0" to the string before the value
        	 if (minCounter < 10) {
        		 strMin = "0" + minCounter;
        	 }
        	 else {
        		 strMin = String.valueOf(minCounter);
        	 }
        	 
	       	 // Prints the time for as long as the game is running (until all vehicles finish their tracks)
	       	 if (drvGame.isStillPlaying())
	       	 {
	           	System.out.println("\r\n" +"Time: " + strMin+":"+strSec + "\r\n");
	         }
	       	 else
	       	 {	       		 
	       		  System.out.println("game ended ");
	       		  timer.stop();
			 }
	    }
 }
}