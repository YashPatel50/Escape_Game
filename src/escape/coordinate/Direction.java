package escape.coordinate;

public interface Direction {
	
	public static enum SquareDirection implements Direction{
		SW, W, NW, S, N, SE, E, NE;
	}
	
	public static enum HexDirection implements Direction{
		SW, NW, S, N, SE, NE;
	}

}
