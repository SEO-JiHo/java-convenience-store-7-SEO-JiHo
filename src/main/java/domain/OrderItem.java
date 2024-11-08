package domain;

import java.util.List;

public class OrderItem {
    private final String name;
    private final int quantity;
    private final Products products;

    public OrderItem(String name, int quantity, Products products) {
        validateOrderItem();
        this.name = name;
        this.quantity = quantity;
        this.products = products;
    }

    private void validateOrderItem() {
        if (products.findProductByName(name) == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }

        if (!products.hasSufficientStock(name, quantity)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public double getItemPrice(Products products) {
        return products.findProductByName(name).getPrice();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
