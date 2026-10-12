public class Vector2f {
    public float x;
    public float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f add(Vector2f vec) {
        return new Vector2f(x + vec.x, y + vec.y);
    }

    public Vector2f sub(Vector2f vec) {
        return new Vector2f(x - vec.x, y - vec.y);
    }

    public float length() {
        return MathUtils.sqrt(x * x + y * y);
//        return Math.sqrt(x * x + y * y);
    }

    public float lengthSquared(){
        return x * x + y * y;
    }

    public float distance(Vector2f vec){
        float dx = x - vec.x;
        float dy = y - vec.y;

        return MathUtils.sqrt(dx * dx + dy * dy);
    }

    public Vector2f multiply(float scalar) {
        return new Vector2f(scalar * x, scalar * y);
    }

    public Vector2f divide(float scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("ZeroDivisionError");
        }

        return new Vector2f(x / scalar, y / scalar);
    }

    public Vector2f normalized() {
        float len = length();
        if (len < 1e-9f) {
            throw new ArithmeticException("Cannot normalize zero vector");
        }

        return new Vector2f(x / len, y / len);
    }

    public float dot(Vector2f vec) {
        return x * vec.x + y * vec.y;
    }

    @Override
    public String toString() {
        return String.format("Vector2f {x=%.3f, y=%.3f}", x, y);
    }
}
