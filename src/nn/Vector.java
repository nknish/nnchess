package nn;

public class Vector {
    private float[] v;
    private int dim;

    // initialize random/zero vector with specified dimensions
    public Vector(int dim, boolean random) {
        this.dim = dim;
        v = new float[dim];
        if (random) {
            for (int i = 0; i < dim; i++) {
                v[i] = (float) (2 * Math.random() - 1);
            }
        }
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
        if (dim != v2.getDim()) {
            throw new IllegalArgumentException("can't add vectors dims " + dim + " and " + v2.getDim());
        }
        
        System.out.println("adding:");
        System.out.println(this);
        System.out.println(v2);
        float[] sum = new float[dim];
        for (int i = 0; i < dim; i++) {
            sum[i] = v[i] + v2.get(i);
        } 
        return new Vector(sum);
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

    private Vector activateLinear() {
        float[] out = new float[dim];
        System.arraycopy(v, 0, out, 0, dim);
        return new Vector(out);
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

    public float get(int i) {
        return v[i];
    }

    public int getDim() {
        return dim;
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
