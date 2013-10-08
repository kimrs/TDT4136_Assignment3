import java.util.ArrayList;


public class AStarPuzzleSolver implements PuzzleSolver
{
	private int preferredHeuristic = 0;
	private ArrayList<State> agenda = new ArrayList<State>();
	
	@Override
	public State solve(State state) 
	{
		System.out.println("State.g: " + state.g + " state.h: " + state.getExpectedNumberOfMoves() + "H: " + preferredHeuristic);
		//System.out.println(state.hash);
		
		state.visited = true;
		
		if(state.checkIfWinner())
			return state;
		
		if(state.g == 0)
			preferredHeuristic = 16;//state.getExpectedNumberOfMoves();
		
//		if(state.g == preferredHeuristic)
//			return solve(state.parent);
		
		ArrayList<State> children = getAllChildren(state);
		children = stripVisitedStates(children);
		
		if(children.size() == 0)
			return solve(state.parent);
		
		if(state.g == preferredHeuristic)
			return solve(state.parent);
		
		State child = stateWithLowestHValue(children);
		return solve(child);
		
		/*
		state.visited = true;
		
		System.out.println("F-value: " + (state.g + state.getExpectedNumberOfMoves()) + " H-value: " + state.getExpectedNumberOfMoves());
		//first time solve is called
		if(agenda.size() == 0)
			agenda.add(state);
		//compares to the saved states. Replaces if better g value.
		compareAndReplaceVine(state);

		
	
		//pop the one with lowest f-value if not visited
		State child = indexOfLowestHValue(childStates);
		
		//System.out.println(child.hash);
		agenda.add(child);
		
		//if winner, 
		if(state.getExpectedNumberOfMoves() == -1 || child.checkIfWinner())
			return agenda;
		else if(!child.visited)
			return solve(child);
		else if(state.parent != null && state.getExpectedNumberOfMoves() > state.parent.getExpectedNumberOfMoves())
			return solve(state.parent);
		else
			return solve(child);
			*/
	}
	
	private ArrayList<State> stripVisitedStates(ArrayList<State> states)
	{
		ArrayList<State> out = new ArrayList<>();
		for(State s : states)
			if(!s.visited)
				out.add(s);
		return out;
	}
	
	private ArrayList<State> getAllChildren(State state)
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
	private void compareAndReplaceVine(State state)
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
	}
	
	/**
	 * Returns the state with the lowest h -value
	 * @param states
	 * @return
	 */
	private State stateWithLowestHValue(ArrayList<State> states)
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
