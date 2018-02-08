import java.awt.Color;
import java.awt.Graphics;
import java.util.TimerTask;
import java.util.Timer;


public class GameBall {
	protected static int ballSize = 10;
	protected static int ballLocationX;
	protected static int ballLocationY;
	protected int G = 10; // "gravity"
	protected static int lives;
	protected static int points;
	static Timer timer1;
	static int interval;
	
	public void setBLocation(int x, int y) {
	    ballLocationX = x;
		ballLocationY = y;
	}

	public int getX() {
		return ballLocationX;
	}

	public int getY() {
		return ballLocationY;
	}
	
	protected static int bX = 5;
	protected static int bY;
	
	protected void setbXSpeed(int v) {
		bX = v;
	}
	protected void setbYSpeed(int v) {
		bY = v;
	}
	
	public static void reset(){
		ballLocationX = GameSetup.CANVAS_WIDTH/2 - ballSize/2;
		ballLocationY = -ballSize/2;
		GamePaddle.paddleLocationX = GameSetup.CANVAS_WIDTH/2;
	}
	
	public void livesCheck(){
		System.out.print("Lives left: ");
		System.out.println(lives);
		if(lives == 0){
			GameSetup.timer.stop();
			System.out.println("You lose the round. Hit SPACEBAR to play round "+ GameSetup.round +" again. Be ready!");
			timer1.cancel();
			GameSetup.round -= 1;
		}
	}
	
	public static void clock(){
		timer1 = new Timer();
	    if(GameSetup.round == 1)
	    interval = 15;
	    else if(GameSetup.round == 2)
	    interval = 20;
	    else 
		interval = 30;
	    System.out.println(interval);
		timer1.scheduleAtFixedRate(new TimerTask() {

	        public void run() {
	        	System.out.println(setInterval());
	        }
	    }, 1000, 1000);
		
	}
	
	public static int setInterval() {
	    if (interval == 0){
	        timer1.cancel();
	        GameSetup.timer.stop();
	        System.out.println("You win the round. 5 points!");
	        points += 5;
	        if(GameSetup.round == 3){
	        	System.out.println("You win the game! Your points total is "+ points);
	        	System.out.println("Press SPACEBAR if you want to play again. Close the window if you want to quit.");
	        }
	        else{
	        	System.out.println("Hit SPACEBAR to play the next round. Be ready!");
	        }
	        reset();
	        bY = 0;
	    }
	    return --interval;
	}
	
	
	
	
	public void draw(Graphics g) {
		int x = ballLocationX - ballSize/2;
		int y = ballLocationY - ballSize/2;
		g.setColor(Color.BLACK);
		g.fillOval(x, y, ballSize, ballSize);
	}

	public void update() {
		
		ballLocationX += bX;
		bY += G;
		ballLocationY += bY;
		
		if(ballLocationX-(ballSize/2) < 0){
			ballLocationX = ballSize/2; 
			bX = -bX;
		}
		if(ballLocationX+ballSize/2 > GameSetup.CANVAS_WIDTH){
			ballLocationX = GameSetup.CANVAS_WIDTH-ballSize/2;
			bX = -bX;
		}
	
		if(ballLocationY-ballSize/2 < 0) {
			ballLocationY = ballSize/2;
			bY = -bY;
		}
		
		if(ballLocationY+ballSize/2 > GamePaddle.paddleLocationY && ballLocationX >= GamePaddle.paddleLocationX-(GamePaddle.paddleLength/2) && ballLocationX <= GamePaddle.paddleLocationX+(GamePaddle.paddleLength/2)){
			ballLocationY = GamePaddle.paddleLocationY-(ballSize/2);
			bY = -bY;
			points +=1;
		}
		
		if(ballLocationY-ballSize/2 > GameSetup.CANVAS_HEIGHT){
			reset();
			bY = 0;
			lives -= 1; 
			livesCheck();
		}
	
	}
}
