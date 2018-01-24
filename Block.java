
import java.awt.*;
import java.awt.geom.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import java.net.URL;
import java.io.File;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.*;

public class Block {

	public final int COLOR = 1, IMAGE = 2;
	private int x, y, size;
	private BufferedImage img;
	private Color color;
	private int kind;
	private String desc;
	private JPanel panel;
	private boolean visible = true;

	public Block(int size){
		this.size = size;
	}

	public Block(int x,int y, int size){
		this.x = x;
		this.y = y;
		this.size = size;
	}

	public Block(int x,int y, int size, BufferedImage img){
		this.x = x;
		this.y = y;
		this.size = size;
		this.img = img;
		kind = IMAGE;
	}

	public Block(int x,int y, int size, BufferedImage img, String desc){
		this.x = x;
		this.y = y;
		this.size = size;
		this.img = img;
		this.desc = desc;
		kind = IMAGE;
	}

	public Block(int x,int y, int size, Color color){
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
		kind = COLOR;
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

	public int getKind(){
		return kind;
	}

	public String getDesc(){
		return desc;
	}

	public BufferedImage getImg(){
		return img;
	}

	public Color getColor(){
		return color;
	}

	public JPanel getPanel(){
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

	public void setKind(int kind){
		this.kind = kind;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public void setImg(BufferedImage img){
		this.img = resizeImage(img,size,size);
		setKind(IMAGE);
	}

	public void setImg(String filepath){
		this.img = resizeImage(loadImage(filepath),size,size);
		setKind(IMAGE);
	}

	public void setColor(Color color){
		this.color = color;
		setKind(COLOR);
	}

	public void setPanel(JPanel panel){
		this.panel = panel;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public void draw(Graphics2D g2){

		if(!visible) return;

		if(kind==COLOR){
			g2.setColor(color);
			g2.fill(new Rectangle(x*size,y*size,size,size));
		}

		if(kind==IMAGE){
			g2.drawImage(img,x*size,y*size,panel);
		}
	}

	private BufferedImage loadImage(String path) {
       System.out.println("Path: " + path);
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