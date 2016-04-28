package es.uned.dia.pathplanning;

import java.util.ArrayList;

import es.uned.dia.pathplanning.CellInGrid.CellType;

public class Grid {
	public CellInGrid[][] cells;
	public final int LEFT = 0;
	public final int UP = 1;
	public final int RIGHT = 2;
	public final int DOWN = 3;

	public float delta_x = 1;
	public float delta_y = 1;
	
	private ArrayList<CellInGrid> obstacles;
	private ArrayList<CellInGrid> sources;
	private ArrayList<CellInGrid> sinks;
	
	public Grid () {
		this.obstacles = new ArrayList<> ();
		this.sources = new ArrayList<> ();
		this.sinks = new ArrayList<> ();
	}
	
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
		if (j < cells[i].length-1) {
			neighbours[DOWN] = cells[i][j+1];
		}
		return neighbours;
	}
	
	public CellInGrid[] getObstacles () {
		return this.obstacles.toArray(new CellInGrid[this.obstacles.size()]);
	}
	
	public CellInGrid[] getSinks () {
		return this.sinks.toArray(new CellInGrid[this.sinks.size()]);
	}
	
	public CellInGrid[] getSources () {
		return this.sources.toArray(new CellInGrid[this.sources.size()]);
	}
	
	public void setObstacle (int i, int j) {
		CellInGrid obstacle = this.cells[i][j];
		
		th
	}
	
	public void setSource (int i, int j) {
		CellInGrid source = this.cells[i][j];
		
		if (source.type == CellType.SOURCE) return;
		
		if (source.type == CellType.SINK) {
			this.sinks.remove(source);
		}
		source.type = CellInGrid.CellType.SOURCE;
		
		this.sources.add (source);
	}
	
	public void setSink (int i, int j) {
		CellInGrid sink = this.cells[i][j];
		
		if (sink.type == CellType.SINK) return;
		
		if (sink.type == CellType.SOURCE) {
			this.sources.remove(sink);
		}
		
		sink.type = CellInGrid.CellType.SINK;
		
		this.sinks.add (sink);
	}
}
