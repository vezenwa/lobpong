import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameSetup extends JFrame {
	
	protected static final String FRAME_TITLE = "Lob Pong";
	protected static final int TIMER_DELAY = 100; // ms
	protected static final int CANVAS_WIDTH = 500;
	protected static final int CANVAS_HEIGHT = 500;
	
	protected static int round = 0;
	static boolean spacePressed = false;
	
	public GameSetup() {
		initGraphics();
		initTimer();
		initHandlers();
		initPaddle();
		initBall();
	}
	
	protected GraphicsCanvas canvas;

	protected void initGraphics() {
		setTitle(FRAME_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setLayout(new BorderLayout());
		canvas = new GraphicsCanvas();
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		add(canvas, BorderLayout.CENTER);
		pack();
	}
	
	protected static Timer timer;

	protected void initTimer() {
		timer = new Timer(TIMER_DELAY, new TimerHandler());
	}

	protected void initHandlers() {
		canvas.addKeyListener(new KeyHandler());
	}
	
	public void start() {
		setVisible(true);
	}
	
	GamePaddle paddle = new GamePaddle();
	
	protected void initPaddle() {
		paddle = new GamePaddle();
		int x = CANVAS_WIDTH/2;
		int y = 7*(CANVAS_HEIGHT/8);
		paddle.setLocation(x, y);
	}
	
	GameBall ball = new GameBall();
	
	protected void initBall() {
		ball = new GameBall();
		int x = (CANVAS_WIDTH/2);
		int y = 0;
		ball.setBLocation(x,y);
	}

	protected class GraphicsCanvas extends JPanel {
		public GraphicsCanvas() {
		    setFocusable(true);
		}
		@Override
		public void paintComponent(Graphics g) {
			paddle.draw(g);
			ball.draw(g);
		}
	}

	
	protected class TimerHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ball.update();
			canvas.repaint();
			paddle.update();		
			canvas.repaint();
		}
	}
	
	protected class KeyHandler implements KeyListener {
		boolean leftPressed = false;
		boolean rightPressed = false;
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				rightPressed = true;
				startMovingRight();
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = true;
				startMovingLeft();
				break;
			case KeyEvent.VK_SPACE:
				spacePressed = true;
				round();
				break;
			}
		}
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				rightPressed = false;
				if (leftPressed) {
					startMovingLeft();
				} else {
					stopMoving();
				}
				break;
			case KeyEvent.VK_LEFT:
				leftPressed = false;
				if (rightPressed) {
					startMovingRight();
				} else {
					stopMoving();
				}
				break;
			case KeyEvent.VK_SPACE:
				spacePressed = false;
				break;
			}
				
		}
		public void keyTyped(KeyEvent e) {
		}
	}
	
	

	protected void startMovingRight() {
		paddle.setHorizSpeed(+10);
	}

	protected void startMovingLeft() {
		paddle.setHorizSpeed(-10);
	}

	protected void stopMoving() {
		paddle.setHorizSpeed(0);
	}
	
	public static void round(){
		round +=1;
		if(round > 3){
			round = 1;
		}
		System.out.println("Round: "+ round);
		GameBall.lives = 3;
		timer.start();
		GameBall.clock();
		
	}
	
	
	public static void main(String[] args) {
		new GameSetup().start();
		System.out.println("Hit SPACEBAR to start.");
	}

}
