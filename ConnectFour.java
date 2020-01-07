import java.util.Scanner;

public class ConnectFour
{
	
	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);
		Board b = new Board();
		int nextCol;
		int nextPiece = 1;
		
		while(!b.full())
		{
			System.out.println(b);
			nextCol = 0;
			while(nextCol < 1 || nextCol > 7 || b.colFull(nextCol - 1))
			{
				if(nextPiece == 1)
					System.out.print("O's turn. what column would you like to drop into: ");
				else
					System.out.print("X's turn. what column would you like to drop into: ");
				nextCol = input.nextInt();
			}
			b.drop(nextPiece, nextCol - 1);
			
			if(nextPiece == 1)
				nextPiece = 2;
			else
				nextPiece = 1;
		}
		System.out.println(b);
		input.close();
	}
}
