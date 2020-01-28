package Controller;

import java.awt.image.BufferedImage;

import Model.Vector2;
import View.MainViewClass;

public class ViewInput {

	MainViewClass view;
	
	public ViewInput() {
		
	}
	
	public void setView(MainViewClass v) {
		view = v;
	}
	
	public void refresh() {
		view.refresh();
	}
	
	public void display(BufferedImage img) {
		view.display(img);
	}

	public void displayM(BufferedImage[] imgs) {
		view.displayM(imgs);
		
	}

	public void setCam(Vector2[] camCord, Vector2[] centerCord) {
		// TODO Auto-generated method stub
		view.setCam(camCord,centerCord);
	}

	public void setLight(Vector2[] cord) {
		// TODO Auto-generated method stub
		view.setLight(cord);
	}

}
