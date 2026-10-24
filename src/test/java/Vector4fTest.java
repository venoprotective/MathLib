import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector4fTest {

    private static final float DELTA = 1e-5f;

    @Test
    void addAndSub() {
        Vector4f a = new Vector4f(1, 2, 3, 4);
        Vector4f b = new Vector4f(5, 6, 7, 8);
        Vector4f sum = a.add(b);
        assertEquals(6f, sum.x, DELTA);
        assertEquals(8f, sum.y, DELTA);
        assertEquals(10f, sum.z, DELTA);
        assertEquals(12f, sum.w, DELTA);

        Vector4f diff = b.sub(a);
        assertEquals(4f, diff.x, DELTA);
        assertEquals(4f, diff.y, DELTA);
        assertEquals(4f, diff.z, DELTA);
        assertEquals(4f, diff.w, DELTA);
    }

    @Test
    void addInPlaceMutates() {
        Vector4f a = new Vector4f(1, 1, 1, 1);
        a.addInPlace(new Vector4f(1, 2, 3, 4));
        assertEquals(2f, a.x, DELTA);
        assertEquals(3f, a.y, DELTA);
        assertEquals(4f, a.z, DELTA);
        assertEquals(5f, a.w, DELTA);
    }

    @Test
    void lengthAndLengthSquared() {
        Vector4f v = new Vector4f(1, 2, 2, 4);
        assertEquals(25f, v.lengthSquared(), DELTA);
        assertEquals(5f, v.length(), DELTA);
    }

    @Test
    void distance() {
        assertEquals(5f,
                new Vector4f(0, 0, 0, 0).distance(new Vector4f(1, 2, 2, 4)),
                DELTA);
    }

    @Test
    void multiplyAndDivide() {
        Vector4f m = new Vector4f(1, 2, 3, 4).multiply(2);
        assertEquals(2f, m.x, DELTA);
        assertEquals(4f, m.y, DELTA);
        assertEquals(6f, m.z, DELTA);
        assertEquals(8f, m.w, DELTA);

        Vector4f d = new Vector4f(2, 4, 6, 8).divide(2);
        assertEquals(1f, d.x, DELTA);
        assertEquals(2f, d.y, DELTA);
        assertEquals(3f, d.z, DELTA);
        assertEquals(4f, d.w, DELTA);
    }

    @Test
    void divideByZeroThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vector4f(1, 1, 1, 1).divide(0));
    }

    @Test
    void normalized() {
        Vector4f n = new Vector4f(0, 0, 3, 4).normalized();
        assertEquals(0f, n.x, DELTA);
        assertEquals(0f, n.y, DELTA);
        assertEquals(0.6f, n.z, DELTA);
        assertEquals(0.8f, n.w, DELTA);
        assertEquals(1f, n.length(), DELTA);
    }

    @Test
    void normalizeZeroThrows() {
        assertThrows(ArithmeticException.class,
                () -> new Vector4f(0, 0, 0, 0).normalized());
    }

    @Test
    void dotProduct() {
        assertEquals(70f,
                new Vector4f(1, 2, 3, 4).dot(new Vector4f(5, 6, 7, 8)), DELTA);
    }

    @Test
    void crossSetsWToZero() {
        Vector4f r = new Vector4f(1, 0, 0, 9).cross(new Vector4f(0, 1, 0, 9));
        assertEquals(0f, r.x, DELTA);
        assertEquals(0f, r.y, DELTA);
        assertEquals(1f, r.z, DELTA);
        assertEquals(0f, r.w, DELTA);
    }

    @Test
    void toStringContainsValues() {
        String s = new Vector4f(1, 2, 3, 4).toString();
        assertTrue(s.contains("1"));
        assertTrue(s.contains("4"));
    }
}