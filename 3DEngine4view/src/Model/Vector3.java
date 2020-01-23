package Model;

import java.io.Serializable;

public class Vector3 implements Serializable{
	
	private static final long serialVersionUID = -3950302783116538854L;
	private double x,y,z,w;
	
	public Vector3(double x, double y, double z,double w) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(w);
	}
	

	public Vector3(double x, double y, double z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(1.0);
	}
	
	public Vector3(Vector3 v) {
		this.setX(v.getX());
		this.setY(v.getY());
		this.setZ(v.getZ());
		this.setW(v.getW());
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

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public void setW(double w) {
		this.w = w;
	}
	
	public double getW() {
		return w;
	}
	
	public double[][] getMatrix(){
		double[][] mat = { {x},{y},{z},{w} };
		return mat;
	}
	
	public double getDistance(Vector3 v) {
		double a = v.x-x;
		double b = v.y-y;
		double c = v.z-z;
		
		
		return Math.sqrt(a*a+b*b+c*c);
	}
	
	public void setCord(double x,double y,double z,double w) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(w);
	}
	
	public void setCord(double x,double y,double z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(1.0);
	}
	
	public void move(double x,double y,double z) {
		this.setX(this.x+x);
		this.setY(this.y+y);
		this.setZ(this.z+z);
	}
	

	public void print() {
		System.out.println("P: "+x+" : "+y +  " : "+z+" : "+w+" ;");
	}
	
	public void print(String s) {
		System.out.println(s+" = ("+x+" , "+y +  " , "+z+")");
	}
	
	public String toString() {
		return x+" : "+y +  " : "+z+" : "+w;
	}
	

	public double lenght() {
		return Math.sqrt( dot_product(this,this));
	}
	
	public Vector2 project() {
		return new Vector2(x,y);
	}
	
	public Vector2 project(int view) {
		switch(view){
			case 1: return new Vector2(x,y);
			case 2: return new Vector2(z,y);
			case 3: return new Vector2(x,z);
			default: return new Vector2(x,y);
		}
	}

	//STATIC 
	

	
	public static Vector3 vector_sub(Vector3 a,Vector3 b) {
		Vector3 res = new Vector3(0,0,0);
		
		res.x = a.x-b.x;
		res.y = a.y-b.y;
		res.z = a.z-b.z;
		return res;
	}
	
	public static Vector3 vector_add(Vector3 a,Vector3 b) {
		Vector3 res = new Vector3(0,0,0);
		
		res.x = a.x+b.x;
		res.y = a.y+b.y;
		res.z = a.z+b.z;
		return res;
	}
	
	
	public static Vector3 multiply(Vector3 vec, double k) {
		return new Vector3( vec.x*k,vec.y*k,vec.z*k );
	}
	
	public static Vector3 div(Vector3 newForward, double k) {
		return new Vector3( newForward.x/k,newForward.y/k,newForward.z/k );
	}
	
	public static double dot_product(Vector3 a, Vector3 b) {
		
		return (a.x*b.x + a.y*b.y+ a.z*b.z);
	}
	
	
	public static Vector3 normalize(Vector3 vec) {
		Vector3 res = new Vector3(0,0,0);
		double l = vec.lenght();
		
		res.x = vec.x / l;
		res.y = vec.y / l;
		res.z = vec.z / l;
		
		return res;
	}
	
	public static Vector3 cross_product(Vector3 a, Vector3 b) {
		Vector3 res = new Vector3(0,0,0);
		
		res.x = a.y*b.z - a.z*b.y;
		res.y = a.z * b.x - a.x*b.z;
		res.z = a.x * b.y - a.y*b.x;
		
		return res;
	}
	
	public static Vector3 IntersectPlane(Vector3 plane_p,Vector3 plane_n, Vector3 lineStart, Vector3 lineEnd) {
		Vector3 planeN = Vector3.normalize(plane_n);
		double plane_d = -Vector3.dot_product(planeN, plane_p);
		double ad = Vector3.dot_product(lineStart, planeN);
		double bd = Vector3.dot_product(lineEnd, planeN);
		double t = (-plane_d - ad) / (bd-ad);
		Vector3 lineStartToEnd = Vector3.vector_sub(lineEnd, lineStart);
		Vector3 lineToIntersect = Vector3.multiply(lineStartToEnd, t);
		return Vector3.vector_add(lineStart, lineToIntersect);
	}


	public static Vector3 transform(Vector3 coord, TransformMatrix transMat) {
		// TODO Auto-generated method stub
		return TransformMatrix.multiplyVector(transMat, coord);
	}

}
