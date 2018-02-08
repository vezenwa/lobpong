import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class GamePaddle extends JPanel {

	protected static int paddleHeight = 15;
	protected static int paddleLength = 80;
	protected static int paddleLocationX; 
	protected static int paddleLocationY; 
	
	public void setLocation(int x, int y) {
		paddleLocationX = x;
		paddleLocationY = y;
	}

	public int getX() {
		return paddleLocationX;
	}

	public int getY() {
		return paddleLocationY;
	}
	
	protected int vX = 0;
	
	protected void setHorizSpeed(int v) {
		vX = v;
	}
	
	public void draw(Graphics g) {
		int x = paddleLocationX - paddleLength/2;
		int y = paddleLocationY; 
		g.setColor(Color.GREEN);
		g.fillRect(x, y, paddleLength, paddleHeight);
	}

	public void update() {
		paddleLocationX += vX;
		paddleLocationX = Math.min(paddleLocationX, GameSetup.CANVAS_WIDTH-paddleLength/2);
		paddleLocationX = Math.max(paddleLength/2, paddleLocationX);
	}

}
