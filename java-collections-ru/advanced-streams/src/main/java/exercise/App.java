package exercise;

import java.util.stream.Collectors;
import java.util.Arrays;

// BEGIN
class App {
    public static String getForwardedVariables(String inputConf) {
        String[] outputStrings = inputConf.split("\n");
        return Arrays.stream(outputStrings)
                .filter(x -> x.startsWith("environment"))
                .map(x -> x.replaceAll("environment=", ""))
                .map(x -> x.replaceAll("\"", ""))
                .map(x -> x.split(","))
                .flatMap(Arrays::stream)
                .filter(x -> x.startsWith("X_FORWARDED_"))
                .map(x -> x.replaceAll("X_FORWARDED_", ""))
                .collect(Collectors.joining(","));
    }
}
//END
