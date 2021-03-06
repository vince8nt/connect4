
public class Board
{
	private int pieces[][];
	private int pSquares[][];
	private int cBias;
	
	// constructor
	public Board()
	{
		cBias = 0;
		pieces = new int[7][6];
		pSquares = new int[7][6];
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				pieces[i][j] = 0;
				pSquares[i][j] = 0;
			}
		}
	}
	
	// override Object's toString
	public String toString()
	{
		String s  = " ";
		for (int i = 0; i < 7; i++)
		{
			s += (" " + (i + 1));
		}
		s += "\n+ - - - - - - - +\n";
		for(int i = 0; i < 6; i++)
		{
			s += "| ";
			for(int j = 0; j < 7; j++)
			{
				if(pieces[j][i] == 0)
					s += "  ";
				else if(pieces[j][i] == 1)
					s += "O ";
				else
					s += "X ";
			}
			s += "|\n";
		}
		s += "+ - - - - - - - +";
		return(s);
	}
	
	// override Object's equals
	public boolean equals(Board comp)
	{
		return(pieces.equals(comp.pieces));
	}
	
	// place a piece in specific column
	public void drop(int color, int col)
	{
		if(color < 1 || color > 2)
		{
			throw new RuntimeException("undefined piece");
		}
		if(col < 0 || col > 6)
		{
			throw new RuntimeException("column out of bounds");
		}
		if(colFull(col))
		{
			throw new RuntimeException("column full");
		}
		
		int depth = 0;
		while(depth != 5 && this.pieces[col][depth + 1] == 0)
		{
			depth++;
		}
		this.pieces[col][depth] = color;
	}

	// deletes the last move in a given col
	public void remove(int col) {
		if(col < 0 || col > 6)
		{
			throw new RuntimeException("column out of bounds");
		}
		if(pieces[col][5] == 0) {
			throw new RuntimeException("column empty");
		}

		int depth = 0;
		while(depth != 5 && this.pieces[col][depth] == 0)
		{
			depth++;
		}
		this.pieces[col][depth] = 0;
	}
	
	// check to see ic column number col if full
	public boolean colFull(int col)
	{
		if(col < 0 || col > 6)
		{
			throw new RuntimeException("column out of bounds");
		}
		if(this.pieces[col][0] == 0)
			return(false);
		return(true);
	}
	
	// check to see if all the columns are full
	public boolean full()
	{
		for(int i = 0; i < 7; i++)
		{
			if(!colFull(i))
				return(false);
		}
		return(true);
	}
	
	// check to see if there is a 4-in-a-row of either color
	public boolean win()
	{
		int color, streak, pStreak, j, pX, pY, pColor;
		

		// reset pSquares
		for(int i = 0; i < 7; i++)
		{
			for(int j2 = 0; j2 < 6; j2++)
			{
				pSquares[i][j2] = 0;
			}
		}
		
		// horizontal 4?
		for(int i = 0; i < 6; i++)
		{
			color = streak = pStreak = j = pColor = 0;
			pX = pY = -1;
			while(j < 7)
			{
				if(pieces[j][i] == 0) {
					pX = j;
					pY = i;
					pStreak = streak + 1;
					color = streak = 0;
				}
				else if(pieces[j][i] == color) {
					streak++;
					pStreak++;
				}
				else
				{
					color = pieces[j][i];
					if (color == pColor) {
						++pStreak;
					}
					else if (streak == 0 && pX != -1) {
						pStreak = 2;
						pColor = color;
					}
					else {
						pStreak = 1;
						pColor = color;
					}
					streak = 1;
				}
				if(streak == 4)
					return(true);
				if (pStreak == 4) {
					if (pColor == 1) {
						if (pSquares[pX][pY] == 0)
							pSquares[pX][pY] = 1;
						if (pSquares[pX][pY] == 2)
							pSquares[pX][pY] = 3;
					}
					else {
						if (pSquares[pX][pY] == 0)
							pSquares[pX][pY] = 2;
						if (pSquares[pX][pY] == 1)
							pSquares[pX][pY] = 3;
					}
				}
				j++;
			}
		}
		
		// vertical 4?
		int xBias = 0, oBias = 0;
		for(int i = 0; i < 7; i++)
		{
			color = streak = j = 0;
			while(j < 6)
			{
				if(pieces[i][j] == 0)
					color = streak = 0;
				else if(pieces[i][j] == color) {
					streak++;
				}
				else
				{
					streak = 1;
					color = pieces[i][j];
				}
				if(streak == 4)
					return(true);
				if (color == 1) {
					oBias += Math.abs(i - 3);
				}
				else if (color == 2) {
					xBias += Math.abs(i - 3);
				}
				j++;
			}
		}
		cBias = xBias - oBias;
		
		int x;
		int y;

		// diagonal (/) 4?
		for(int i = -2; i < 4; ++i) {
			color = streak = pStreak = j = pColor = 0;
			if (i < 0) {
				x = 0;
				y = 0 - i;
			}
			else {
				x = i;
				y = 0;
			}
			pX = pY = -1;
			while (x < 7 && y < 6) {
				if(pieces[x][y] == 0) {
					pX = x;
					pY = y;
					pStreak = streak + 1;
					color = streak = 0;
				}
				else if(pieces[x][y] == color) {
					streak++;
					pStreak++;
				}
				else
				{
					color = pieces[x][y];
					if (color == pColor) {
						++pStreak;
					}
					else if (streak == 0 && pX != -1) {
						pStreak = 2;
						pColor = color;
					}
					else {
						pStreak = 1;
						pColor = color;
					}
					streak = 1;
				}
				if(streak == 4)
					return(true);
				if (pStreak == 4) {
					if (pColor == 1) {
						if (pSquares[pX][pY] == 0)
							pSquares[pX][pY] = 1;
						if (pSquares[pX][pY] == 2)
							pSquares[pX][pY] = 3;
					}
					else {
						if (pSquares[pX][pY] == 0)
							pSquares[pX][pY] = 2;
						if (pSquares[pX][pY] == 1)
							pSquares[pX][pY] = 3;
					}
				}
				++x;
				++y;
			}
		}

		// diagonal (\) 4?
		for(int i = 3; i < 9; ++i) {
			color = streak = pStreak = j = pColor = 0;
			if (i > 6) {
				x = 6;
				y = i - 6;
			}
			else {
				x = i;
				y = 0;
			}
			pX = pY = -1;
			while (x > -1 && y < 6) {
				if(pieces[x][y] == 0) {
					pX = x;
					pY = y;
					pStreak = streak + 1;
					color = streak = 0;
				}
				else if(pieces[x][y] == color) {
					streak++;
					pStreak++;
				}
				else
				{
					color = pieces[x][y];
					if (color == pColor) {
						++pStreak;
					}
					else if (streak == 0 && pX != -1) {
						pStreak = 2;
						pColor = color;
					}
					else {
						pStreak = 1;
						pColor = color;
					}
					streak = 1;
				}
				if(streak == 4)
					return(true);
				if (pStreak == 4) {
					if (pColor == 1) {
						if (pSquares[pX][pY] == 0)
							pSquares[pX][pY] = 1;
						if (pSquares[pX][pY] == 2)
							pSquares[pX][pY] = 3;
					}
					else {
						if (pSquares[pX][pY] == 0)
							pSquares[pX][pY] = 2;
						if (pSquares[pX][pY] == 1)
							pSquares[pX][pY] = 3;
					}
				}
				--x;
				++y;
			}
		}

		return(false);
	}

	// ranks a board state non recursively
	// ranking is dependent on the difference in winning squares
	public double Rating() {
		int x = 0, o = 0, last = 0;
		for(int i = 0; i < 7; ++i) {
			last = 0;
			for(int j = 5; j > -1; --j) {
				if (pSquares[i][j] == 2) {
					if (last == 2) {
						x += 5;
						last = 0;
					}
					else {
						last = 2;
						if (j % 2 == 0)
							x += 2;
						++x;
					}
				}
				else if (pSquares[i][j] == 1) {
					if (last == 1) {
						o += 5;
						last = 0;
					}
					else {
						last = 1;
						if (j % 2 == 1)
							o += 2;
						++o;
					}
				}
				else if (pSquares[i][j] == 3) {
					if (last == 1) {
						o += 3;
						last = 0;
					}
					else if (last == 2) {
						x += 3;
						last = 0;
					}
					else last = 3;
				}
				else
					last = 0;
			}
		}
		return (o - x) / 100.0 + cBias / 1000.0;
	}

	// shows winning squares (used for debugging)
	public void Test() {
		String s  = "\n-------------------------\n";
		s += "Debug: winning squares\n ";
		for (int i = 0; i < 7; i++)
		{
			s += (" " + (i + 1));
		}
		s += "\n+ - - - - - - - +\n";
		for(int i = 0; i < 6; i++)
		{
			s += "| ";
			for(int j = 0; j < 7; j++)
			{
				if(pSquares[j][i] == 0)
					s += "  ";
				else if(pSquares[j][i] == 1)
					s += "O ";
				else if(pSquares[j][i] == 2)
					s += "X ";
				else
					s += "B ";
			}
			s += "|\n";
		}
		s += "+ - - - - - - - +\n";
		s += "O is favored by:\n";
		s += "winning squares: " + (Rating() - (cBias / 1000.0)) + "\n";
		s += "center bias: " + cBias + "\n";
		s += "overall: " + Rating() + "\n";
		s += "-------------------------\n";
		System.out.println(s);
	}
}







