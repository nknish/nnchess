package nn;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Timer.start();

        // metadata
        String fname = "data/data.csv"; // generated with data/gen.py
        int inputDim = 4;
        int outputDim = 4;
        int numSamples = 100000;
        int seed = 12345;

        // hyperparameters
        int numLayers = 5;
        int layerWidth = 5;
        int epochs = 2;
        int batchSize = 10000;
        float lr = 0.1f;

        // initialize network with random weights
        RNG.init(seed);
        Network n = new Network(numLayers, layerWidth, inputDim, outputDim);

        // process input data
        Reader.readData(fname, inputDim, outputDim, numSamples);

        // get training data
        Reader.splitTestData(0.1);
        List<Vector> xTrain = Reader.getXTrain();
        List<Vector> yTrain = Reader.getYTrain();

        // train network on training data
        n.train(xTrain, yTrain, batchSize, epochs, lr);

        // run inference on heldout data
        float meanLoss = 0;
        List<Vector> xTest = Reader.getXTest();
        List<Vector> yTest = Reader.getYTest();
        List<Vector> yPred = n.infer(xTest);
        for (int i = 0; i < yTest.size(); i++) {
            Vector exp = yTest.get(i);
            Vector pred = yPred.get(i);
            meanLoss += exp.sub(pred).rms();
        }
        meanLoss /= yTest.size();
        System.out.println("average RMSE on test data after training: " + meanLoss);

        Timer.stop();
    }
}
