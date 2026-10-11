
public class MathUtils {
    public static float sqrt(float x) {
        if (x < 0) return Float.NaN;
        if (x == 0) return 0.0f;

        int i = Float.floatToIntBits(x);
        i = 0x1fbb4000 + (i >> 1); // получение приближенного грубого значения корня через сдвиг битов
        float y = Float.intBitsToFloat(i); // биты во флоат вернули назад

        // формула Ньютона: Новое_Y = 0.5 * (Старое_Y + X / Старое_Y)
        y = 0.5f * (y + x / y);
        // второй раз для бОльшей точности
        y = 0.5f * (y + x / y);

        return y;
    }
}