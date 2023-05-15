package TrafficControl;
import java.util.Scanner;

// Program class - Gili Faibish 315141481
public class Program {
	public static void main(String[] args) {

		// Scanner definition
		Scanner scan = new Scanner(System.in);
		
		// Gets the requested amount of junctions from the user
        System.out.print("Enter the number of junctions you want in your map: ");
        int numJunctions = scan.nextInt();
        
        // Gets the requested amount of vehicles from the user
        System.out.print("Enter the number of vehicles you want in your map: ");
        int numVehicles = scan.nextInt();

        // Creates this game's map according to the amounts of vehicles and junctions from the user's  input
		Map thisGameMap = new Map(numJunctions, numVehicles);
		
        // Closing Scanner after the use
        scan.close();
        
        // Creates a game
        DrivingGame game = new DrivingGame(thisGameMap);
        
        // Plays the game
        game.play();
	}
}