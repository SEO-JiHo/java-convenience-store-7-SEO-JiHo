package product;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

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
}
