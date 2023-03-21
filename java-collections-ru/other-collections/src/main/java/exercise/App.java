package exercise;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// BEGIN
class App {
    public static Map<String, String> genDiff(Map<String, Object> map1, Map<String, Object> map2) {
        Map<String, String> outputMap = new LinkedHashMap<>();
        Set<String> keys = new TreeSet<>();
        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());
        for (String key : keys) {
            if (!map1.containsKey(key)) {
                outputMap.put(key, "added");
            } else if (!map2.containsKey(key)) {
                outputMap.put(key, "deleted");
            } else if (map1.get(key) == map2.get(key)) {
                outputMap.put(key, "unchanged");
            } else {
                outputMap.put(key, "changed");
            }
        }
        return outputMap;
    }
}
//END
