package nn;

public class Layer {
    private int width;
    private int inputWidth;
    private String activation;
    private Matrix weights;
    private Matrix weightsGrad;
    private Vector biases;
    private Vector biasesGrad;
    private Vector inputs;
    private Vector nodes;
    private Vector activations;

    // new layer with specified width, width of input, and activation function
    public Layer(int width, int inputWidth, String activation) {
        this.width = width;
        this.inputWidth = inputWidth;
        this.activation = activation;
        weights = new Matrix(width, inputWidth, true);
        biases = new Vector(width);
        weightsGrad = new Matrix(width, inputWidth, false);
        biasesGrad = new Vector(width);
    }

    // feed forward inputs by multiplying by weights and applying activation
    public void forward(Vector inputs) {
        this.inputs = inputs;
        nodes = weights.mul(inputs).add(biases);
        activations = nodes.activate(activation);
    }

    // accumulate weight gradients and backpropagate gradient
    public Vector backward(Vector grad) {
        // compute gradients
        Vector dldz = grad.mul(nodes.grad(activation));
        Matrix dldW = dldz.outer(inputs);
        Vector dldx = weights.t().mul(dldz);

        // update accumulated gradients for weights and biases
        weightsGrad = weightsGrad.add(dldW);
        biasesGrad = biasesGrad.add(dldz);

        // backpropagate
        return dldx;
    }

    public void applyGradients(int batchSize, float lr) {
        // calculate gradients to apply, normalized for batch size
        weightsGrad = weightsGrad.mul(lr / batchSize);
        biasesGrad = biasesGrad.mul(lr / batchSize);

        // apply gradients
        weights = weights.sub(weightsGrad);
        biases = biases.sub(biasesGrad);

        // reset gradient accumulators
        weightsGrad = new Matrix(width, inputWidth, false);
        biasesGrad = new Vector(width);
    }

    // return the activations (output)
    public Vector getActivations() {
        return activations;
    }

    public int getWidth() {
        return width;
    }

    public int getInputWidth() {
        return inputWidth;
    }
}
