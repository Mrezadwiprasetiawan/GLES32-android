package rpengine.core.gles32core.view;

import android.opengl.Matrix;

public final class Projection {
  private float[] projection = new float[16];
  private int width, height;
  private float near, far;

  public void setup(int width, int height, float near, float far) {
    this.width = width;
    this.height = height;
    this.near = near;
    this.far = far;
	  update();
  }

  public void update() {
    Matrix.frustumM(
        projection, 0, -(float) width / height, (float) width / height, -1, 1, near, far);
  }

  public float[] Matrix() {
    return projection;
  }

  public int getWidth() {
    return this.width;
  }

  public void setWidth(int width) {
    this.width = width;
	  update();
  }

  public int getHeight() {
    return this.height;
  }

  public void setHeight(int height) {
    this.height = height;
	  update();
  }

  public float getNear() {
    return this.near;
  }

  public void setNear(float near) {
    this.near = near;
	  update();
  }

  public float getFar() {
    return this.far;
  }

  public void setFar(float far) {
    this.far = far;
	  update();
  }
}
