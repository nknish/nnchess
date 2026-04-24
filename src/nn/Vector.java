package nn;

public class Vector {
    private float[] v;
    private int dim;

    // initialize zero vector with specified dimensions
    public Vector(int dim) {
        this.dim = dim;
        v = new float[dim];
    }

    // initialize vector with given values
    public Vector(float[] data) {
        dim = data.length;
        v = new float[dim];
        for (int i = 0; i < dim; i++) {
            v[i] = data[i];
        }
    }

    // add another vector of the same dimension
    public Vector add(Vector v2) {
        verifySameDim(v2, "addition");
        float[] sum = new float[dim];
        for (int i = 0; i < dim; i++) {
            sum[i] = v[i] + v2.get(i);
        }
        return new Vector(sum);
    }

    // subtract another vector of the same dimension
    public Vector sub(Vector v2) {
        verifySameDim(v2, "subtraction");
        float[] diff = new float[dim];
        for (int i = 0; i < dim; i++) {
            diff[i] = v[i] - v2.get(i);
        }
        return new Vector(diff);
    }

    // multiply by a scalar
    public Vector mul(float a) {
        float[] prod = new float[dim];
        for (int i = 0; i < dim; i++) {
            prod[i] = a * v[i];
        }
        return new Vector(prod);

    }

    // multiply element-wise by a vector of the same dimension
    public Vector mul(Vector v2) {
        verifySameDim(v2, "element-wise mul");

        float[] prod = new float[dim];
        for (int i = 0; i < dim; i++) {
            prod[i] = v[i] * v2.get(i);
        }
        return new Vector(prod);

    }

    // dot the vector with another vector of the same dimension
    public float dot(Vector v2) {
        verifySameDim(v2, "dot");

        float sum = 0;
        for (int i = 0; i < dim; i++) {
            sum += v[i] * v2.get(i);
        }
        return sum;
    }

    // compute the outer product with another vector
    public Matrix outer(Vector v2) {
        float[][] out = new float[dim][v2.getDim()];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < v2.getDim(); j++) {
                out[i][j] = v[i] * v2.get(j);
            }
        }

        return new Matrix(out);
    }

    // sum all elements
    public float sum() {
        float sum = 0;
        for (float v_i : v) {
            sum += v_i;
        }
        return sum;
    }

    // root mean square of elements
    public float rms() {
        float ms = 0;
        for (float v_i : v) {
            ms += Math.pow(v_i, 2);
        }
        ms /= dim;
        return (float) Math.sqrt(ms);
    }

    // element-wise maximum of two vectors
    public Vector max(Vector v2) {
        verifySameDim(v2, "max");
        float[] max = new float[dim];
        for (int i = 0; i < dim; i++) {
            max[i] = Math.max(v[i], v2.get(i));
        }
        return new Vector(max);
    }

    // element-wise minimum of two vectors
    public Vector min(Vector v2) {
        verifySameDim(v2, "min");
        float[] max = new float[dim];
        for (int i = 0; i < dim; i++) {
            max[i] = Math.min(v[i], v2.get(i));
        }
        return new Vector(max);
    }

    // apply default (linear aka nothing) activation function
    public Vector activate() {
        return activateLinear();
    }

    // apply specified activation function, if supported
    public Vector activate(String activationFunction) {
        if (activationFunction.equals("linear")) {
            return activateLinear();
        } else if (activationFunction.equals("relu")) {
            return activateRelu();
        } else if (activationFunction.equals("softmax")) {
            return activateSoftmax();
        } else {
            throw new IllegalArgumentException(activationFunction + " not supported");
        }
    }

    public Vector grad() {
        return gradLinear();
    }

    public Vector grad(String activationFunction) {
        if (activationFunction.equals("linear")) {
            return gradLinear();
        } else if (activationFunction.equals("relu")) {
            return gradRelu();
        } else if (activationFunction.equals("softmax")) {
            return gradSoftmax();
        } else {
            throw new IllegalArgumentException(activationFunction + " not supported");
        }
    }

    private Vector activateLinear() {
        return copy();
    }

    private Vector activateRelu() {
        float[] out = new float[dim];
        for (int i = 0; i < dim; i++) {
            out[i] = Math.max(v[i], 0);
        }
        return new Vector(out);
    }

    private Vector activateSoftmax() {
        float[] out = new float[dim];
        float sum = 0;
        for (int i = 0; i < dim; i++) {
            out[i] = (float) Math.exp(v[i]);
            sum += out[i];
        }
        for (int i = 0; i < dim; i++) {
            out[i] /= sum;
        }
        return new Vector(out);
    }

    private Vector gradLinear() {
        float[] grad = new float[dim];
        for (int i = 0; i < dim; i++) {
            grad[i] = 1;
        }
        return new Vector(grad);
    }

    private Vector gradRelu() {
        float[] grad = new float[dim];
        for (int i = 0; i < dim; i++) {
            if (v[i] > 0) {
                grad[i] = 1;
            }
        }
        return new Vector(grad);
    }

    private Vector gradSoftmax() {
        throw new UnsupportedOperationException("haven't implemented softmax grad");
    }

    private Vector copy() {
        float[] out = new float[dim];
        System.arraycopy(v, 0, out, 0, dim);
        return new Vector(out);
    }

    public float get(int i) {
        return v[i];
    }

    public int getDim() {
        return dim;
    }

    private void verifySameDim(Vector v2, String operation) {
        if (dim != v2.getDim()) {
            throw new IllegalArgumentException(operation + " not supported for vectors dims " + dim + " and " + v2.getDim());
        }
    }

    @Override
    public String toString() {
        String s = "[ ";
        for (int i = 0; i < dim; i++) {
            s += v[i] + " ";
        }
        s += "]";
        return s;
    }
}
