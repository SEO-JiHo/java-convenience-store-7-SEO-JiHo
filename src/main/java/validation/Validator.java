package validation;

import order.Order;
import order.OrderItem;
import product.Products;

import java.util.List;

public class Validator {
    private static final String INVALID_INPUT_FORMAT_ERROR =
            "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    private static final String PRODUCT_NOT_FOUND_ERROR =
            "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    private static final String INSUFFICIENT_STOCK_ERROR =
            "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    private static final String GENERAL_INVALID_INPUT_ERROR =
            "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.";
    private static final char PRODUCT_PREFIX = '[';
    private static final char PRODUCT_SUFFIX = ']';
    private static final String SPLIT_DELIMITER = "-";

    public static void validateProductFieldCount(String[] field) {
        if (field.length != 4) {
            throw new IllegalArgumentException(
                    "[ERROR] 필드의 수가 올바르지 않습니다: " + String.join(",", field));
        }
    }

    public static void validateProductAndQuantityInput(String userInput, List<OrderItem> order) {
        String[] parts = userInput.split(",");
        for (String part : parts) {
            validateNotEmptyValue(part, INVALID_INPUT_FORMAT_ERROR);
            String formattedString = validateWrappedWithBracket(part);
            order.add(validateSplittableWithDash(formattedString));
        }
    }

    private static String validateWrappedWithBracket(String inputString) {
        if (inputString.charAt(0) != PRODUCT_PREFIX
                || inputString.charAt(inputString.length()-1) != PRODUCT_SUFFIX) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT_ERROR);
        }

        return inputString.substring(1, inputString.length()-1);
    }

    private static OrderItem validateSplittableWithDash(String inputString) {
        String[] parts = inputString.split(SPLIT_DELIMITER);
        for (String part : parts) {
            validateNotEmptyValue(part, INVALID_INPUT_FORMAT_ERROR);
        }

        try {
            return new OrderItem(parts[0], Integer.parseInt(parts[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT_ERROR);
        }
    }

    public static void validateOrderItemIsInStock(Products products, String name) {
        products.getProducts();
    }

    private static void validateNotEmptyValue(String string, String errorMessage) {
        if (string.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
