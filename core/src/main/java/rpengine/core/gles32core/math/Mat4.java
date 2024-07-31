package rpengine.core.gles32core.math;

import android.opengl.Matrix;
import java.util.Arrays;

/**
 * Kelas ini merepresentasikan matriks 4x4.
 * Kelas ini menyediakan berbagai metode untuk manipulasi dan transformasi matriks 4x4, termasuk operasi dasar 
 * seperti identitas, skala, translasi, rotasi, serta operasi matriks lebih lanjut seperti perkalian, transposisi, 
 * dan inversi.
 */
public class Mat4 {
  private float[] m = new float[16];

  /**
   * Membuat matriks 4x4 dengan nilai default 0.0f.
   * Inisialisasi matriks dengan semua elemen diatur ke 0.0f.
   */
  public Mat4() {
    Default();
  }

  /**
   * Membuat matriks 4x4 dari array input.
   * 
   * @param input array yang berisi 16 elemen untuk menginisialisasi matriks
   * @throws IndexOutOfBoundsException jika input array kurang dari 16 elemen
   */
  public Mat4(float[] input) {
    if (input.length >= 16) {
      System.arraycopy(input, 0, m, 0, 16);
    } else {
      throw new IndexOutOfBoundsException("Array harus memiliki minimal 16 elemen.");
    }
  }

  /**
   * Membuat matriks 4x4 dari elemen-elemen individu.
   * 
   * @param m00 elemen baris pertama kolom pertama
   * @param m01 elemen baris pertama kolom kedua
   * @param m02 elemen baris pertama kolom ketiga
   * @param m03 elemen baris pertama kolom keempat
   * @param m10 elemen baris kedua kolom pertama
   * @param m11 elemen baris kedua kolom kedua
   * @param m12 elemen baris kedua kolom ketiga
   * @param m13 elemen baris kedua kolom keempat
   * @param m20 elemen baris ketiga kolom pertama
   * @param m21 elemen baris ketiga kolom kedua
   * @param m22 elemen baris ketiga kolom ketiga
   * @param m23 elemen baris ketiga kolom keempat
   * @param m30 elemen baris keempat kolom pertama
   * @param m31 elemen baris keempat kolom kedua
   * @param m32 elemen baris keempat kolom ketiga
   * @param m33 elemen baris keempat kolom keempat
   */
  public Mat4(
      float m00, float m01, float m02, float m03,
      float m10, float m11, float m12, float m13,
      float m20, float m21, float m22, float m23,
      float m30, float m31, float m32, float m33) {
    m[0] = m00; m[1] = m01; m[2] = m02; m[3] = m03;
    m[4] = m10; m[5] = m11; m[6] = m12; m[7] = m13;
    m[8] = m20; m[9] = m21; m[10] = m22; m[11] = m23;
    m[12] = m30; m[13] = m31; m[14] = m32; m[15] = m33;
  }

  private void Default() {
    Arrays.fill(m, 0.0f);
  }
  
  /**
   * Mengatur matriks ini ke matriks identitas.
   * Matriks identitas memiliki 1 pada elemen diagonal dan 0 pada elemen lainnya.
   */
  public void setIdentity() {
    Matrix.setIdentityM(m, 0);
  }
  
  /**
   * Mengaplikasikan transformasi skala ke matriks ini.
   * 
   * @param s vektor skala yang berisi nilai skala pada sumbu x, y, dan z
   */
  public void Scale(Vec3 s) {
    Matrix.scaleM(m, 0, s.x, s.y, s.z);
  }
  
  /**
   * Mengaplikasikan transformasi translasi ke matriks ini.
   * 
   * @param offset vektor yang menentukan pergeseran pada sumbu x, y, dan z
   */
  public void Translate(Vec3 offset) {
    Matrix.translateM(m, 0, offset.x, offset.y, offset.z);
  }
  
  /**
   * Mengaplikasikan transformasi rotasi ke matriks ini.
   * 
   * @param a sudut rotasi dalam derajat
   * @param cord vektor yang menentukan sumbu rotasi
   */
  public void Rotate(float a, Vec3 cord) {
    Matrix.rotateM(m, 0, a, cord.x, cord.y, cord.z);
  }
  
  /**
   * Mengatur matriks ini sebagai matriks pandangan dengan posisi kamera, titik fokus, dan vektor up.
   * 
   * @param eye vektor posisi kamera
   * @param center vektor titik fokus
   * @param up vektor up yang menentukan orientasi vertikal
   */
  public void setLookAt(Vec3 eye, Vec3 center, Vec3 up) {
    Matrix.setLookAtM(m, 0, eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z);
  }
  
  /**
   * Mengalikan matriks ini dengan matriks lain.
   * 
   * @param rmat4 matriks lain yang akan dikalikan dengan matriks ini
   */
  public void Multiply(Mat4 rmat4) {
    Matrix.multiplyMM(m, 0, m, 0, rmat4.actual(), 0);
  }

  /**
   * Mengalikan matriks ini dengan vektor.
   * 
   * @param vec vektor yang akan dikalikan dengan matriks ini
   */
  public void Multiply(Vec4 vec) {
    Matrix.multiplyMV(m, 0, m, 0, vec.actual(), 0);
  }
  
  /**
   * Mentrasposisi matriks ini.
   * Matriks transposisi mengubah baris menjadi kolom dan sebaliknya.
   */
  public void Transpose() {
    Matrix.transposeM(m, 0, m, 0);
  }
  
  /**
   * Menghitung invers dari matriks ini.
   * 
   * @return true jika inversi berhasil, false jika tidak bisa dihitung
   */
  public boolean Invert() {
    return Matrix.invertM(m, 0, m, 0);
  }
  
  /**
   * Mengatur matriks ini sebagai matriks proyeksi frustum.
   * 
   * @param left batas kiri frustum
   * @param right batas kanan frustum
   * @param bot batas bawah frustum
   * @param top batas atas frustum
   * @param near jarak dekat frustum
   * @param far jarak jauh frustum
   */
  protected void Frustum(float left, float right, float bot, float top, float near, float far) {
    Matrix.frustumM(m, 0, left, right, bot, top, near, far);
  }

  /**
   * Mengatur matriks ini sebagai matriks proyeksi perspektif.
   * 
   * @param fovy sudut pandang vertikal dalam derajat
   * @param aspect rasio aspek dari jendela tampilan
   * @param near jarak dekat dari kamera
   * @param far jarak jauh dari kamera
   */
  protected void Perspective(float fovy, float aspect, float near, float far) {
    Matrix.perspectiveM(m, 0, fovy, aspect, near, far);
  }

  /**
   * Mengatur matriks ini sebagai matriks proyeksi ortogonal.
   * 
   * @param left batas kiri proyeksi ortogonal
   * @param right batas kanan proyeksi ortogonal
   * @param bot batas bawah proyeksi ortogonal
   * @param top batas atas proyeksi ortogonal
   * @param near jarak dekat proyeksi ortogonal
   * @param far jarak jauh proyeksi ortogonal
   */
  protected void Ortho(float left, float right, float bot, float top, float near, float far) {
    Matrix.orthoM(m, 0, left, right, bot, top, near, far);
  }
  
  /**
   * Mengembalikan array float yang merepresentasikan matriks 4x4.
   * 
   * @return array float yang berisi elemen-elemen matriks
   */
  public float[] actual() {
    return m.clone();
  }

  /**
   * Mengonversi matriks 4x4 ke matriks normal 3x3.
   * Matriks normal sering digunakan untuk transformasi normal dalam grafika 3D.
   * 
   * @return objek Mat3 yang merepresentasikan matriks normal
   */
  public Mat3 toNormalMatrix() {
    float[] tmpMat = new float[16];
    Matrix.invertM(tmpMat, 0, actual(), 0);
    Matrix.transposeM(tmpMat, 0, tmpMat, 0);
    return new Mat4(tmpMat).toMat3();
  }

  /**
   * Mengonversi matriks 4x4 ke matriks 3x3.
   * 
   * @return objek Mat3 yang merepresentasikan matriks 3x3
   */
  public Mat3 toMat3() {
    return new Mat3(
      m[0], m[1], m[2],
      m[4], m[5], m[6],
      m[8], m[9], m[10]
    );
  }
}