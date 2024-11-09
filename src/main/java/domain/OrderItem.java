package domain;

public class OrderItem {
    private final String name;
    private int itemQuantity;
    private int freeItemQuantity;
    private final Products products;

    public OrderItem(String name, int itemQuantity, Products products) {
        this.name = name;
        this.itemQuantity = itemQuantity;
        this.freeItemQuantity = 0;
        this.products = products;
        validateOrderItem();
    }

    private void validateOrderItem() {
        if (products.findProductByName(name) == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }

        if (!products.hasSufficientStock(name, itemQuantity)) {
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
        return itemQuantity;
    }

    public int getFreeQuantity() {
        return freeItemQuantity;
    }

    public void addFreeItemQuantity(int newFreeQuantity) {
        this.freeItemQuantity += newFreeQuantity;
    }

    public void addOrderItemQuantity(int addedQuantity) {
        this.itemQuantity += addedQuantity;
    }
}
