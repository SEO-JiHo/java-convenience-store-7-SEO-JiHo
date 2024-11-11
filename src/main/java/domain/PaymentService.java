package domain;

public class PaymentService {
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final double MEMBERSHIP_DISCOUNT_MAX_AMOUNT = 8000;
    private final Products products;
    private final Order order;
    private final Promotions promotions;

    public PaymentService(Products products, Order order, Promotions promotions) {
        this.products = products;
        this.order = order;
        this.promotions = promotions;
    }

    public void applyPromotion() {
        for (OrderItem item : order.getOrderItems()) {
            if (products.getPromotionAppliedProductByName(item.getName()) != null &&
                    item.isPromotionEligibleItem(promotions)) {
                item.setOrderItemQuantity(promotions);
            }
        }
    }

    public void adjustStock() {
        products.setProductQuantity(order);
    }

    public double totalPurchaseAmount() {
        double totalAmount = 0;
        for (OrderItem item : order.getOrderItems()) {
            double itemPrice = item.getQuantity() * item.getItemPrice();
            totalAmount += itemPrice;
        }

        return totalAmount;
    }

    public double promotionalDiscountAmount() {
        double PromotionDiscount = 0;
        for (OrderItem item : order.getOrderItems()) {
            double discount = item.getFreeQuantity() * item.getItemPrice();
            PromotionDiscount += discount;
        }
        return PromotionDiscount;
    }

    public double membershipDiscountAmount() {
        double membershipDiscount = 0;
        for (OrderItem item : order.getOrderItems()) {
            double discount = item.getMembershipEligibleAmount(promotions) * MEMBERSHIP_DISCOUNT_RATE;
            membershipDiscount += discount;
        }
        return Math.min(membershipDiscount, MEMBERSHIP_DISCOUNT_MAX_AMOUNT);
    }
}
