package exercise;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

// BEGIN
public class App {
    public static Map<String, Integer> getWordCount(String sentence) {
        Map<String, Integer> wordsCount = new HashMap<>();
        List<String> listWords = new ArrayList<>(List.of(sentence.split(" ")));
        for (String words : listWords) {
            if (sentence.length() == 0) {
                break;
            } else if (wordsCount.containsKey(words)) {
                wordsCount.put(words, wordsCount.get(words) + 1);
            } else {
                wordsCount.put(words, 1);
            }
        }
        return wordsCount;
    }
    public static String toString(Map wordsMap) {
        if (wordsMap.size() == 0) {
            return "{}";
        } else {
            StringBuilder result = new StringBuilder("{");
            for (Object key: wordsMap.keySet()) {
                result.append("\n  ").append(key);
                result.append(": ").append(wordsMap.get(key));
            }
            result.append("\n}");
            return result.toString();
        }
    }
}
//END
