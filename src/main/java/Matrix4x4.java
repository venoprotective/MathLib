public class Matrix4x4 {
    public float[][] data;

    public Matrix4x4() {
        this.data = new float[4][4];
    }

    public Matrix4x4(float[][] inputData) {
        if (inputData.length != 4 || inputData[0].length != 4) {
            throw new IllegalArgumentException("wrong input matrix");
        }
        this.data = new float[4][4];
        for (int row = 0; row < data.length; row++) {
            System.arraycopy(inputData[row], 0, data[row], 0, data.length);
        }
    }

    public static Matrix4x4 zeros() {
        return new Matrix4x4();
    }

    public static Matrix4x4 ones() {
        Matrix4x4 result = new Matrix4x4();
        for (int row = 0; row < result.data.length; row++) {
            for (int col = 0; col < result.data[0].length; col++) {
                result.data[row][col] = 1.0f;
            }
        }
        return result;
    }

    public static Matrix4x4 identity() {
        Matrix4x4 result = new Matrix4x4();
        for (int i = 0; i < result.data.length; i++) {
            result.data[i][i] = 1.0f;
        }
        return result;
    }

    public Matrix4x4 add(Matrix4x4 matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix must not be null");
        }

        float[][] result = new float[4][4];

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[0].length; col++) {
                result[row][col] = data[row][col] + matrix.data[row][col];
            }
        }

        return new Matrix4x4(result);
    }

    public Matrix4x4 sub(Matrix4x4 matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix must not be null");
        }

        float[][] result = new float[4][4];
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[0].length; col++) {
                result[row][col] = data[row][col] - matrix.data[row][col];
            }
        }

        return new Matrix4x4(result);
    }

    public Matrix4x4 multiply(Matrix4x4 matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix must not be null");
        }

        float[][] result = new float[4][4];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                float sum = 0f;
                for (int k = 0; k < data.length; k++) {
                    sum += data[i][k] * matrix.data[k][j];
                }
                result[i][j] = sum;
            }
        }
        return new Matrix4x4(result);
    }

    public Vector4f multiply(Vector4f vec) {
        if (vec == null) {
            throw new IllegalArgumentException("vector must not be null");
        }

        float x = data[0][0] * vec.x + data[0][1] * vec.y + data[0][2] * vec.z + data[0][3] * vec.w;
        float y = data[1][0] * vec.x + data[1][1] * vec.y + data[1][2] * vec.z + data[1][3] * vec.w;
        float z = data[2][0] * vec.x + data[2][1] * vec.y + data[2][2] * vec.z + data[2][3] * vec.w;
        float w = data[3][0] * vec.x + data[3][1] * vec.y + data[3][2] * vec.z + data[3][3] * vec.w;

        return new Vector4f(x, y, z, w);
    }

    public Matrix4x4 transpose() {
        Matrix4x4 result = new Matrix4x4();

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