import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector3fTest {

    private static final float DELTA = 1e-5f;

    @Test
    void addAndSub() {
        Vector3f a = new Vector3f(1, 2, 3);
        Vector3f b = new Vector3f(4, 5, 6);
        Vector3f sum = a.add(b);
        assertEquals(5f, sum.x, DELTA);
        assertEquals(7f, sum.y, DELTA);
        assertEquals(9f, sum.z, DELTA);

        Vector3f diff = b.sub(a);
        assertEquals(3f, diff.x, DELTA);
        assertEquals(3f, diff.y, DELTA);
        assertEquals(3f, diff.z, DELTA);
    }

    @Test
    void addInPlaceMutates() {
        Vector3f a = new Vector3f(1, 1, 1);
        a.addInPlace(new Vector3f(2, 3, 4));
        assertEquals(3f, a.x, DELTA);
        assertEquals(4f, a.y, DELTA);
        assertEquals(5f, a.z, DELTA);
    }

    @Test
    void lengthAndLengthSquared() {
        Vector3f v = new Vector3f(2, 3, 6);
        assertEquals(49f, v.lengthSquared(), DELTA);
        assertEquals(7f, v.length(), DELTA);
    }

    @Test
    void distance() {
        assertEquals(7f,
                new Vector3f(0, 0, 0).distance(new Vector3f(2, 3, 6)), DELTA);
    }

    @Test
    void multiplyAndDivide() {
        Vector3f r = new Vector3f(1, 2, 3).multiply(2);
        assertEquals(2f, r.x, DELTA);
        assertEquals(4f, r.y, DELTA);
        assertEquals(6f, r.z, DELTA);

        Vector3f d = new Vector3f(2, 4, 6).divide(2);
        assertEquals(1f, d.x, DELTA);
        assertEquals(2f, d.y, DELTA);
        assertEquals(3f, d.z, DELTA);
    }

    @Test
    void divideByZeroThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Vector3f(1, 1, 1).divide(0));
    }

    @Test
    void normalized() {
        Vector3f n = new Vector3f(0, 0, 5).normalized();
        assertEquals(0f, n.x, DELTA);
        assertEquals(0f, n.y, DELTA);
        assertEquals(1f, n.z, DELTA);
        assertEquals(1f, n.length(), DELTA);
    }

    @Test
    void normalizeZeroThrows() {
        assertThrows(ArithmeticException.class,
                () -> new Vector3f(0, 0, 0).normalized());
    }

    @Test
    void dotProduct() {
        assertEquals(32f,
                new Vector3f(1, 2, 3).dot(new Vector3f(4, 5, 6)), DELTA);
    }

    @Test
    void crossProduct() {
        // i × j = k
        Vector3f k = new Vector3f(1, 0, 0).cross(new Vector3f(0, 1, 0));
        assertEquals(0f, k.x, DELTA);
        assertEquals(0f, k.y, DELTA);
        assertEquals(1f, k.z, DELTA);

        // стандартный пример
        Vector3f r = new Vector3f(1, 2, 3).cross(new Vector3f(4, 5, 6));
        assertEquals(-3f, r.x, DELTA);
        assertEquals(6f, r.y, DELTA);
        assertEquals(-3f, r.z, DELTA);
    }

    @Test
    void crossProductIsAntiCommutative() {
        Vector3f a = new Vector3f(1, 2, 3);
        Vector3f b = new Vector3f(4, 5, 6);
        Vector3f ab = a.cross(b);
        Vector3f ba = b.cross(a);
        assertEquals(-ab.x, ba.x, DELTA);
        assertEquals(-ab.y, ba.y, DELTA);
        assertEquals(-ab.z, ba.z, DELTA);
    }

    @Test
    void crossProductIsPerpendicular() {
        Vector3f a = new Vector3f(1, 2, 3);
        Vector3f b = new Vector3f(4, 5, 6);
        Vector3f c = a.cross(b);
        assertEquals(0f, a.dot(c), DELTA);
        assertEquals(0f, b.dot(c), DELTA);
    }
}