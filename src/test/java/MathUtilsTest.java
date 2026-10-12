import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MathUtils")
class MathUtilsTest {

    // Относительный допуск для float: ~ несколько ulp
    private static final float REL = 1e-5f;
    // Абсолютный допуск для значений около нуля
    private static final float ABS = 1e-6f;

    private static void assertClose(float expected, float actual, String msg) {
        float delta = Math.max(ABS, Math.abs(expected) * REL);
        assertEquals(expected, actual, delta, msg);
    }

    // =====================================================================
    // sqrt
    // =====================================================================
    @Nested
    @DisplayName("sqrt")
    class SqrtTest {

        @ParameterizedTest(name = "sqrt({0})")
        @ValueSource(floats = {
                0f, 1f, 2f, 4f, 9f, 16f, 100f, 0.25f, 0.0001f,
                123456.789f, 1e-30f, 1e30f, Float.MIN_NORMAL, Float.MAX_VALUE
        })
        void matchesMathForPositive(float x) {
            float got  = MathUtils.sqrt(x);
            float want = (float) Math.sqrt(x);
            assertClose(want, got, "sqrt(" + x + ")");
        }

        @Test
        @DisplayName("sqrt(-1) = NaN")
        void negativeIsNaN() {
            assertTrue(Float.isNaN(MathUtils.sqrt(-1f)));
            assertTrue(Float.isNaN(MathUtils.sqrt(-0.0001f)));
        }

        @Test
        @DisplayName("sqrt(NaN) = NaN")
        void nanIsNaN() {
            assertTrue(Float.isNaN(MathUtils.sqrt(Float.NaN)));
        }

        @Test
        @DisplayName("sqrt(+inf) = +inf, sqrt(-inf) = NaN")
        void infinities() {
            assertEquals(Float.POSITIVE_INFINITY, MathUtils.sqrt(Float.POSITIVE_INFINITY));
            assertTrue(Float.isNaN(MathUtils.sqrt(Float.NEGATIVE_INFINITY)));
        }

        @Test
        @DisplayName("sqrt(-0.0) сохраняет знак нуля")
        void negativeZero() {
            // Math.sqrt(-0.0) = -0.0
            assertEquals(Float.floatToIntBits(-0.0f),
                    Float.floatToIntBits(MathUtils.sqrt(-0.0f)));
        }
    }

    // =====================================================================
    // max / min
    // =====================================================================
    @Nested
    @DisplayName("max / min")
    class MinMaxTest {

        @Test
        void maxBasics() {
            assertEquals(5f, MathUtils.max(3f, 5f));
            assertEquals(5f, MathUtils.max(5f, 3f));
            assertEquals(5f, MathUtils.max(5f, 5f));
            assertEquals(0f, MathUtils.max(-1f, 0f));
        }

        @Test
        void minBasics() {
            assertEquals(3f, MathUtils.min(3f, 5f));
            assertEquals(3f, MathUtils.min(5f, 3f));
            assertEquals(5f, MathUtils.min(5f, 5f));
            assertEquals(-1f, MathUtils.min(-1f, 0f));
        }

        @Test
        @DisplayName("max/min с NaN = NaN")
        void nanPropagates() {
            assertTrue(Float.isNaN(MathUtils.max(Float.NaN, 1f)));
            assertTrue(Float.isNaN(MathUtils.max(1f, Float.NaN)));
            assertTrue(Float.isNaN(MathUtils.min(Float.NaN, 1f)));
            assertTrue(Float.isNaN(MathUtils.min(1f, Float.NaN)));
        }

        @Test
        @DisplayName("max/min с бесконечностями")
        void infinities() {
            assertEquals(Float.POSITIVE_INFINITY,
                    MathUtils.max(Float.POSITIVE_INFINITY, 1f));
            assertEquals(Float.NEGATIVE_INFINITY,
                    MathUtils.min(Float.NEGATIVE_INFINITY, 1f));
        }

        @Test
        @DisplayName("max/min с -0.0 и +0.0")
        void signedZeros() {
            // Math.max(-0.0f, 0.0f) = 0.0f, Math.min = -0.0f
            assertEquals(0.0f,  MathUtils.max(-0.0f, 0.0f));
            assertEquals(-0.0f, MathUtils.min(-0.0f, 0.0f));
            assertEquals(0, Float.floatToIntBits(MathUtils.max(-0.0f, 0.0f)) & 0x80000000);
        }
    }

    // =====================================================================
    // abs
    // =====================================================================
    @Nested
    @DisplayName("abs")
    class AbsTest {

        @ParameterizedTest
        @ValueSource(floats = {0f, 1f, -1f, 3.14f, -3.14f, Float.MAX_VALUE, -Float.MAX_VALUE})
        void matchesMath(float x) {
            assertEquals(Math.abs(x), MathUtils.abs(x));
        }

        @Test
        @DisplayName("abs(-0.0) = +0.0")
        void negativeZero() {
            assertEquals(0, Float.floatToIntBits(MathUtils.abs(-0.0f)) & 0x80000000);
        }

        @Test
        @DisplayName("abs(NaN) = NaN")
        void nan() {
            assertTrue(Float.isNaN(MathUtils.abs(Float.NaN)));
        }

        @Test
        @DisplayName("abs(±inf) = +inf")
        void infinities() {
            assertEquals(Float.POSITIVE_INFINITY, MathUtils.abs(Float.POSITIVE_INFINITY));
            assertEquals(Float.POSITIVE_INFINITY, MathUtils.abs(Float.NEGATIVE_INFINITY));
        }
    }

    // =====================================================================
    // powInt
    // =====================================================================
    @Nested
    @DisplayName("powInt")
    class PowIntTest {

        @ParameterizedTest(name = "powInt({0}, {1})")
        @ValueSource(floats = {-5, -3, -1, 0, 1, 2, 3, 4, 10})
        void matchesMathForBase2(float exp) {
            assertClose((float) Math.pow(2, exp), MathUtils.powInt(2f, exp),
                    "powInt(2," + exp + ")");
        }

        @Test
        void negativeBase() {
            assertClose(-8f, MathUtils.powInt(-2f, 3f),  "(-2)^3");
            assertClose( 4f, MathUtils.powInt(-2f, 2f),  "(-2)^2");
            assertClose(-2f, MathUtils.powInt(-2f, 1f),  "(-2)^1");
        }

        @Test
        void oneAndZeroExponent() {
            assertEquals(1f, MathUtils.powInt(123f, 0f));
            assertEquals(1f, MathUtils.powInt(0f, 0f)); // 0^0 = 1 по соглашению
        }

        @Test
        void zeroBase() {
            assertEquals(0f, MathUtils.powInt(0f, 5f));
            assertEquals(Float.POSITIVE_INFINITY, MathUtils.powInt(0f, -1f));
        }
    }

    // =====================================================================
    // exp
    // =====================================================================
    @Nested
    @DisplayName("exp")
    class ExpTest {

        @ParameterizedTest(name = "exp({0})")
        @ValueSource(floats = {
                0f, 0.1f, -0.1f, 1f, -1f, 2f, -2f, 5f, -5f, 10f, -10f, 20f, -20f
        })
        void matchesMath(float x) {
            float got  = MathUtils.exp(x);
            float want = (float) Math.exp(x);
            assertClose(want, got, "exp(" + x + ")");
        }

        @Test
        @DisplayName("exp(NaN) = NaN")
        void nan() {
            assertTrue(Float.isNaN(MathUtils.exp(Float.NaN)));
        }

        @Test
        @DisplayName("exp(+большое) = +inf, exp(-большое) = 0")
        void saturates() {
            assertEquals(Float.POSITIVE_INFINITY, MathUtils.exp(100f));
            assertEquals(0f, MathUtils.exp(-200f));
        }

        @Test
        @DisplayName("exp(0) = 1")
        void one() {
            assertEquals(1f, MathUtils.exp(0f));
        }
    }

    // =====================================================================
    // ln
    // =====================================================================
    @Nested
    @DisplayName("ln")
    class LnTest {

        @ParameterizedTest(name = "ln({0})")
        @ValueSource(floats = {
                1f, 2f, 3f, 10f, 12f, 100f, 1000f,
                0.5f, 0.1f, 0.001f, 1e-6f, 1e-20f, 1e20f, 1e30f
        })
        void matchesMath(float x) {
            float got  = MathUtils.ln(x);
            float want = (float) Math.log(x);
            assertClose(want, got, "ln(" + x + ")");
        }

        @Test
        @DisplayName("ln(1) = 0")
        void one() {
            assertEquals(0f, MathUtils.ln(1f), 1e-7f);
        }

        @Test
        @DisplayName("ln(x <= 0) = NaN")
        void nonPositive() {
            assertTrue(Float.isNaN(MathUtils.ln(0f)));
            assertTrue(Float.isNaN(MathUtils.ln(-1f)));
            assertTrue(Float.isNaN(MathUtils.ln(-0.0f)));
        }

        @Test
        @DisplayName("ln(+inf) = +inf")
        void infinity() {
            assertEquals(Float.POSITIVE_INFINITY, MathUtils.ln(Float.POSITIVE_INFINITY));
        }
    }

    // =====================================================================
    // pow
    // =====================================================================
    @Nested
    @DisplayName("pow")
    class PowTest {

        @ParameterizedTest(name = "pow({0}, {1})")
        @ValueSource(floats = {0f, 1f, -1f, 0.5f, -0.5f, 2f, -2f, 10f, -10f})
        void positiveBaseMatchesMath(float exp) {
            float[] bases = {0.5f, 1f, 2f, 3f, 10f, 100f};
            for (float base : bases) {
                float got  = MathUtils.pow(base, exp);
                float want = (float) Math.pow(base, exp);
                assertClose(want, got, "pow(" + base + "," + exp + ")");
            }
        }

        @Test
        @DisplayName("отрицательное основание + целый exp")
        void negativeBaseIntegerExp() {
            assertClose(-8f,  MathUtils.pow(-2f, 3f), "(-2)^3");
            assertClose( 4f,  MathUtils.pow(-2f, 2f), "(-2)^2");
            assertClose(-2f,  MathUtils.pow(-2f, 1f), "(-2)^1");
        }

        @Test
        @DisplayName("отрицательное основание + нецелый exp = NaN")
        void negativeBaseFractionalExp() {
            assertTrue(Float.isNaN(MathUtils.pow(-2f, 0.5f)));
            assertTrue(Float.isNaN(MathUtils.pow(-4f, 1.5f)));
        }

        @Test
        @DisplayName("x^0 = 1 для любого x")
        void zeroExp() {
            assertEquals(1f, MathUtils.pow(2f, 0f));
            assertEquals(1f, MathUtils.pow(-2f, 0f));
            assertEquals(1f, MathUtils.pow(0f, 0f));
            assertEquals(1f, MathUtils.pow(123f, 0f));
        }

        @Test
        @DisplayName("0^exp")
        void zeroBase() {
            assertEquals(0f, MathUtils.pow(0f, 5f));
            assertEquals(Float.POSITIVE_INFINITY, MathUtils.pow(0f, -5f));
            assertEquals(0f, MathUtils.pow(0f, 0.5f));
        }

        @Test
        @DisplayName("1^exp = 1")
        void oneBase() {
            assertEquals(1f, MathUtils.pow(1f, 123f));
            assertEquals(1f, MathUtils.pow(1f, -123f));
            assertEquals(1f, MathUtils.pow(1f, 0.5f));
        }

        @Test
        @DisplayName("сравнение с Math.pow на сетке значений")
        void gridAgainstMath() {
            float[] bases = {0.1f, 0.5f, 1.5f, 2f, 3f, 7.5f, 10f};
            float[] exps  = {-3f, -1f, -0.5f, 0.5f, 1f, 2.5f, 4f};
            for (float b : bases) {
                for (float e : exps) {
                    assertClose((float) Math.pow(b, e), MathUtils.pow(b, e),
                            "pow(" + b + "," + e + ")");
                }
            }
        }
    }
}