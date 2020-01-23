package Model;

import java.math.BigDecimal;

public class TransformMatrix {
	
	private double[][] matrix;
	
	
	
	public TransformMatrix() {
		matrix = new double[4][4];
		fillWitchZero();
		
	}
	
	public TransformMatrix(double[][] a) {

		matrix = new double[4][4];
		for (int i = 0; i < matrix.length; i++)
            System.arraycopy(a[i], 0, this.matrix[i], 0, a.length);
		
	}
	
	public void fillWitchZero() {
		for(int i=0;i<4;i++) {
			matrix[0][i] = 0.0;
			matrix[1][i] = 0.0;
			matrix[2][i] = 0.0;
			matrix[3][i] = 0.0;
		}
	}
	
	public void diagonalMatrix() {
		fillWitchZero();
		
		matrix[0][0] = 1.0;
		matrix[1][1] = 1.0;
		matrix[2][2] = 1.0;
		matrix[3][3] = 1.0;
	}

		
	double[][] getTransformMatrix(){
		return matrix;
	}
	
	
	
	
	public void print() {
		System.out.println("========================================");
		for(int a=0;a<matrix.length;a++) {
			
			for(int b=0;b<matrix[a].length;b++) {
				System.out.print(" [ "+matrix[a][b]+" ]");
			}
			
			System.out.println("");
			
		}
		System.out.println("========================================");
		
	}


	
	public void setTransformMatrix(double[][] mult) {
		this.matrix = mult;
		
	}

	
	public Vector3 multiply(Vector3 vector) {
		
		
		
		double x = vector.getX()*matrix[0][0]+vector.getY()*matrix[1][0]+vector.getZ()*matrix[2][0]+vector.getW()*matrix[3][0];
		double y = vector.getX()*matrix[0][1]+vector.getY()*matrix[1][1]+vector.getZ()*matrix[2][1]+vector.getW()*matrix[3][1];
		double z = vector.getX()*matrix[0][2]+vector.getY()*matrix[1][2]+vector.getZ()*matrix[2][2]+vector.getW()*matrix[3][2];
		double w = vector.getX()*matrix[0][3]+vector.getY()*matrix[1][3]+vector.getZ()*matrix[2][3]+vector.getW()*matrix[3][3];
		
		
		return new Vector3(x,y,z);
	
	}
	
	//############### STATIC ##########################################

	public static Vector3 multiplyVector(TransformMatrix m, Vector3 vector) {
		
		
		double x = vector.getX()*m.matrix[0][0]+vector.getY()*m.matrix[1][0]+vector.getZ()*m.matrix[2][0]+vector.getW()*m.matrix[3][0];
		double y = vector.getX()*m.matrix[0][1]+vector.getY()*m.matrix[1][1]+vector.getZ()*m.matrix[2][1]+vector.getW()*m.matrix[3][1];
		double z = vector.getX()*m.matrix[0][2]+vector.getY()*m.matrix[1][2]+vector.getZ()*m.matrix[2][2]+vector.getW()*m.matrix[3][2];
		double w = vector.getX()*m.matrix[0][3]+vector.getY()*m.matrix[1][3]+vector.getZ()*m.matrix[2][3]+vector.getW()*m.matrix[3][3];
		
		return  new Vector3(x,y,z,w);
	}
	
	public static TransformMatrix getDiagonalMatrix() {
		double[][] m = new double[4][4];
		
		
		for(int i=0;i<4;i++) {
			m[0][i] = 0.0;
			m[1][i] = 0.0;
			m[2][i] = 0.0;
			m[3][i] = 0.0;
		}
		
		m[0][0] = 1.0;
		m[1][1] = 1.0;
		m[2][2] = 1.0;
		m[3][3] = 1.0;
		
		return new TransformMatrix(m);
	}
	
	public static TransformMatrix makeRotationX(double angleRad) {
		TransformMatrix mat = new TransformMatrix();
		
		mat.matrix[0][0] = 1.0;
		mat.matrix[1][1] = Math.cos(angleRad);
		mat.matrix[1][2] = Math.sin(angleRad);
		mat.matrix[2][1] = -Math.sin(angleRad);
		mat.matrix[2][2] = Math.cos(angleRad);
		mat.matrix[3][3] = 1.0;
		
		return mat;
	}
	

	public static TransformMatrix makeRotationY(double angleRad) {
		TransformMatrix mat = new TransformMatrix();
		
		mat.matrix[0][0] = Math.cos(angleRad);
		mat.matrix[0][2] = Math.sin(angleRad);
		mat.matrix[2][0] = -Math.sin(angleRad);
		mat.matrix[1][1] = 1.0;
		mat.matrix[2][2] = Math.cos(angleRad);
		mat.matrix[3][3] = 1.0;
		
		return mat;
	}
	

	public static TransformMatrix makeRotationZ(double angleRad) {
		TransformMatrix mat = new TransformMatrix();
		
		mat.matrix[0][0] = Math.cos(angleRad);
		mat.matrix[0][1] = Math.sin(angleRad);
		mat.matrix[1][0] = -Math.sin(angleRad);
		mat.matrix[1][1] = Math.cos(angleRad);
		mat.matrix[2][2] = 1.0;
		mat.matrix[3][3] = 1.0;
		
		return mat;
	}
	
	
	public static TransformMatrix makeTranslation(double x, double y, double z) {
		TransformMatrix mat = new TransformMatrix();
		
		mat.matrix[0][0] = 1.0;
		mat.matrix[1][1] = 1.0;
		mat.matrix[2][2] = 1.0;
		mat.matrix[3][3] = 1.0;
		
		mat.matrix[3][0] = x;
		mat.matrix[3][1] = y;
		mat.matrix[3][2] = z;
		
		return mat;
	}
	
	public static TransformMatrix makeProjection(double dFovRad, double dAspectRatio, double dNear, double dFar) {
		TransformMatrix mat = new TransformMatrix();
		
		double dFov = 1.0 / Math.tan(dFovRad);
		
		
		mat.matrix[0][0] = dAspectRatio * dFov;
		mat.matrix[1][1] = dFov;
		mat.matrix[2][2] = dFar / (dFar-dNear);
		mat.matrix[3][2] = (-1.0*dFar * dNear) / (dFar - dNear);
		mat.matrix[2][3] = 1.0;
		mat.matrix[3][3] = 0.0;
		
		return mat;
	}
	
	public static TransformMatrix Matrix_QuickInverse(TransformMatrix mat) // Only for Rotation/Translation Matrices
	{
		double[][] mTrix = new double[4][4];
		double[][] m = mat.getTransformMatrix();
		mTrix[0][0] = m[0][0]; mTrix[0][1] = m[1][0]; mTrix[0][2] = m[2][0]; mTrix[0][3] = 0.0f;
		mTrix[1][0] = m[0][1]; mTrix[1][1] = m[1][1]; mTrix[1][2] = m[2][1]; mTrix[1][3] = 0.0f;
		mTrix[2][0] = m[0][2]; mTrix[2][1] = m[1][2]; mTrix[2][2] = m[2][2]; mTrix[2][3] = 0.0f;
		
		mTrix[3][0] = -(m[3][0] * mTrix[0][0] + m[3][1] * mTrix[1][0] + m[3][2] * mTrix[2][0]);
		mTrix[3][1] = -(m[3][0] * mTrix[0][1] + m[3][1] * mTrix[1][1] + m[3][2] * mTrix[2][1]);
		mTrix[3][2] = -(m[3][0] * mTrix[0][2] + m[3][1] * mTrix[1][2] + m[3][2] * mTrix[2][2]);
		mTrix[3][3] = 1.0f;
		return new TransformMatrix(mTrix);
	}
	
	public static TransformMatrix Transpose(TransformMatrix mat) 
	{
		double[][] mTrix = new double[4][4];
		double[][] m = mat.getTransformMatrix();
		mTrix[0][0] = m[0][0]; mTrix[0][1] = m[1][0]; mTrix[0][2] = m[2][0]; mTrix[0][3] = mTrix[3][0];
		mTrix[1][0] = m[0][1]; mTrix[1][1] = m[1][1]; mTrix[1][2] = m[2][1]; mTrix[1][3] = mTrix[3][1];
		mTrix[2][0] = m[0][2]; mTrix[2][1] = m[1][2]; mTrix[2][2] = m[2][2]; mTrix[2][3] = mTrix[3][2];
		mTrix[3][0] = m[0][3]; mTrix[3][1] = m[1][3]; mTrix[3][2] = m[2][3]; mTrix[2][3] = mTrix[3][3];
		
		return new TransformMatrix(mTrix);
	}
	
	
	public static TransformMatrix pointAt( Vector3 pos, Vector3 target, Vector3 up ) {
		
		TransformMatrix m = new TransformMatrix();
		
		// New Forward direction
		Vector3 newForward = Vector3.vector_sub(target, pos);
		newForward = Vector3.normalize(newForward);
		
		
		// new Up direction
		Vector3 a = Vector3.multiply(newForward, Vector3.dot_product(up, newForward));
		Vector3 newUp = Vector3.vector_sub(up, a);
		newUp = Vector3.normalize(newUp);
		
		// New Right direction
		Vector3 newRight = Vector3.cross_product(newUp, newForward);
 		
 		double[][] matrix = new double[4][4];
 		
 		m.matrix[0][0] = newRight.getX();
 		m.matrix[1][0] = newUp.getX();
 		m.matrix[2][0] = newForward.getX();
 		m.matrix[3][0] = pos.getX();

 		m.matrix[0][1] = newRight.getY();
 		m.matrix[1][1] = newUp.getY();
 		m.matrix[2][1] = newForward.getY();
 		m.matrix[3][1] = pos.getY();
 		
 		m.matrix[0][2] = newRight.getZ();
 		m.matrix[1][2] = newUp.getZ();
 		m.matrix[2][2] = newForward.getZ();
 		m.matrix[3][2] = pos.getZ();
 		

 		m.matrix[0][3] = 0.0;
 		m.matrix[1][3] = 0.0;
 		m.matrix[2][3] = 0.0;
 		m.matrix[3][3] = 1.0;
 	
 		return m;
 		
	}
	
	public static TransformMatrix Matrix_MultiplyMatrix(TransformMatrix m1, TransformMatrix m2)
	{
		double[][] mTrix = new double[4][4];
		for (int c = 0; c < 4; c++)
			for (int r = 0; r < 4; r++)
				mTrix[r][c] = m1.matrix[r][0] * m2.matrix[0][c] + m1.matrix[r][1] * m2.matrix[1][c] + m1.matrix[r][2] * m2.matrix[2][c] + m1.matrix[r][3] * m2.matrix[3][c];
		return new TransformMatrix(mTrix);
	}

	
}

/*
 * 
 * 

 
 
 */
