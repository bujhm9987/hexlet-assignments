package exercise;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// BEGIN
public class Validator {

    public static List<String> validate(Address address) {
        List<String> result = new ArrayList<>();
        for (Field field: address.getClass().getDeclaredFields()) {
            try {
                if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                    if (field.get(address) == null) {
                        result.add(field.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Map<String, List<String>> advancedValidate(Address address) {
        Map<String, List<String>> result = new HashMap<>();
        for (Field field: address.getClass().getDeclaredFields()) {
            List<String> errors = new ArrayList<>();
            try {
                field.setAccessible(true);
                if (field.isAnnotationPresent(NotNull.class)) {
                    if (field.get(address) == null) {
                        errors.add("can not be null");
                    }
                }
                if (field.isAnnotationPresent(MinLength.class)) {
                    MinLength length = field.getAnnotation(MinLength.class);
                    if (field.get(address).toString().length() < length.minLength()) {
                        errors.add("length less than " + length.minLength());
                    }
                }
                if (!errors.isEmpty()) {
                    result.put(field.getName(), errors);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
// END
