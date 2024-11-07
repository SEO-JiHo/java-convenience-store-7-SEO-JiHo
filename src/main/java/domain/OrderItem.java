package domain;

public class OrderItem {
    private final String name;
    private final int quantity;

    public OrderItem(String name, int quantity, Products products) {
        validateOrderItem(products);
        this.name = name;
        this.quantity = quantity;
    }

    private void validateOrderItem(Products products) {
        if (!products.containsProduct(name)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }

        if (!products.hasSufficientStock(name, quantity)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }
}
