
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.File;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.*;
import java.lang.Math;
import sun.audio.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class MazePanel extends JPanel {
	private final int MAX_ROW=36, BLOCK_SIZE = 18, DELAY = 10;
	private Player player;
	private Timer t;
	private Random rand = new Random();
	private double time = 0.0, maxTime = 90.0, lastTime = 0;
	private boolean gameOver = false;
	private int score = 0, totalGems = 6, wallIndex = 1, sWallIndex = 1;
	private BufferedImage floorImage, diamondImage, exitImage, doorClosedImage, doorOpenImage, keyImage;
	private BufferedImage [] wallImages = new BufferedImage[4];
	private List<Block> collectibles = new ArrayList<Block> ();
	String message;

   	private int[][] maze=
	           {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},  				 
	    	    {0,0,1,1,1,0,0,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1},  				 
	    		{1,0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,0,0,1,0,0,0,0,0,1,1,1,0,0,1,0,1},			 	 
	    		{1,1,0,1,1,0,1,1,1,1,0,0,0,0,1,1,1,0,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
             {1,1,0,0,1,0,0,0,1,1,1,1,1,1,1,0,0,0,1,1,1,0,0,0,0,1,1,1,0,0,0,1,1,1,1,1},
             {1,1,1,0,1,1,1,0,1,1,1,1,1,1,0,0,1,1,1,1,1,0,1,1,0,1,1,1,0,1,0,1,1,1,1,1},
             {1,1,0,0,1,1,1,0,0,0,0,0,1,1,0,1,1,1,1,1,0,0,1,1,0,0,0,0,0,1,0,0,0,1,1,1},
             {1,0,0,1,1,1,1,0,1,1,1,0,1,1,0,0,0,1,1,0,0,1,1,0,1,0,1,1,1,1,1,1,0,0,1,1},
             {1,0,1,1,1,1,0,0,1,1,0,0,1,1,1,1,0,0,0,1,1,1,0,0,0,0,1,1,1,1,1,1,1,0,1,1},
             {1,0,0,0,1,1,0,1,1,1,0,1,1,1,1,0,0,1,0,0,0,0,1,1,1,0,1,1,1,1,1,1,1,0,1,1},
             {1,1,1,0,1,0,0,1,1,1,0,1,1,1,0,0,1,1,1,1,1,0,1,1,0,0,1,1,1,1,0,0,1,0,1,1},
             {1,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,1,1,1,0,0,0,1,1,0,1,1,1,0,0,0,1,1,0,1,1},
             {1,1,0,1,0,1,1,0,1,1,1,0,0,0,1,1,1,0,0,0,1,1,1,1,0,1,1,1,0,1,0,1,1,0,0,1},
             {1,0,0,1,0,0,0,0,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,0,0,1,0,1,1,1,0,1},
             {1,0,1,1,1,1,0,1,1,1,1,0,0,1,1,1,1,0,0,0,1,1,1,0,0,1,1,0,1,1,0,1,1,0,0,1},
             {1,0,0,0,1,1,0,0,0,1,1,1,0,1,1,1,1,1,1,0,0,1,1,0,1,1,1,0,1,1,0,1,1,0,1,1},
             {1,1,1,0,1,1,1,1,0,0,1,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,0,0,1,1,0,1,1,5,1,1},
             {1,1,0,0,1,1,1,1,1,0,0,1,1,1,0,1,1,1,1,0,0,1,1,1,0,0,0,1,1,1,0,1,1,1,1,1},
             {1,0,0,1,1,1,0,0,0,1,0,1,1,1,0,0,0,1,1,0,1,1,1,0,0,1,1,1,0,1,0,1,1,1,0,3},
             {1,0,1,1,1,1,0,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1,0,0,1,1,1,1,0,1,0,1,1,1,0,1},
             {1,0,0,0,1,1,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,0,1,1,1,1,0,0,1,0,0,0,1,0,1},
             {1,1,1,0,1,1,4,0,0,0,0,1,1,1,0,0,1,1,1,1,1,1,0,1,1,1,0,0,1,1,0,1,0,1,0,1},
             {1,1,1,1,0,0,0,1,1,1,0,1,1,1,0,1,1,1,1,1,0,0,0,1,1,1,0,1,1,1,0,1,0,0,0,1},
             {1,1,1,1,0,1,1,1,1,1,0,1,1,1,0,0,0,1,1,1,0,1,1,1,1,0,0,1,1,1,0,1,1,1,1,1},
             {1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,0,1,1,1,1,0,1,1,1,1,0,0,1,1,1,1},
             {1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,0,0,0,0,1,0,1,1,1,1,1,0,0,1,1,1},
             {1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,1,1},
             {1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,1,0,0,0,1,1,1,1,1,1,1,1},
             {1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,0,1,1,1,1,0,1,1,1,1,1,1,1,1},
             {1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1},
             {2,2,2,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1},
             {2,2,2,1,1,1,1,1,0,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1,1,1,0,0,0,1,1,1,1},
             {2,2,2,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0,1,0,1,1,1,1},
             {2,2,2,1,1,1,0,1,1,1,1,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1,1,0,1,0,1,0,0,0,1,1},
             {2,2,2,1,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,1,1,1,0,1,1},
             {2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

    public MazePanel(MazeGame mFrame){
    	super();
    	this.setBackground(Color.white);
    	this.setPreferredSize(new Dimension(MAX_ROW*BLOCK_SIZE,MAX_ROW*BLOCK_SIZE));
    	this.setSize(new Dimension(MAX_ROW*BLOCK_SIZE,MAX_ROW*BLOCK_SIZE));

    	loadImages();
    	generateCollectibles(totalGems);

    	player = new Player(1,0,BLOCK_SIZE);
    	player.setPanel(this);
    	player.setTexture("/img/player.png");
    	player.setImgFromFrame(2);

    	this.addKeyListener(new KeyListener(){

    		public void keyPressed(KeyEvent e) {
    			int id = e.getKeyCode();
    			if(gameOver) return;
    			if(id == KeyEvent.VK_UP) player.move(Player.UP);
    			else if(id == KeyEvent.VK_LEFT) player.move(Player.LEFT);
    			else if(id == KeyEvent.VK_DOWN) player.move(Player.DOWN);
    			else if(id == KeyEvent.VK_RIGHT) player.move(Player.RIGHT);
    			else if(id == KeyEvent.VK_P) System.out.println(player.getX() + "  " + player.getY());
    			else if(id == KeyEvent.VK_T) sWallIndex = (sWallIndex+1)%3;
    			else if(id == KeyEvent.VK_O) time=maxTime-20;
    		}

    		public void keyReleased(KeyEvent e){
    			player.stop();
    		}

    		public void keyTyped(KeyEvent e) {}
    	});

    	setFocusable(true);

    	t = new Timer(DELAY, new ActionListener(){
    		public void actionPerformed(ActionEvent event){
    			if(gameOver) return;
    			time += 2*DELAY/1000.0;
    			//player.update();
    			if(time > maxTime){
    				gameOver = true;
    				message = "You Lose!";
    			}
    			if(maxTime-time < 15){
    				if(time-lastTime > 0.5){
	    				if(wallIndex == 3){
	    					wallIndex = sWallIndex;
	    				}
	    				else{
	    					wallIndex = 3;
	    				}
	    				lastTime = time;
	    			}
    			}
    			else {
    				wallIndex = sWallIndex;
    			}
    			repaint();
    		}
    	});

    	t.start();
    	mFrame.setResizable(false);
    }


    public void loadImages(){
    	BufferedImage tmpImage = loadImage("/img/blocks.png");
    	for(int i = 0;i < 4;i++){
    		wallImages[i] = resizeImage(tmpImage.getSubimage(i*32,0,32,32),BLOCK_SIZE,BLOCK_SIZE);
    	}
    	floorImage = resizeImage(loadImage("/img/floor.jpg"),BLOCK_SIZE,BLOCK_SIZE);
    	diamondImage = resizeImage(loadImage("/img/diamond.png"),BLOCK_SIZE,BLOCK_SIZE);
    	exitImage = resizeImage(loadImage("/img/exit.jpg"),BLOCK_SIZE,BLOCK_SIZE);
    	doorClosedImage = resizeImage(loadImage("/img/cdoor.png"),BLOCK_SIZE,BLOCK_SIZE);
    	doorOpenImage = resizeImage(loadImage("/img/odoor.png"),BLOCK_SIZE,BLOCK_SIZE);
    	keyImage = resizeImage(loadImage("/img/key.png").getSubimage(176,48,16,16),BLOCK_SIZE,BLOCK_SIZE);
    	
    }

    public void generateCollectibles(int n){
    	int x,y;
    	for(int i = 0;i < n;i++){
    		while( !isEmpty( x=randomInt(1,MAX_ROW-2) , y=randomInt(1,MAX_ROW-2) ) ) {}
    		
    		collectibles.add(new Block(x,y,BLOCK_SIZE,diamondImage,"diamond"));
    		maze[x][y] = 2;
    	}
    	collectibles.add(new Block(16,33,BLOCK_SIZE,keyImage,"key"));
    }

    public double getTime(){
    	return time;
    }

    public boolean isWall(int x, int y){
    	return (x == MAX_ROW || y == MAX_ROW || x < 0 || y < 0 || maze[x][y] == 1 || maze[x][y] == 4);
    }

    public boolean isEmpty(int x, int y){
    	return (maze[x][y] == 0);
    }

    public void checkCollectible(int x, int y){
    	for(Block i: collectibles){
    		if(i.getX() == x && i.getY() == y){
    			if(i.getDesc() == "key"){
    				maze[21][6] = 6;
    			}
    			else{
	    			maze[x][y] = 0;
	    			score++;
	    		}
	    		collectibles.remove(i);
	    		break;
    		}
    	}
    }

    public void checkWin(int x,int y){
    	if((x == MAX_ROW-1 || y == MAX_ROW-1) && collectibles.size() == 0){
    		gameOver = true;
    		message = "You Win!";
    		repaint();
    	}
    }

    private int randomInt(int a, int b){
		return rand.nextInt(b-a+1)+a;
	}

    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2 = (Graphics2D) g;

    	Block wall = new Block(BLOCK_SIZE);
    	Block floor = new Block(BLOCK_SIZE);
    	
    	//wall.setColor(Color.black);
    	//floor.setColor(Color.yellow);

    	wall.setImg(wallImages[wallIndex]);
    	floor.setImg(floorImage);

    	for(int i = 0;i < MAX_ROW;i++){
    		for(int j = 0;j < MAX_ROW;j++){
    			if(maze[i][j] == 1){
    				wall.setX(i);
    				wall.setY(j);
    				wall.draw(g2);
    			}
    			else{
    				floor.setX(i);
    				floor.setY(j);
    				floor.draw(g2);
    			}

    			if(maze[i][j] == 3){
    				new Block(i,j,BLOCK_SIZE,exitImage).draw(g2);
    			}
    			else if(maze[i][j] == 4){
    				new Block(i,j,BLOCK_SIZE,doorClosedImage).draw(g2);
    			}
    			else if(maze[i][j] == 6){
    				new Block(i,j,BLOCK_SIZE,doorOpenImage).draw(g2);
    			}
    		}
    	}

    	for(Block i: collectibles){
    		i.draw(g2);
    	}

    	player.draw(g2);

    	g2.setFont(new Font("Serif",Font.BOLD,24));
		g2.setColor(Color.black);
		g2.drawString("Time: " + String.format("%.0f",Math.max(maxTime-time,0)), this.getWidth()-100,20);
		g2.drawString("Gems: " + (totalGems-score), this.getWidth()-100,45);

		if(gameOver){
			g2.setFont(new Font("Serif",Font.BOLD,55));
			g2.setColor(Color.black);
			g2.drawString(message, this.getWidth()/3,this.getHeight()/2);
			if(score!=totalGems) g2.drawString("Score: " + score, this.getWidth()/3,2*this.getHeight()/3);


		}

    }

    private BufferedImage loadImage(String path) {
       URL url = this.getClass().getResource(path);
       try{
           return ImageIO.read(url);
       } catch (Exception e) {
           System.out.println("Exception: " + e);
       }
       return null;
   }

   private BufferedImage resizeImage(BufferedImage img, int x, int y){
      BufferedImage newImage = new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);

      Graphics g = newImage.createGraphics();
      g.drawImage(img,0,0,x,y,null);
      g.dispose();

      return newImage;
   }

}

