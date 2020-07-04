
public class Board
{
	private int pieces[][];
	
	// constructor
	public Board()
	{
		pieces = new int[7][6];
		for(int i = 0; i < 7; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				pieces[i][j] = 0;
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
		return(this.pieces.equals(comp.pieces));
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
		int color, streak, j;
		
		// horizontal 4?
		for(int i = 0; i < 6; i++)
		{
			color = streak = j = 0;
			while(j < 7)
			{
				if(this.pieces[j][i] == 0)
					color = streak = 0;
				else if(this.pieces[j][i] == color)
					streak++;
				else
				{
					streak = 1;
					color = this.pieces[j][i];
				}
				if(streak == 4)
					return(true);
				j++;
			}
		}
		
		// vertical 4?
		for(int i = 0; i < 7; i++)
		{
			color = streak = j = 0;
			while(j < 6)
			{
				if(this.pieces[i][j] == 0)
					color = streak = 0;
				else if(this.pieces[i][j] == color)
					streak++;
				else
				{
					streak = 1;
					color = this.pieces[i][j];
				}
				if(streak == 4)
					return(true);
				j++;
			}
		}
		
		int x;
		int y;

		// diagonal (/) 4?
		for(int i = -2; i < 4; ++i) {
			color = streak = 0;
			if (i < 0) {
				x = 0;
				y = 0 - i;
			}
			else {
				x = i;
				y = 0;
			}
			while (x < 7 && y < 6) {
				if(pieces[x][y] == 0)
					color = streak = 0;
				else if(pieces[x][y] == color)
					streak++;
				else
				{
					streak = 1;
					color = pieces[x][y];
				}
				if(streak == 4)
					return(true);
				++x;
				++y;
			}
		}

		// diagonal (\) 4?
		for(int i = 3; i < 9; ++i) {
			color = streak = 0;
			if (i > 6) {
				x = 6;
				y = i - 6;
			}
			else {
				x = i;
				y = 0;
			}
			while (x > -1 && y < 6) {
				if(pieces[x][y] == 0)
					color = streak = 0;
				else if(pieces[x][y] == color)
					streak++;
				else
				{
					streak = 1;
					color = pieces[x][y];
				}
				if(streak == 4)
					return(true);
				--x;
				++y;
			}
		}
		
		return(false);
	}

	public double Rating() {
		return 0;
	}
}







