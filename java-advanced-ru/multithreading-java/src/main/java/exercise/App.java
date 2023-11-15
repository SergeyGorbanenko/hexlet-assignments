package exercise;

import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN
    public static Map<String, Integer> getMinMax(int[] numbers) {

        MaxThread maxThread = new MaxThread(numbers);
        MinThread minThread = new MinThread(numbers);

        maxThread.start();
        LOGGER.info("Thread " + maxThread.getName() + " started");
        minThread.start();
        LOGGER.info("Thread " + minThread.getName() + " started");

        try {
            minThread.join();
            LOGGER.info("Thread " + minThread.getName() + " finished");
        } catch (InterruptedException e) {
            System.out.println(minThread.getName() + "is interrupted");
        }

        try {
            maxThread.join();
            LOGGER.info("Thread " + maxThread.getName() + " finished");
        } catch (InterruptedException e) {
            System.out.println(maxThread.getName() + "is interrupted");
        }

        return Map.of(
            "min", minThread.getMin(),
            "max", maxThread.getMax()
        );
    }
    // END
}
