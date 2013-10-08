import java.util.ArrayList;
import java.util.Scanner;


public class Test 
{
	
	//public static final int[] MAP_SIZE = {6, 6};	
	//public static final int[] MAP_EXIT = {5, 2};
	
	public static void main(String args[])
	{
		ArrayList<Vehicle> vehicles = new ArrayList();
		vehicles.add(new Vehicle(0, 2, 2, 2));
		vehicles.add(new Vehicle(0, 0, 4, 3));
		vehicles.add(new Vehicle(0, 3, 4, 2));
		vehicles.add(new Vehicle(0, 4, 1, 2));
		vehicles.add(new Vehicle(1, 2, 0, 2));
		vehicles.add(new Vehicle(1, 4, 2, 2));
		
		State state = new State(vehicles);
		testAlgorithm(state);

	}
	
	private static void testAlgorithm(State state)
	{
		PuzzleSolver solver = new NonRecursiveAStarPuzzleSolver();
		State winner = solver.solve(state);
	}
	
	private static void testGame(State state)
	{
		if(state.checkIfWinner())
			System.out.println("Game won!");
		else
		{
			ArrayList<Move> moves = state.getAvailableMoves();
			System.out.println("Cost of state: " + state.g + "\nAvailable moves");
			int choise;
			for(int i = 0; i < moves.size(); i++)
				System.out.println(i + " - " + moves.get(i).toString());
			
			Scanner scan = new Scanner(System.in);
			choise = scan.nextInt();
			
			if(choise >= 0 && choise < moves.size())
				testGame(state.getNextState(moves.get(choise)));
		}
	}
	
	private static void testObstacleObserver(ArrayList<Vehicle> vehicles)
	{
		ObstacleObserver observer = new ParkinglotObstacleObserver(new int[]{6, 6});
		ArrayList<Vehicle> vehiclesWithObstaclesSet = observer.findObstacles(vehicles);
		
		for(Vehicle c : vehiclesWithObstaclesSet)
			System.out.println("Front: " + c.frontOccupied + "\tBack: " + c.backOccupied);
	}
}
