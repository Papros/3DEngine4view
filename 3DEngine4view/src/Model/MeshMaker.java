package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;



public class MeshMaker {

	Mesh[] meshList;
	Camera cam;
	
	public Mesh[] getMeshFromFile(File file) throws Exception {
		meshList = null;
		if(!file.canRead() || !file.isFile()) {
			return null;
		}
		BufferedReader br;
		
		br = new BufferedReader(new FileReader(file));
		
		Vector3[] top = null;
		Vector3[] fVectors = null;
		Face[] faces = null;
		
		String line;
		String lineBf = "";
		line = br.readLine();
		
		String positionArray = null,faceVectors=null;
		
		boolean end = false;
		String password1 = "-mesh-positions-array\" count=\"";
		String password2 = "</triangles>";
		
		while( line != null && !end) {
			
			if( line.contains(password1) && top == null) {

				int posStrat = line.indexOf(">")+1;
				int posEnd = line.indexOf("</");
				String posString = line.substring(posStrat, posEnd);
				top = this.getTopFromArray(posString);
				System.out.println("Top found:"+top.length);
			}
			else if( line.contains(password2) ){

			
				int posStart = lineBf.indexOf("<p>")+3;
				int posEnd = lineBf.indexOf("</");
				
				String posString = lineBf.substring(posStart,posEnd);
				fVectors = this.getTop2FromArray(posString);
				faces = this.getFaceFromArray(fVectors);
				System.out.println("Faces found:"+faces.length);
				end = true;
				meshList = new Mesh[1];
				meshList[0] = new Mesh("mesh",top,faces);
			}
			
			
			if(positionArray != null &&  faceVectors!=null) end = true;
			lineBf = line;
			line = br.readLine();
	
		};
		
		
		br.close();
		return meshList;
	}
	
	
	
	public Vector3[] getTopFromArray(String s) {
	
		Vector3[] top = null;
		
		String[] vectors = s.split(" ");
		top = new Vector3[vectors.length/3];
		
		int j = 0;
		for(int i=0; i <= vectors.length-3;i=i+3) {
			top[j] = new Vector3( Double.parseDouble(vectors[i]),Double.parseDouble(vectors[i+1]),Double.parseDouble(vectors[i+2])  );
			//top[j].print("P"+j+" = ");
			j++;
		}
		
		
		return top;
	}
	
	public Vector3[] getTop2FromArray(String s) {
		
		Vector3[] top = null;
		
		String[] vectors = s.split(" ");
		top = new Vector3[vectors.length/3];
		
		int j = 0;
		for(int i=0; i <= vectors.length-3;i=i+3) {
			top[j] = new Vector3( Integer.parseInt(vectors[i]),Integer.parseInt(vectors[i+1]),Integer.parseInt(vectors[i+2])  );
			//top[j].print("P"+j+" = ");
			j++;
		}
		
		
		return top;
	}
	
	public Face[] getFaceFromArray(Vector3[] faceV) {
		
		Face[] face = new Face[faceV.length/3];
		int j = 0;
		for(int k=0; k <= face.length-1;k++) {
			face[k] = new Face((int)faceV[j].getX(),(int)faceV[j+1].getX(),(int)faceV[j+2].getX());
			//face[k].print("W"+k);
			j+=3;
		}
		
		return face;
		
	}
	
	
	
	

}
