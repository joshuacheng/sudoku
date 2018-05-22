import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*

 * 
 * Currently SudokuMain is running code with this class instead of SudokuView.
 */

@SuppressWarnings("serial")
public class SudokuView extends JPanel {

	private SudokuModel model;
	private SudokuController controller;
	
	private int highlightedX, highlightedY;
	private int boxX, boxY;
	private boolean highlightOn;
	
	private boolean[][] startingNums;
	
	private Font bold, plain;
	
	private JLabel message, instructions;
	private JButton easy, medium, hard;
	private CardLayout cardLayout;

	public SudokuView(SudokuModel model) {
		this.model = model;
		this.setBackground(Color.WHITE);
		
		highlightOn = false;
		startingNums = this.model.getStartingNums();
		controller = new SudokuController(this, model);		
		bold = new Font("arial",Font.BOLD,60);
		plain = new Font("timesroman",Font.PLAIN,60);
		
		JPanel startScreen = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				setBackground(Color.WHITE);
				Graphics2D g2 = (Graphics2D) g;
				g2.setFont(new Font("arial", Font.PLAIN, 42));
				g.drawString("Welcome to Sudoku! Select a difficulty", 300, 300);
			}
		};
		startScreen.setLayout(null);
		
		instructions = new JLabel("Click boxes to type numbers.\n Press"
									+" Backspace to delete numbers.");
		instructions.setFont(new Font("arial", Font.BOLD, 30));
		instructions.setBounds(200, 600, 1000, 200);
		startScreen.add(instructions);
		
		GamePanel gamePanel = new GamePanel();
		
		message = new JLabel("");
		message.setFont(bold);
		gamePanel.add(message);
		
		cardLayout = new CardLayout();
		
		easy = new JButton("Easy");
		easy.addActionListener(controller);
		easy.setBounds(300, 500, 200, 100);
		easy.setFont(new Font("arial", Font.PLAIN, 40));
		startScreen.add(easy);
		
		medium = new JButton("Medium");
		medium.addActionListener(controller);
		medium.setBounds(550,500,200,100);
		medium.setFont(new Font("arial", Font.PLAIN, 40));
		startScreen.add(medium);
		
		hard = new JButton("Hard");
		hard.addActionListener(controller);
		hard.setBounds(800, 500, 200, 100);
		hard.setFont(new Font("arial", Font.PLAIN, 40));
		startScreen.add(hard);
			
		this.setLayout(cardLayout);
		this.add(startScreen, "startScreen");
		this.add(gamePanel, "gamePanel");
		cardLayout.show(this, "startScreen");		
	}
	

	public void showGameBoard() {
		cardLayout.show(this, "gamePanel");
	}
	
	/*
	 * Used for restarting.
	 * TODO: Finish implementing and check for possible effects this may have
	 * on other parts of the project.
	 */
	public void showStartScreen() {
		cardLayout.show(this, "startScreen");
	}
	
	public void setHighlight(boolean bool) {
		highlightOn = bool;
	}
	
	public boolean getHighlightOn() {
		return highlightOn;
	}
	
	public int getHighlightedX() {
		return highlightedX;
	}

	public void setHighlightedX(int highlightedX) {
		this.highlightedX = highlightedX;
	}

	public int getHighlightedY() {
		return highlightedY;
	}

	public void setHighlightedY(int highlightedY) {
		this.highlightedY = highlightedY;
	}
	
	public JLabel getMessage() {
		return message;
	}
	
	public void setMessage(String str) {
		message.setText(str);
	}
	
	public void setBoxX(int num) {
		boxX = num;
	}
	
	public void setBoxY(int num) {
		boxY = num;
	}
	
	public CardLayout getCardLayout() {
		return cardLayout;
	}
	
	public int numberInHighlightedSquare() {
		return model.getShownNums()[controller.getBoxY()][controller.getBoxX()];
	}
	
	private class GamePanel extends JPanel {


		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			drawSudokuLines(g);
			if (highlightOn) {
				int rectX = (getWidth() - 900)/2;
				int rectY = (getHeight() - 900)/2;
				highlightedX = (rectX + boxX * 100 + 1);
				highlightedY = (rectY + boxY * 100 + 1);
				
				highlightSquare(g);
				highlightCopies(g);
			}
			drawNums(g);
			
			
		}
		
		/*
		 * Draws the sudoku lines. 
		 */
		private void drawSudokuLines(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			int rectX = (getWidth() - 900)/2;
			int rectY = (getHeight() - 900)/2;
			
			
			g.drawRect(rectX, rectY, 900, 900);
//			System.out.println(getHeight() + " " + getWidth());
			for (int i = 1; i < 9; i++) {
				if (i % 3 == 0) {
					g2.setStroke(new BasicStroke(3));
				} else {
					g2.setStroke(new BasicStroke(1));
				}
				g2.drawLine(rectX, rectY + 100 * i, rectX + 900, rectY + 100 * i);
				g2.drawLine(rectX + 100 * i, rectY, rectX + 100 * i, rectY + 900);

			}
			
		}
		
		/*
		 * Draws the shown numbers. 
		 */
		private void drawNums(Graphics g) {
			g.setColor(Color.BLACK);
			// rectX and rectY are the top left corner of my sudoku square.
			int rectX = (getWidth() - 900)/2;
			int rectY = (getHeight() - 900)/2;
			
			Graphics2D g2 = (Graphics2D)g;
			int[][] shownNums = model.getShownNums();
			
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (shownNums[i][j] != 0) {
						if (startingNums[i][j]) {
							g.setFont(bold);
						} else {
							g.setFont(plain);
						}
						String msg = new Integer(shownNums[i][j]).toString();
						g2.drawString(msg, rectX + 100 * j + 35, rectY + 100 * i + 70);
					}
				}
			}
			
			
			
			
		}
		
		public void highlightSquare(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g.setColor(Color.CYAN);
			g.fillRect(highlightedX, highlightedY, 99, 99);
			
		}
		
		/*
		 * This method needs to be differentiated from highlightSquare because 
		 * highlighted numbers are changed when pressing a number.
		 * They need to be a different kind of "highlighted", one that does not allow changing 
		 * of value. All the sames of a number should not be changed when the user 
		 * only wants to change one.
		 * 
		 */
		public void highlightCopies(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g.setColor(Color.GREEN);
			
			boolean[][] sames = model.returnAllSameNumbers(numberInHighlightedSquare());
			
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (sames[i][j] && !(i == controller.getBoxY() && j == controller.getBoxX())) {
						int rectX = (getWidth() - 900)/2;
						int rectY = (getHeight() - 900)/2;
						int tempHighlightX = (rectX + j * 100 + 1);
						int tempHighlightY = (rectY + i * 100 + 1);
						
						g.fillRect(tempHighlightX, tempHighlightY, 99, 99);
					}	
				}
			}
		}
	}
}
