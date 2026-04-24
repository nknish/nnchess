package nn;

public class Network {
    private Layer[] layers;
    private int depth;
    private int width;
    private int inputWidth;
    private int outputWidth;

    // initialize, specifying depth, width, and input/output width
    public Network(int depth, int width, int inputWidth, int outputWidth) {
        this.depth = depth;
        this.width = width;
        this.inputWidth = inputWidth;
        this.outputWidth = outputWidth;
        layers = new Layer[depth];

        // first hidden layer, shaped by input width
        layers[0] = new Layer(3, inputWidth, "relu");

        // other hidden layers
        for (int i = 1; i < depth - 1; i++) {
            layers[i] = new Layer(3, 3, "relu");
        }

        // output layer
        layers[depth - 1] = new Layer(outputWidth, 3, "softmax");
    }

    // feed an input through all layers and return the output
    public Vector infer(Vector input) {
        if (input.getDim() != inputWidth) {
            throw new IllegalArgumentException("input vector has wrong dim");
        }

        layers[0].forward(input);
        for (int i = 1; i < depth; i++) {
            layers[i].forward(layers[i - 1].getActivations());
        }

        return layers[depth - 1].getActivations();
    }

    public int getDepth() {
        return depth;
    }

    public int getWidth() {
        return width;
    }

    public int getInputWidth() {
        return inputWidth;
    }

    public int getOutputWidth() {
        return outputWidth;
    }
}
