package nn;

public class Layer {
    private int width;
    private int inputWidth;
    private String activation;
    private Matrix weights;
    private Vector biases;
    private Vector activations;

    // new layer with specified width, width of input, and activation function
    public Layer(int width, int inputWidth, String activation) {
        this.width = width;
        this.inputWidth = inputWidth;
        this.activation = activation;
        weights = new Matrix(width, inputWidth, true);
        biases = new Vector(width, true);
    }

    // feed forward inputs by multiplying by weights and applying activation
    public void forward(Vector inputs) {
        System.out.println("feeding forward");
        Vector nodes = weights.mul(inputs).add(biases);
        System.out.println("applied weights and biases");
        System.out.println(nodes);
        activations = nodes.activate(activation);
        System.out.println("applied activation");
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
