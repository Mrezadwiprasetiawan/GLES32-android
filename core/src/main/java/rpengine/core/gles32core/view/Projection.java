package rpengine.core.gles32core.view;

import android.opengl.Matrix;

public final class Projection {
  private float[] projection = new float[16];
  private int width,height;
  private float near, far;
  
  public Projection(){
		
	}

  public void setup(int width, int height,float near, float far) {
	  this.width=width;
	  this.height=height;
	  this.near=near;
	  this.far=far;
    Matrix.frustumM(projection, 0,-(float)width / height,(float)width/height,-1,1,near, far);
  }
  public float[] Matrix(){
		return projection;
	}
}
