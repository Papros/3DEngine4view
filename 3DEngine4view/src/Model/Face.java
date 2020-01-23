package Model;

import java.awt.Color;

class Face {
	private int A;
	private int B;
	private int C;
	Color color;
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Face(int a,int b,int c) {
		this.A =a;
		this.B = b;
		this.C = c;
		color = Color.white;
	}
	
	public int getA() {
		return A;
	}
	
	public int getB() {
		return B;
	}
	
	public int getC() {
		return C;
	}

	public void setA(int a) {
		A = a;
	}

	public void setB(int b) {
		B = b;
	}

	public void setC(int c) {
		C = c;
	}
	
	
	
}
