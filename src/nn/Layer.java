package nn;

public class Layer {
    private int width;
    private int inputWidth;
    private String activation;
    private Matrix weights;
    private Vector biases;
    private Vector inputs;
    private Vector nodes;
    private Vector activations;

    // new layer with specified width, width of input, and activation function
    public Layer(int width, int inputWidth, String activation) {
        this.width = width;
        this.inputWidth = inputWidth;
        this.activation = activation;
        weights = new Matrix(width, inputWidth);
        biases = new Vector(width);
    }

    // feed forward inputs by multiplying by weights and applying activation
    public void forward(Vector inputs) {
        this.inputs = inputs;
        nodes = weights.mul(inputs).add(biases);
        activations = nodes.activate(activation);
    }

    // update weights and backpropagate gradient
    public Vector backward(Vector grad, float lr) {
        // compute gradients
        Vector dldz = grad.mul(nodes.grad(activation));
        Matrix dldW = dldz.outer(inputs);
        Vector dldx = weights.t().mul(dldz);

        // update weights and biases
        weights = weights.sub(dldW.mul(lr));
        biases = biases.sub(dldz.mul(lr));

        // backpropagate
        return dldx;
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
