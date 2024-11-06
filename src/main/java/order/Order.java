package order;

import java.util.Collections;
import java.util.List;

public class Order {
    private final List<OrderItem> orders;

    public Order(List<OrderItem> orders) {
        this.orders = orders;
    }

    public List<OrderItem> getOrder() {
        return Collections.unmodifiableList(orders);
    }
}
