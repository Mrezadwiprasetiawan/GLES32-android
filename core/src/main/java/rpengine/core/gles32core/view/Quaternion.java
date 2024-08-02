package rpengine.core.gles32core.view;

public class Quaternion {
  private float w, x, y, z;

  public Quaternion() {
    set(0, 0, 0, 1);
  }

  public void set(float x, float y, float z, float w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public void setFromAxisAngle(float axisX, float axisY, float axisZ, float angle) {
    float halfAngle = angle * 0.5f;
    float s = (float) Math.sin(halfAngle);
    w = (float) Math.cos(halfAngle);
    x = axisX * s;
    y = axisY * s;
    z = axisZ * s;
  }

  public void multiply(Quaternion q) {
    float newW = w * q.w - x * q.x - y * q.y - z * q.z;
    float newX = w * q.x + x * q.w + y * q.z - z * q.y;
    float newY = w * q.y - x * q.z + y * q.w + z * q.x;
    float newZ = w * q.z + x * q.y - y * q.x + z * q.w;
    w = newW;
    x = newX;
    y = newY;
    z = newZ;
  }

  public void toRotationMatrix(float[] matrix) {
    matrix[0] = 1 - 2 * y * y - 2 * z * z;
    matrix[1] = 2 * x * y - 2 * w * z;
    matrix[2] = 2 * x * z + 2 * w * y;
    matrix[3] = 0;

    matrix[4] = 2 * x * y + 2 * w * z;
    matrix[5] = 1 - 2 * x * x - 2 * z * z;
    matrix[6] = 2 * y * z - 2 * w * x;
    matrix[7] = 0;

    matrix[8] = 2 * x * z - 2 * w * y;
    matrix[9] = 2 * y * z + 2 * w * x;
    matrix[10] = 1 - 2 * x * x - 2 * y * y;
    matrix[11] = 0;

    matrix[12] = 0;
    matrix[13] = 0;
    matrix[14] = 0;
    matrix[15] = 1;
  }
}
