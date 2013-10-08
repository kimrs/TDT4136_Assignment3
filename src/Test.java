import java.util.ArrayList;
import java.util.Scanner;


public class Test 
{
	
	//public static final int[] MAP_SIZE = {6, 6};	
	//public static final int[] MAP_EXIT = {5, 2};
	
	public static void main(String args[])
	{	
		boolean done = false;
		while(!done)
		{
			System.out.println("1.test game\n2.test easy\n3.test medium\n4.test hard\n5.test expert\n6.quit");
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			switch(choice)
			{
			case 1:
				testGame(testEasy());
				break;
			case 2:
				testAlgorithm(testEasy());
				break;
			case 3:
				testAlgorithm(testMedium());
				break;
			case 4:
				testAlgorithm(testHard());
				break;
			case 5:
				testAlgorithm(testExpert());
				break;
			default:
				done = true;
			}
		}
	}
	
	private static State testEasy()
	{
		System.out.println("--- Test easy-3 ---");
		ArrayList<Vehicle> vehicles = new ArrayList();
		vehicles.add(new Vehicle(0, 2, 2, 2));
		vehicles.add(new Vehicle(0, 0, 4, 3));
		vehicles.add(new Vehicle(0, 3, 4, 2));
		vehicles.add(new Vehicle(0, 4, 1, 2));
		vehicles.add(new Vehicle(1, 2, 0, 2));
		vehicles.add(new Vehicle(1, 4, 2, 2));
		
		return new State(vehicles);
	}
	
	private static State testMedium()
	{
		System.out.println(" --- Test medium-1 ---");
		ArrayList<Vehicle> vehicles = new ArrayList();
		vehicles.add(new Vehicle(0, 1, 2, 2));
		vehicles.add(new Vehicle(0, 0, 5, 3));
		vehicles.add(new Vehicle(0, 1, 3, 2));
		vehicles.add(new Vehicle(0, 3, 0, 2));
		vehicles.add(new Vehicle(1, 0, 2, 3));
		vehicles.add(new Vehicle(1, 2, 0, 2));
		vehicles.add(new Vehicle(1, 3, 1, 2));
		vehicles.add(new Vehicle(1, 3, 3, 3));
		vehicles.add(new Vehicle(1, 4, 2, 2));
		vehicles.add(new Vehicle(1, 5, 0, 2));
		vehicles.add(new Vehicle(1, 5, 2, 2));
		return new State(vehicles);
		
	}
	
	private static State testHard()
	{
		System.out.println(" --- Test hard-3 ---");
		ArrayList<Vehicle> vehicles = new ArrayList();
		vehicles.add(new Vehicle(0, 2, 2, 2));
		vehicles.add(new Vehicle(0, 0, 4, 2));
		vehicles.add(new Vehicle(0, 0, 5, 2));
		vehicles.add(new Vehicle(0, 2, 5, 2));
		vehicles.add(new Vehicle(0, 4, 0, 2));
		vehicles.add(new Vehicle(1, 0, 0, 3));
		vehicles.add(new Vehicle(1, 1, 1, 3));
		vehicles.add(new Vehicle(1, 2, 0, 2));
		vehicles.add(new Vehicle(1, 3, 0, 2));
		vehicles.add(new Vehicle(1, 4, 2, 2));
		vehicles.add(new Vehicle(1, 4, 4, 2));
		vehicles.add(new Vehicle(1, 5, 3, 3));
		return new State(vehicles);
	}
	
	private static State testExpert()
	{
		System.out.println(" --- Test expert-2 ---");
		ArrayList<Vehicle> vehicles = new ArrayList();
		vehicles.add(new Vehicle(0, 0, 2, 2));
		vehicles.add(new Vehicle(0, 0, 1, 3));
		vehicles.add(new Vehicle(0, 0, 5, 2));
		vehicles.add(new Vehicle(0, 1, 0, 2));
		vehicles.add(new Vehicle(0, 2, 3, 2));
		vehicles.add(new Vehicle(0, 3, 4, 2));
		vehicles.add(new Vehicle(1, 0, 3, 2));
		vehicles.add(new Vehicle(1, 2, 4, 2));
		vehicles.add(new Vehicle(1, 3, 0, 3));
		vehicles.add(new Vehicle(1, 4, 0, 2));
		vehicles.add(new Vehicle(1, 4, 2, 2));
		vehicles.add(new Vehicle(1, 5, 2, 2));
		vehicles.add(new Vehicle(1, 5, 4, 2));
		return new State(vehicles);
	}
	
	private static void testAlgorithm(State state)
	{
		PuzzleSolver solver = new NonRecursiveAStarPuzzleSolver();
		State winner = solver.solve(state);
		
		ArrayList<State> path = new ArrayList<>();
		
		while(winner.parent != null)
		{
			path.add(winner);
			winner = winner.parent;
		}
		
		for(int i = path.size() - 1; i >= 0; i--)	
		{
			HelpfullAlorithms.printInfo(path.get(i));
		}
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
}
