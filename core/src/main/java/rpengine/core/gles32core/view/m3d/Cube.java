package rpengine.core.gles32core.view.m3d;

import android.content.Context;
import android.opengl.GLES32;
import android.opengl.Matrix;
import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import rpengine.core.gles32core.Utils;
import rpengine.core.gles32core.view.Camera;
import rpengine.core.gles32core.view.Light;
import rpengine.core.gles32core.view.Model;
import rpengine.core.gles32core.view.Projection;
import rpengine.core.gles32core.view.Quaternion;

public class Cube implements Model {
  private final float[] cubeVertices = {
    // Positions         // Normals
    -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
     0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
     0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
     0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
    -0.5f,  0.5f, -0.5f,  0.0f,  0.0f, -1.0f,
    -0.5f, -0.5f, -0.5f,  0.0f,  0.0f, -1.0f,

    -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
     0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
     0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
     0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
    -0.5f,  0.5f,  0.5f,  0.0f,  0.0f,  1.0f,
    -0.5f, -0.5f,  0.5f,  0.0f,  0.0f,  1.0f,

    -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
    -0.5f,  0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
    -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
    -0.5f, -0.5f, -0.5f, -1.0f,  0.0f,  0.0f,
    -0.5f, -0.5f,  0.5f, -1.0f,  0.0f,  0.0f,
    -0.5f,  0.5f,  0.5f, -1.0f,  0.0f,  0.0f,

     0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
     0.5f,  0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
     0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
     0.5f, -0.5f, -0.5f,  1.0f,  0.0f,  0.0f,
     0.5f, -0.5f,  0.5f,  1.0f,  0.0f,  0.0f,
     0.5f,  0.5f,  0.5f,  1.0f,  0.0f,  0.0f,

    -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
     0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,
     0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
     0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
    -0.5f, -0.5f,  0.5f,  0.0f, -1.0f,  0.0f,
    -0.5f, -0.5f, -0.5f,  0.0f, -1.0f,  0.0f,

    -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
     0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f,
     0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
     0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
    -0.5f,  0.5f,  0.5f,  0.0f,  1.0f,  0.0f,
  -0.5f,  0.5f, -0.5f,  0.0f,  1.0f,  0.0f
};
  private int program;
  private int VAO, VBO;
  private final float[] mvpMatrix = new float[16];
  private final float[] modelMatrix = new float[16];
  private final float[] rotationMatrix=new float[16];
  private float[] viewMatrix;
  private float[] projectionMatrix;
  private Context context;
  private long oldTime=0;
  private Quaternion rotation=new Quaternion();

  public Cube(Context context){
		this.context=context;
	}
  @Override
  public void init(Light light) {
    try {
      program =
          Utils.createProgram(
              Utils.loadShaderAsset(context, "vs"), Utils.loadShaderAsset(context, "fs"));
    } catch (IOException e) {
      Log.e("Renderer", e.getMessage());
    }
    GLES32.glUseProgram(program);

    // Create VAO and VBO
    int[] vao = new int[1];
    int[] vbo = new int[1];
    GLES32.glGenVertexArrays(1, vao, 0);
    GLES32.glGenBuffers(1, vbo, 0);
    VAO = vao[0];
    VBO = vbo[0];

    GLES32.glBindVertexArray(VAO);

    GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, VBO);
    FloatBuffer vertexBuffer =
        ByteBuffer.allocateDirect(cubeVertices.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(cubeVertices);
    vertexBuffer.position(0);
    GLES32.glBufferData(
        GLES32.GL_ARRAY_BUFFER, cubeVertices.length * 4, vertexBuffer, GLES32.GL_STATIC_DRAW);

    GLES32.glVertexAttribPointer(0, 3, GLES32.GL_FLOAT, false, 6 * 4, 0);
    GLES32.glEnableVertexAttribArray(0);
    GLES32.glVertexAttribPointer(1, 3, GLES32.GL_FLOAT, false, 6 * 4, 3 * 4);
    GLES32.glEnableVertexAttribArray(1);

    GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
    GLES32.glBindVertexArray(0);
  }
  @Override
  public float[] modelMatrix() {
  	return modelMatrix;
  }
  
  @Override
  public void subInit() {
  	Matrix.setIdentityM(modelMatrix,0);
	  Matrix.setIdentityM(rotationMatrix,0);
	  rotation.set(0,0,0,1);
  }
  

  @Override
  public void draw(Projection projection, Camera camera) {
	
	  
	  long currentTime=System.currentTimeMillis();
	  if(oldTime!=0){
		  Log.i("","fps :"+(float)1000/(currentTime-oldTime));
		}
		oldTime=currentTime;
	  GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT|GLES32.GL_DEPTH_BUFFER_BIT);
	  GLES32.glUseProgram(program);
	  this.projectionMatrix = projection.Matrix();
    this.viewMatrix = camera.Matrix();
    Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, modelMatrix, 0);
    Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);

    int uMVPMatrixLocation = GLES32.glGetUniformLocation(program, "uMVPMatrix");
    GLES32.glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);

    int uModelMatrixLocation = GLES32.glGetUniformLocation(program, "uModelMatrix");
    GLES32.glUniformMatrix4fv(uModelMatrixLocation, 1, false, modelMatrix, 0);

    GLES32.glUniform3f(GLES32.glGetUniformLocation(program, "lightPos"), 0.0f, 1.0f, 3.0f);
    GLES32.glUniform3f(GLES32.glGetUniformLocation(program, "viewPos"), 0.0f, 0.0f, 3.0f);
    GLES32.glUniform3f(GLES32.glGetUniformLocation(program, "lightColor"), 1.0f, 1.0f, 1.0f);
    GLES32.glUniform3f(GLES32.glGetUniformLocation(program, "objectColor"), 0.3f, 0.8f, 0.3f);
	
    GLES32.glBindVertexArray(VAO);
    GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, 36);
    GLES32.glBindVertexArray(0);
  }
  
  public void rotate(float dx,float dy){
		Quaternion pitchQuat = new Quaternion();
    pitchQuat.setFromAxisAngle(1.0f, 0.0f, 0.0f, dy);

    Quaternion yawQuat = new Quaternion();
    yawQuat.setFromAxisAngle(0.0f, 1.0f, 0.0f, dx);

    // Combine quaternions
    rotation.multiply(pitchQuat);
    rotation.multiply(yawQuat);

    // Convert quaternion to rotation matrix
    rotation.toRotationMatrix(rotationMatrix);

    // Apply the rotation matrix to the view matrix
    Matrix.multiplyMM(modelMatrix,0,modelMatrix,0,rotationMatrix,0);
	}
}
