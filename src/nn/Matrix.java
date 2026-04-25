package nn;

public class Matrix {
    private float[][] m;
    private int rows;
    private int cols;

    // xavier-initialize random matrix with specified dimensions
    public Matrix(int rows, int cols, boolean random) {
        this.rows = rows;
        this.cols = cols;
        m = new float[rows][cols];
        if (random) {
            double scale = 1.0 / Math.sqrt(cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    m[i][j] = (float) (scale * (2 * RNG.rand() - 1));
                }
            }
        }
    }

    // initialize matrix with given values
    public Matrix(float[][] data) {
        rows = data.length;
        cols = data[0].length;
        m = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m[i][j] = data[i][j];
            }
        }
    }

    // add to another matrix of the same dimensions
    public Matrix add(Matrix m2) {
        if (rows != m2.getNumRows() || cols != m2.getNumCols()) {
            throw new IllegalArgumentException(
                    "can't add " + rows + "x" + cols + " by " + m2.getNumRows() + "x" + m2.getNumCols());
        }
        float[][] sum = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum[i][j] = m[i][j] + m2.get(i, j);
            }
        }
        return new Matrix(sum);
    }

    // subtract another matrix of the same dimensions
    public Matrix sub(Matrix m2) {
        if (rows != m2.getNumRows() || cols != m2.getNumCols()) {
            throw new IllegalArgumentException(
                    "can't add " + rows + "x" + cols + " by " + m2.getNumRows() + "x" + m2.getNumCols());
        }
        float[][] diff = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                diff[i][j] = m[i][j] - m2.get(i, j);
            }
        }
        return new Matrix(diff);
    }

    // multiply by another matrix
    public Matrix mul(Matrix m2) {
        if (cols != m2.getNumRows()) {
            throw new IllegalArgumentException(
                    "can't multiply " + rows + "x" + cols + " by " + m2.getNumRows() + "x" + m2.getNumCols());
        }

        float[][] prod = new float[rows][m2.getNumCols()];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m2.getNumCols(); j++) {
                prod[i][j] = 0;
                for (int k = 0; k < cols; k++) {
                    prod[i][j] += m[i][k] * m2.get(k, j);
                }
            }
        }

        return new Matrix(prod);
    }

    // multiply by a vector
    public Vector mul(Vector v) {
        if (cols != v.getDim()) {
            throw new IllegalArgumentException(
                    "can't multiply " + rows + "x" + cols + " matrix by " + v.getDim() + "-dim vector");
        }
        float[] prod = new float[rows];
        for (int i = 0; i < rows; i++) {
            prod[i] = 0;
            for (int k = 0; k < cols; k++) {
                prod[i] += m[i][k] * v.get(k);
            }
        }

        return new Vector(prod);
    }

    // multiply by a scalar
    public Matrix mul(float a) {
        float[][] prod = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                prod[i][j] = a * m[i][j];
            }
        }
        return new Matrix(prod);
    }

    // transpose the matrix
    public Matrix t() {
        float[][] transposed = new float[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = m[i][j];
            }
        }
        return new Matrix(transposed);
    }

    // get a single value from the matrix
    public float get(int i, int j) {
        return m[i][j];
    }

    public int getNumRows() {
        return rows;
    }

    public int getNumCols() {
        return cols;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                s += m[i][j] + " ";
            }
            if (i + 1 < rows) {
                s += "\n";
            }
        }
        return s;
    }
}
