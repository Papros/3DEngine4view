package Model;

public class Vector2 {
	
	double x,y;
	
	public Vector2(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double[][] getMatrix(){
		double[][] mat = { {x},{y},{1} };
		return mat;
	}
	
	public void setCord(double x,double y) {
		this.setX(x);
		this.setY(y);
	}
	
	public void setcord(double[][] cord) {
		
		this.setX( cord[0][0]);
		this.setY( cord[1][0]);
	}
	
	public void print() {
		System.out.println("P: "+x+" : "+y + " ;");
	}

	public void print(String string) {
		// TODO Auto-generated method stub
		System.out.println(string+":->: "+x+" : "+y + " ;");
	}
	
	public String toString() {
		return "("+x+" , "+y+")";
	}

	public void multiply(double d) {
		this.x = d*x;
		this.y = d*y;
	}

}
