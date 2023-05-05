package exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReversedSequenceTest {
    private CharSequence text = new ReversedSequence("abcdef");

    @Test
    void testLength() {
        int result = text.length();
        assertThat(result).isEqualTo(6);
    }
    @Test
    void testCharAt() {
        char result = text.charAt(1);
        assertThat(result).isEqualTo('e');
    }
    @Test
    void testSubSequence() {
        String result = text.subSequence(1, 4).toString();
        assertThat(result).isEqualTo("edc");
    }
    @Test
    void testToString() {
        String result = text.toString();
        assertThat(result).isEqualTo("fedcba");
    }
}
