public class MathUtils {
    // NaN и infinity в битах IEEE 754
    private static final int POS_INF_BITS = 0x7f800000;
    private static final int NEG_ZERO_BITS = 0x80000000;
    private static final float epsilon = 1e-10F;

    public static float sqrt(float x) {
        if (x < 0 || Float.isNaN(x)) return Float.NaN;
        if (x == 0.0f || x == Float.POSITIVE_INFINITY) return x;

        int i = Float.floatToIntBits(x);
        i = 0x1fbb4000 + (i >> 1); // получение приближенного грубого значения корня через сдвиг битов
        float y = Float.intBitsToFloat(i);

        if (y == 0.0f) y = Float.MIN_NORMAL;

        // формула Ньютона: Новое_Y = 0.5 * (Старое_Y + X / Старое_Y)
        y = 0.5f * (y + x / y);
        // второй раз для бОльшей точности
        y = 0.5f * (y + x / y);
        // третий раз для ещё бОльшей точности
        y = 0.5f * (y + x / y);

        return y;
    }

    public static float max(float x, float y) {
        if (Float.isNaN(x) || Float.isNaN(y)) {
            return Float.NaN;
        }
        if (x > y) return x;

        return y;
    }

    public static float min(float x, float y) {
        if (Float.isNaN(x) || Float.isNaN(y)) {
            return Float.NaN;
        }
        if (x < y) return x;

        return y;
    }

    public static float abs(float x) {
        if (x <= 0.0f) {
            return -x;
        }
        return x;
    }

    public static float powInt(float x, float exp) {
        // проверка степени на отрицателньую
        int n = (int) exp;
        if (n < 0) {
            x = 1.0f / x;
            n = -n;
        }
        float result = 1.0f;
        float curX = x;
        while (n > 0) {
            if ((n & 1) == 1) { // последний бит == 1
                result *= curX;
            }
            curX *= curX; // возведение степени в квадрат
            n >>= 1; // сдвиг битов вправо 1101 -> 110
        }
        return result;
    }

    public static float exp(float x){
        if (Float.isNaN(x)) return Float.NaN;
        if (x > 88.7f) return Float.POSITIVE_INFINITY;
        if (x < -103.9f) return 0.0f;

        float sum = 1.0f;
        float term = 1.0f;
        int n = 1;

        // рекуррентная формула: а_n = a_{n-1} * x / n
        while (abs(term) > epsilon && n < 100) {
            term = term * x / n;
            sum += term;
            n++;
        }

        return sum;
    }

    public static float pow(float x, float exp) {
        if (exp == 0.0f) return 1.0f;
        if (x == 1.0f) return 1.0f;

        if (x == 0.0f) {
            return exp > 0 ? 0.0f : Float.POSITIVE_INFINITY;
        }

        // корень любой степени из отрицательного числа во float-математике не определен
        if (x < 0 && exp != (int) exp) return Float.NaN;

        if (exp == (float) (int) exp) return powInt(x, exp);

        if (x < 0) {
            float r = exp(exp * ln(-x));
            if (((int) exp & 1) == 1){
                return -r;
            }
            return r;
        }

        return exp(exp * ln(x));
    }

    public static float ln(float x) {
        if (x <= 0) return Float.NaN;
        if (x == Float.POSITIVE_INFINITY) return x;

        // раскладываем x = m * 2^k, где m в [1, 2)
        int bits = Float.floatToIntBits(x);
        int k = ((bits >> 23) & 0xff) - 127;

        // собираем m: оставляем мантиссу, exponent ставим в 127 (= 2^0), т.е. m в [1,2)
        int mantissa = bits & 0x007fffff;
        float m = Float.intBitsToFloat(mantissa | 0x3f800000); // 0x3f800000 = 127 << 23

        // ln(x) = k*ln(2) + ln(m), и ln(m) считаем рядом:
        // ln(m) = 2 * (t + t^3/3 + t^5/5 + ...),  где t = (m-1)/(m+1)
        float t = (m - 1) / (m + 1);
        float t2 = t * t;
        float term = t;
        float sum = 0;

        // t <= 1/3, 12 членов хватает на float-точность
        for (int n = 0; n < 12; n++) {
            sum += term / (2 * n + 1);
            term *= t2;
        }

        // ln(x) = ln(m * 2^k) = ln(m) + ln(2^k) = ln(m) + k * ln(2)
        return 2 * sum + k * 0.6931472f; // ln(2)
    }
}