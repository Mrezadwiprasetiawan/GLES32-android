package rpengine.core.gles32core;

import android.content.Context;
import android.opengl.GLES32;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
  public static final String VERSION = "0.2 alpha";
  public static final int VERSION_NUMBER = 2;

  public static String loadShaderAsset(Context context, String filename) throws IOException {
    StringBuilder shaderSource = new StringBuilder();
    // inputStream diambil dari context.getAssets().open(String filename atau path)
    InputStream inputStream = context.getAssets().open(filename);
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    // membaca per baris sekaligus menambahkan newline agar tetap sama dengan file sumber
    String line;
    while ((line = reader.readLine()) != null) {
      shaderSource.append(line).append("\n");
    }

    reader.close();
    // mengembalikan String yang dihasilkan
    return shaderSource.toString();
  }

  public static int loadShader(int type, String shaderCode) {
    // Buat shader berdasarkan tipe (vertex atau fragment)
    int shader = GLES32.glCreateShader(type);

    // Sertakan kode shader ke dalam shader object
    GLES32.glShaderSource(shader, shaderCode);

    // Kompilasi shader
    GLES32.glCompileShader(shader);

    // Periksa kompilasi shader untuk kesalahan
    final int[] compileStatus = new int[1];
    String log = GLES32.glGetShaderInfoLog(shader);
    GLES32.glGetShaderiv(shader, GLES32.GL_COMPILE_STATUS, compileStatus, 0);
    Log.e("Utils Shader" + type, log);
    if (compileStatus[0] == 0) {
      GLES32.glDeleteShader(shader);
      throw new RuntimeException("Error compiling shader: " + log);
    }

    return shader;
  }

  public static int createProgram(String vertexShaderCode, String fragmentShaderCode) {
    // Load dan kompilasi shader
    int vertexShader = loadShader(GLES32.GL_VERTEX_SHADER, vertexShaderCode);
    int fragmentShader = loadShader(GLES32.GL_FRAGMENT_SHADER, fragmentShaderCode);

    // Buat program
    int program = GLES32.glCreateProgram();

    // Lampirkan shader ke program
    GLES32.glAttachShader(program, vertexShader);
    GLES32.glAttachShader(program, fragmentShader);

    // Hubungkan program
    GLES32.glLinkProgram(program);

    // Periksa program untuk kesalahan
    final int[] linkStatus = new int[1];
    GLES32.glGetProgramiv(program, GLES32.GL_LINK_STATUS, linkStatus, 0);
    GLES32.glValidateProgram(program);
    String log = GLES32.glGetProgramInfoLog(program);
    Log.e("Utils CreateProgram", log);
    if (linkStatus[0] == 0) {
      GLES32.glDeleteProgram(program);
      throw new RuntimeException("Error linking program: " + log);
    }

    return program;
  }
}
