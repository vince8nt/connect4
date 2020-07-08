import java.util.Scanner;

public class ConnectFour
{
	
	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);
		Board b = new Board();
		int nextCol;
		int nextPiece = 1;
		int order = 0;
		while(order < 1 || order > 2) {
			System.out.print("Would you like to go first or second (1, 2): ");
			order = input.nextInt();
		}
		
		// main game loop
		while(!b.full() && !b.win())
		{
			System.out.println(b);
			nextCol = 0;
			
			// makes sure a valid column is chosen
			while(nextCol < 1 || nextCol > 7 || b.colFull(nextCol - 1))
			{
				
				if (order == nextPiece) {
					System.out.print("Your turn. what column would you like to drop into?: ");
					nextCol = input.nextInt();
				}
				else {
					nextCol = BestMove(b, 8, nextPiece) + 1;
				}
				
				if (nextCol == 0) {
					b.Test();
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
			if(nextPiece == order)
				System.out.println("You lost!");
			else
				System.out.println("You won!");
		}
		else
			System.out.println("It's a tie.");
			
		input.close();
	}

	static int BestMove(Board b, int depth, int nextPiece){
		double bestRating = 2;
		int bestCol = -1;
		if (nextPiece == 1) {
			bestRating = -2;
		}

		if (b.full() || b.win()) {
			throw new RuntimeException("no possible move");
		}
		for (int i = 0; i < 7; ++i) {
			if (!b.colFull(i)) {
				b.drop(nextPiece, i);
				double cur;
				if (nextPiece == 2)
					cur = PosRating(b, depth - 1, 1);
				else
					cur = PosRating(b, depth - 1, 2);
				if (nextPiece == 2) {
					if (cur < bestRating) {
						bestRating = cur;
						bestCol = i;
					}
					b.remove(i);
					if (bestRating == -1) {
						return bestCol;
					}
				}
				else {
					if (cur > bestRating) {
						bestRating = cur;
						bestCol = i;
					}
					b.remove(i);
					if (bestRating == 1) {
						return bestCol;
					}
				}
				
			}
		}
		return bestCol;
	}

	// 1 is a win for O, -1 is a win for X,
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
		return b.Rating();
	}
}


