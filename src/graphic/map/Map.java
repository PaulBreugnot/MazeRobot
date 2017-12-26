package graphic.map;

import java.util.ArrayList;
import java.util.Hashtable;

import util.Coordinates;

public class Map {

	public enum Door {
		TOP, BOTTOM, LEFT, RIGHT
	};

	private int Width;
	private int Height;

	private static double topDoorProbability = 0.5;
	private static double bottomDoorProbability = 0.5;
	private static double leftDoorProbability = 0.5;
	private static double rightDoorProbability = 0.5;

	private Hashtable<Coordinates, Room> rooms = new Hashtable<>();

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

	public void addRoom(Room room) {
		rooms.put(new Coordinates(room.getXPos(), room.getYPos()), room);
	}

	public void addRooms(ArrayList<Room> roomList) {
		for (Room room : roomList) {
			rooms.put(new Coordinates(room.getXPos(), room.getYPos()), room);
		}
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

	/*
	 * public ArrayList<ArrayList<Room>> getRooms(){ return rooms; }
	 */

	public Room getRoom(double x, double y) {
		return rooms.get(new Coordinates(x, y));
	}

	public Hashtable<Coordinates, Room> getRooms() {
		return rooms;
	}

	public void randomlyGenerateMaze(Room initRoom) {
		if (initRoom.topDoor()) {
			randomlyGenerateMaze(initRoom, Door.TOP);
		}
		if (initRoom.bottomDoor()) {
			randomlyGenerateMaze(initRoom, Door.BOTTOM);
		}
		if (initRoom.leftDoor()) {
			randomlyGenerateMaze(initRoom, Door.LEFT);
		}
		if (initRoom.rightDoor()) {
			randomlyGenerateMaze(initRoom, Door.RIGHT);
		}
	}
	public void randomlyGenerateMaze(Room room, Door door) {
		switch (door) {
		case TOP: {
			if (room.getYPos() == Height - 1) {
				room.setTopDoor(false);
				rooms.put(new Coordinates(room.getXPos(), room.getYPos()), room);
			} else {
				int XnewRoom = room.getXPos();
				int YnewRoom = room.getYPos() + 1;
				if (rooms.get(new Coordinates(XnewRoom, YnewRoom)) == null) {
					boolean topDoor = randomlyGeneratedTopDoor(XnewRoom, YnewRoom);
					boolean leftDoor = randomlyGeneratedLeftDoor(XnewRoom, YnewRoom);
					boolean rightDoor = randomlyGeneratedRightDoor(XnewRoom, YnewRoom);
					Room newRoom = new Room(topDoor, true, leftDoor, rightDoor, Room.RoomType.NEUTRAL, XnewRoom,
							YnewRoom);
					rooms.put(new Coordinates(XnewRoom, YnewRoom), newRoom);
					if (topDoor) {
						randomlyGenerateMaze(newRoom, Door.TOP);
					}
					if (leftDoor) {
						randomlyGenerateMaze(newRoom, Door.LEFT);
					}
					if (rightDoor) {
						randomlyGenerateMaze(newRoom, Door.RIGHT);
					}
				}
			}
			break;
		}
		case BOTTOM: {
			if (room.getYPos() == 0) {
				room.setBottomDoor(false);
				rooms.put(new Coordinates(room.getXPos(), room.getYPos()), room);
			} else {
				int XnewRoom = room.getXPos();
				int YnewRoom = room.getYPos() - 1;
				if (rooms.get(new Coordinates(XnewRoom, YnewRoom)) == null) {
					boolean bottomDoor = randomlyGeneratedBottomDoor(XnewRoom, YnewRoom);
					boolean leftDoor = randomlyGeneratedLeftDoor(XnewRoom, YnewRoom);
					boolean rightDoor = randomlyGeneratedRightDoor(XnewRoom, YnewRoom);
					Room newRoom = new Room(true, bottomDoor, leftDoor, rightDoor, Room.RoomType.NEUTRAL, XnewRoom,
							YnewRoom);
					rooms.put(new Coordinates(XnewRoom, YnewRoom), newRoom);
					if (bottomDoor) {
						randomlyGenerateMaze(newRoom, Door.BOTTOM);
					}
					if (leftDoor) {
						randomlyGenerateMaze(newRoom, Door.LEFT);
					}
					if (rightDoor) {
						randomlyGenerateMaze(newRoom, Door.RIGHT);
					}
				}
			}
			break;
		}
		case LEFT: {
			if (room.getXPos() == 0) {
				room.setLeftDoor(false);
				rooms.put(new Coordinates(room.getXPos(), room.getYPos()), room);
			} else {
				int XnewRoom = room.getXPos() - 1;
				int YnewRoom = room.getYPos();
				if (rooms.get(new Coordinates(XnewRoom, YnewRoom)) == null) {
					boolean topDoor = randomlyGeneratedTopDoor(XnewRoom, YnewRoom);
					boolean bottomDoor = randomlyGeneratedBottomDoor(XnewRoom, YnewRoom);
					boolean leftDoor = randomlyGeneratedLeftDoor(XnewRoom, YnewRoom);
					Room newRoom = new Room(topDoor, bottomDoor, leftDoor, true, Room.RoomType.NEUTRAL, XnewRoom,
							YnewRoom);
					rooms.put(new Coordinates(XnewRoom, YnewRoom), newRoom);
					if (topDoor) {
						randomlyGenerateMaze(newRoom, Door.TOP);
					}
					if (bottomDoor) {
						randomlyGenerateMaze(newRoom, Door.BOTTOM);
					}
					if (leftDoor) {
						randomlyGenerateMaze(newRoom, Door.LEFT);
					}
				}
			}
			break;
		}
		case RIGHT: {
			if (room.getXPos() == Width - 1) {
				room.setRightDoor(false);
				rooms.put(new Coordinates(room.getXPos(), room.getYPos()), room);
			} else {
				int XnewRoom = room.getXPos() + 1;
				int YnewRoom = room.getYPos();
				if (rooms.get(new Coordinates(XnewRoom, YnewRoom)) == null) {
					boolean topDoor = randomlyGeneratedTopDoor(XnewRoom, YnewRoom);
					boolean bottomDoor = randomlyGeneratedBottomDoor(XnewRoom, YnewRoom);
					boolean rightDoor = randomlyGeneratedRightDoor(XnewRoom, YnewRoom);
					Room newRoom = new Room(topDoor, bottomDoor, true, rightDoor, Room.RoomType.NEUTRAL, XnewRoom,
							YnewRoom);
					rooms.put(new Coordinates(XnewRoom, YnewRoom), newRoom);
					if (topDoor) {
						randomlyGenerateMaze(newRoom, Door.TOP);
					}
					if (bottomDoor) {
						randomlyGenerateMaze(newRoom, Door.BOTTOM);
					}
					if (rightDoor) {
						randomlyGenerateMaze(newRoom, Door.RIGHT);
					}
				}
			}
			break;
		}
		}

	}

	private boolean randomlyGeneratedTopDoor(int XnewRoom, int YnewRoom) {
		boolean topDoor = false;
		if (rooms.get(new Coordinates(XnewRoom, YnewRoom + 1)) != null) {
			topDoor = rooms.get(new Coordinates(XnewRoom, YnewRoom + 1)).bottomDoor();
		} else {
			double p = Math.random();
			if (p < topDoorProbability) {
				topDoor = true;
			}
		}
		return topDoor;
	}

	private boolean randomlyGeneratedBottomDoor(int XnewRoom, int YnewRoom) {
		boolean bottomDoor = false;
		if (rooms.get(new Coordinates(XnewRoom, YnewRoom - 1)) != null) {
			bottomDoor = rooms.get(new Coordinates(XnewRoom, YnewRoom - 1)).topDoor();
		} else {
			double p = Math.random();
			if (p < bottomDoorProbability) {
				bottomDoor = true;
			}
		}
		return bottomDoor;
	}

	private boolean randomlyGeneratedLeftDoor(int XnewRoom, int YnewRoom) {
		boolean leftDoor = false;
		if (rooms.get(new Coordinates(XnewRoom - 1, YnewRoom)) != null) {
			leftDoor = rooms.get(new Coordinates(XnewRoom - 1, YnewRoom)).rightDoor();
		} else {
			double p = Math.random();
			if (p < leftDoorProbability) {
				leftDoor = true;
			}
		}
		return leftDoor;
	}

	private boolean randomlyGeneratedRightDoor(int XnewRoom, int YnewRoom) {
		boolean rightDoor = false;
		if (rooms.get(new Coordinates(XnewRoom + 1, YnewRoom)) != null) {
			rightDoor = rooms.get(new Coordinates(XnewRoom + 1, YnewRoom)).leftDoor();
		} else {
			double p = Math.random();
			if (p < rightDoorProbability) {
				rightDoor = true;
			}
		}
		return rightDoor;
	}
}
