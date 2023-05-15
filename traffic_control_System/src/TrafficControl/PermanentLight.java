package TrafficControl;

// Permanent Light class
public class PermanentLight extends Light{
	
	// Parent definitions
	public  PermanentLight(Junction junction) {
		super(junction);
	}

	// Green light function at permanent light
	@Override
	public void greenLight() {
		int enterRoadID = this.getJunction().getEnter().indexOf(this.getCurrentGreen());	
		if (enterRoadID +1>= this.getJunction().getEnter().size()) {
			setCurrentGreen(this.getJunction().getEnter().get(0));
		}
		else {
			setCurrentGreen(this.getJunction().getEnter().get(enterRoadID +1));
			System.out.println("Permanent light switched!" + "\r\n"+ "Green light at " + this.getJunction().getName() + "\r\n"+
			"Is now : " + getCurrentGreen().getName());
		}
	}
	
	// Converts to string
	@Override
	public String toString() {
		//return "Permanent light " + this.getJunction().getName() + " , ID : "+ super.toString();
		return "Permanent light " + this.getJunction().getName();
	}
}