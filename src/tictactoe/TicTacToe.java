package tictactoe;

import java.util.Scanner;
import java.util.ArrayList;

public class TicTacToe {
	
	//Variables to be tracked throughout the game
	static String[][] board;
	static ArrayList<String> choicesAvailable;
	
	static int size;
	static String player1;
	static String player2;
	static int turns;
	
	static boolean win;
	static boolean draw;
	
	public static void main(String[] args) {

		//Call the start game function
		startGame();
	}
	
	//Function: Start Game
	public static void startGame() {
		initGame(); //Initialize player names and board size

		showBoard(); //prints out the board
		
		do {
			//Function is called player by player
			playerTurn(player1); //Calls the playerTurn function 
			showBoard();
			playerTurn(player2);
			showBoard();
		} while(!win && !draw); //Loop till win or draw
	}
	
	//Function: Restart Game
	public static void restartGame() {
		//Asks user whether they want to restart
		//Y - starts the game again
		//N - ends the game
		
		Scanner restartScanner = new Scanner(System.in);
		String input = "";
		System.out.println("\nWould you like to restart game? Enter Y or N");
		
		try {
			input = restartScanner.nextLine();
		}
		catch(Exception e) {
			System.err.println(e);
		}
		
		if(input.equalsIgnoreCase("Y")) {
			startGame();
		}
		else {
			System.exit(0);
		}
	}
	
	//Function: Players choose which box to put "x" or "o"
	public static void playerTurn(String player) {
		
		Scanner turnScanner = new Scanner(System.in);
		String input = "";
		String choice = "";
		
		if(player.equals(player1)) { //check if its player 1 or player 2
			input = "x";
		}
		else {
			input = "o";
		}
		
		do {
			try {
				System.out.println("\n" + player + ", choose a box to place an '"+ input + "' into : ");
				choice = turnScanner.nextLine();
				
				if(choicesAvailable.contains(choice)) { //If the list has the choice selected
					choicesAvailable.remove(choice); //Removes the choice selected from the list of choices available
					turns++; //Counter for turns
				}
				else { //if choice not within the list, ask for user input again
					System.out.println("\nInvalid choice." + player + ", choose a box to place an '"+ input + "' into : ");
					choice = turnScanner.nextLine();
				}
				
				makeMove(choice, input);		
			}
			
			catch(Exception e) {
				System.err.println(e);
			}	
			
		}while(!isValidString(choice)); //Loop to check if input is not empty
			
	}
	
	//Function: Makes the move and checks if win/draw
	public static void makeMove(String choice, String input) {
		
		int r = 0;
		int c = 0;
		
		for(int row = 0; row < size; row++) {
			for(int cln = 0; cln < board[row].length; cln++) {
				if(board[row][cln].equals(choice)) {
					board[row][cln] = input;
					r = row;
					c = cln;
				}
			}
		}	
		
		//If the list of choices is empty, its a draw
		if(choicesAvailable.isEmpty()) {
			draw = true;
			showBoard();
			System.out.println("The game is a draw!");
			restartGame();
		}
		
		//Checks if there is a 3 in a row, horizontally/vertically/diagonally
		if(checkR(r,c,input) || checkC(r,c,input) || checkD(r,c,input) || checkBD(r,c,input)) {
			
			win = true;
			showBoard();
			
			if(input == "x") {
				System.out.println("Congratulations " + player1 + "! You have won.");
			}
			else {
				System.out.println("Congratulations " + player2 + "! You have won.");
			}
			
			restartGame();
		}
	}
	
	//Function: Checking rows of "x" or "o" horizontally
	public static boolean checkR(int row, int cln, String input) {

		for(int i = 0; i<size-2; i++) {
			if(board[row][i] == board[row][i+1]) { 
				if(board[row][i+1] == board[row][i+2]) { 
					return true;
				}
			}
		}
		
		return false;
	}
	
	//Function: Checking rows of "x" or "o" vertically
	public static boolean checkC(int row, int cln, String input) {
		
		for(int i = 0; i<size-2; i++) {
			if(board[i][cln] == board[i+1][cln]) { 
				if(board[i+1][cln] == board[i+2][cln]) { 
					return true;
				}
			}
		}
		
		return false;
	}
	
	//Function: Checking rows of "x" or "o" diagonally, i.e. Front diagonal
	public static boolean checkD(int row, int cln, String input) {
		
		for(int i=0; i<size-2;i++) {
			if(board[i][i] == board[i+1][i+1]) {
				if(board[i+1][i+1] == board[i+2][i+2]) {
					return true;
				}
			}
		}
		return false;
	}
	
	//Function: Checking rows of "x" or "o" diagonally, i.e. Back diagonal
	public static boolean checkBD(int row, int cln, String input) {
		
		for(int i = 0; i<size-1; i++) { // i -> 012
			for(int j = size-1; j>0; j--) {
				if( board[i][j] == board[i+1][j-1]) {
					if(board[i+1][j-1] == board[i+2][j-2]) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	//Function: Set initial values of the game
	public static void initGame() {
		
		Scanner initScanner = new Scanner(System.in);
		
		win = false;
		draw = false;
		
		player1 = requestName(1);
		player2 = requestName(2);
		
		size = requestSize();
		
		board = new String[size][size];
		
		choicesAvailable = new ArrayList<String>();
		turns = 0;
		
		int num = 1;
		
		for(int row = 0; row < size; row++) {
			for(int cln = 0; cln < board[row].length; cln++) {
				board[row][cln] = ""+num; //Populate 2D array with choices
				num++;
			}
		}
		
		for(int i = 0; i<size*size;i++) {
			choicesAvailable.add(""+(i+1)); //Populate list with available choices
		}
	}
	
	//Function: Ask user for their name
	public static String requestName(int n) {
		
		Scanner nameScanner = new Scanner(System.in);
		String name = "";
		
		do {
			try {
				System.out.println("Enter name for Player"+ n + ": ");
				name = nameScanner.nextLine();	
			}
			catch(Exception e) {
				System.err.println(e);
			}
		}while(!isValidString(name)); //Loop to check if input is empty
			
		return name;
	}
	
	//Function: Check if String input is empty
	public static boolean isValidString(String s) {
		if(!s.equals("")) {
			return true;
		}
		return false;
	}
	
	//Function: Ask user for the board size they would prefer
	public static int requestSize() {
		
		Scanner sizeScanner = new Scanner(System.in);
		String input = "";
		int n = 0;

		do {
			try {
				System.out.println("Enter board size: ");
				input = sizeScanner.nextLine();
				
				n = Integer.parseInt(input);
			}
			catch(Exception e) {
				System.out.println("Invalid Input. Enter board size: ");
				
				input = sizeScanner.nextLine();
				
				n = Integer.parseInt(input);
			}
		}while(!isValidString(input)); //Loop to check if input is empty
		
		return n;
	}
	
	//Function: Prints out the board
	public static void showBoard() {
		
		for(int row = 0; row < size; row++) {
			for(int cln = 0; cln < board[row].length; cln++) {
				
				if(cln==0 || cln==size) {
					System.out.print(board[row][cln]);
				}
				
				else {
					System.out.print(" | ");
					System.out.print(board[row][cln]);
					
				}
				
				if(cln==size-1 && cln != row) {
					System.out.println("\n---------");
				}
			}
		}
		
		System.out.println("\n");
	}
}

