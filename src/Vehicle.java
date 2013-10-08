
public class Vehicle 
{
	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 0;
	
	public static final int ORIENTATION = 0;
	public static final int COORD_X = 1;
	public static final int COORD_Y = 2;
	public static final int SIZE = 3;
	
	public int[] representation;
	public String hash = "";
	public boolean backOccupied = false;
	public boolean frontOccupied = false;
	
	/**
	 * 
	 * @param orientation, horizontal 0, vertical 1
	 * @param coordX
	 * @param coordY
	 * @param size
	 */
	public Vehicle(int orientation, int coordX, int coordY, int size)
	{
		representation = new int[]{orientation, coordX, coordY, size };
		for(int i:representation)
			hash += i;
	}
	
	public int getOrientation(){	return representation[ORIENTATION]									;}
	public int[] getCoord(){		return new int[]{ representation[COORD_X], representation[COORD_Y]}	;}
	public int getSize(){			return representation[SIZE]											;}
}
