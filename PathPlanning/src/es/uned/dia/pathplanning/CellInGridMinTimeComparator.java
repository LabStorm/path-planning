package es.uned.dia.pathplanning;

import java.util.Comparator;

public class CellInGridMinTimeComparator implements Comparator<CellInGrid>
{
    @Override
    public int compare(CellInGrid x, CellInGrid y)
    {
    	if (x.cell.time < y.cell.time) return -1;
    	if (x.cell.time > y.cell.time) return 1;
    	
    	return 0;
    }
}