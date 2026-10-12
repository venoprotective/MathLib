public class Vector3f {
    public float x;
    public float y;
    public float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f add(Vector3f vec) {
        return new Vector3f(x + vec.x, y + vec.y, z + vec.z);
    }

    public Vector3f sub(Vector3f vec) {
        return new Vector3f(x - vec.x, y - vec.y, z - vec.z);
    }

    public float distance() {
        return MathUtils.sqrt(x * x + y * y + z * z);
//        return Math.sqrt(x * x + y * y);
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vector3f multiply(float scalar) {
        return new Vector3f(scalar * x, scalar * y, scalar * z);
    }

    public Vector3f divide(float scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Cannot divide vector by zero");
        }

        return new Vector3f(x / scalar, y / scalar, z / scalar);
    }

    public Vector3f normalized() {
        float len = distance();
        if (len < 1e-9f) {
            throw new ArithmeticException("Cannot normalize zero vector");
        }

        return new Vector3f(x / len, y / len, z / len);
    }

    public float dot(Vector3f vec) {
        return x * vec.x + y * vec.y + z * vec.z;
    }

    public Vector3f cross(Vector3f vec){
        return new Vector3f(y * vec.z - z * vec.y,
                            z * vec.x - x * vec.z,
                            x * vec.y - y * vec.x);
    }

    @Override
    public String toString() {
        return String.format("Vector3f {x=%.3f, y=%.3f, z=%.3f}", x, y, z);
    }
}
