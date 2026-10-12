public class Matrix3x3 {
    public float[][] data;

    public Matrix3x3() {
        this.data = new float[3][3];
    }

    public Matrix3x3(float[][] inputData) {
        if (inputData.length != 3 || inputData[0].length != 3) {
            throw new IllegalArgumentException("wrong input matrix");
        }
        this.data = new float[3][3];
        for (int row = 0; row < data.length; row++) {
            System.arraycopy(inputData[row], 0, data[row], 0, data.length);
        }
    }

    public static Matrix3x3 zeros() {
        return new Matrix3x3();
    }

    public static Matrix3x3 ones() {
        Matrix3x3 result = new Matrix3x3();
        for (int row = 0; row < result.data.length; row++) {
            for (int col = 0; col < result.data[0].length; col++) {
                result.data[row][col] = 1.0f;
            }
        }
        return result;
    }

    public static Matrix3x3 identity() {
        Matrix3x3 result = new Matrix3x3();
        for (int i = 0; i < result.data.length; i++) {
            result.data[i][i] = 1.0f;
        }
        return result;
    }

    public Matrix3x3 add(Matrix3x3 matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix must not be null");
        }

        float[][] result = new float[3][3];

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[0].length; col++) {
                result[row][col] = data[row][col] + matrix.data[row][col];
            }
        }

        return new Matrix3x3(result);
    }

    public Matrix3x3 sub(Matrix3x3 matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix must not be null");
        }

        float[][] result = new float[3][3];
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[0].length; col++) {
                result[row][col] = data[row][col] - matrix.data[row][col];
            }
        }

        return new Matrix3x3(result);
    }

    public Matrix3x3 multiply(Matrix3x3 matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix must not be null");
        }

        float[][] result = new float[3][3];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                float sum = 0f;
                for (int k = 0; k < data.length; k++) {
                    sum += data[i][k] * matrix.data[k][j];
                }
                result[i][j] = sum;
            }
        }
        return new Matrix3x3(result);
    }

    public Vector3f multiply(Vector3f vec) {
        if (vec == null) {
            throw new IllegalArgumentException("vector must not be null");
        }

        float x = data[0][0] * vec.x + data[0][1] * vec.y + data[0][2] * vec.z;
        float y = data[1][0] * vec.x + data[1][1] * vec.y + data[1][2] * vec.z;
        float z = data[2][0] * vec.x + data[2][1] * vec.y + data[2][2] * vec.z;

        return new Vector3f(x, y, z);
    }

    public Matrix3x3 transpose() {
        Matrix3x3 result = new Matrix3x3();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                result.data[i][j] = data[j][i];
            }
        }
        return result;
    }

    public void print() {
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[0].length; col++) {
                System.out.printf(" %6.2f", data[row][col]);
            }
            System.out.println();
        }
    }
}