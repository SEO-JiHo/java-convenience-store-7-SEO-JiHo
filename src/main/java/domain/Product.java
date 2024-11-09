package domain;

public class Product {
    private final String name;
    private final double price;
    private int quantity;
    private final String promotion;

    public Product(String name, double price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getStockStatus() {
        return String.format("- %s %,.0f원 %s %s",
                name,
                price,
                quantity != 0 ? quantity + "개" : "재고 없음",
                !promotion.equals("null") ? promotion : "");
    }

    public boolean matchName(String productName) {
        return productName.equals(name);
    }

    public int reduceStockQuantity(int purchaseQuantity) {
        if (quantity >= purchaseQuantity) {
            quantity -= purchaseQuantity;
            return 0;
        }
        int remaining = purchaseQuantity - quantity;
        quantity = 0;
        return remaining;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getPromotion() {
        return promotion;
    }
}
