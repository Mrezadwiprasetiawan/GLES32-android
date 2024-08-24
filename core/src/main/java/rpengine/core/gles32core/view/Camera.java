package rpengine.core.gles32core.view;

import android.opengl.Matrix;

public class Camera {
  private final float[] ViewMatrix;

  public Camera() {
    ViewMatrix = new float[16];
  }

  public float[] Matrix() {
    return ViewMatrix;
  }

  public void moveTo(float x, float y, float z) {
    moveTo(x, y, z, 0.0f, 0.0f, 0.0f);
  }

  public void moveTo(float x, float y, float z, float cx, float cy, float cz) {
    moveTo(x, y, z, cx, cy, cz, 0.0f, 1.0f, 0.0f);
  }

  public void moveTo(
      float eyeX,
      float eyeY,
      float eyeZ,
      float cX,
      float cY,
      float cZ,
      float uX,
      float uY,
      float uZ) {
    Matrix.setLookAtM(ViewMatrix, 0, eyeX, eyeY, eyeZ, cX, cY, cZ, uX, uY, uZ);
  }

  public void moveX(float offset){
		move(offset,0,0);
	}
	public void moveY(float offset){
		move(0,offset,0);
	}
	public void moveZ(float offset){
		move(0,0,offset);
	}
  public void move(float[] offset) {
    move(offset[0], offset[1], offset[2]);
  }

  public void move(float offsetX, float offsetY, float offsetZ) {
    Matrix.translateM(ViewMatrix, 0, offsetX, offsetY, offsetZ);
  }

  public void rotate(float dx, float dy) {
    float len = (float) Math.sqrt((dx * dx + dy * dy));
    Matrix.rotateM(ViewMatrix, 0, len, dy, dx, 0);
  }
  public float[] position(){
	  float[] tmpMatrix=new float[16];
	  Matrix.invertM(tmpMatrix,0,ViewMatrix,0);
	  Matrix.transposeM(tmpMatrix,0,tmpMatrix,0);
		return new float[]{tmpMatrix[12],tmpMatrix[13],tmpMatrix[14]};
	}
}
