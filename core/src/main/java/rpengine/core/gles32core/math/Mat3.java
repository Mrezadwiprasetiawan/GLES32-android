package rpengine.core.gles32core.math;
import java.util.Arrays;

/**
 * Kelas ini merepresentasikan matriks 3x3.
 */
public class Mat3 {
  private float[] m = new float[9];

  /**
   * Membuat matriks 3x3 dengan nilai identitas jika parameter true,
   * atau nilai default 0.0f jika parameter false.
   * 
   * @param identity jika true, matriks diatur ke nilai identitas
   */
  public Mat3(boolean identity) {
    if (identity) {
      m[0] = m[4] = m[8] = 1.0f;
      for (int i = 1; i < 8; i++) {
        m[i] = 0.0f;
      }
    } else {
      Default();
    }
  }
  
  /**
   * Membuat matriks 3x3 dari array input.
   * 
   * @param input array yang berisi 9 elemen
   * @throws IndexOutOfBoundsException jika input array kurang dari 9 elemen
   */
  public Mat3(float[] input) {
    if (input.length >= 9) {
      System.arraycopy(input, 0, m, 0, 9);
    } else {
      throw new IndexOutOfBoundsException("Array harus memiliki minimal 9 elemen.");
    }
  }
  
  /**
   * Membuat matriks 3x3 dari elemen-elemen individu.
   */
  public Mat3(
      float m00, float m01, float m02,
      float m10, float m11, float m12,
      float m20, float m21, float m22) {
    m[0] = m00; m[1] = m01; m[2] = m02;
    m[3] = m10; m[4] = m11; m[5] = m12;
    m[6] = m20; m[7] = m21; m[8] = m22;
  }

  private void Default() {
    Arrays.fill(m,0.0f);
  }
  
  
  /**
   * Mengembalikan array float yang merepresentasikan matriks 3x3.
   * 
   * @return array float yang berisi elemen-elemen matriks
   */
  public float[] actual() {
    return m.clone();
  }
}