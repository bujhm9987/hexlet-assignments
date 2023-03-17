package exercise;

import java.util.List;

// BEGIN
public class App {
    public static long getCountOfFreeEmails(List<String> emails) {
        return emails.stream()
                .map(email -> email.substring(email.lastIndexOf('@') + 1))
                .filter(email -> email.equalsIgnoreCase("gmail.com")
                        || email.equalsIgnoreCase("yandex.ru")
                        || email.equalsIgnoreCase("hotmail.com"))
                .count();
    }
}
// END
