import java.util.ArrayList;


public class ParkinglotObstacleObserver implements ObstacleObserver
{ 
	private final int X = 0;
	private final int Y = 1;
	
	private int size[];
	
	public ParkinglotObstacleObserver(int[] size)
	{
		this.size = size;
	}
	
	@Override
	public ArrayList<Vehicle> findObstacles(ArrayList<Vehicle> vehicles) 
	{
		ArrayList<int[]> occupiedSpaces = HelpfullAlorithms.fillOccupiedSpaces(vehicles);
		
		ArrayList<Vehicle> vehiclesWithObstaclesSet = new ArrayList();
		for(Vehicle vehicle : vehicles)
		{
			int x = vehicle.representation[Vehicle.COORD_X];
			int y = vehicle.representation[Vehicle.COORD_Y];

			int s = vehicle.representation[Vehicle.SIZE];

			if(vehicle.representation[Vehicle.ORIENTATION] == Vehicle.HORIZONTAL)//checks then so see if any of the cars are in the way.
				vehiclesWithObstaclesSet.add(findHorizontalObstacles(vehicle, x, y, s, occupiedSpaces));
			else
				vehiclesWithObstaclesSet.add(findVerticalObstacles(vehicle, x, y, s, occupiedSpaces));
		}
		
		return vehiclesWithObstaclesSet;
		
	}
	
	/**
	 * The back of the vehicle is in this case pointing up or to the left
	 * @param vehicle
	 * @return
	 */
	private Vehicle findHorizontalObstacles(Vehicle vehicle, int x, int y, int s, ArrayList<int[]> occupiedSpaces)
	{
		vehicle.backOccupied = false;
		vehicle.frontOccupied = false;
		
		//Checks first to see if vehicle is at the walls of the parkinglot.
		if(x == 0)
			vehicle.frontOccupied = true;
		if(x + s == size[0])
			vehicle.backOccupied = true;
		 
		//checks then so see if any of the cars are in the way.
		for(int[] ia : occupiedSpaces)
			if(ia[Y] == y)
				if(ia[X] == x - 1)
					vehicle.frontOccupied = true;
				else if(ia[X] == x + s)
					vehicle.backOccupied = true;
	
		return vehicle;				
	}
	
	
	private Vehicle findVerticalObstacles(Vehicle vehicle, int x, int y, int s, ArrayList<int[]> occupiedSpaces)
	{
		vehicle.frontOccupied = false;
		vehicle.backOccupied = false;
		
		//Checks first to see if vehicle is at the walls of the parkinglot.
		if(y == 0)
			vehicle.frontOccupied = true;
		if(y + s == size[0])
			vehicle.backOccupied = true;
		//checks then so see if any of the cars are in the way.
		for(int[] ia : occupiedSpaces)
			if(ia[X] == x)
				if(ia[Y] == y - 1)
					vehicle.frontOccupied = true;
				else if(ia[Y] == y + s)
					vehicle.backOccupied = true;
	
		return vehicle;				
	}

}
