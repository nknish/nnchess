package nn;

public class Matrix {
    private float[][] m;
    private int rows;
    private int cols;

    // initialize random/zero matrix with specified dimensions
    public Matrix(int rows, int cols, boolean random) {
        this.rows = rows;
        this.cols = cols;
        m = new float[rows][cols];
        if (random) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    m[i][j] = (float) (2 * Math.random() - 1);
                }
            }
        }
    }

    // initialize matrix with given values
    public Matrix(float[][] data) {
        rows = m.length;
        cols = m[0].length;
        m = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m[i][j] = data[i][j];
            }
        }
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

        System.out.println("vector:");
        System.out.println(v);

        System.out.println("matrix:");
        System.out.println(this);

        float[] prod = new float[rows];
        for (int i = 0; i < rows; i++) {
            prod[i] = 0;
            for (int k = 0; k < cols; k++) {
                prod[i] += m[i][k] * v.get(k);
            }
        }

        return new Vector(prod);
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
            s += "\n";
        }
        return s;
    }

}
