package recipes.utils;

import java.util.List;

public interface StringValidator {

    default boolean isValid(String str) {
        return str != null && !str.isEmpty() && !str.isBlank();
    }

    default boolean isValid(List<String> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        return list.stream().allMatch(this::isValid);
    }
}
