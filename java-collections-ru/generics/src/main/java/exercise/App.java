package exercise;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

// BEGIN
public class App {
    public static List<Map<String, String>> findWhere(List<Map<String, String>> listBook, Map<String, String> where) {
        List<Map<String, String>> result = new ArrayList<>();

        for (Map<String, String> bookMap: listBook) {
            int inside = 0;
            for (Map.Entry<String, String> bookWhere: where.entrySet()) {
                for (Map.Entry<String, String> bookInList : bookMap.entrySet()) {
                    inside = bookInList.equals(bookWhere) ? inside + 1 : inside;
                }
            }
            if (inside == where.size()) {
                result.add(bookMap);
            }
        }
        return result;
    }
}
//END
