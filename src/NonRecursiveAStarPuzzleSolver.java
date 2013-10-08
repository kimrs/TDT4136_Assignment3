import java.util.ArrayList;


public class NonRecursiveAStarPuzzleSolver implements PuzzleSolver
{
	ArrayList<State> agenda = new ArrayList<>();
	private ArrayList<String> visitedStates = new ArrayList<String>();
	
	@Override
	public State solve(State state) 
	{
		int preferredHeuristic = 0;
		int level = 0;
		boolean done = false;
		while(!done)
		{
			state.visited = true;
			visitedStates.add(state.hash);
			if(level == 0)
				preferredHeuristic = state.getExpectedNumberOfMoves();
			
			if(state.checkIfWinner())
			{
				System.out.println("winner");
				done = true;
			}
			else
			{
				ArrayList<State> children = HelpfullAlorithms.getAllChildren(state);
				children = HelpfullAlorithms.stripVisitedStates(children);
				//children = HelpfullAlorithms.stripTwinStates(children, visitedStates);
				
				if(children.size() == 0 || level == preferredHeuristic)
				{
					if(state.parent == null)
					{
						System.out.println("finished without finding a path");
						done = true;
					}else
					{
						state = state.parent;
						level--;
					}
				} else
				{
					level++;
					state = HelpfullAlorithms.stateWithLowestHValue(children);
				}
			}
			HelpfullAlorithms.printInfo(state, "level: " + level);
		}
		
		return state;
	}
}
