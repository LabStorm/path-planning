package es.uned.dia.pathplanning;


public class CellInGrid {
	public enum CellType { SOURCE, SINK, OBSTACLE };
	public Cell cell;
	public CellType type;
	private int row, column;
	
	public CellInGrid (int row, int column) {
		this.cell = new Cell ();
		this.row = row;
		this.column = column;
	}
	
	public CellInGrid(Cell cell, int row, int column, CellType type) {
		this.cell = cell;
		this.row = row;
		this.column = column;
		this.type = type;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}