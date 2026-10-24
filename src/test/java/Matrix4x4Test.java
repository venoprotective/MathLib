import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Matrix4x4Test {

    private static final float DELTA = 1e-5f;

    private static Matrix4x4 fromRows(float[]... rows) {
        return new Matrix4x4(rows);
    }

    private static void assertMatrixEquals(Matrix4x4 expected, Matrix4x4 actual) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected.data[i][j], actual.data[i][j], DELTA,
                        "Mismatch at [" + i + "][" + j + "]");
            }
        }
    }

    @Test
    void zerosOnesIdentity() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(0f, Matrix4x4.zeros().data[i][j], DELTA);
                assertEquals(1f, Matrix4x4.ones().data[i][j], DELTA);
                assertEquals(i == j ? 1f : 0f,
                        Matrix4x4.identity().data[i][j], DELTA);
            }
        }
    }

    @Test
    void constructorRejectsWrongDimensions() {
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix4x4(new float[3][4]));
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix4x4(new float[4][3]));
    }

    @Test
    void constructorCopiesData() {
        float[][] src = new float[4][4];
        src[0][0] = 7f;
        Matrix4x4 m = new Matrix4x4(src);
        src[0][0] = 99f;
        assertEquals(7f, m.data[0][0], DELTA);
    }

    @Test
    void add() {
        Matrix4x4 a = Matrix4x4.ones();
        Matrix4x4 b = Matrix4x4.ones();
        Matrix4x4 r = a.add(b);
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertEquals(2f, r.data[i][j], DELTA);
    }

    @Test
    void addNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Matrix4x4.identity().add(null));
    }

    @Test
    void sub() {
        Matrix4x4 r = Matrix4x4.ones().sub(Matrix4x4.ones());
        assertMatrixEquals(Matrix4x4.zeros(), r);
    }

    @Test
    void subNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Matrix4x4.identity().sub(null));
    }

    @Test
    void multiplyIdentity() {
        Matrix4x4 a = fromRows(
                new float[]{1, 2, 3, 4},
                new float[]{5, 6, 7, 8},
                new float[]{9, 10, 11, 12},
                new float[]{13, 14, 15, 16});
        assertMatrixEquals(a, a.multiply(Matrix4x4.identity()));
        assertMatrixEquals(a, Matrix4x4.identity().multiply(a));
    }

    @Test
    void multiplyGeneral() {
        Matrix4x4 a = fromRows(
                new float[]{1, 2, 3, 4},
                new float[]{5, 6, 7, 8},
                new float[]{9, 10, 11, 12},
                new float[]{13, 14, 15, 16});
        Matrix4x4 b = Matrix4x4.identity();
        b.data[0][3] = 10f;
        Matrix4x4 r = a.multiply(b);

        // r[i][3] = 10 * a[i][0] + a[i][3]
        assertEquals(14f,  r.data[0][3], DELTA);
        assertEquals(58f,  r.data[1][3], DELTA);
        assertEquals(102f, r.data[2][3], DELTA);
        assertEquals(146f, r.data[3][3], DELTA);

        // остальные столбцы не изменились
        for (int i = 0; i < 4; i++) {
            assertEquals(a.data[i][0], r.data[i][0], DELTA);
            assertEquals(a.data[i][1], r.data[i][1], DELTA);
            assertEquals(a.data[i][2], r.data[i][2], DELTA);
        }
    }

    @Test
    void multiplyByZeroMatrix() {
        assertMatrixEquals(Matrix4x4.zeros(),
                Matrix4x4.ones().multiply(Matrix4x4.zeros()));
    }

    @Test
    void multiplyNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Matrix4x4.identity().multiply((Matrix4x4) null));
    }

    @Test
    void multiplyVector() {
        Vector4f v = Matrix4x4.identity().multiply(new Vector4f(1, 2, 3, 4));
        assertEquals(1f, v.x, DELTA);
        assertEquals(2f, v.y, DELTA);
        assertEquals(3f, v.z, DELTA);
        assertEquals(4f, v.w, DELTA);
    }

    @Test
    void multiplyVectorWithTranslation() {
        Matrix4x4 t = Matrix4x4.identity();
        t.data[0][3] = 5f;
        t.data[1][3] = -2f;
        t.data[2][3] = 3f;

        Vector4f v = t.multiply(new Vector4f(1, 1, 1, 1));
        assertEquals(6f, v.x, DELTA);
        assertEquals(-1f, v.y, DELTA);
        assertEquals(4f, v.z, DELTA);
        assertEquals(1f, v.w, DELTA);
    }

    @Test
    void multiplyVectorNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Matrix4x4.identity().multiply((Vector4f) null));
    }

    @Test
    void transpose() {
        Matrix4x4 m = fromRows(
                new float[]{1, 2, 3, 4},
                new float[]{5, 6, 7, 8},
                new float[]{9, 10, 11, 12},
                new float[]{13, 14, 15, 16});
        Matrix4x4 t = m.transpose();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertEquals(m.data[i][j], t.data[j][i], DELTA);
    }

    @Test
    void transposeTwiceIsOriginal() {
        Matrix4x4 m = fromRows(
                new float[]{1, 2, 3, 4},
                new float[]{5, 6, 7, 8},
                new float[]{9, 10, 11, 12},
                new float[]{13, 14, 15, 16});
        assertMatrixEquals(m, m.transpose().transpose());
    }
}