package Model;

public class Camera {
	
	private Vector3 vPosition;  //location of camera in 3D space
	private Vector3 vLookDir; // Direction vector along the direction camera points
	private Vector3 vTarget;	//position of what we looking for
	


	private Vector3 vUp;	//up vector, not implemented
	private double angle;	//fov angle
	private double dYaw;	//caemra rotation inXZ plane
	private double dTheta;	//spins world transform
	private double dFar;	
	private double dNear;
	
	
	
	
	// FUNCTION
	
	public Camera() {
		vPosition = new Vector3(0,0,5);
		vLookDir = new Vector3(0,0,1);
		vTarget = new Vector3(0,0,0);
		vUp = new Vector3(0,1,0);
		angle = 0.9;
		dYaw = 0.0;
		dTheta = 0.0;
		dFar = 100.0;
		dNear = 0.5;
	}
	

	// GETERS AND SETTERS
	
	public Vector3 getvPosition() {
		return vPosition;
	}
	public void setvPosition(Vector3 vPosition) {
		this.vPosition = vPosition;
	}
	public Vector3 getvLookDir() {
		
		return vLookDir;
	}
	
	public void setvLookDir(Vector3 dir) {
		this.vLookDir = Vector3.normalize(dir);
		
		
		double d =  Vector3.vector_sub(vTarget, vPosition).lenght();
		
		
		this.vTarget = Vector3.vector_add(vPosition, Vector3.multiply(vLookDir, d));
	}
	
	public void setvTarget(Vector3 targ) {
		this.vTarget = targ;
		this.vLookDir = Vector3.normalize( Vector3.vector_sub(vTarget, vPosition) );
	}
	
	public double getDistance() {
		return vPosition.getDistance(vTarget);
	}
	
	public Vector3 getvTarget() {
		return vTarget;
	}
	
	public Vector3 getvUp() {
		return vUp;
	}
	public void setvUp(Vector3 vUp) {
		this.vUp = vUp;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public double getdYaw() {
		return dYaw;
	}
	public void setdYaw(double dYaw) {
		this.dYaw = dYaw;
	}
	public double getdTheta() {
		return dTheta;
	}
	public void setdTheta(double dTheta) {
		this.dTheta = dTheta;
	}
	public double getdFar() {
		return dFar;
	}
	public void setdFar(double dFar) {
		this.dFar = dFar;
	}
	public double getdNear() {
		return dNear;
	}
	public void setdNear(double dNear) {
		this.dNear = dNear;
	}


	public void setPosition(double d, double e, double f) {
		this.vPosition.setCord(d, e, f);
	}

	//ratio mean height/width, like 9/16
	public Vector3[] getFOW(double ratio) {
		Vector3[] fov = new Vector3[4];
		
		double hTana = Math.atan(angle/1.0)*getDistance();
		Vector3 xp,yp,zp;
		
		zp = Vector3.multiply(Vector3.normalize(vLookDir),this.getDistance());
		xp = Vector3.multiply( Vector3.normalize( Vector3.cross_product(vUp, zp) ),hTana );
		yp = Vector3.multiply( Vector3.normalize( Vector3.cross_product(xp, zp) ), xp.lenght()*ratio  );
		
		
		fov[0] = Vector3.vector_add(zp, Vector3.vector_add(xp, yp) );
		fov[1] = Vector3.vector_add(zp, Vector3.vector_sub(yp, xp) );
		fov[2] = Vector3.vector_add(zp, Vector3.vector_sub(Vector3.multiply(yp, -1.0), xp) );
		fov[3] = Vector3.vector_add(zp, Vector3.vector_sub(xp, yp) );
		fov[0] = Vector3.vector_add(vPosition, fov[0]);
		fov[1] = Vector3.vector_add(vPosition, fov[1]);
		fov[2] = Vector3.vector_add(vPosition, fov[2]);
		fov[3] = Vector3.vector_add(vPosition, fov[3]);
		
		/*
		getvPosition().print("Pos");
		getvTarget().print("Tar");
		this.getvLookDir().print("Dir");
		System.out.println(this.getDistance());
		fov[0].print("F0");
		fov[1].print("F1");
		fov[2].print("F2");
		fov[3].print("F3");
		xp.print("xp");
		yp.print("yp");
		zp.print("zp");
		*/
		
		return fov;
	}
	
	

}
