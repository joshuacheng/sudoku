/*
package oldWay;

import java.io.*;
import java.util.Scanner;

public class SudokuModelFileScanner {

//	private SudokuModel;
//
//	private File easy, medium, hard;
//	private File selectedDifficulty;
//
//	private Scanner scanner;


	public SudokuModelFileScanner() {
		FileReader reader = null;
//		scanner = null;

//		scanner = new Scanner()
//		easy = new File("Easy");
//		medium = new File("Medium");
//		hard = new File("Hard");


	}

	public static void main(String[] args) {
		File f = new File("Easy");
		System.out.println(getLinesOfFile(f));

//		FileReader reader = null;
//		File medium = new File("medium");
//		Scanner scanner = null;
//		try {
//			scanner = new Scanner(medium);
//		} catch (FileNotFoundException e1) {}
//		BufferedReader bReader;
//
//		try {
//			reader = new FileReader(medium);
//		} catch (FileNotFoundException e) {}
//
//
//		bReader = new BufferedReader(reader);
//		while (scanner.hasNextLine()) {
//
//		}
//
//
//		for (int i = 0; i < 9; i++) {
//			try {
//				int line = new Integer(bReader.readLine());
//				System.out.println(line);
//			} catch (IOException e) {}
//
//		}
//

	}


	public File getEasy() {
		return easy;
	}

	public File getMedium() {
		return medium;
	}

	public File getHard() {
		return hard;
	}

	private static int getLinesOfFile(File f) {
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

}
*/