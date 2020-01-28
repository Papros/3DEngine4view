package Model;

import java.io.File;

import Controller.ViewInput;

public class ModelMainClass {
	
	ViewInput view;
	Device dev;
	double speed,rotSpeed;
	Camera cam1;
	Mesh[] mesh;
	MeshMaker meshMaker;
	
	public ModelMainClass(ViewInput v) {
		this.view = v;
		mesh = new Mesh[1];
		mesh[0] = new Mesh();
		speed = 2;
		rotSpeed = 4;
		dev = new Device();
		meshMaker = new MeshMaker();
		
		dev.setMeshList(mesh);
		
		cam1 = new Camera();
		cam1.setAngle(Math.toRadians(80));
		cam1.setvPosition(new Vector3(0,0,10));
		cam1.setvUp(new Vector3(0,1,0));
		cam1.setvTarget(new Vector3(0,0,0));
		
		dev.setCam(cam1);
	}
	
	public void moveCamera(int x,int y,int z) {
		//cam1.setPosition( cam1.getvPosition().getX()+ x*speed, cam1.getvPosition().getY()+y*speed, cam1.getvPosition().getZ()+z*speed );
		
		if(z != 0) forward(z);
		else if(x != 0) leftRight(x);
		else if(y != 0) updown(y);
		
		repaint();
	}
	
  	 
	 private void leftRight(int x) {
		Vector3 leftright = Vector3.cross_product( cam1.getvUp(), cam1.getvLookDir() );
		if(x >0) cam1.setvPosition( Vector3.vector_add(cam1.getvPosition(), leftright ));
		else cam1.setvPosition(Vector3.vector_sub(cam1.getvPosition(), leftright));
		repaint();
		
	}
	 
	 private void updown(int y) {
			if(y >0) cam1.setvPosition( Vector3.vector_add(cam1.getvPosition(), cam1.getvUp() ));
			else cam1.setvPosition(Vector3.vector_sub(cam1.getvPosition(), cam1.getvUp()));
			repaint();
	 }

	public void repaint() {
	 dev.refresh();
	 //view.display(dev.getBufferedImage());
	 view.setCam(dev.getCamCord(),dev.getCenterCord());
	 view.setLight(dev.getLightCord());
	 view.displayM(dev.getBufferedImages());
	 }
	 
	 

	public void initiate(int d, int e) {
		// TODO Auto-generated method stub
		dev.initiate(d, e);
	}

	public void printCam() {
		// TODO Auto-generated method stub
		cam1.getvPosition().print("Camera:");
	}
	
	public boolean makeMesh(File file) {
		
		Mesh[] newMesh = null;
		
		try {
			newMesh =  meshMaker.getMeshFromFile(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(newMesh != null) {
			this.mesh = newMesh;
			dev.setMeshList(mesh);
			
			System.out.println("Mesh set");
			repaint();
			
			return true;
		}else {
			System.out.println("File error");
			return false;
		}
	}

	public void moveCenter(int x,int y,int z) {
		repaint();
	}
	
	public void rotate(int dir) {
		//cam1.setdYaw(cam1.getdYaw()+0.05*dir);
		Vector3 direct = cam1.getvLookDir();
		direct = Vector3.transform(direct, TransformMatrix.makeRotationY(0.1*dir) );
		cam1.setvLookDir(direct);
		repaint();
		
	}

	public void printCenter() {
		// TODO Auto-generated method stub
		cam1.getvPosition().print("Center");
	}

	
	public void rotateUp(int dir) {
		// TODO Auto-generated method stub
		Vector3 direct = cam1.getvLookDir();
		direct = Vector3.transform(direct, TransformMatrix.makeRotationX(0.1*dir) );
		cam1.setvLookDir(direct);
		repaint();
	}
	
	public void forward(int i) {
		// TODO Auto-generated method stub
		if(i>0) cam1.setvPosition(Vector3.vector_add(cam1.getvPosition(), Vector3.multiply(cam1.getvLookDir(), 0.5)));
		else cam1.setvPosition(Vector3.vector_sub(cam1.getvPosition(), Vector3.multiply(cam1.getvLookDir(), 0.5)));
		repaint();
	}

	public void setAngle(int value) {
		// TODO Auto-generated method stub
		cam1.setAngle(Math.toRadians(value));
		repaint();
	}

	public void setShow(boolean selected, boolean selected2, boolean selected3) {
		// TODO Auto-generated method stub
		dev.setShow(selected, selected2, selected3);
		repaint();
	}

	public void setCam(int x, int y, int activeB) {
		// TODO Auto-generated method stub
		dev.setCam(x,y,activeB);
		repaint();
	}

	public void setCenter(int x, int y, int activeB) {
		// TODO Auto-generated method stub
		dev.setCenter(x,y,activeB);
		repaint();
		
	}

	public void setLight(int x, int y, int activeB) {
		// TODO Auto-generated method stub
		dev.setLight(x, y, activeB);
		repaint();
	}

	public void setShadow(boolean selected, boolean selected2, boolean selected3) {
		// TODO Auto-generated method stub
		dev.setShadow(selected,selected2,selected3);
		repaint();
	}
}
