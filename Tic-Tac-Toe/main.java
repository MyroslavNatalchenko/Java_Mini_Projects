import java.util.Scanner;

class Main {

	public static void main(String[] args) {
		
		//tic tac toe board
		char[][] game_board = new char[3][3];
  
    //variables to keep track of players
		char c = '-';
		boolean p1Turn = true; 

		//Initialize our board with '-'
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				game_board[i][j] = '-';
			}
		}
		
		while(true) {
			displayBoard(game_board);
			c = checkTurn(c, p1Turn);
			move(c,game_board);

			char temp = winner(game_board);
			if(temp == 'x') {
				displayBoard(game_board);
				System.out.println("Player1 (x) has won!");
				return;
			} 
			else if(temp == 'o') {
				displayBoard(game_board);
				System.out.println("Player2 (o) has won!");
				return;
			} 
			else {
				//If it's a tie or not
				if(tie(game_board)) {
				System.out.println("It's a tie!");
					return;
				} 
				else {
					//If player1 is true, make it false, and vice versa; this way, the players alternate each turn
					p1Turn = !p1Turn;
				}
			}
		}
    }

	//Method to check for a tie
	public static boolean tie(char[][] game_board) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(game_board[i][j] == '-') {
					return false;
				}
			}
		}
		return true;
	}

	//Method to implement winning conditions
	public static char winner(char[][] game_board) {
		
		//Check all the rows
		for(int i = 0; i < 3; i++) {
			if(game_board[i][0] == game_board[i][1] && game_board[i][1] == game_board[i][2] && game_board[i][0] != ' ') {
				return game_board[i][0];
			}
		}

		//Check each column
		for(int j = 0; j < 3; j++) {
			if(game_board[0][j] == game_board[1][j] && game_board[1][j] == game_board[2][j] && game_board[0][j] != ' ') {
				return game_board[0][j];
			}
		}

		//Check the diagonals
		if(game_board[0][0] == game_board[1][1] && game_board[1][1] == game_board[2][2] && game_board[0][0] != ' ') {
			return game_board[0][0];
		}
		if(game_board[2][0] == game_board[1][1] && game_board[1][1] ==  game_board[0][2] && game_board[2][0] != ' ') {
			return game_board[2][0];
		}

		//Nobody has not won yet
		return ' ';

	}

    //Play the move
	public static void move(char c, char[][] game_board) {

		//Indexes that correspond to a position on our board
		int row = 0;
		int col = 0;

		while(true) {
				
			Scanner in = new Scanner(System.in);
			System.out.print("Enter a row number (0, 1, or 2): ");
			row = in.nextInt();
			System.out.print("Enter a column number (0, 1, or 2): ");
			col = in.nextInt();

			//Check if the row and col are 0, 1, or 2
			if(row < 0 || col < 0 || row > 2 || col > 2) {
				System.out.println("This position is off the bounds of the board! Try again.");
			}		
			//Check if the position on the board the user entered is empty (has a -) or not
			else if(game_board[row][col] != '-') {
				System.out.println("Someone has already made a move at this position! Try again.");
			}
			//Otherwise, the position is valid so break out of the while loop
			else {
				game_board[row][col] = c;	
				return;
			}
			
		}	

	}

	//Method to display the Tic-Tac-Toe board
	public static void displayBoard(char[][] game_board) {
		System.out.println("\nTic Tac Toe Board");
		
		//Outer loop will follow the row-major order
		for(int i = 0; i < 3; i++) {
			//Inner loop will follow the column-major order
			for(int j = 0; j < 3; j++) {
				System.out.print(game_board[i][j]+" ");
			}
			//This will make a new line after every row so that each row is printed on a separate line
			System.out.println();
		}
	}

    //Method to check whose turn it is
	public static char checkTurn(char c, boolean p1Turn){

		if(p1Turn) {
			System.out.println("Player1's Turn (x):");
			c = 'x';
		} 
		else {
			System.out.println("Player2's Turn (o):");
			c = 'o';
		}
	    return c;
	}
}
