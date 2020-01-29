package Model;

import java.awt.Color;

public class Light {
	
	private Vector3 position;
	private Color color;
	
	public Light() {
		
	}
	
	public Light(Vector3 p) {
		setPosition(p);
		setColor(Color.WHITE);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public void setPosition(double xp, double yp, double z) {
		// TODO Auto-generated method stub
		position.setCord(xp, yp, z);
	}

}
