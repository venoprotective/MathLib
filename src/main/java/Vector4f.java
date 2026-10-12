public class Vector4f {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f add(Vector4f vec) {
        return new Vector4f(x + vec.x, y + vec.y, z + vec.z, w + vec.w);
    }

    public Vector4f sub(Vector4f vec) {
        return new Vector4f(x - vec.x, y - vec.y, z - vec.z, w - vec.w);
    }

    public float length() {
        return MathUtils.sqrt(lengthSquared());
    }

    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    public float distance(Vector4f vec) {
        float dx = x - vec.x;
        float dy = y - vec.y;
        float dz = z - vec.z;
        float dw = w - vec.w;

        return MathUtils.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    public Vector4f multiply(float scalar) {
        return new Vector4f(scalar * x, scalar * y, scalar * z, scalar * w);
    }

    public Vector4f divide(float scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Cannot divide vector by zero");
        }

        return new Vector4f(x / scalar, y / scalar, z / scalar, w / scalar);
    }

    public Vector4f normalized() {
        float len = length();
        if (len < 1e-9f) {
            throw new ArithmeticException("Cannot normalize zero vector");
        }

        return new Vector4f(x / len, y / len, z / len, w / len);
    }

    public float dot(Vector4f vec) {
        return x * vec.x + y * vec.y + z * vec.z + w * vec.w;
    }

    public Vector4f cross(Vector4f vec){
        return new Vector4f
                (y * vec.z - z * vec.y,
                z * vec.x - x * vec.z,
                x * vec.y - y * vec.x,
                0.0f);
    }

    @Override
    public String toString() {
        return String.format("Vector4f {x=%.3f, y=%.3f, z=%.3f, w=%.3f}", x, y, z, w);
    }
}
