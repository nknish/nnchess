package nn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RNG {
    private static Random gen;
    private static boolean seeded = false;

    // initialize RNG with a seed
    public static void init(int seed) {
        if (seeded) {
            throw new IllegalStateException("RNG already seeded");
        }
        gen = new Random((long) seed);
        seeded = true;
    }

    // get a random float from 0 to 1
    public static float rand() {
        if (!seeded) {
            throw new IllegalStateException("RNG needs to be seeded");
        }
        return gen.nextFloat();
    }

    public static List<Integer> shuffleIndices(int len) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, gen);
        return indices;
    }

    private RNG() {
    }
}
