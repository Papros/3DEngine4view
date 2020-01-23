package Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Vector;

public class Device {

	BufferedImage bmp;
	BufferedImage front,side,top;
	
	Graphics2D g2d;
	TransformMatrix viewMatrix;
	Camera cam;
	Mesh[] meshList;
	
	Vector<IndependFace> facesToDraw;
	
	Vector2 frontCam, sideCam, topCam;
	Vector2 frontCenter,sideCenter,topCenter;
	
	boolean edges,faces,textures;
	
	public Device() {
		cam = new Camera();
		meshList = new Mesh[1];
		meshList[0] = new Mesh();
		facesToDraw = new Vector<IndependFace>();
		edges = true;
		faces = true;
		textures = true;
	}
	
	public Device(Camera c, Mesh[] m ) {
		cam = c;
		meshList = m;
		facesToDraw = new Vector<IndependFace>();
		edges = true;
		faces = true;
		textures = true;
	}
	
	public void setMeshList(Mesh[] m) {
		this.meshList = m;
	}
	
	public void setMeshList(Mesh m) {
		this.meshList = new Mesh[1];
		meshList[0] = m;
	}
	
	public void setCam(Camera c) {
		this.cam = c;
	}
	
	public void initiate(int w,int h) {
		bmp = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
		top = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
		side = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
		front = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void setShow(boolean b1,boolean b2,boolean b3) {
		edges = b1;
		faces = b2;
		textures = b3;
	}
	
	public void refresh() {
		render(cam,meshList);
	}
	
	// Clear the back buffer with a specific color
	 public void Clear(Color c,BufferedImage img) {
         g2d = (Graphics2D) img.getGraphics();
         g2d.setColor(c);
         g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
     }
	     
     // Called to put a pixel on screen at a specific X,Y coordinates
     public void PutPixel(int x, int y, Color color)
     {
         bmp.setRGB(x, y, color.getRGB());
     }
     
     //Drawing tirangle
     public Path2D path(Vector2 v1,Vector2 v2,Vector2 v3) {
    	 Path2D path = new Path2D.Double();
    	 path.moveTo(v1.getX(), v1.getY());
    	 path.lineTo(v2.getX(), v2.getY());
    	 path.lineTo(v3.getX(), v3.getY());
    	 path.closePath();
		return path;
     }
     
     
     //Drawinig line
     public Path2D path(Vector2 v1,Vector2 v2) {
    	 Path2D path = new Path2D.Double();
    	 path.moveTo(v1.getX(), v1.getY());
    	 path.lineTo(v2.getX(), v2.getY());
    	 path.closePath();
		return path;
     }
     
     // Transforming Vector3 coord with TransformMatrix and project on 2D screen
     public Vector2 Project(Vector3 coord, TransformMatrix transMat)
     {
         Vector3 point = Vector3.transform(coord, transMat);
         
        double x = (bmp.getWidth()/2.0f+ (  point.getX()* bmp.getHeight()/(point.getZ())  ));
        double y = (bmp.getHeight()/2.0f- ( point.getY()* bmp.getHeight()/(point.getZ()) ));
         
         return (new Vector2(x, y));
     }
     
     
     
     public void DrawPoint(Vector2 point)
     {
    	
         if (point.getX() >= 0 && point.getY() >= 0 && point.getX() < bmp.getWidth() && point.getY() < bmp.getHeight())
         {
             PutPixel(Math.round( Math.round( point.getX() ) ), Math.round( Math.round( point.getY() ) ), Color.white);
         }
     }
     
     public void DrawPoint(Vector2 point,Color c)
     {
         if (point.getX() >= 0 && point.getY() >= 0 && point.getX() < bmp.getWidth() && point.getY() < bmp.getHeight())
         {
             PutPixel((int)point.getX(), (int)point.getY(), c);
         }
     }
     
     public Vector3 inteserectPlane(Vector3 plane_p, Vector3 plane_n, Vector3 lineStart, Vector3 lineEnd) {
    	 plane_n = Vector3.normalize(plane_n);
    	 double plane_d = -Vector3.dot_product(plane_n, plane_p);
    	 double ad = Vector3.dot_product(lineStart, plane_n);
    	 double bd = Vector3.dot_product(lineEnd, plane_n);
    	 double t = (-plane_d - ad) / (bd - ad);
    	 Vector3 lineStartToEnd = Vector3.vector_sub(lineEnd, lineStart);
    	 Vector3 lineToIntersect = Vector3.multiply(lineStartToEnd, t);
    	 return Vector3.vector_add(lineStart, lineToIntersect);
    	 
     }
     
     public double dist(Vector3 n,Vector3 f,Vector3 p) {
    	 n = Vector3.normalize(n);
    	 return( n.getX()*p.getX() + n.getY()*p.getY() + n.getZ()+p.getZ() - Vector3.dot_product(n, f));
    	// return( n.getX()*n1.getX() + n.getY()*n1.getY() + n.getZ()+n1.getZ() - Vector3.dot_product(n, f));
     }
     
     public IndependFace[] clipAgainsPlane(Vector3 plane_p, Vector3 plane_n,IndependFace in_tri) {
    	 IndependFace[] cutF;
    	 
    	 plane_n = Vector3.normalize(plane_n);
    	 
    	 Vector3 insidePoints[] = new Vector3[3]; int ninsideP = 0;
    	 Vector3 outsidePoints[] = new Vector3[3]; int noutsideP = 0;
    	 
    	 double d0 = dist(plane_n, plane_p, in_tri.getA());
    	 double d1 = dist(plane_n, plane_p, in_tri.getB());
    	 double d2 = dist(plane_n, plane_p, in_tri.getC());
    	 
    	 if( d0 >= 0 ) { insidePoints[ninsideP++] = in_tri.getA(); } 
    	 else { outsidePoints[noutsideP++] = in_tri.getA(); }
    	 
    	 if( d1 >= 0 ) { insidePoints[ninsideP++] = in_tri.getB(); } 
    	 else { outsidePoints[noutsideP++] = in_tri.getB(); }
    	 
    	 if( d2 >= 0 ) { insidePoints[ninsideP++] = in_tri.getC(); } 
    	 else { outsidePoints[noutsideP++] = in_tri.getC(); }
    	 
    	 
    	 if( ninsideP == 0) {
    		 cutF = null;
    	 }
    	 else if( ninsideP == 3) {
    		 cutF = new IndependFace[1];
    		 cutF[0] = in_tri;
    		 cutF[0].setColor(in_tri.getColor());
    	 }
    	 else if(ninsideP ==1 && noutsideP ==2) {
    		 cutF = new IndependFace[1];
    		 
    		 cutF[0] = new IndependFace();
    		 cutF[0].setA(insidePoints[0]);
    		 cutF[0].setB( inteserectPlane(plane_p, plane_n, insidePoints[0], outsidePoints[0]) );
    		 cutF[0].setC( inteserectPlane(plane_p, plane_n, insidePoints[0], outsidePoints[1]) );
    		 cutF[0].setColor(in_tri.getColor());
    	 }
    	 else if(ninsideP == 2 && noutsideP == 1) {
    		 
    		 cutF = new IndependFace[2];
    		 cutF[0] = new IndependFace();
    		 cutF[0].setA(insidePoints[0]);
    		 cutF[0].setB(insidePoints[1]);
    		 cutF[0].setC(  inteserectPlane(plane_p, plane_n, insidePoints[0], outsidePoints[0]) );
    		 cutF[0].setColor(in_tri.getColor());
    		 
    		 cutF[1] = new IndependFace();
    		 cutF[1].setA(insidePoints[1]);
    		 cutF[1].setB( cutF[0].getC() );
    		 cutF[1].setC(  inteserectPlane(plane_p, plane_n, insidePoints[1], outsidePoints[0]) );
    		 cutF[1].setColor(in_tri.getColor());
    		 
    	 }else {
    		 cutF = null;
    	 }
    	 
    	 
    	 return cutF;
     }
     
        
     public void render(Camera cam, Mesh[] meshes) {
    	 
    	Clear(Color.BLACK,bmp);
    	TransformMatrix matProj = TransformMatrix.makeProjection(cam.getAngle()/2.0,( 9.0 / 16.0), cam.getdNear(), cam.getdFar());
    	
    	
    	for(int i=0;i<meshes.length;i++) {
    		
    		Mesh mesh = meshes[i];
    		
    		TransformMatrix matRotZ,matRotX,matRotY;
    		
    		matRotZ = TransformMatrix.makeRotationZ(mesh.getRotation().getZ());
    		matRotX = TransformMatrix.makeRotationX(mesh.getRotation().getX());
    		matRotY = TransformMatrix.makeRotationY(mesh.getRotation().getY());
    		
    		TransformMatrix matTrans;
    		matTrans = TransformMatrix.makeTranslation(mesh.getPosition().getX(),mesh.getPosition().getY(),mesh.getPosition().getZ());
    		
    		TransformMatrix matWorld;
    		matWorld = TransformMatrix.getDiagonalMatrix();
    		matWorld = TransformMatrix.Matrix_MultiplyMatrix(matRotZ, matRotX);
    		matWorld = TransformMatrix.Matrix_MultiplyMatrix(matWorld, matRotY);
    		matWorld = TransformMatrix.Matrix_MultiplyMatrix(matWorld, matTrans);
    		
    		TransformMatrix matCameraRot = TransformMatrix.makeRotationY(cam.getdYaw());
    		Vector3 lookDir = TransformMatrix.multiplyVector(matCameraRot, cam.getvLookDir());
    		Vector3 vTarget = Vector3.vector_add(cam.getvPosition(), lookDir);
    		TransformMatrix matCamera = TransformMatrix.pointAt(cam.getvPosition(),vTarget, cam.getvUp());
    		
    		TransformMatrix matView = TransformMatrix.Matrix_QuickInverse(matCamera);		
    		
    		
    		for(int j=0;j<mesh.getFaces().length;j++) {
    			Face face = mesh.getFaces()[j];
    			
    			Vector3 a = mesh.getVertices()[face.getA()];
    			Vector3 b = mesh.getVertices()[face.getB()];
    			Vector3 c = mesh.getVertices()[face.getC()];
    			
    			
    			Vector3[]	triProjected = {new Vector3(0,0,0),new Vector3(0,0,0),new Vector3(0,0,0)},
    						triTransformed = {new Vector3(0,0,0),new Vector3(0,0,0),new Vector3(0,0,0)},
    						triViewed = {new Vector3(0,0,0),new Vector3(0,0,0),new Vector3(0,0,0)};
    			
    			
    			triTransformed[0] = Vector3.transform(a, matWorld);
    			triTransformed[1] = Vector3.transform(b, matWorld);
    			triTransformed[2] = Vector3.transform(c, matWorld);
    			
    			
    			Vector3 normal,line1,line2;
    			
    			line1 = Vector3.vector_sub(triTransformed[1], triTransformed[0]);
    			line2 = Vector3.vector_sub(triTransformed[2], triTransformed[0]);
    			
    			normal = Vector3.cross_product(line1, line2);
    			
    			normal = Vector3.normalize(normal);
    			
    			Vector3 vCameraRay = Vector3.vector_sub(triTransformed[0], cam.getvPosition());
    			
    			double dot = Vector3.dot_product(normal, vCameraRay);
    			
    			if(dot < 0.0) {	
    			
    				
					
    				
    				triViewed[0] = Vector3.transform(triTransformed[0], matView);
    				triViewed[1] = Vector3.transform(triTransformed[1], matView);
    				triViewed[2] = Vector3.transform(triTransformed[2], matView);
    				
    				IndependFace[] clipped = clipAgainsPlane(new Vector3(0,0,cam.getdNear()), new Vector3(0,0,1),new IndependFace(triViewed[0],triViewed[1],triViewed[2]) ); 
    				
    				if(clipped != null)
    				for(int n=0;n<clipped.length;n++) {
    					
        				triProjected[0] = Vector3.transform(clipped[n].getA(), matProj);
        				triProjected[1] = Vector3.transform(clipped[n].getB(), matProj);
        				triProjected[2] = Vector3.transform(clipped[n].getC(), matProj);
        				
        				
        				triProjected[0] = Vector3.div(triProjected[0],triProjected[0].getW() );
        				triProjected[1] = Vector3.div(triProjected[1],triProjected[1].getW() );
        				triProjected[2] = Vector3.div(triProjected[2],triProjected[2].getW() );
        				        				
        				
        				triProjected[0].setX( triProjected[0].getX() );
        				triProjected[1].setX( triProjected[1].getX() );
        				triProjected[2].setX( triProjected[2].getX() );
        				
        				triProjected[0].setY( -triProjected[0].getY() );
        				triProjected[1].setY( -triProjected[1].getY() );
        				triProjected[2].setY( -triProjected[2].getY() );
        				
        				Vector3 vOffsetView = new Vector3(1,1,0);
        				
        				triProjected[0] = Vector3.vector_add(triProjected[0], vOffsetView);
        				triProjected[1] = Vector3.vector_add(triProjected[1], vOffsetView);
        				triProjected[2] = Vector3.vector_add(triProjected[2], vOffsetView);
        				//#######################################################################################################
        				double scale = 0.5;
        				triProjected[0].setX(  triProjected[0].getX()*scale*bmp.getWidth()); 
        				triProjected[0].setY(  triProjected[0].getY()*scale*bmp.getHeight()); 
        				
        				triProjected[1].setX(  triProjected[1].getX()*scale*bmp.getWidth()); 
        				triProjected[1].setY(  triProjected[1].getY()*scale*bmp.getHeight()); 
        				
        				triProjected[2].setX(  triProjected[2].getX()*scale*bmp.getWidth()); 
        				triProjected[2].setY(  triProjected[2].getY()*scale*bmp.getHeight()); 
        				
        				
        				IndependFace iface = new IndependFace(triProjected[0],triProjected[1],triProjected[2]);
        				iface.setColor(face.getColor());
        				
        				
        				facesToDraw.add(iface);
        				//drawFilledTriangle(triProjected[0].project(),triProjected[1].project(),triProjected[2].project(),Color.YELLOW);
        				
        				
    				}
    				
    			}
    		}
    		
    		
    		
    	}
    	
    	facesToDraw.removeIf(o1 -> ((o1.getA().getZ()+o1.getB().getZ()+o1.getC().getZ()) / 3.0) <= cam.getdNear() );
    	
    	Collections.sort(facesToDraw, new Comparator<IndependFace>() {

			@Override
			public int compare(IndependFace o1, IndependFace o2) {
				// TODO Auto-generated method stub
				double z1 = (o1.getA().getZ()+o1.getB().getZ()+o1.getC().getZ()) / 3.0;
				double z2 = (o2.getA().getZ()+o2.getB().getZ()+o2.getC().getZ()) / 3.0;
			
				if(z1 < z2)  return 1; else 
				if(z1 == z2) return 0; else return -1;
			}
    		
    	});
    	
    	
    	
    	
    	
    	for(IndependFace face: facesToDraw){
    		
    		ArrayDeque<IndependFace> toClip = new ArrayDeque<IndependFace>();
    		
    		toClip.push(face);
    		int nNew = 1;
    		
    		for(int i=0; i <4; i++) {
    			
    			IndependFace[] toAdd = new IndependFace[2];
    			while(nNew > 0) {
    				
    				IndependFace test = toClip.pop();
    				nNew--;
    				
    				switch(i) {
    				case 0: toAdd = clipAgainsPlane( new Vector3(0,0,0), new Vector3(0,1,0), test); break;
    				case 1: toAdd = clipAgainsPlane( new Vector3(0,bmp.getHeight()-1.0,0), new Vector3(0,-1,0), test); break;
    				case 2: toAdd = clipAgainsPlane( new Vector3(0,0,0), new Vector3(1,0,0), test); break;
    				case 3: toAdd = clipAgainsPlane( new Vector3(bmp.getWidth()-1.0,0,0), new Vector3(-1,0,0), test); break;
    				}
    				
    				if(toAdd != null)
    				for(int w = 0; w < toAdd.length; w++) {
    					toClip.push(toAdd[w]);
    				}
    				
    			}
    			
    			nNew = toClip.size();
    		}
    		
    		for(IndependFace f: toClip) {
    			drawFilledTriangle(f.getA().project(),f.getB().project(),f.getC().project(),f.getColor(),bmp);
    		}

    	}
    	
    	
 		facesToDraw.clear();
 		
 		render4views(cam,meshes);
     }
     
     private void drawTriangle(Vector2 pointA, Vector2 pointB, Vector2 pointC, Color c,BufferedImage img) {
    	 Path2D path = this.path(pointA, pointB, pointC);
    	 g2d = (Graphics2D) img.getGraphics();
         g2d.setColor(c);
         g2d.draw(path);
	}
     
     private void drawFilledTriangle(Vector2 pointA, Vector2 pointB, Vector2 pointC, Color c,BufferedImage img) {
    	 Path2D path = this.path(pointA, pointB, pointC);
    	 g2d = (Graphics2D) img.getGraphics();
         g2d.setColor(c);
        if(faces)g2d.fill(path);
         g2d.setColor(Color.RED);
        if(edges) g2d.draw(path);
	}
     
    private void drawFow(Camera cam,int type,BufferedImage img) {
    	
    	 double xRatio = front.getHeight() / 20.0;
 		double yRatio = front.getHeight() / 20.0;
    	 Vector3[] fov = cam.getFOW(9.0/16.0);
    	 Vector2[] fov2 = new Vector2[4];
 		
    	 for(int i=0;i<4;i++) {
    		 fov[i] = Vector3.multiply(fov[i], -1.0);
			 fov2[i] = new Vector2(front.getWidth()/2.0f + fov[i].project(type).getX()*xRatio, front.getHeight()/2.0f + fov[i].project(type).getY()*yRatio );
    	 }
    	 double x = ( front.getWidth()/2.0f + cam.getvPosition().project(type).getX()*xRatio ); 
 		double y = ( front.getHeight()/2.0f + cam.getvPosition().project(type).getY()*yRatio );
    	
 		
    	 drawFOWshape(img,fov2[0],fov2[1],fov2[2],fov2[3],new Vector2(x,y));
    }
     
    private void drawFOWshape(BufferedImage img, Vector2 a,Vector2 b,Vector2 c,Vector2 d,Vector2 e) {
    	 g2d = (Graphics2D) img.getGraphics();
    	 g2d.setColor(Color.GREEN);
    	
    	 g2d.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(), (int)b.getY());
    	 g2d.drawLine((int)b.getX(), (int)b.getY(), (int)c.getX(), (int)c.getY());
    	 g2d.drawLine((int)c.getX(), (int)c.getY(), (int)d.getX(), (int)d.getY());
    	 g2d.drawLine((int)d.getX(), (int)d.getY(), (int)a.getX(), (int)a.getY());
    	 
    	 g2d.setColor(Color.MAGENTA);
    	 g2d.drawLine((int)e.getX(), (int)e.getY(), (int)b.getX(), (int)b.getY());
    	 g2d.drawLine((int)e.getX(), (int)e.getY(), (int)c.getX(), (int)c.getY());
    	 g2d.drawLine((int)e.getX(), (int)e.getY(), (int)d.getX(), (int)d.getY());
    	 g2d.drawLine((int)e.getX(), (int)e.getY(), (int)a.getX(), (int)a.getY());
    	 
    	
    }

	private void render4views(Camera cam, Mesh[] meshes) {
		double xRatio = front.getHeight() / 20.0;
		double yRatio = front.getHeight() / 20.0;
		
		Clear(Color.BLACK,front);
		for(Mesh mesh: meshes) {
			
			for(Face face:mesh.getFaces()) {
				
				
				Vector3[] ver = new Vector3[3];
				ver[0] = new Vector3( mesh.getVertices()[face.getA()] );
    			ver[1] = new Vector3( mesh.getVertices()[face.getB()] );
    			ver[2] = new Vector3( mesh.getVertices()[face.getC()] );
				
    			
    		
    			
				ver[0].setX( front.getWidth()/2.0f + ver[0].getX()*xRatio ); 
				ver[0].setY( front.getHeight()/2.0f + ver[0].getY()*yRatio );  
				
				ver[1].setX( front.getWidth()/2.0f + ver[1].getX()*xRatio ); 
				ver[1].setY( front.getHeight()/2.0f + ver[1].getY()*yRatio );  
				
				ver[2].setX( front.getWidth()/2.0f + ver[2].getX()*xRatio ); 
				ver[2].setY( front.getHeight()/2.0f + ver[2].getY()*yRatio);  
				
				facesToDraw.add(new IndependFace(ver[0],ver[1],ver[2]) );
			}
			
			Collections.sort(facesToDraw, new Comparator<IndependFace>() {

				@Override
				public int compare(IndependFace o1, IndependFace o2) {
					// TODO Auto-generated method stub
					double z1 = (o1.getA().getZ()+o1.getB().getZ()+o1.getC().getZ()) / 3.0;
					double z2 = (o2.getA().getZ()+o2.getB().getZ()+o2.getC().getZ()) / 3.0;
				
					if(z1 < z2)  return 1; else 
					if(z1 == z2) return 0; else return -1;
				}
	    		
	    	});
			
			for(IndependFace iface: facesToDraw) {

				drawFilledTriangle(iface.getA().project(1),iface.getB().project(1),iface.getC().project(1),Color.WHITE,front);
			}
		
		}
		facesToDraw.clear();
		
		double x = ( front.getWidth()/2.0f + cam.getvPosition().getX()*xRatio ); 
		double y = ( front.getHeight()/2.0f + cam.getvPosition().getY()*yRatio );
		frontCam = new Vector2(x,y);
		
		x = ( front.getWidth()/2.0f + cam.getvTarget().getX()*-xRatio ); 
		y = ( front.getHeight()/2.0f + cam.getvTarget().getY()*-yRatio );
		frontCenter = new Vector2(x,y);
		
		drawFow(cam,1,front);
		
		Clear(Color.BLACK,side);
		

		for(Mesh mesh: meshes) {
			
			for(Face face:mesh.getFaces()) {
				
				
				Vector3[] ver = new Vector3[3];
				ver[0] = new Vector3( mesh.getVertices()[face.getA()] );
    			ver[1] = new Vector3( mesh.getVertices()[face.getB()] );
    			ver[2] = new Vector3( mesh.getVertices()[face.getC()] );
				
    			
    			
				ver[0].setZ( side.getWidth()/2.0f + ver[0].getZ()*xRatio ); 
				ver[0].setY( side.getHeight()/2.0f + ver[0].getY()*yRatio );  
				
				ver[1].setZ( side.getWidth()/2.0f + ver[1].getZ()*xRatio ); 
				ver[1].setY( side.getHeight()/2.0f + ver[1].getY()*yRatio );  
				
				ver[2].setZ( side.getWidth()/2.0f + ver[2].getZ()*xRatio ); 
				ver[2].setY( side.getHeight()/2.0f + ver[2].getY()*yRatio);  
				
				facesToDraw.add(new IndependFace(ver[0],ver[1],ver[2]) );
			}
			
			Collections.sort(facesToDraw, new Comparator<IndependFace>() {

				@Override
				public int compare(IndependFace o1, IndependFace o2) {
					// TODO Auto-generated method stub
					double z1 = (o1.getA().getX()+o1.getB().getX()+o1.getC().getX()) / 3.0;
					double z2 = (o2.getA().getX()+o2.getB().getX()+o2.getC().getX()) / 3.0;
				
					if(z1 < z2)  return 1; else 
					if(z1 == z2) return 0; else return -1;
				}
	    		
	    	});
			
			for(IndependFace iface: facesToDraw) {

				drawFilledTriangle(iface.getA().project(2),iface.getB().project(2),iface.getC().project(2),Color.WHITE,side);
			}
		
		}
		facesToDraw.clear();
		
		
		x = ( side.getWidth()/2.0f + cam.getvPosition().getZ()*xRatio ); 
		y = ( side.getHeight()/2.0f + cam.getvPosition().getY()*yRatio );
		sideCam = new Vector2(x,y);
		
		x = ( side.getWidth()/2.0f + cam.getvTarget().getZ()*-xRatio ); 
		y = ( side.getHeight()/2.0f + cam.getvTarget().getY()*-yRatio );
		sideCenter = new Vector2(x,y);
		
		drawFow(cam,2,side);
		
		Clear(Color.BLACK,top);
		
		for(Mesh mesh: meshes) {
			
			for(Face face:mesh.getFaces()) {
				
				
				Vector3[] ver = new Vector3[3];
				ver[0] = new Vector3( mesh.getVertices()[face.getA()] );
    			ver[1] = new Vector3( mesh.getVertices()[face.getB()] );
    			ver[2] = new Vector3( mesh.getVertices()[face.getC()] );
				
    			
    			
				ver[0].setX( top.getWidth()/2.0f + ver[0].getX()*xRatio ); 
				ver[0].setZ( top.getHeight()/2.0f + ver[0].getZ()*yRatio );  
				
				ver[1].setX( top.getWidth()/2.0f + ver[1].getX()*xRatio ); 
				ver[1].setZ( top.getHeight()/2.0f + ver[1].getZ()*yRatio );  
				
				ver[2].setX( top.getWidth()/2.0f + ver[2].getX()*xRatio ); 
				ver[2].setZ( top.getHeight()/2.0f + ver[2].getZ()*yRatio);  
				
				facesToDraw.add(new IndependFace(ver[0],ver[1],ver[2]) );
			}
			
			Collections.sort(facesToDraw, new Comparator<IndependFace>() {

				@Override
				public int compare(IndependFace o1, IndependFace o2) {
					// TODO Auto-generated method stub
					double z1 = (o1.getA().getY()+o1.getB().getY()+o1.getC().getY()) / 3.0;
					double z2 = (o2.getA().getY()+o2.getB().getY()+o2.getC().getY()) / 3.0;
				
					if(z1 < z2)  return 1; else 
					if(z1 == z2) return 0; else return -1;
				}
	    		
	    	});
			
			for(IndependFace iface: facesToDraw) {

				drawFilledTriangle(iface.getA().project(3),iface.getB().project(3),iface.getC().project(3),Color.WHITE,top);
			}
		
		}
		facesToDraw.clear();
		
		
		x = ( top.getWidth()/2.0f + cam.getvPosition().getX()*xRatio ); 
		y = ( top.getHeight()/2.0f + cam.getvPosition().getZ()*yRatio );
		topCam = new Vector2(x,y);
		
		x = ( top.getWidth()/2.0f + cam.getvTarget().getX()*-xRatio ); 
		y = ( top.getHeight()/2.0f + cam.getvTarget().getZ()*-yRatio );
		topCenter = new Vector2(x,y);
		
		
		drawFow(cam,3,top);
	}

	public BufferedImage[] getBufferedImages() {
		BufferedImage[] imgs= {front,side,top,bmp};
		
		
		return imgs;
	}
	
	public Vector2[] getCamCord() {
		Vector2[] camCord = {frontCam, sideCam, topCam};
		return camCord;
	}

	public Vector2[] getCenterCord() {
		Vector2[] camCord = {frontCenter, sideCenter, topCenter};
		return camCord;
	}
}
