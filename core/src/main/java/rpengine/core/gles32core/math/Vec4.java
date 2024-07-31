package rpengine.core.gles32core.math;

/**
 * Kelas Vec4 merepresentasikan vektor empat dimensi.
 */
public class Vec4 {
    public float x, y, z, w;

    /**
     * Membuat objek Vec4 dengan komponen x, y, z, dan w.
     * 
     * @param x komponen x vektor
     * @param y komponen y vektor
     * @param z komponen z vektor
     * @param w komponen w vektor
     */
    public Vec4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Membuat objek Vec4 dengan komponen w default (1.0f).
     * 
     * @param x komponen x vektor
     * @param y komponen y vektor
     * @param z komponen z vektor
     */
    public Vec4(float x, float y, float z) {
        this(x, y, z, 1.0f);
    }

    /**
     * Mengubah Vec4 menjadi Vec3 dengan mengabaikan komponen w.
     * 
     * @return Vec3 yang merepresentasikan komponen x, y, dan z dari vektor ini
     */
    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }

    /**
     * Mengembalikan array float yang merepresentasikan vektor 4 dimensi.
     * 
     * @return array float dari komponen x, y, z, dan w
     */
    public float[] actual() {
        return new float[]{x, y, z, w};
    }
}