package exercise;

import java.util.ArrayList;
import java.util.List;

// BEGIN
public class App {

    public static boolean scrabble(String chars, String word) {
        if (chars.length() < word.length()) {
            return false;
        } else {
            List<String> listChars = new ArrayList<>(List.of(chars.split("")));
            List<String> listWord = new ArrayList<>(List.of(word.toLowerCase().split("")));

            int i = 0;
            while (i < listChars.size() && !listWord.isEmpty()) {
                if (listWord.contains(listChars.get(i))) {
                    listWord.remove(listWord.indexOf(listChars.get(i)));
                }
                i++;
            }
            return listWord.isEmpty();
        }
    }
}
//END
