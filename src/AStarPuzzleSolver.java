import java.util.ArrayList;


public class AStarPuzzleSolver implements PuzzleSolver
{
	private int preferredHeuristic = 0;
	private ArrayList<String> visitedStates = new ArrayList<String>();
	
	@Override
	public State solve(State state) 
	{
		HelpfullAlorithms.printInfo(state);
		state.visited = true;
		
		if(state.checkIfWinner())
			return state;
		
		if(state.g == 0)
			preferredHeuristic = 16;//state.getExpectedNumberOfMoves();
		
		ArrayList<State> children = HelpfullAlorithms.getAllChildren(state);
		children = HelpfullAlorithms.stripVisitedStates(children); //remove the nodes that has already been visited
		//children = HelpfullAlorithms.stripTwinStates(children, visitedStates); //remove the nodes that are allike the ones already visited
		
		if(children.size() == 0 && state.parent == null)
		{
			System.out.println("failed to find a path");
			return null;
		}
		
		if(children.size() == 0)
			return solve(state.parent);
		
		if(state.g == preferredHeuristic)
			return solve(state.parent);
		
		State child = HelpfullAlorithms.stateWithLowestHValue(children);
		return solve(child);	
	}
}
