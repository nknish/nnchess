package nn;

public class Timer {
    private static boolean running = false;
    private static long startTime;

    public static void start() {
        if (running) {
            throw new IllegalStateException("timer is already running");
        }
        running = true;
        startTime = System.currentTimeMillis();
    }

    public static void stop() {
        long endTime = System.currentTimeMillis();
        if (!running) {
            throw new IllegalStateException("timer was never started");
        }
        running = false;
        System.out.println("Total execution time: " + formatTime(endTime - startTime));
    }

    private static String formatTime(long ms) {
        int days = 0;
        int hours = 0;
        int minutes = 0;
        while (ms >= 1000 * 60 * 60 * 24) {
            days += 1;
            ms -= 1000 * 60 * 60 * 24;
        }
        while (ms >= 1000 * 60 * 60) {
            hours += 1;
            ms -= 1000 * 60 * 60;
        }
        while (ms >= 1000 * 60) {
            minutes += 1;
            ms -= 1000 * 60;
        }
        String s = "";
        if (days > 0) {
            s += days + "d ";
        }
        if (hours > 0) {
            s += hours + "h ";
        }
        if (minutes > 0) {
            s += minutes + "m ";
        }
        s += (ms / 1000.0) + "s";
        return s;
    }
}
