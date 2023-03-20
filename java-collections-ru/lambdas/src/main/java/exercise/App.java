package exercise;

import java.util.Arrays;
import java.util.stream.Stream;

// BEGIN
public class App {
    public static String[][] enlargeArrayImage(String[][] image) {
        return Arrays.stream(image)
                .flatMap(line -> Stream.of(enLargeRow(line), enLargeRow(line)))
                .toArray(String[][]::new);
    }
    private static String[] enLargeRow(String[] image) {
        return Arrays.stream(image)
                .flatMap(row -> Stream.of(row, row))
                .toArray(String[]::new);
    }
}
// END
