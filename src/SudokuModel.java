import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


//   WORKING SUDOKUS
//   1.
//	{{2,9,5,7,4,3,8,6,1}, 
//	 {4,3,1,8,6,5,9,2,7},
//	 {8,7,6,1,9,2,5,4,3},
//	 {3,8,7,4,5,9,2,1,6},
//	 {6,1,2,3,8,7,4,9,5},
//	 {5,4,9,2,1,6,7,3,8},
//	 {7,6,3,5,2,4,1,8,9},
//	 {9,2,8,6,7,1,3,5,4},
//	 {1,5,4,9,3,8,6,7,2}};

//	 1. Unsolved
//	 {{2,9,5,7,0,0,8,6,0}, 
//	 {0,3,1,8,6,5,0,2,0},
//	 {8,0,6,0,0,0,0,0,0},
//	 {0,0,7,0,5,0,0,0,6},
//	 {0,0,0,3,8,7,0,0,0},
//	 {5,0,0,0,1,6,7,0,0},
//	 {0,0,0,5,0,0,1,0,9},
//	 {0,2,0,6,0,0,3,5,0},
//	 {0,5,4,0,0,8,6,7,2}};

public class SudokuModel {

	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	
	private int[][] sudokuNums;
	
	/*
	 * The shownNums are the numbers that the user
	 * can see. Anything that they cannot see will be
	 * represented as a 0. This is also what they will
	 * be modifying with their guesses.
	 */
	private int[][] shownNums;
	
	/*
	 * Array indicating which numbers are the "starting numbers", 
	 * which means they are bolded and cannot be changed.
	 */
	private boolean[][] startingNums;
	
	private File selectedDifficulty;
	
	
	public SudokuModel() {
		
//		TODO: remove this once difficulty setting is working
		sudokuNums = new int[9][9];
		shownNums = new int[9][9];
								 
		
//		TODO: PUT THIS IN ANOTHER METHOD TO SYNC WITH SETTING DIFFICULTY		 
		startingNums = new boolean[9][9];
		
		
		
		
		
//		generateSudoku();
		
	}
	
	
	
	/*
	 * CURRENTLY NO FUNCTIONALITY b/c 
	 * I am grabbing puzzles from my text files instead.
	 */
	public void generateSudoku() {
		
	}
	
	
	/*
	 * Says if the board is in a legal state,
	 * i.e. solved 
	 * TODO: make this check an unsolved legal state too
	 */
	public boolean boardIsLegal() {
		int[] checks;
		
		// Checks if all the rows are 1-9. 
		for (int i = 0; i < 9; i++) {
			checks = new int[9];
			for (int j = 0; j < 9; j++) {
				try {
					checks[shownNums[i][j] - 1]++;
				} catch (IndexOutOfBoundsException e) {
					System.out.println("An element was not 1-9!");
					return false;
				}
			}
			if (!rowOrColOrSquareIsLegal(checks)) {
				System.out.println("failed at rows " + i);
				return false;
			}
		}
		
		// Checks if all the columns are 1-9. 
		for (int i = 0; i < 9; i++) {
			checks = new int[9];
			for (int j = 0; j < 9; j++) {
				try {
					checks[shownNums[j][i] - 1]++;
				} catch (IndexOutOfBoundsException e) {
					System.out.println("An element was not 1-9!");
					return false;
				}
			}
			if (!rowOrColOrSquareIsLegal(checks)) {
				System.out.println("failed at cols");
				return false;
			}
		}

		// Checks if all the squares are legal
		// messy
		for (int squareX = 0; squareX < 3; squareX++) {
			for (int squareY = 0; squareY < 3; squareY++) {
				checks = new int[9];
				for (int i = 3 * squareX; i < 3 * squareX + 3; i++) {
					for (int j = 3 * squareY; j < 3 * squareY + 3; j++) {
						try {
							checks[shownNums[i][j] - 1]++;
						} catch (IndexOutOfBoundsException e) {
							System.out.println("An element was not 1-9!");
							return false;
						}
					}
				}
				if (!rowOrColOrSquareIsLegal(checks)) {
					System.out.println("failed at regions");
					return false;
				}
			}
		}
	
		
		return true;
	}
	
	/*
	 * Helper method for boardIsLegal().
	 * 
	 * In order for a row, col, or square to be legal:
	 * All elements must be 1-9 
	 * ^ (This is checked by method isLegal()) 
	 * each element may only appear once.
	 */
	private boolean rowOrColOrSquareIsLegal(int[] arr) {
		int[] ones = new int[] {1,1,1,1,1,1,1,1,1};
		return (Arrays.equals(arr, ones));
	}
	
	public boolean sudokuComplete() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (shownNums[i][j] == 0)
					return false;
			}
		}
		
		return true;
	}

	/*
	 * Precondition: 
	 *  - access method for the Controller when the user guesses a number.
	 *  - row and col are within the bounds
	 *  - 0 is allowed because that is considered no number.
	 */
	public void guessNum(int row, int col, int guess) {
		if (guess > 9 || guess < 0)
			return;
		shownNums[row][col] = guess;
		
	}
	
	/*
	 * This method returns the positions of where num appears in 
	 * the 2D array. It uses a boolean array, where true means same number
	 * and false means not. Somewhat inefficient, could make a coordinate class and return
	 * array of coordinates. Oh well.
	 */
	public boolean[][] returnAllSameNumbers(int num) {
		boolean[][] sames = new boolean[9][9];
		if (num == 0) {
			return sames;
		}
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (num == shownNums[i][j])
					sames[i][j] = true;
			}
		}
		
//		for (int i = 0; i < 9; i++) {
//			System.out.println(Arrays.toString(sames[i]));
//		}
		
		return sames;
	}
	
	
	
	public int[][] getShownNums() {
		return shownNums;
	}
	
	public boolean[][] getStartingNums() {
		return startingNums;
	}
	
	public void setDifficultyAndBoard(int diff) {
		switch (diff) {
			case EASY:
				selectedDifficulty = new File("Easy");
				break;
			case MEDIUM:
				selectedDifficulty = new File("Medium");
				break;			
			default:
				selectedDifficulty = new File("Hard");
		}
		
		setShownAndSudokuNums();
		setStartingNums();
	}
	
	/*
	 * This method reads the selectedDifficulty and
	 * sets sudokuNums and shownNums accordingly.
	 */
	private void setShownAndSudokuNums() {
		FileReader reader = null;
		try {
			reader = new FileReader(selectedDifficulty);
		} catch (FileNotFoundException e) {}
		
		BufferedReader bReader = new BufferedReader(reader);
		
		int puzzleToStart = (int)(Math.random()*(getLinesOfFile(selectedDifficulty) / 20));
		int lineToStart = puzzleToStart * 20 + 1;
		
//		System.out.println("Puzzle: " + puzzleToStart + " Line: " + lineToStart);
		
		/*
		 * This gets the bReader up to the line you start reading.
		 */
		for (int i = 0; i < lineToStart - 1; i++) {
			try {
				bReader.readLine();
			} catch (IOException e) {}
		}
		
		/*
		 * Actually reads the sudoku puzzle and
		 * fills in sudokuNums.
		 */
		
		for (int i = 0; i < 9; i++) {
			String line = "";
			try {
				line = bReader.readLine();
			} catch (IOException e) {}
			
			for (int j = 0; j < 9; j++) {
				sudokuNums[i][j] = new Integer(line.substring(j, j+1));
			}
		}

		try {
			bReader.readLine();
		} catch (IOException e) {}
		
		for (int k = 0; k < 9; k++) {
			String line = "";
			try {
				line = bReader.readLine();
			} catch (IOException e) {}
			
			for (int l = 0; l < 9; l++) {
				shownNums[k][l] = Integer.valueOf(line.substring(l, l + 1));
			}
		}
	}
	
	
	/*
	 *  This helper method finds number of lines in the selected difficulty file
	 *  to help randomly select one puzzle.
	 */
	private int getLinesOfFile(File f) {
		int count = 1;
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e) {}

		while (scanner.hasNextLine()) {
			count++;
			scanner.nextLine();
		}
		return count;
	}
	
	private void setStartingNums() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (shownNums[i][j] != 0) {
					startingNums[i][j] = true;
				}
			}
		}
	}
}
