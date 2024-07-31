package rpengine.core.gles32core.math;

/**
 * Kelas `Func` menyediakan berbagai metode utilitas untuk operasi matematika vektor.
 */
public class Func {

    /**
     * Menormalisasi vektor 3D.
     * <p>
     * Menghitung vektor unit dari vektor input dengan membagi setiap komponen
     * vektor dengan panjangnya. Jika panjangnya nol, akan mengembalikan vektor (0, 0, 0)
     * untuk menghindari pembagian dengan nol.
     * </p>
     * 
     * @param vec3 Vektor 3D yang akan dinormalisasi.
     * @return Vektor 3D yang telah dinormalisasi.
     */
    public static Vec3 normalize(Vec3 vec3) {
        float length = (float) Math.sqrt(vec3.x * vec3.x + vec3.y * vec3.y + vec3.z * vec3.z);
        // Return (0,0,0) to avoid divide by zero
        return length > 0 ? new Vec3(vec3.x / length, vec3.y / length, vec3.z / length) : new Vec3(0, 0, 0);
    }

    /**
     * Menghitung produk silang dari dua vektor 3D.
     * <p>
     * Produk silang dari dua vektor menghasilkan vektor yang tegak lurus terhadap
     * kedua vektor input. 
     * </p>
     * 
     * @param a Vektor 3D pertama.
     * @param b Vektor 3D kedua.
     * @return Vektor 3D hasil produk silang dari vektor `a` dan `b`.
     */
    public static Vec3 cross(Vec3 a, Vec3 b) {
        return new Vec3(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }

    /**
     * Menghitung produk titik dari dua vektor 3D.
     * <p>
     * Produk titik dari dua vektor menghasilkan nilai skalar yang merupakan jumlah
     * dari hasil perkalian elemen yang bersesuaian dari kedua vektor.
     * </p>
     * 
     * @param a Vektor 3D pertama.
     * @param b Vektor 3D kedua.
     * @return Nilai skalar hasil produk titik dari vektor `a` dan `b`.
     */
    public static float dot(Vec3 a, Vec3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    /**
     * Menghitung produk titik dari dua vektor 4D.
     * <p>
     * Produk titik dari dua vektor menghasilkan nilai skalar yang merupakan jumlah
     * dari hasil perkalian elemen yang bersesuaian dari kedua vektor.
     * </p>
     * 
     * @param a Vektor 4D pertama.
     * @param b Vektor 4D kedua.
     * @return Nilai skalar hasil produk titik dari vektor `a` dan `b`.
     */
    public static float dot(Vec4 a, Vec4 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z + a.w * b.w;
    }

    // Placeholder for native methods.
    // Implement these methods in native code if needed.

    /**
     * Placeholder untuk metode native yang mungkin digunakan untuk menyaring vektor
     * berdasarkan indeks wajah.
     * 
     * @param input Array vektor 3D yang akan diproses.
     * @param faceIndex Array indeks wajah yang menentukan cara memproses vektor.
     * @return Array vektor 3D yang telah diproses.
     */
    public static Vec3[] vertexs(Vec3[] input, short[] faceIndex) {
        // Implementasi belum tersedia.
        throw new UnsupportedOperationException("Method not implemented");
    }

    /**
     * Placeholder untuk metode native yang mungkin digunakan untuk mengambil data normal
     * dari sumber eksternal.
     * 
     * @return String yang merepresentasikan data normal.
     */
    public static String getNormals() {
        // Implementasi belum tersedia.
        throw new UnsupportedOperationException("Method not implemented");
    }
}