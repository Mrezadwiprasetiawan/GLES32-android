package rpengine.core.gles32core.math;

/**
 * Kelas Vec3 merepresentasikan vektor tiga dimensi.
 */
public class Vec3 {
    public float x, y, z;

    /**
     * Membuat objek Vec3 dengan komponen x, y, dan z.
     * 
     * @param x komponen x vektor
     * @param y komponen y vektor
     * @param z komponen z vektor
     */
    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Mengubah Vec3 menjadi Vec4 dengan komponen w default (1.0f).
     * 
     * @return Vec4 yang merepresentasikan vektor ini dengan w = 1.0f
     */
    public Vec4 toVec4() {
        return new Vec4(x, y, z);
    }

    /**
     * Mengubah Vec3 menjadi Vec4 dengan komponen w yang ditentukan.
     * 
     * @param w komponen w vektor
     * @return Vec4 yang merepresentasikan vektor ini dengan komponen w yang ditentukan
     */
    public Vec4 toVec4(float w) {
        return new Vec4(x, y, z, w);
    }

    /**
     * Mengembalikan array float yang merepresentasikan vektor 3 dimensi.
     * 
     * @return array float dari komponen x, y, dan z
     */
    public float[] actual() {
        return new float[]{x, y, z};
    }
}