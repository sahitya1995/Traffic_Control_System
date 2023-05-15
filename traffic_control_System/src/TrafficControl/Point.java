package TrafficControl;

import java.text.DecimalFormat;
import java.util.Random;

// Point class
public class Point {

	// Random definition
	Random r = new Random();
	
	// Decimal format 
	DecimalFormat df = new DecimalFormat("#.##");
	
	// Coordinate variable definition
	public double x;
	public double y;
	public double DefaultX = 800 * r.nextDouble();
	public double DefaultY = 600 * r.nextDouble();
	
	// Point creator function
	public Point(double x, double y) {
		
		// Sets X
		if(0<=x && x<=800 ) {
			this.x = x;
		}
		
		// Replaces illegal value with default random, in case of output out of range
		else {
			this.x = DefaultX;
		}
		
		// Sets Y
		if(0<=y && y<=600 ) {
			this.y = y;
		}
		
		// Replaces illegal value with default random, in case of output out of range
		else {
			this.y = DefaultY;
		}
	}
	
	// Prints the point
	public void printPoint(){
		System.out.println("Point " + "("+ df.format(x) +","+ df.format(y) +")");
	}
	
	// Calculating the distance between two points
	public double calcDistance(Point other){
		double dx = x - other.x;
		double dy = y - other.y;
		return Math.sqrt(dx*dx + dy*dy);
	}
}