package rpengine.core.gles32core.view;
import android.content.Context;
import android.opengl.Matrix;

public abstract class GenericModel implements Model{
    private Quaternion rotation;
  private float[] modelMatrix=new float[16];
  private float[] rotationMatrix=new float[16];
    
    private Context context;
    private boolean first;
  @Override
  public void init(Light Light) {
  	// TODO: Implement this method
  }
  @Override
  public void setIdentity() {
  	// TODO: Implement this method
  }
  @Override
  public void draw(Projection projection, Camera camera) {
  	// TODO: Implement this method
  }
  @Override
  public float[] modelMatrix() {
  	return modelMatrix;
  }
  
	public void rotate(float deltaX, float deltaY) {
    // Create quaternions for rotation
    Quaternion pitchQuat = new Quaternion();
    pitchQuat.setFromAxisAngle(1.0f, 0.0f, 0.0f, deltaY);

    Quaternion yawQuat = new Quaternion();
    yawQuat.setFromAxisAngle(0.0f, 1.0f, 0.0f, deltaX);

    // Combine quaternions
    rotation.multiply(pitchQuat);
    rotation.multiply(yawQuat);

    // Convert quaternion to rotation matrix
    rotation.toRotationMatrix(rotationMatrix);

    // Apply the rotation matrix to the view matrix
    Matrix.multiplyMM(modelMatrix,0,modelMatrix,0,rotationMatrix,0);
  }
}
