package domain;

import java.util.Collections;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public boolean containsProduct(String name) {
        for (Product product : products) {
            if (product.matchName(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSufficientStock(String name, int purchaseQuantity) {
        int totalStockQuantity = 0;
        for (Product product : products) {
            if (product.matchName(name)) {
                totalStockQuantity += product.getQuantity();
            }
        }

        return totalStockQuantity - purchaseQuantity >= 0;
    }
}
