package graphic.map;

import java.util.ArrayList;

public class Map {

	private int Width;
	private int Height;
	
	private ArrayList<ArrayList<Room>> rooms;
	
	public Map(int Width, int Height) {
		setWidth(Width);
		setHeight(Height);
	}
	public void setWidth(int Width) {
		this.Width = Width;
	}
	
	public void setHeight(int Height) {
		this.Height = Height;
	}
	
	public void setRooms(ArrayList<ArrayList<Room>> rooms) {
		this.rooms = rooms;
	}
	
	public int getWidth() {
		return Width;
	}
	
	public int getHeight() {
		return Height;
	}
	
	public double getRealWidth() {
		return Width * Room.getSize();
	}
	
	public double getRealHeight() {
		return Height * Room.getSize();
	}
	
	public ArrayList<ArrayList<Room>> getRooms(){
		return rooms;
	}
}
