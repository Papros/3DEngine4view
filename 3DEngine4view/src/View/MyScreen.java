package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyScreen extends JPanel{

	
	BufferedImage image;
	

	  @Override
	  protected void paintComponent(Graphics g2d) {
	    super.paintComponent(g2d);
	    
		
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, this.getWidth(), this.getHeight());
	    
		if(image != null) {
			g2d.drawImage(image,0,0, this);
		}
		
	  }
	  
	  public void setImage(BufferedImage image) {
		  this.image = image;
	  }

	  
	
}
