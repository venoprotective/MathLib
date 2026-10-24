import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Matrix3x3Test {

    private static final float DELTA = 1e-5f;

    private static Matrix3x3 fromRows(float[]... rows) {
        return new Matrix3x3(rows);
    }

    private static void assertMatrixEquals(Matrix3x3 expected, Matrix3x3 actual) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(expected.data[i][j], actual.data[i][j], DELTA,
                        "Mismatch at [" + i + "][" + j + "]");
            }
        }
    }

    @Test
    void defaultConstructorCreatesZeros() {
        Matrix3x3 m = new Matrix3x3();
        assertMatrixEquals(fromRows(
                new float[]{0, 0, 0},
                new float[]{0, 0, 0},
                new float[]{0, 0, 0}), m);
    }

    @Test
    void zerosOnesIdentity() {
        assertMatrixEquals(fromRows(
                new float[]{0, 0, 0},
                new float[]{0, 0, 0},
                new float[]{0, 0, 0}), Matrix3x3.zeros());

        assertMatrixEquals(fromRows(
                new float[]{1, 1, 1},
                new float[]{1, 1, 1},
                new float[]{1, 1, 1}), Matrix3x3.ones());

        assertMatrixEquals(fromRows(
                new float[]{1, 0, 0},
                new float[]{0, 1, 0},
                new float[]{0, 0, 1}), Matrix3x3.identity());
    }

    @Test
    void constructorRejectsWrongDimensions() {
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix3x3(new float[2][3]));
        assertThrows(IllegalArgumentException.class,
                () -> new Matrix3x3(new float[3][2]));
    }

    @Test
    void constructorCopiesData() {
        float[][] src = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3x3 m = new Matrix3x3(src);
        src[0][0] = 999f;
        assertEquals(1f, m.data[0][0], DELTA);
    }

    @Test
    void add() {
        Matrix3x3 a = fromRows(
                new float[]{1, 2, 3},
                new float[]{4, 5, 6},
                new float[]{7, 8, 9});
        Matrix3x3 b = Matrix3x3.ones();
        Matrix3x3 r = a.add(b);
        assertMatrixEquals(fromRows(
                new float[]{2, 3, 4},
                new float[]{5, 6, 7},
                new float[]{8, 9, 10}), r);
    }

    @Test
    void addNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Matrix3x3.identity().add(null));
    }

    @Test
    void sub() {
        Matrix3x3 a = fromRows(
                new float[]{2, 3, 4},
                new float[]{5, 6, 7},
                new float[]{8, 9, 10});
        Matrix3x3 r = a.sub(Matrix3x3.ones());
        assertMatrixEquals(fromRows(
                new float[]{1, 2, 3},
                new float[]{4, 5, 6},
                new float[]{7, 8, 9}), r);
    }

    @Test
    void subNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Matrix3x3.identity().sub(null));
    }

    @Test
    void multiplyIdentity() {
        Matrix3x3 a = fromRows(
                new float[]{1, 2, 3},
                new float[]{4, 5, 6},
                new float[]{7, 8, 9});
        assertMatrixEquals(a, a.multiply(Matrix3x3.identity()));
    }

    @Test
    void multiplyByZeroMatrix() {
        Matrix3x3 a = fromRows(
                new float[]{1, 2, 3},
                new float[]{4, 5, 6},
                new float[]{7, 8, 9});
        assertMatrixEquals(Matrix3x3.zeros(), a.multiply(Matrix3x3.zeros()));
    }

    @Test
    void multiplyGeneral() {
        Matrix3x3 a = fromRows(
                new float[]{1, 2, 3},
                new float[]{4, 5, 6},
                new float[]{7, 8, 9});
        Matrix3x3 b = fromRows(
                new float[]{9, 8, 7},
                new float[]{6, 5, 4},
                new float[]{3, 2, 1});
        Matrix3x3 r = a.multiply(b);
        assertMatrixEquals(fromRows(
                new float[]{30, 24, 18},
                new float[]{84, 69, 54},
                new float[]{138, 114, 90}), r);
    }

    @Test
    void multiplyNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Matrix3x3.identity().multiply((Matrix3x3) null));
    }

    @Test
    void multiplyVector() {
        Matrix3x3 m = fromRows(
                new float[]{1, 2, 3},
                new float[]{4, 5, 6},
                new float[]{7, 8, 9});
        Vector3f v = m.multiply(new Vector3f(1, 1, 1));
        assertEquals(6f, v.x, DELTA);
        assertEquals(15f, v.y, DELTA);
        assertEquals(24f, v.z, DELTA);
    }

    @Test
    void multiplyVectorNullThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Matrix3x3.identity().multiply((Vector3f) null));
    }

    @Test
    void multiplyVectorByIdentity() {
        Vector3f v = Matrix3x3.identity().multiply(new Vector3f(3, -2, 5));
        assertEquals(3f, v.x, DELTA);
        assertEquals(-2f, v.y, DELTA);
        assertEquals(5f, v.z, DELTA);
    }

    @Test
    void transpose() {
        Matrix3x3 m = fromRows(
                new float[]{1, 2, 3},
                new float[]{4, 5, 6},
                new float[]{7, 8, 9});
        assertMatrixEquals(fromRows(
                new float[]{1, 4, 7},
                new float[]{2, 5, 8},
                new float[]{3, 6, 9}), m.transpose());
    }

    @Test
    void transposeTwiceIsOriginal() {
        Matrix3x3 m = fromRows(
                new float[]{1, 2, 3},
                new float[]{4, 5, 6},
                new float[]{7, 8, 9});
        assertMatrixEquals(m, m.transpose().transpose());
    }
}