package product;

import validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductLoader {
    private static final String SPLIT_DELIMITER = ",";

    public List<Product> loadProducts(String filePath) {
        List<Product> products = new ArrayList<>();

        processFile(filePath, products);

        return products;
    }

    private void processFile(String filePath, List<Product> products) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));) {
            String oneLine;
            while ((oneLine = reader.readLine()) != null) {
                loadSingleProduct(oneLine, products);
            }
        } catch (IOException e) {
            System.out.println("[ERROR] 파일 경로에 오류가 있습니다.");
        }
    }

    private void loadSingleProduct(String oneLine, List<Product> products) {
        String[] field = oneLine.split(SPLIT_DELIMITER);
        Validator.validateProductFieldCount(field);

        try {
            Product product = new Product(
                    field[0], Integer.parseInt(field[1]), Integer.parseInt(field[2]), field[3]);
            products.add(product);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] 필드의 값에 오류가 있습니다: " + oneLine);
        }
    }
}
