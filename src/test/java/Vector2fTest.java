import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2fTest {

    private static final float DELTA = 1e-5f;

    @Test
    void constructorStoresComponents() {
        Vector2f v = new Vector2f(1.5f, -2.5f);
        assertEquals(1.5f, v.x, DELTA);
        assertEquals(-2.5f, v.y, DELTA);
    }

    @Test
    void addReturnsNewVector() {
        Vector2f a = new Vector2f(1, 2);
        Vector2f b = new Vector2f(3, 4);
        Vector2f r = a.add(b);
        assertEquals(4f, r.x, DELTA);
        assertEquals(6f, r.y, DELTA);
        // исходные не изменились
        assertEquals(1f, a.x, DELTA);
        assertEquals(3f, b.x, DELTA);
    }

    @Test
    void subReturnsCorrectDifference() {
        Vector2f r = new Vector2f(5, 7).sub(new Vector2f(2, 3));
        assertEquals(3f, r.x, DELTA);
        assertEquals(4f, r.y, DELTA);
    }

    @Test
    void addInPlaceMutatesVector() {
        Vector2f a = new Vector2f(1, 1);
        a.addInPlace(new Vector2f(2, 3));
        assertEquals(3f, a.x, DELTA);
        assertEquals(4f, a.y, DELTA);
    }

    @Test
    void lengthAndLengthSquared() {
        Vector2f v = new Vector2f(3, 4);
        assertEquals(25f, v.lengthSquared(), DELTA);
        assertEquals(5f, v.length(), DELTA);
    }

    @Test
    void distance() {
        assertEquals(5f, new Vector2f(0, 0).distance(new Vector2f(3, 4)), DELTA);
        assertEquals(0f, new Vector2f(2, 2).distance(new Vector2f(2, 2)), DELTA);
    }

    @Test
    void multiplyByScalar() {
        Vector2f r = new Vector2f(1, -2).multiply(3);
        assertEquals(3f, r.x, DELTA);
        assertEquals(-6f, r.y, DELTA);
    }

    @Test
    void divideByScalar() {
        Vector2f r = new Vector2f(4, -8).divide(2);
        assertEquals(2f, r.x, DELTA);
        assertEquals(-4f, r.y, DELTA);
    }

    @Test
    void divideByZeroThrows() {
        Vector2f v = new Vector2f(1, 1);
        assertThrows(IllegalArgumentException.class, () -> v.divide(0));
    }

    @Test
    void normalizedReturnsUnitVector() {
        Vector2f n = new Vector2f(3, 4).normalized();
        assertEquals(0.6f, n.x, DELTA);
        assertEquals(0.8f, n.y, DELTA);
        assertEquals(1f, n.length(), DELTA);
    }

    @Test
    void normalizeZeroVectorThrows() {
        assertThrows(ArithmeticException.class, () -> new Vector2f(0, 0).normalized());
    }

    @Test
    void dotProduct() {
        assertEquals(11f, new Vector2f(1, 2).dot(new Vector2f(3, 4)), DELTA);
        assertEquals(0f, new Vector2f(1, 0).dot(new Vector2f(0, 1)), DELTA);
    }

    @Test
    void toStringContainsComponents() {
        String s = new Vector2f(1, 2).toString();
        assertTrue(s.contains("1"));
        assertTrue(s.contains("2"));
    }
}