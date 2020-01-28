package Model;

import java.awt.Color;

public class IndependFace {
	private Vector3 A;
	private Vector3 B;
	private Vector3 C;
	private Color color;
	private Color shade;
	
	public IndependFace(Vector3 a,Vector3 b,Vector3 c) {
		this.A = a;
		this.B = b;
		this.C = c;
		color = Color.white;
	}
	
	public IndependFace() {
		// TODO Auto-generated constructor stub
		this.A = new Vector3(0,0,0);
		this.B = new Vector3(0,0,0);
		this.C = new Vector3(0,0,0);
		color = Color.white;
	}

	public Vector3 getA() {
		return A;
	}
	
	public Vector3 getB() {
		return B;
	}
	
	public Vector3 getC() {
		return C;
	}

	public void setA(Vector3 a) {
		A = a;
	}

	public void setB(Vector3 b) {
		B = b;
	}

	public void setC(Vector3 c) {
		C = c;
	}
	
	public void print(String s) {
		System.out.println(s+" -----------------");
		A.print("a");
		B.print("b");
		C.print("c");
		System.out.println(color.toString());
		System.out.println("----------------------");
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getShade() {
		return shade;
	}

	public void setShade(Color shade) {
		this.shade = shade;
	}
}
