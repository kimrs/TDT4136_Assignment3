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
	
	public static String printPath(State state)
	{
		printInfo(state);
		if(state.parent != null)
			return state.generatedFrom + "\n" + printPath(state.parent);
		return "";
	}
	
	public static void printInfo(State state, String info)
	{
		System.out.print(info + " ");
		printInfo(state);
	}
	
	public static void printInfo(State state)
	{
		System.out.print("State.g: " + state.g + " state.h: " + state.getExpectedNumberOfMoves());
		if(state.generatedFrom != null)
			System.out.println(" move: " + state.generatedFrom);
		else System.out.println();
	}
	
	public static ArrayList<State> stripVisitedStates(ArrayList<State> states)
	{
		ArrayList<State> out = new ArrayList<>();
		for(State s : states)
			if(!s.visited)
				out.add(s);
		return out;
	}
	
	public static ArrayList<State> stripTwinStates(ArrayList<State> states, ArrayList<String> visitedStates)
	{
		ArrayList<State> out = new ArrayList<>();
		for(State s : states)
		{
			boolean exists = false;
			for(String hash:visitedStates)
			{
				if(s.hash.equals(hash))
					exists = true;
			}
			if(!exists)
				out.add(s);
		}
		
		return out;
	}
	
	public static ArrayList<State> getAllChildren(State state)
	{
		ArrayList<Move> moves = state.getAvailableMoves();
		//Expand leafs
		ArrayList<State> childStates = new ArrayList<>();
		//System.out.print("Moves: ");
		for(Move m:moves)
		{
			//System.out.print(m.toString() + "|");
			childStates.add(state.getNextState(m));
		}
		return childStates;
	}
	
	/**
	 * changes the parent if child has been seen before
	 * @param state
	 */
	public static ArrayList<State> compareAndReplaceVine(State state, ArrayList<State> agenda)
	{
		int index = 0;
		for(int i = 0; i < agenda.size(); i++)
			if(agenda.get(i).compare(state) && agenda.get(i).g > state.g)
				index = i;
		
		if(index != 0)
		{
			agenda.remove(index);
			agenda.add(index, state);
			for(int i = index + 1; i < agenda.size(); i++)
				agenda.remove(i);
			
			agenda.trimToSize();
		}
		return agenda;
	}
	
	/**
	 * Returns the state with the lowest h -value
	 * @param states
	 * @return
	 */
	public static State stateWithLowestHValue(ArrayList<State> states)
	{
		int index = 0;
		int currentH = states.get(index).getExpectedNumberOfMoves();
		
		for(int i = 0; i < states.size(); i++)
		{
			if(states.get(i).getExpectedNumberOfMoves() < currentH && !states.get(i).visited)
				index = i;
		}
		
		return states.get(index);
	}

}
