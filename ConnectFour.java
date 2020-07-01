import java.util.Scanner;

public class ConnectFour
{
	
	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);
		Board b = new Board();
		int nextCol;
		int nextPiece = 1;
		
		// main game loop
		while(!b.full() && !b.win())
		{
			System.out.println(b);
			nextCol = 0;
			
			// makes sure a valid column is chosen
			while(nextCol < 1 || nextCol > 7 || b.colFull(nextCol - 1))
			{
				if(nextPiece == 1) {
					System.out.print("Your turn. what column would you like to drop into?: ");
					nextCol = input.nextInt();
				}
				else {
					// System.out.print("X's turn. what column would you like to drop into?: ");
					nextCol = BestMove(b, 6) + 1;
				}
			}
			b.drop(nextPiece, nextCol - 1);
			
			// switch colors
			if(nextPiece == 1)
				nextPiece = 2;
			else
				nextPiece = 1;
		}
		System.out.println(b);
		
		// determine game outcome
		if(b.win())
		{
			if(nextPiece == 1)
				System.out.println("You lost!");
			else
				System.out.println("You won!");
		}
		else
			System.out.println("It's a tie.");
			
		input.close();
	}

	static int BestMove(Board b, int depth){
		double bestRating = 2;
		int bestCol = -1;

		if (b.full() || b.win()) {
			throw new RuntimeException("no possible move");
		}
		for (int i = 0; i < 7; ++i) {
			if (!b.colFull(i)) {
				b.drop(2, i);
				double cur = PosRating(b, depth - 1, 1);
				if (cur < bestRating) {
					bestRating = cur;
					bestCol = i;
				}
				b.remove(i);
				if (bestRating == -1) {
					return bestCol;
				}
			}
		}
		return bestCol;
	}

	// 1 is a win for you, -1 is a win for the computer,
	// in between is who is likely favored
	static double PosRating(Board b, int depth, int nextPiece) {
		double bestRating;
		if (nextPiece == 1)
			bestRating = -2;
		else
			bestRating = 2;

		if (b.win()) {
			if (nextPiece == 1)
				return -1;
			else
				return 1;
		}
		if (b.full()) {
			return 0;
		}
		if (depth > 0) {
			for (int i = 0; i < 7; ++i) {
				if (!b.colFull(i)) {
					double cur;
					b.drop(nextPiece, i);
					if (nextPiece == 1) {
						cur = PosRating(b, depth - 1, 2);
						if (cur > bestRating)
							bestRating = cur;
					}
					else {
						cur = PosRating(b, depth - 1, 1);
						if (cur < bestRating)
							bestRating = cur;
					}
					b.remove(i);
				}
			}
			return bestRating;
		}

		// non-recursive analysis
		return 0; // needs to be added to, assesses every position as equal.
	}
}


