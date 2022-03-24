package card_game_resources;

public class Coordinate {
	private int x;
	private int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean equals(Coordinate c) {
		boolean equal = false;
		if(x == c.getX() && y == c.getY())
			equal = true;
		return equal;
	}
	
	public boolean withinCircularRange(Coordinate origin, double radius) {
		boolean withinRange = false;
		
		int xDistance = origin.getX() - this.x;
		int yDistance = origin.getY() - this.y;
		
		double distance = Math.sqrt(Math.pow(xDistance, 2)+Math.pow(yDistance, 2));
		
		if(radius >= distance)
			withinRange = true;
		
		return withinRange;
	}
	
	public String toString() {
		return x + " " + y;
	}
	
	public int getDistanceTo(Coordinate c) {
		
		int xDistance = c.getX() - this.x;
		int yDistance =c.getY() - this.y;
		
		double distance = Math.sqrt(Math.pow(xDistance, 2)+Math.pow(yDistance, 2));
		
		return (int) Math.abs(distance);
		
	}
	
	public static void main(String[] args) {
		Coordinate c = new Coordinate(0,0);
		Coordinate d = new Coordinate(71,71);
		System.out.println(c.withinCircularRange(d, 100));
	}
}
