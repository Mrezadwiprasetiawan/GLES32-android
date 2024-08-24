package rpengine.core.gles32core.view;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES32;
import android.opengl.Matrix;

public abstract class GenericModel implements Model {
  private Context context;
  private final float[] modelMatrix = new float[16];
  private final float[] normalMatrix = new float[16];
  private final float[] MVPMatrix = new float[16];
  private final float[] currRot = new float[16];
  private final float[] accRot = new float[16];
  private final float density;
  private Light light;
  protected int VAO;
  protected int VBO;
  protected int program;
  private float[] vertices;
  private float[] normals;
  private Color color;

  public GenericModel(Context context) {
    this.context = context;
    density = context.getResources().getDisplayMetrics().density;
  }

  protected void setVertices(float[] vertices) {
    this.vertices = vertices;
  }

  protected void setNormals(float[] normals) {
    this.normals = normals;
  }

  protected void setVertices(float[] data, int count, int offset, int stride) {
    if (data == null || data.length == 0) return;

    int numVertices = (data.length - offset) / stride * count;
    vertices = new float[numVertices];

    for (int i = 0, j = offset; i < numVertices; i += count, j += stride) {
      System.arraycopy(data, j, vertices, i, count);
    }
  }

  protected void setNormals(float[] data, int count, int offset, int stride) {
    if (data == null || data.length == 0) return;

    int numNormals = (data.length - offset) / stride * count;
    normals = new float[numNormals];

    for (int i = 0, j = offset; i < numNormals; i += count, j += stride) {
      System.arraycopy(data, j, normals, i, count);
    }
  }

  protected Context getContext() {
    return context;
  }

  @Override
  public void init(Light light) {
    Matrix.setIdentityM(accRot, 0);
    /*
     Shader harus memiliki setidaknya:
    in vec3 VertPos = vektor Posisi Vertices
    in vec3 Normals = vektor Normal vertices
     Uniform mat4 MVPmatrix = Matrix yang menyimpan nilai transformasi objek dari ruang objek ke ruang dunia ke ruang layar
	  Uniform mat4 ModelMatrix = Matrix yang menyimpan transformasi objek di ruang objek itu sendiri
     Uniform mat3 normalMatrix = Matrix yang menyimpan vektor normal permukaan objek
     in vec3 LightPos = vektor yang menyimpan posisi cahaya
     in vec3 LightCol = vektor yang menyimpan warna cahaya
     in vec3 ObjectCol = vektor yang menyimpan warna objek
     */
    setupShader();
    this.light = light;
    setupBindVertexArray();
  }

  @Override
  public void setIdentity() {
    Matrix.setIdentityM(modelMatrix, 0);
  }

  @Override
  public void rotate(float dx, float dy, float sensitivity) {
    Matrix.setIdentityM(currRot, 0);
    Matrix.rotateM(currRot, 0, dx / density * sensitivity, 0, 1.0f, 0);
    Matrix.rotateM(currRot, 0, dy / density * sensitivity, 1.0f, 0, 0);
    Matrix.multiplyMM(accRot, 0, currRot, 0, accRot, 0);
    Matrix.multiplyMM(modelMatrix, 0, modelMatrix, 0, accRot, 0);
  }

  private void calcMVP(Projection projection, Camera camera) {
    calcNormalMatrix();
    Matrix.multiplyMM(MVPMatrix, 0, camera.Matrix(), 0, modelMatrix, 0);
    Matrix.multiplyMM(MVPMatrix, 0, projection.Matrix(), 0, MVPMatrix, 0);
    GLES32.glUniformMatrix4fv(
        GLES32.glGetUniformLocation(program, "MVPmatrix"), 1, false, MVPMatrix, 0);
    GLES32.glUniformMatrix4fv(
        GLES32.glGetUniformLocation(program, "ModelMatrix"), 1, false, modelMatrix, 0);
    GLES32.glUniformMatrix3fv(
        GLES32.glGetUniformLocation(program, "NormalMatrix"), 1, false, normalMatrix, 0);
    GLES32.glUniform3fv(
        GLES32.glGetUniformLocation(program, "LightPos"), 1, light.getPosition(), 0);
    GLES32.glUniform3f(
        GLES32.glGetUniformLocation(program, "LightCol"),
        Color.red(light.getColor()) / 255.0f,
        Color.green(light.getColor()) / 255.0f,
        Color.blue(light.getColor()) / 255.0f);
    GLES32.glUniform3fv(GLES32.glGetUniformLocation(program, "ViewPos"), 1, camera.position(), 0);
    GLES32.glUniform3f(
        GLES32.glGetUniformLocation(program, "ObjectCol"),
        color.red(),
        color.green(),
        color.blue());
  }

  protected abstract void setupShader();

  protected abstract void setupBindVertexArray();

  private void calcNormalMatrix() {
    final float[] tmpMatrix = new float[16];
    Matrix.invertM(tmpMatrix, 0, modelMatrix, 0);
    Matrix.transposeM(tmpMatrix, 0, tmpMatrix, 0);
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        normalMatrix[row * 3 + col] = tmpMatrix[row * 4 + col]; // Elemen 3x3 dari matriks 4x4
      }
    }
  }

  @Override
  public float[] MVP() {
    return MVPMatrix;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  protected void CreateAndBindVao() {
    final int vao[] = new int[1];
    final int vbo[] = new int[1];
    GLES32.glGenVertexArrays(1, vao, 0);
    GLES32.glGenBuffers(1, vbo, 0);
    VAO = vao[0];
    VBO = vbo[0];
    GLES32.glBindVertexArray(VAO);
  }

  @Override
  public void draw(Projection projection, Camera camera) {
    calcMVP(projection, camera);
    GLES32.glBindVertexArray(VAO);
    GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, vertices.length / 3);
    GLES32.glBindVertexArray(0);
  }

  protected float[] getVertices() {
    return vertices;
  }

  protected float[] getNormals() {
    return normals;
  }

  protected float[] getNormalMatrix() {
    return normalMatrix;
  }
}
