
import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.File;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;

public class Player {

	public static final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
	private int dirx[] = {0,-1,0,1}, diry[] = {-1,0,1,0};
	private int x, y, size, d;
	private BufferedImage img, texture;
	private BufferedImage[] frames = new BufferedImage[4];
	private MazePanel panel;
	private boolean visible = true, moving = false;

	public Player(int size){
		this.size = size;
	}

	public Player(int x,int y, int size){
		this.x = x;
		this.y = y;
		this.size = size;
	}

	public Player(int x,int y, int size, BufferedImage img){
		this.x = x;
		this.y = y;
		this.size = size;
		this.img = img;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getSize(){
		return size;
	}

	public BufferedImage getImg(){
		return img;
	}

	public MazePanel getPanel(){
		return panel;
	}

	public boolean isVisible(){
		return visible;
	}

	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public void setSize(int size){
		this.size = size;
	}

	public void setImg(BufferedImage img){
		this.img = resizeImage(img,size,size);
	}

	public void setImg(String filepath){
		this.img = resizeImage(loadImage(filepath),size,size);
	}

	public void setTexture(String filepath){
		this.texture = loadImage(filepath);
		frames[0] = resizeImage(getImgFromTexture(12,12,40,52),size,size);
		frames[1] = resizeImage(getImgFromTexture(12,76,40,52),size,size);
		frames[2] = resizeImage(getImgFromTexture(12,140,40,52),size,size);
		frames[3] = resizeImage(getImgFromTexture(12,204,40,52),size,size);
	}

	public BufferedImage getImgFromTexture(int x, int y, int w, int h){
		return texture.getSubimage(x,y,w,h);
	}

	public void setImgFromFrame(int n){
		img = frames[n];
	}

	public void setPanel(MazePanel panel){
		this.panel = panel;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public void translate(){
		int nx, ny;
		nx = x+dirx[d];
		ny = y+diry[d];

		if(!isWall(nx,ny)){
			x = nx;
			y = ny;
		}

		panel.checkCollectible(x,y);
		panel.checkWin(x,y);
	}

	public boolean isWall(int x, int y){
		return panel.isWall(x,y);
	}

	public void move(int direction){
		moving = true;
		d = direction;
		setImgFromFrame(d);
		translate();
	}

	public void stop(){
		moving = false;
	}

	public void draw(Graphics2D g2){
		if(!visible) return;

		g2.drawImage(img,x*size,y*size,panel);
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