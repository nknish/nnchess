package nn;

public class Main {
    public static void main(String[] args) {
        Network n = new Network(2, 3, 4, 4);
        Vector input = new Vector(new float[] {1, 2, 3, 4});

        Vector output = n.infer(input);
        System.out.println(output);
    }
}
