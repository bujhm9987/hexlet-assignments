package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

// BEGIN
class AppTest {
    @Test
    void enlargeArrayImage() {
        String[][] imageEmpty = {};
        String[][] enlargedImageEmpty = {};
        String[][] actualEmpty = App.enlargeArrayImage(imageEmpty);
        assertThat(actualEmpty).isEqualTo(enlargedImageEmpty);

        String[][] image1 = {
                {"*", "*", "*", "*"},
                {"*", " ", " ", "*"},
                {"*", " ", " ", "*"},
                {"*", "*", "*", "*"},
        };
        String[][] enlargedImage1 = {
                {"*", "*", "*", "*", "*", "*", "*", "*"},
                {"*", "*", "*", "*", "*", "*", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", " ", " ", " ", " ", "*", "*"},
                {"*", "*", "*", "*", "*", "*", "*", "*"},
                {"*", "*", "*", "*", "*", "*", "*", "*"},
        };
        String[][] actual1 = App.enlargeArrayImage(image1);
        assertThat(actual1).isEqualTo(enlargedImage1);

        String[][] image2 = {
                {"+", "@", "@", " ", "@", "@", "+"},
                {"+", "@", " ", " ", " ", "@", "+"},
                {"+", "@", "@", " ", "@", "@", "+"},
        };
        String[][] enlargedImage2 = {
                {"+", "+", "@", "@", "@", "@", " ", " ", "@", "@", "@", "@", "+", "+"},
                {"+", "+", "@", "@", "@", "@", " ", " ", "@", "@", "@", "@", "+", "+"},
                {"+", "+", "@", "@", " ", " ", " ", " ", " ", " ", "@", "@", "+", "+"},
                {"+", "+", "@", "@", " ", " ", " ", " ", " ", " ", "@", "@", "+", "+"},
                {"+", "+", "@", "@", "@", "@", " ", " ", "@", "@", "@", "@", "+", "+"},
                {"+", "+", "@", "@", "@", "@", " ", " ", "@", "@", "@", "@", "+", "+"},
        };
        String[][] actual2 = App.enlargeArrayImage(image2);
        assertThat(actual2).isEqualTo(enlargedImage2);

        String[][] image3 = {
                {"+", "-", "*"},
                {"*", " ", "+"},
                {"-", "+", "-"},
        };
        String[][] enlargedImage3 = {
                {"+", "+", "-", "-", "*", "*"},
                {"+", "+", "-", "-", "*", "*"},
                {"*", "*", " ", " ", "+", "+"},
                {"*", "*", " ", " ", "+", "+"},
                {"-", "-", "+", "+", "-", "-"},
                {"-", "-", "+", "+", "-", "-"},
        };
        String[][] actual3 = App.enlargeArrayImage(image3);
        assertThat(actual3).isEqualTo(enlargedImage3);
    }
}
// END
