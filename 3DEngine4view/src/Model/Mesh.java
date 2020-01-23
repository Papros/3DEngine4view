package Model;

import java.awt.Color;

public class Mesh {
	
	
	private String Name;
    private Vector3[] Vertices;
    private Face[] Faces;
    private Vector3 Position;
    private Vector3 Rotation;
    
    ///CUBE
    public Mesh() {
    	 setVertices(new Vector3[4]);
    	 setFaces(new Face[4]);
         setName("cube");
         
         Vertices[0] = new Vector3(-4, -1, 0);
         Vertices[1] = new Vector3(0,-1 ,3);
         Vertices[2] = new Vector3(0, -1, -3);
         Vertices[3] = new Vector3(-1, 4, 0);
         
         Faces[0] = new Face (0,1,2);
         Faces[0].setColor(Color.RED);
         
         Faces[1] = new Face (1,3,0);
         Faces[1].setColor(Color.GREEN);
         
         Faces[2] = new Face (2,3,1);
         Faces[2].setColor(Color.BLUE);
         
         Faces[3] = new Face (0,3,2);
         Faces[3].setColor(Color.WHITE);
         
 		Position = new Vector3(0,0,0);
 		Rotation = new Vector3(0,0,0);
    }
    
    public Mesh(String name,Vector3[] v, Face[] f) {
    	setVertices(v);
    	setFaces(f);
        setName(name);
        Position = new Vector3(0,0,0);
 		Rotation = new Vector3(0,0,0);
    }

    public Mesh(String name, int verticesCount,int FacesCount)
    {
        setVertices(new Vector3[verticesCount]);
        setFaces(new Face[FacesCount]);
        setName(name);
    }

	private void setFaces(Face[] faces2) {
		this.Faces = faces2;
		
	}
	
	public Face[] getFaces() {
		return Faces;
	}
	

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Vector3[] getVertices() {
		return Vertices;
	}

	public void setVertices(Vector3[] vertices) {
		Vertices = vertices;
	}

	public Vector3 getPosition() {
		return Position;
	}

	public void setPosition(Vector3 position) {
		Position = position;
	}

	public Vector3 getRotation() {
		return Rotation;
	}

	public void setRotation(Vector3 rotation) {
		Rotation = rotation;
	}
	
	public void print() {
		for(int i=0;i<Vertices.length;i++)
			Vertices[i].print(i+"");
	}
	

}
