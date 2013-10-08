import java.util.ArrayList;


public class State 
{
	public static int[] EXIT = new int[]{ 5, 2 };
	public static int[] SIZE = new int[]{ 6, 6 };
	
	public State parent = null;
	public ArrayList<State> children = null;
	public Move generatedFrom = null;
	
	private ArrayList<Vehicle> vehicles;
	private ArrayList<Move> availableMoves = null;
	private ObstacleObserver oObserver;
	private int[] size = null;
	private int[] exit = null;
	
	public boolean visited = false;
	public final int g; //cost of getting to this node
	private int h = -1; //estimated cost of getting to goal
	
	
	public String hash = "";
	
	public State(ArrayList<Vehicle> vehicles, int[] size, int[] exit, int cost)
	{
		this.g = cost + 1;
		this.size = size;
		this.exit = exit;
		init(vehicles);
	}
	
	public State(ArrayList<Vehicle> vehicles, int[] size, int cost)
	{
		this.g = cost + 1;
		this.size = size;
		if(exit == null)
			exit = EXIT;
		init(vehicles);
	}
	
	public State(ArrayList<Vehicle> vehicles, State state)
	{
		this.g = state.g + 1;
		this.size = state.size;
		this.exit = state.exit;
		this.parent = state;
		init(vehicles);
	}
	
	public State(ArrayList<Vehicle> vehicles)
	{
		if(size == null)
			size = SIZE;
		if(exit == null)
			exit = EXIT;
		g = 0;
		init(vehicles);
	}
	
	private void init(ArrayList<Vehicle> vehicles)
	{
		//visited = true;
		oObserver = new ParkinglotObstacleObserver(size);
		this.vehicles = oObserver.findObstacles(vehicles);		
		
		for(Vehicle v:vehicles)
		{
			hash += v.hash + "\n";
		}
	}
	
	/**
	 * Game is won when the first vehicle in the list is touching the exit (5, 2)
	 * Goal state is set implicitly.
	 */
	public boolean checkIfWinner()
	{
		Vehicle v = vehicles.get(0);
		int orientation = v.getOrientation();
		int back = v.getCoord()[orientation] + v.getSize();
		int front = v.getCoord()[orientation];

		if(back == exit[orientation] || front == exit[orientation])
			return true;
		else
			return false;
	}
	
	/**
	 * Compares the hash-values of input with the hash value of 
	 * this object. 
	 * @param state
	 * @return true if alike
	 */
	public boolean compare(State state)
	{
		return state.hash.equals(this.hash);
	}
	
	/**
	 * Return list of available moves. If available moves not computed, computes it. (Lazy-loading)
	 * @return
	 */
	public ArrayList<Move> getAvailableMoves()
	{
		if(availableMoves == null)
		{
			availableMoves = new ArrayList<Move>();
		
			for(Vehicle v: vehicles)
			{
				if(!v.backOccupied)
					availableMoves.add(new Move(v, 1));
				if(!v.frontOccupied)
					availableMoves.add(new Move(v, -1));
			}
		}
		
		return availableMoves;
	}
	
	/**
	 * Generates the state after move has been applied
	 * @param move
	 * @return
	 */
	public State getNextState(Move move)
	{
		//see if already generated
		if(children != null)
		{
			for(State s:children)
			{
				if(s.generatedFrom != null && s.generatedFrom.toString().equals(move.toString()))
					return s;
			}
		} else //create an arraylist for the children about to be popped
		{
			children = new ArrayList<>();
		}
		
		/*
		 * Sets the vehicles of th new state the same as this state. Move the vehicle the move action belongs to
		 */
		ArrayList<Vehicle> nsVehicles = new ArrayList<Vehicle>();
		for(Vehicle v : vehicles)
		{
			if(move.vehicle.hash.equals(v.hash))	
				nsVehicles.add(move.getNextVehicle());
			else
				nsVehicles.add(v);
		}
		State next = new State(nsVehicles, this);
		next.generatedFrom = move;
		children.add(next);
		
		return next;
	}
	
	
	/**
	 * Computes h(s)
	 * two moves for each obstacle in front plus two moves for each space between the first vehicle and the goal.
	 * if the problem is to be expanded, in other words the exit or vehicle is changed, the this algorithm needs 
	 * to be modified. 
	 * @return
	 */
	public int getExpectedNumberOfMoves()
	{
		if(h == -1)
		{
			Vehicle vehicle = vehicles.get(0);
			int[] coords = vehicle.getCoord();
			int size = vehicle.getSize();
			int distance = this.size[0] - (size + coords[0]);
			
			ArrayList<int[]> occupiedSpaces = HelpfullAlorithms.fillOccupiedSpaces(vehicles);
			int expectedMoves = 0;
			
			//for each blocked point in front of our hero
			//expected movment is increased by 10
			for(int i = 0; i < distance; i++)
				if(occupiedSpaces.get(i)[1] == vehicle.getCoord()[1])
				{
					if(occupiedSpaces.get(i)[0] > vehicle.getCoord()[0])
						expectedMoves += 10;
				}
			
			h = expectedMoves + distance;
			}
	
		return h;
	}
}


