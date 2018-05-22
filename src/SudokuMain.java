import javax.swing.JFrame;

public class SudokuMain {

	public static void main(String[] args) {
		JFrame window = new JFrame("Sudoku");
		
		window.setSize(1350, 1350);
		window.setLocation(1000, 200);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SudokuModel model = new SudokuModel();
		SudokuView view = new SudokuView(model);

		new SudokuController(view, model);
		
		window.setContentPane(view);
		window.setVisible(true);
		
	}
	
	/*
	 * TODO:
	 * 1. Add instructions to the start screen. (done, but update after adding hints)
	 * 1.25. Clicking a number highlights all the same numbers. (DONE)
	 * 1.5. Possibly add being able to move around board using arrow keys.	(DONE)
	 * 2. Restarting. 
	 * 3. Hints.
	 * 4. Generating own puzzles.
	 * 
	 */
	
}
