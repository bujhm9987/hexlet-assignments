package exercise;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// BEGIN
public class App {
    public static void swapKeyValue(KeyValueStorage storage) {
        Map<String, String> mapStorage = storage.toMap();
        Set<String> setKey = new HashSet<>(mapStorage.keySet());

        for (String key : setKey) {
            String value = storage.get(key, "default");
            storage.unset(key);
            storage.set(value, key);
        }
    }
}
// END
