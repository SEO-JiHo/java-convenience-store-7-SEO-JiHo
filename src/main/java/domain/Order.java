package domain;

import java.util.Collections;
import java.util.List;

public class Order {
    private final List<OrderItem> items;

    public Order(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(items);
    }

    public int getTotalItemCount() {
        int totalCount = 0;
        for (OrderItem item : items) {
            totalCount += item.getQuantity();
        }
        return totalCount;
    }
}
