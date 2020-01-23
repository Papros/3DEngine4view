package Controller;

import java.io.File;
import Model.ModelMainClass;

public class ModelInput {

	ModelMainClass model;
	
	public void Model() {
		
	}

	public void setModel(ModelMainClass engine) {
		// TODO Auto-generated method stub
		this.model = engine;
	}

	public boolean openFile(File file) {
		// TODO Auto-generated method stub
		return model.makeMesh(file);
	}

	public void saveFile(String name) {
		// TODO Auto-generated method stub
		
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	public void setDefaultSize(double d, double e) {
		// TODO Auto-generated method stub
		model.initiate((int)d, (int) e);
		
	}
	
	public void moveCamera(int x,int y,int z) {
		model.moveCamera(x, y, z);
	}

	public void printCam() {
		// TODO Auto-generated method stub
		model.printCam();
	}

	public void moveCenter(int i, int j, int k) {
		// TODO Auto-generated method stub
		model.moveCenter(i,j,k);
	}

	public void printCenter() {
		// TODO Auto-generated method stub
		model.printCenter();
	}
	
	public void rotate(int dir) {
		model.rotate(dir);
	}

	public void rotateUp(int i) {
		// TODO Auto-generated method stub
		model.rotateUp(i);
	}

	public void setDegrees(int value) {
		// TODO Auto-generated method stub
		model.setAngle(value);
	}

	public void setShow(boolean selected, boolean selected2, boolean selected3) {
		model.setShow(selected,selected2,selected3);
		
	}

	
}
