package validation;

public class Validator {
    private static final char PRODUCT_PREFIX = '[';
    private static final char PRODUCT_SUFFIX = ']';
    private static final String SPLIT_DELIMITER = "-";

    public static String validateWrappedWithBracket(String inputString) {
        if (inputString.charAt(0) != PRODUCT_PREFIX
                || inputString.charAt(inputString.length()-1) != PRODUCT_SUFFIX) {
            throw new IllegalArgumentException();
        }

        return inputString.substring(1, inputString.length()-1);
    }

    public static String[] validateSplittableWithDash(String inputString) {
        String[] parts = inputString.split(SPLIT_DELIMITER);
        for (String part : parts) {
            Validator.validateNotEmptyValue(part);
        }

        return parts;
    }

    public static void validateFieldCount(String[] field, int fieldCount) {
        if (field.length != fieldCount) {
            throw new IllegalArgumentException(
                    "[ERROR] 필드의 수가 올바르지 않습니다: " + String.join(",", field));
        }
    }

    public static void validateNotEmptyValue(String string) {
        if (string.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}
