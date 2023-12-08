package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN
    public static void main(String[] args) {
        int[] numbers = {65, 6, 12, -123, 88, 266, 27, 1234, -12};
        System.out.println(App.getMinMax(numbers));
    }

    public static Map<String, Integer> getMinMax(int[] numbers) {
        HashMap<String, Integer> result = new HashMap<>();
        MinThread minThread = new MinThread(numbers);
        MaxThread maxThread = new MaxThread(numbers);
        LOGGER.info("Thread " + minThread.getName() + " started");
        minThread.start();
        LOGGER.info("Thread " + maxThread.getName() + " started");
        maxThread.start();

        try {
            minThread.join();
            LOGGER.info("Thread " + minThread.getName() + "finished");
            maxThread.join();
            LOGGER.info("Thread " + maxThread.getName() + " finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        result.put("min=", minThread.getMinNumber());
        result.put("max=", maxThread.getMinNumber());

        return result;
    }
    // END
}
