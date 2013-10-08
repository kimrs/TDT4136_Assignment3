import java.util.ArrayList;


public abstract class HelpfullAlorithms 
{
	/**
	 * Fills array with coordinates of spaces currently occupied.
	 * @param vehicles
	 */
	public static ArrayList<int[]> fillOccupiedSpaces(ArrayList<Vehicle> vehicles)
	{
		ArrayList<int[]> occupiedSpaces = new ArrayList<int[]>();
		
		for(Vehicle v : vehicles)
		{
			int orientation = v.representation[Vehicle.ORIENTATION];
			int size = v.representation[Vehicle.SIZE];
			int x = v.representation[Vehicle.COORD_X];
			int y = v.representation[Vehicle.COORD_Y];
			
			if(orientation == Vehicle.HORIZONTAL)
				for(int i = 0; i < size; i++)
					occupiedSpaces.add(new int[]{x + i, y});
			else
				for(int i = 0; i < size; i++)
					occupiedSpaces.add(new int[]{x, y + i});
		}
		/*System.out.println("Vehicles: " + vehicles.size());
		for(int[] i : occupiedSpaces)
			System.out.println("X: " + i[0] + "\tY: " + i[1]);*/
		
		return occupiedSpaces;
	}

}
