package robot.model;

public class Robot {

	//Coordinates are expressed in rooms
	public int XPos;
	public int YPos;
	//Radius is expressed in meters
	public double radius;
	
	public Robot(int XPos, int YPos, double radius) {
		this.XPos = XPos;
		this.YPos = YPos;
		this.radius = radius;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void setXPos(int XPos) {
		this.XPos = XPos;
	}
	
	public void setYPos(int YPos) {
		this.YPos = YPos;
	}
	
	public int getXPos() {
		return XPos;
	}
	
	public int getYPos() {
		return YPos;
	}
	
	public void incrementXPos(int i) {
		XPos = XPos + i;
	}
	
	public void incrementYPos(int i) {
		YPos = YPos + i;
	}
}
