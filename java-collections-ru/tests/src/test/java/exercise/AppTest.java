package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testTake() {
        // BEGIN
        int count = 5;

        List<Integer> isEmptyList = new ArrayList<>();
        List<Integer> actualList1 = App.take(isEmptyList, count);
        assertThat(actualList1).isEmpty();

        List<Integer> listLessCount = List.of(12, 5, 1, 4);
        List<Integer> actualList2 = App.take(listLessCount, count);
        assertThat(actualList2).isEqualTo(List.of(12, 5, 1, 4));

        List<Integer> listLargerCount = List.of(5, 74, 62, 2, 15, 23, 5, 7);
        List<Integer> actualList3 = App.take(listLargerCount, count);
        assertThat(actualList3).isEqualTo(List.of(5, 74, 62, 2, 15));

        List<Integer> listExactlyCount = List.of(12, 1, 1, 5, 45);
        List<Integer> actualList4 = App.take(listExactlyCount, count);
        assertThat(actualList4).isEqualTo(List.of(12, 1, 1, 5, 45));
        // END
    }
}
