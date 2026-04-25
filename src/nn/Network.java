package nn;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private Layer[] layers;
    private int depth;
    private int width;
    private int inputWidth;
    private int outputWidth;
    private Vector predicted;
    private Vector lossGrad;
    private float runningLoss;
    
    // initialize, specifying depth, width, and input/output width
    public Network(int depth, int width, int inputWidth, int outputWidth) {
        this.depth = depth;
        this.width = width;
        this.inputWidth = inputWidth;
        this.outputWidth = outputWidth;
        
        layers = new Layer[depth];

        // first hidden layer, shaped by input width
        layers[0] = new Layer(width, inputWidth, "relu");

        // other hidden layers
        for (int i = 1; i < depth - 1; i++) {
            layers[i] = new Layer(width, width, "relu");
        }

        // output layer
        layers[depth - 1] = new Layer(outputWidth, width, "linear");

        // initialize with no normalization (span 1, mean 0)
        float[] normWeights = new float[inputWidth];
        for (int i = 0; i < inputWidth; i++) {
            normWeights[i] = 1;
        }
    }
        
    // make a prediction for a given input
    public Vector infer(Vector input) {
        forward(input);
        return predicted;
    }

    // make a prediction for a given list of inputs
    public List<Vector> infer(List<Vector> input) {
        List<Vector> predicteds = new ArrayList<Vector>();
        for (Vector x : input) {
            forward(x);
            predicteds.add(predicted);
        }
        return predicteds;
    }
    
    // train the network, applying gradients and printing loss every batch
    public void train(List<Vector> x, List<Vector> y, int batchSize, int epochs, float lr) {
        if (x.size() != y.size()) {
            throw new IllegalArgumentException("x and y sizes must match");
        }
        for (int epoch = 0; epoch < epochs; epoch++) {
            List<Integer> indices = RNG.shuffleIndices(x.size());
            int batchCount = 0;
            int samplesThisBatch = 0;
            for (int i : indices) {
                forward(x.get(i));
                runningLoss += loss(y.get(i));
                backward();
                samplesThisBatch++;
                if (samplesThisBatch == batchSize || indices.getLast() == i) {
                    applyGradients(samplesThisBatch, lr);
                    runningLoss /= samplesThisBatch;
                    System.out.println("avg loss epoch " + (epoch + 1) + " batch " + (batchCount + 1) + ": " + runningLoss);
                    runningLoss = 0;
                    samplesThisBatch = 1;
                    batchCount++;
                }
            }
        }
    }

    private void forward(Vector input) {
        if (input.getDim() != inputWidth) {
            throw new IllegalArgumentException("input vector has wrong dim");
        }

        layers[0].forward(input);
        for (int i = 1; i < depth; i++) {
            layers[i].forward(layers[i - 1].getActivations());
        }

        predicted = layers[depth - 1].getActivations();
    }

    private float loss(Vector expected) {
        lossGrad = predicted.sub(expected);
        return lossGrad.rms();
    }

    private void backward() {
        Vector grad = lossGrad;
        for (int i = depth - 1; i >= 0; i--) {
            Layer l = layers[i];
            grad = l.backward(grad);
        }
    }

    private void applyGradients(int batchSize, float lr) {
        for (Layer l : layers) {
            l.applyGradients(batchSize, lr);
        }
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
