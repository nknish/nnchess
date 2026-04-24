package nn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {
    private static List<Vector> x;
    private static List<Vector> y;
    private static int splitIndex;

    // read csv of width xDim + yDim into memory
    public static void readData(String fname, int xDim, int yDim) {
        x = new ArrayList<>();
        y = new ArrayList<>();
        File f = new File(fname);
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                // read a row and parse floats
                float[] row = new float[xDim + yDim];
                String[] line = s.nextLine().split(",");
                for (int i = 0; i < xDim + yDim; i++) {
                    row[i] = Float.parseFloat(line[i]);
                }

                // store in 2 arrays, one for input and one for output
                float[] xArray = new float[xDim];
                float[] yArray = new float[yDim];
                for (int i = 0; i < xDim; i++) {
                    xArray[i] = row[i];
                }
                for (int i = 0; i < yDim; i++) {
                    yArray[i] = row[xDim + i];
                }

                // turn into vectors, add to list
                Vector input = new Vector(xArray);
                Vector expected = new Vector(yArray);
                x.add(input);
                y.add(expected);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("can't read file");
        }
    }

    // read csv, specifying number of samples to maintain (discard excess)
    public static void readData(String fname, int xDim, int yDim, int len) {
        readData(fname, xDim, yDim);
        if (len > x.size()) {
            throw new IllegalArgumentException("cannot get " + len + " samples out of " + x.size());
        }
        x = x.subList(0, len);
        y = y.subList(0, len);
    }

    // designate a portion of data for testing (e.g. 0.2 holds out 20%)
    public static void splitTestData(double heldout) {
        int len = x.size();
        Reader.splitIndex = len - (int) (len * heldout);
    }

    public static List<Vector> getXTrain() {
        return x.subList(0, splitIndex);
    }

    public static List<Vector> getYTrain() {
        return y.subList(0, splitIndex);
    }

    public static List<Vector> getXTest() {
        return x.subList(splitIndex, x.size());
    }

    public static List<Vector> getYTest() {
        return y.subList(splitIndex, y.size());
    }

    public static List<Vector> getX() {
        return x;
    }

    public static List<Vector> getY() {
        return y;
    }

    private Reader() {
    }
}
