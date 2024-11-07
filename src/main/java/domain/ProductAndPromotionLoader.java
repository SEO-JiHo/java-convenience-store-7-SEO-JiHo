package domain;

import validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductLoader {
    private static final int PRODUCT_FIELD_COUNT = 4;
    private static final int PROMOTION_FIELD_COUNT = 5;

    private static final String SPLIT_DELIMITER = ",";

    public List<Product> loadProducts(String filePath) {
        processFile(filePath);

        return products;
    }

    private List<String> processFile(String filePath) {
        try {
            return Files.lines(Paths.get(filePath))
                    .skip(1)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일 경로에 오류가 있습니다.");
        }
    }

    private void loadSingleProduct(String oneLine, List<Product> products) {
        String[] field = oneLine.split(SPLIT_DELIMITER);
        Validator.validateFieldCount(field);

        try {
            Product product = new Product(
                    field[0], Integer.parseInt(field[1]), Integer.parseInt(field[2]), field[3]);
            products.add(product);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] 필드의 값에 오류가 있습니다: " + oneLine);
        }
    }
}
