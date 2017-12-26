package robot.model;

public class Robot {

	//Coordinates are expressed in rooms
	public double XPos;
	public double YPos;
	//Radius is expressed in meters
	public double radius;
	
	public Robot(double XPos, double YPos, double radius) {
		this.XPos = XPos;
		this.YPos = YPos;
		this.radius = radius;
	}
}
