package exercise;

import java.util.ArrayList;
import java.util.List;

// BEGIN
public class App {
    public static List<String> buildApartmentsList(List<Home> homeList, Integer n) {

        for (int left = 0; left < homeList.size(); left++) {
            int minInd = left;
            for (int i = left; i < homeList.size(); i++) {
                if (homeList.get(i).getArea() < homeList.get(minInd).getArea()) {
                    minInd = i;
                }
            }
            Home tmp = homeList.get(left);
            homeList.set(left, homeList.get(minInd));
            homeList.set(minInd, tmp);
        }
        if (n > homeList.size()) n = homeList.size();

        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(homeList.get(i).toString());
        }
        return result;
    }
}
// END
