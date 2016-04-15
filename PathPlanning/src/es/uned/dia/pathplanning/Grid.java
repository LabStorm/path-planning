package es.uned.dia.pathplanning;

public class Grid {
	public CellInGrid[][] cells;
	public final int LEFT = 0;
	public final int UP = 1;
	public final int RIGHT = 2;
	public final int DOWN = 3;

	public float delta_x = 1;
	public float delta_y = 1;

	public CellInGrid[] getNeighbours(CellInGrid cell) {
		return getNeighbours(cell.getRow(), cell.getColumn());
	}

	public CellInGrid[] getNeighbours(int i, int j) {
		CellInGrid[] neighbours = new CellInGrid[4];
		if (i > 0) {
			neighbours[LEFT] = cells[i-1][j];
		}
		if (j > 0) {
			neighbours[UP] = cells[i][j-1];
		}
		if (i < cells.length-1) {
			neighbours[RIGHT] = cells[i+1][j];
		}
		if (j > cells[i].length-1) {
			neighbours[DOWN] = cells[i][j+1];
		}
		return neighbours;
	}
}
