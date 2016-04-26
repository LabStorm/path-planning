package es.uned.dia.pathplanning;

import java.sql.Time;
import java.util.*;

public class Algorithm {
	private Grid grid;
	
	public Algorithm(Grid grid) {
		this.grid = grid;
	}

	private float getTime(CellInGrid cell) {
		CellInGrid[] neighbours = grid.getNeighbours(cell);
		float T1 = getHorizontalMinTime(neighbours), T2 = getVerticalMinTime(neighbours);

		if (Math.abs(T1-T2) > grid.delta_x/cell.cell.speed) {
			return Math.min (T1,T2) + grid.delta_x/cell.cell.speed;
		}

		return (float)((T1+T2)/2 + 0.5*Math.sqrt(Math.pow(T1+T2, 2) - 2*(Math.pow(T1,2) + Math.pow(T2, 2) - Math.pow(grid.delta_x/cell.cell.speed, 2))));
	}

	private float getHorizontalMinTime(CellInGrid[] neighbours) {
		float T1 = 0;
		if (neighbours[grid.LEFT] != null && neighbours[grid.RIGHT] != null) {
			T1 = Math.min(neighbours[grid.LEFT].cell.time, neighbours[grid.RIGHT].cell.time);
		} else if (neighbours[grid.LEFT] != null) {
			T1 = neighbours[grid.LEFT].cell.time;
		} else if (neighbours[grid.RIGHT] != null) {
			T1 = neighbours[grid.RIGHT].cell.time;
		}
		return T1;
	}
	
	private float getVerticalMinTime(CellInGrid[] neighbours) {
		float T2 = 0;
		if (neighbours[grid.UP] != null && neighbours[grid.DOWN] != null) {
			T2 = Math.min(neighbours[grid.UP].cell.time, neighbours[grid.DOWN].cell.time);
		} else if (neighbours[grid.UP] != null) {
			T2 = neighbours[grid.UP].cell.time;
		} else if (neighbours[grid.DOWN] != null) {
			T2 = neighbours[grid.DOWN].cell.time;
		}
		return T2;
	}

	public void run() {
        Comparator<CellInGrid> comparator = new CellInGridMinTimeComparator();
        
        PriorityQueue<CellInGrid> narrowBand = new PriorityQueue<CellInGrid>(10,comparator);
        
        CellInGrid currentCell;
        
        float p = 0;
        
        // Initialization
        for (CellInGrid source: this.grid.getSources()) {
        	source.cell.time = 0;
			source.cell.state = Cell.CellState.Frozen;
			
			for (CellInGrid neighbour: this.grid.getNeighbours (source)) {
				if (neighbour != null)
				{
					if (neighbour.cell.state != Cell.CellState.Frozen && neighbour.type != CellInGrid.CellType.OBSTACLE && neighbour.cell.speed > 0) {
						p = getTime (neighbour);
						
						if (neighbour.cell.state == Cell.CellState.Narrow) {
							if (p < neighbour.cell.time) {
								narrowBand.remove(neighbour);
								neighbour.cell.time = p;
								narrowBand.add(neighbour);
							}
						} else if (neighbour.cell.state == Cell.CellState.Open) {
							neighbour.cell.state = Cell.CellState.Narrow;
							neighbour.cell.time = p;
							narrowBand.add(neighbour);
						}
					}
				}
			}
        }
        
        // Main code
        boolean stop;
        
        while (narrowBand.size() > 0) {
            currentCell = narrowBand.poll();
            
            stop = true;
            for (CellInGrid sink : this.grid.getSinks()) {
            	if (sink.cell.time > currentCell.cell.time) {
            		stop = false;
            		break;
            	}
            }
            if (stop) return;
            
            currentCell.cell.state = Cell.CellState.Frozen;
            
            for (CellInGrid neighbour : grid.getNeighbours(currentCell)) {
	            if (neighbour != null) {
	            	if (neighbour.cell.state != Cell.CellState.Frozen && neighbour.type != CellInGrid.CellType.OBSTACLE && neighbour.cell.speed > 0) {			                
	            		p = getTime(neighbour);
	            		
	            		if (neighbour.cell.state == Cell.CellState.Narrow) {
							if (p < neighbour.cell.time) {
								narrowBand.remove(neighbour);
								neighbour.cell.time = p;
								narrowBand.add(neighbour);
							}
						} else if (neighbour.cell.state == Cell.CellState.Open) {
							neighbour.cell.state = Cell.CellState.Narrow;
							neighbour.cell.time = p;
							narrowBand.add(neighbour);
						}
	            	}
	            }
            }
        }
	}
	
	public static void main (String[] args) {
		Grid g = new Grid ();
		
		int rows = 512, columns = 512;
		
		g.cells = new CellInGrid[rows][];
		for (int i = 0; i < rows; i++) {
			g.cells[i] = new CellInGrid[columns];
			for (int j = 0; j < columns; j++) {
				g.cells[i][j] = new CellInGrid (i,j);
			}
		}
		
		Random r = new Random();
		
		g.setSource (0, 0);
		g.setSink (r.nextInt(rows), r.nextInt(columns));
		
		Algorithm fmm = new Algorithm (g);
		
		long startTime = System.nanoTime();
		fmm.run();
		System.out.println((System.nanoTime() - startTime)/1e6);
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(g.cells[i][j].cell.time + " ");
			}
			System.out.println();
		}
	}
}