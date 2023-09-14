package escape.coordinate;



public class RawCoordinate implements Coordinate{

	//Stores the x and y coordinates
	private int x;
	private int y;
		
	RawCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	/**
	 * Checks to see if the coordinates are the same
	 * @param other Object to compare to
	 */
	public boolean equals(Object other) {
		if (other instanceof RawCoordinate) {
			RawCoordinate otherRawCoordinate = (RawCoordinate) other;
			
			return (otherRawCoordinate.getX() == this.x && otherRawCoordinate.getY() == this.y);
			
		} else {
			return false;
		}
	}
	
	/**
	 * HashCode function to check for hashmap
	 */
	public int hashCode() {
		String hash = this.x + " " + this.y;
		return hash.hashCode();
	}
	

}
