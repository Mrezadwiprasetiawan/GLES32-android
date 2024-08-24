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
import rpengine.core.gles32core.view.GenericModel;
import rpengine.core.gles32core.view.Light;
import rpengine.core.gles32core.view.Model;
import rpengine.core.gles32core.view.Projection;
public class Cube extends GenericModel{
  private final float[] data = {
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
  

  public Cube(Context context){
		super(context);
		setVertices(data,3,0,6);
		setNormals(data,3,3,6);
	}
	
  @Override
  protected void setupShader() {
  	try {
      program =
          Utils.createProgram(
              Utils.loadShaderAsset(getContext(), "vs"), Utils.loadShaderAsset(getContext(), "fs"));
    } catch (IOException e) {
      Log.e("Renderer", e.getMessage());
    }
  }
  @Override
  protected void setupBindVertexArray() {
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
        ByteBuffer.allocateDirect(data.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(data);
    vertexBuffer.position(0);
    GLES32.glBufferData(
        GLES32.GL_ARRAY_BUFFER, data.length * 4, vertexBuffer, GLES32.GL_STATIC_DRAW);

    GLES32.glVertexAttribPointer(GLES32.glGetAttribLocation(program,"vert"), 3, GLES32.GL_FLOAT, false, 6 * 4, 0);
    GLES32.glEnableVertexAttribArray(GLES32.glGetAttribLocation(program,"vert"));
    GLES32.glVertexAttribPointer(GLES32.glGetAttribLocation(program,"norm"), 3, GLES32.GL_FLOAT, false, 6 * 4, 3 * 4);
    GLES32.glEnableVertexAttribArray(GLES32.glGetAttribLocation(program,"norm"));

    GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0);
    GLES32.glBindVertexArray(0);
  }
}
