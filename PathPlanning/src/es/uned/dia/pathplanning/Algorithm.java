package es.uned.dia.pathplanning;

public class Algorithm {
	private Grid grid;
	
	public Algorithm(Grid grid) {
		this.grid = grid;
	}
	
	private float Eik(CellInGrid cell) {
		CellInGrid[] neighbours = grid.getNeighbours(cell);
		float T1 = getHorizontalMinTime(neighbours), T2 = getVerticalMinTime(neighbours);

		if (Math.abs(T1-T2) > grid.delta_x/cell.speed) {
			return Math.min (T1,T2) + grid.delta_x/cell.speed;
		}

		//Debug.Log ("Eik: " + T1 + ", " + T2 + ", " + Mathf.Abs (T1-T2) + ", " + delta_x/cell.speed);
		return (float)((T1+T2)/2 + 0.5f*Math.sqrt(Math.pow(T1+T2, 2) - 2*(Math.pow(T1,2) + Math.pow(T2, 2) - Math.pow(grid.delta_x/cell.speed, 2))));
	}

	private float getHorizontalMinTime(CellInGrid[] neighbours) {
		float T1 = 0;
		if (i > 0 && i < grid.sizeX()) {
			T1 = Math.min(neighbours[grid.LEFT].cell.time, neighbours[grid.RIGHT].cell.time);
		} else if (i > 0) {
			T1 = neighbours[grid.LEFT].cell.time;
		} else {
			T1 = neighbours[grid.RIGHT].cell.time;
		}
		return T1;
	}
	
	private float getVerticalMinTime(CellInGrid[] neighbours) {
		float T2 = 0;
		if (j > 0 && j < grid.sizeY()) {
			T2 = Math.min(neighbours[grid.UP].cell.time, neighbours[grid.DOWN].cell.time);
		} else if (j > 0) {
			T2 = neighbours[grid.UP].cell.time;
		} else {
			T2 = neighbours[grid.DOWN].cell.time;
		}
		return T2;
	}
}






//public class FMM : MonoBehaviour {
//
//	public Grid board;
//
//	public GameObject cellPrefab;
//
//	public List<Cell> NB;
//
//	// Use this for initialization
//	void Start ()
//	{
//		
//	}
//	
//	// Update is called once per frame
//	void Update ()
//	{
//		if (Input.GetKeyDown (KeyCode.F))
//		{
//			this.Run ();
//			//StartCoroutine (this.Run ());
//		}
//		if (Input.GetKeyDown (KeyCode.P))
//		{
//			this.Paint ();
//		}
//	}
//
//	public void DeleteChilds ()
//	{
//		for (int i = 0; i < this.transform.childCount; i++)
//		{
//			Destroy (this.transform.GetChild (i).gameObject);
//		}
//	}
//		
//
//	public void Paint ()
//	{
//		GameObject go;
//		Cell cell;
//
//		this.DeleteChilds ();
//
//		for (int i = 0; i < this.board.rows.Count; i++)
//		{
//			for (int j = 0; j < this.board.rows[i].rowCells.Count; j++)
//			{
//				go = Instantiate (this.cellPrefab) as GameObject;
//				go.transform.position = new Vector3 (j/1.9f, i/1.9f);
//				go.transform.localScale = new Vector3 (0.5f, 0.5f);
//				go.transform.parent = this.transform;
//
//				cell = this.board.rows[i].rowCells[j];
//
//				if (cell.source)
//				{
//					go.GetComponentInChildren<Renderer>().material.color = Color.white;
//				}
//				else if (cell.dest)
//				{
//					go.GetComponentInChildren<Renderer>().material.color = Color.black;
//				}
//				else if (cell.occupied)
//				{
//					go.GetComponentInChildren<Renderer>().material.color = Color.gray;
//				}
//				else
//				{
//					go.GetComponentInChildren<Renderer>().material.color = new Color (1-cell.time/10f, 0*(1-cell.time/4f), 1-cell.time/10f);
//				}
//			}
//		}
//	}
//

//	void Run ()
//	{
//		List<Cell> S = new List<Cell> ();
//		List<Cell> SNB = new List<Cell> ();
//		float p;
//
//		foreach (Grid.Row row in this.board.rows)
//		{
//			foreach (Cell cell in row.rowCells)
//			{
//				if (cell.source)
//				{
//					S.Add (cell);
//					cell.time = 0;
//				}
//				else
//				{
//					cell.time = float.MaxValue;
//				}
//			}
//		}
//
//		Debug.Log ("Source cells: " + S.Count);
//
//		foreach (Cell sourceCell in S)
//		{
//			sourceCell.time = 0;
//			sourceCell.state = CellState.Frozen;
//
//			NB = new List<Cell> ();
//			NB.AddRange (this.GetNB (sourceCell));
//
//			foreach (Cell cell in NB)
//			{
//				if (cell.state != CellState.Frozen && !cell.occupied && cell.speed > 0)
//				{
//					p = Eik (cell);
//
//					if (cell.state == CellState.Narrow)
//					{
//						if (p < cell.time) cell.time = p;
//					}
//					else if (cell.state == CellState.Open)
//					{
//						cell.state = CellState.Narrow;
//						cell.time = p;
//						SNB.Add (cell);
//					}
//					this.Paint ();
//				}
//			}
//		}
//
//		//return;
//
//		while (SNB.Count > 0)
//		{
//			Cell smallestCell = SNB[0];
//			for (int i = 1; i < SNB.Count; i++)
//			{
//				if (SNB[i].time < smallestCell.time)
//				{
//					smallestCell = SNB[i];
//				}
//			}
//
//			smallestCell.state = CellState.Frozen;
//
//			NB = new List<Cell> ();
//			NB.AddRange (GetNB (smallestCell));
//
//			foreach (Cell cell in NB)
//			{
//				if (cell.state != CellState.Frozen && !cell.occupied && cell.speed > 0)
//				{
//					p = Eik (cell);
//
//					if (cell.state == CellState.Narrow)
//					{
//						if (p < cell.time) cell.time = p;
//					}
//					else if (cell.state == CellState.Open)
//					{
//						cell.state = CellState.Narrow;
//						cell.time = p;
//						SNB.Add (cell);
//					}
//					this.Paint ();
//				}
//			}
//
//			//yield return null;
//			SNB.Remove (smallestCell);
//		}
//
//		//yield return null;
//	}
//