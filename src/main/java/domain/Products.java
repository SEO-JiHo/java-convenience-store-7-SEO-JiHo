package domain;

import java.util.Collections;
import java.util.List;

public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
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

    public void setProductQuantity(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            int remainingQuantity = item.getQuantity();
            remainingQuantity = remainingPromotionStock(item, remainingQuantity);

            if (remainingQuantity > 0) {
                reduceNoPromotionStock(item, remainingQuantity);
            }
        }
    }

    private int remainingPromotionStock(OrderItem item, int remainingQuantity) {
        for (Product product : products) {
            if (product.matchName(item.getName()) && !product.getPromotion().equals("null")) {
                return product.reduceStockQuantity(remainingQuantity);
            }
        }
        return remainingQuantity;
    }

    private void reduceNoPromotionStock(OrderItem item, int remainingQuantity) {
        for (Product product : products) {
            if (product.matchName(item.getName()) && product.getPromotion().equals("null")) {
                remainingQuantity = product.reduceStockQuantity(remainingQuantity);
                if (remainingQuantity == 0) {
                    break;
                }
            }
        }
    }

    public Product findProductByName(String name) {
        for (Product product : products) {
            if (product.matchName(name)) {
                return product;
            }
        }
        return null;
    }

    public Product getPromotionAppliedProductByName(String productName) {
        for (Product product : products) {
            if (product.matchName(productName) && !product.getPromotion().equals("null")) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
