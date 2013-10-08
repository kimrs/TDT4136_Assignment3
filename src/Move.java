
public class Move 
{
	public int direction;
	public Vehicle vehicle;
	
	public Move(Vehicle vehicle, int direction)
	{
		this.vehicle = vehicle;
		this.direction = direction;
	}
	
	public Vehicle getNextVehicle()
	{
		if(vehicle.getOrientation() == Vehicle.HORIZONTAL)
			return new Vehicle(
					vehicle.getOrientation(), 
					vehicle.getCoord()[0] + direction,
					vehicle.getCoord()[1],
					vehicle.getSize());
		else
			return new Vehicle(
					vehicle.getOrientation(),
					vehicle.getCoord()[0],
					vehicle.getCoord()[1] + direction,
					vehicle.getSize());
	}
	
	public String toString()
	{
		return "vehicle: " + vehicle.hash + ", direction: " + direction;
	}
}
