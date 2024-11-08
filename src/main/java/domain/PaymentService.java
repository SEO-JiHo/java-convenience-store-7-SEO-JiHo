package domain;

import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private final Products products;
    private final Order order;
    private final Promotions promotions;
    private final List<ReceiptLine> receipt = new ArrayList<>();

    public PaymentService(Products products, Order order, Promotions promotions, List<ReceiptLine> receipt) {
        this.products = products;
        this.order = order;
        this.promotions = promotions;
    }

    public double calculateTotalPurchaseAmount() {
        double totalAmount = 0;
        for (OrderItem item : order.getOrderItems()) {
            double itemPrice = item.getQuantity() * item.getItemPrice(products);
            receipt.add(new ReceiptLine(item.getName(), item.getQuantity(), itemPrice));
            totalAmount += itemPrice;
        }

        return totalAmount;
    }

    public double calculatePromotionalDiscountAmount() {
        double discountAmount = 0;
        for (OrderItem item : order.getOrderItems()) {
            int discountQunatity = promotions.getDiscountQuantityIfApplicable(item.getName());
            if (discountQunatity > 0) {
                double itemPrice = item.getItemPrice(products);
            }
        }
        return discountAmount;
    }

//    public double getMembershipDiscountAmount() {
//        return calculateTotalPurchaseAmount() * 0.3;
//    }
//
//    public double getPaymentAmount() {
//        return ;
//    }
}
