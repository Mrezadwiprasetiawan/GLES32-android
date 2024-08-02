package rpengine.core.gles32core.view;

import android.opengl.Matrix;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

public class Camera {
  private final float[] mViewMatrix = new float[16];
  private final float[] mRotationMatrix = new float[16];
  private final Quaternion mRotation = new Quaternion();

  
  public void setup(
      float eyeX,
      float eyeY,
      float eyeZ,
      float cX,
      float cY,
      float cZ,
      float uX,
      float uY,
      float uZ) {
    Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, cX, cY, cZ, uX, uY, uZ);
    Matrix.setIdentityM(mRotationMatrix, 0);
  }
  
  public void setIdentity(){
		Matrix.setIdentityM(mRotationMatrix,0);
		mRotation.set(0,0,0,1);
	}

  public float[] Matrix() {
    return mViewMatrix;
  }
  
  public void move(float[] offset){
		move(offset[0],offset[1],offset[2]);
	}
	public void move(float offsetX,float offsetY,float offsetZ){
		Matrix.translateM(mViewMatrix,0,offsetX,offsetY,offsetZ);
	}
	public void moveTo(float x, float y, float z){
		mViewMatrix[12]=x;
		mViewMatrix[13]=y;
		mViewMatrix[14]=z;
	}
  public void rotate(float deltaX, float deltaY) {
    // Create quaternions for rotation
    Quaternion pitchQuat = new Quaternion();
    pitchQuat.setFromAxisAngle(1.0f, 0.0f, 0.0f, deltaY);

    Quaternion yawQuat = new Quaternion();
    yawQuat.setFromAxisAngle(0.0f, 1.0f, 0.0f, deltaX);

    // Combine quaternions
    mRotation.multiply(pitchQuat);
    mRotation.multiply(yawQuat);

    // Convert quaternion to rotation matrix
    mRotation.toRotationMatrix(mRotationMatrix);

    // Apply the rotation matrix to the view matrix
    Matrix.multiplyMM(mViewMatrix, 0, mRotationMatrix, 0, mViewMatrix, 0);
  }
}
