package nn;

import java.util.Random;

public class RNG {
    private static Random gen;
    private static boolean seeded = false;

    // initialize RNG with a seed
    public static void init(int seed) {
        if (seeded) {
            throw new UnsupportedOperationException("RNG already seeded");
        }
        gen = new Random((long) seed);
        seeded = true;
    }

    // get a random float from 0 to 1
    public static float rand() {
        if (!seeded) {
            throw new UnsupportedOperationException("RNG needs to be seeded");
        }
        return gen.nextFloat();
    }

    private RNG() {
    }
}
