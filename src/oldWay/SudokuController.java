package oldWay;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SudokuController implements MouseListener, KeyListener, ActionListener {

	
	private SudokuView view;
	private SudokuModel model;
	
	/*
	 * boxX and boxY are the x and y positions of a certain box
	 * corresponding to the model's 2D array.
	 * Ex: boxY = 2, boxX = 5 would refer to array[2][5].
	 * The reason they are "switched" is because 
	 * 2D arrays go down then right, not right then down.
	 * 2confusing4me
	 */
	private int boxX, boxY;
	
	
	public SudokuController(SudokuView view2, SudokuModel model) {
		this.view = view2;
		this.model = model;
		this.view.addMouseListener(this);
		this.view.addKeyListener(this);
		
	}
	@Override
	public void mouseClicked(MouseEvent ev) {
		
		/*
		 * rectX and rectY are the sudoku box's x and y regardless
		 * of how big the window is.
		 */
		int rectX = (view.getWidth() - 900)/2;
		int rectY = (view.getHeight() - 900)/2;
		view.requestFocusInWindow();
		
		boolean inX = ev.getX() > rectX && ev.getX() < rectX + 900;
		boolean inY = ev.getY() > rectY && ev.getY() < rectY + 900;
	
		// If the user clicks inside the sudoku square.
		if (inX && inY) {
			boxX = (ev.getX() - rectX) / 100;
			boxY = (ev.getY() - rectY) / 100;	
//			System.out.println("BoxY: " + boxY + " BoxX: " + boxX);

			view.setBoxX(boxX);
			view.setBoxY(boxY);
		}

		view.setHighlight(true);
		
		// This code tells the view where to draw the highlighted box. 
		// Note to self:
		// To convert boxX to HighlightedX, multiply by 100 and add rectX. 
		// Update: commented out b/c apparently overridden right after inside paintComponent()
//		view.setHighlightedX(rectX + boxX * 100);
//		view.setHighlightedY(rectY + boxY * 100);
			
		
		view.repaint();
		
	}


	@Override
	public void keyPressed(KeyEvent ev) {
		int key = ev.getKeyCode();
		
		view.setMessage("");
		
		if (view.getHighlightOn()) {
			switch (key) {
				case KeyEvent.VK_UP:
					if (boxY - 1 >= 0) {
						view.setBoxY(boxY - 1);
						boxY--;
					}
					break;
				case KeyEvent.VK_DOWN:
					if (boxY + 1 < 9) {
						view.setBoxY(boxY + 1);
						boxY++;
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (boxX + 1 < 9) {
						view.setBoxX(boxX + 1);
						boxX++;
					}
					break;
				case KeyEvent.VK_LEFT:
					if (boxX - 1 >= 0) {
						view.setBoxX(boxX - 1);
						boxX--;
					}
					break;
				default:
					if (!model.getStartingNums()[boxY][boxX]) {
						switch (key) {
						case KeyEvent.VK_1: 
							model.guessNum(boxY, boxX, 1);
							break;
						case KeyEvent.VK_2:
							model.guessNum(boxY, boxX, 2);
							break;
						case KeyEvent.VK_3:
							model.guessNum(boxY, boxX, 3);
							break;
						case KeyEvent.VK_4:
							model.guessNum(boxY, boxX, 4);
							break;
						case KeyEvent.VK_5:
							model.guessNum(boxY, boxX, 5);
							break;
						case KeyEvent.VK_6:
							model.guessNum(boxY, boxX, 6);
							break;
						case KeyEvent.VK_7:
							model.guessNum(boxY, boxX, 7);
							break;
						case KeyEvent.VK_8:
							model.guessNum(boxY, boxX, 8);
							break;
						case KeyEvent.VK_9:
							model.guessNum(boxY, boxX, 9);
							break;
						case KeyEvent.VK_BACK_SPACE:
							System.out.println("getting here");
							model.guessNum(boxY, boxX, 0);
							break;
						default:
							view.setMessage("Not valid input");
							System.out.println("not valid!");
						
						}
					}
			
			
			}
			
		}
		
		
		// TODO: IF PLAYER WINS, GIVE OPTION TO RESTART.
		if (model.sudokuComplete()) {
			if (model.boardIsLegal())
				view.setMessage("You win!");
			else
				view.setMessage("Not everything is correct...");
		}
		
		view.repaint();		
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		String command = ev.getActionCommand();
		
		if (command.equals("Easy")) {
			model.setDifficultyAndBoard(SudokuModel.EASY);
			view.showGameBoard();
		} else if (command.equals("Medium")) {
			model.setDifficultyAndBoard(SudokuModel.MEDIUM);
			view.showGameBoard();
		} else if (command.equals("Hard")) {
			model.setDifficultyAndBoard(SudokuModel.HARD);
			view.showGameBoard();
		} else {
			System.out.println("how did this happen");
		}
		
	}
	
	public int getBoxX() {
		return boxX;
	}
	
	public int getBoxY() {
		return boxY;
	}

	
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void keyReleased(KeyEvent ev) {}
	public void keyTyped(KeyEvent ev) {}


}
