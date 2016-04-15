package es.uned.dia.pathplanning;

public class Cell {
	public enum CellState { Open, Narrow, Frozen };
	public float time;
	public CellState state;
	public float speed;

	public Cell ()
	{
		this.time = Float.MAX_VALUE;
		this.state = CellState.Open;
		this.speed = 1;
	}

	public static void main(String args[]) {
		System.out.println("HOla");
	}

}