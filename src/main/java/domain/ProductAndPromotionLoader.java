package domain;

import validation.Validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductAndPromotionLoader {
    private static final int PRODUCT_FIELD_COUNT = 4;
    private static final int PROMOTION_FIELD_COUNT = 5;
    private static final String SPLIT_DELIMITER = ",";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<Product> loadProducts(String filePath) {
        List<Product> products = new ArrayList<>();

        for (String line : processFile(filePath)) {
            loadSingleProduct(line, products);
        }

        return products;
    }

    public List<Promotion> loadPromotions(String filePath) {
        List<Promotion> promotions = new ArrayList<>();

        for (String line : processFile(filePath)) {
            loadSinglePromotion(line, promotions);
        }

        return promotions;
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
        Validator.validateFieldCount(field, PRODUCT_FIELD_COUNT);

        try {
            Product product = new Product(
                    field[0], Integer.parseInt(field[1]), Integer.parseInt(field[2]), field[3]);
            products.add(product);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] 필드의 값에 오류가 있습니다: " + oneLine);
        }
    }

    private void loadSinglePromotion(String oneLine, List<Promotion> promotions) {
        String[] field = oneLine.split(SPLIT_DELIMITER);
        Validator.validateFieldCount(field, PROMOTION_FIELD_COUNT);

        try {
            Promotion promotion = new Promotion(field[0], Integer.parseInt(field[1]), Integer.parseInt(field[2]),
                    dateFormat.parse(field[3]), dateFormat.parse(field[4]));
            promotions.add(promotion);
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("[ERROR] 필드의 값에 오류가 있습니다: " + oneLine);
        }
    }
}