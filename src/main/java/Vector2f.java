public class Vector2f {
    private final float x;
    private final float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {return x;}

    public float getY() {return y;}

    public Vector2f addition(Vector2f vec1, Vector2f vec2){
        return new Vector2f(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY());
    }
}
