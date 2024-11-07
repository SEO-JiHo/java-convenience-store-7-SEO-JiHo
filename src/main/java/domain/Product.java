package domain;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getStockStatus() {
        return String.format("- %s %d원 %s %s",
                name,
                price,
                quantity != 0 ? quantity + "개" : "재고 없음",
                !promotion.equals("null") ? promotion : "");
    }

    public boolean matchName(String productName) {
        return productName.equals(name);
    }

    public boolean isInStock(int purchaseQuantity) {
        return (quantity - purchaseQuantity) >= 0;
    }

    public void reduceStockQuantity(int purchaseQuantity) {
        quantity -= purchaseQuantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
