package us.takiyo;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static int generateNumber(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to + 1);
    }
}
