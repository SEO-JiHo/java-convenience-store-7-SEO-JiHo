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
            if (products.getPromotionAppliedProductByName(item.getName()) != null) {
                if (item.applyPromotionToItem(products, promotions)) {
                    item.setOrderItemQuantity(products, promotions);
                }
            }
        }
    }

    public void adjustStock() {
        products.setProductQuantity(order);
    }

    public double totalPurchaseAmount() {
        double totalAmount = 0;
        for (OrderItem item : order.getOrderItems()) {
            double itemPrice = item.getQuantity() * item.getItemPrice(products);
            totalAmount += itemPrice;
        }

        return totalAmount;
    }

    public double promotionalDiscountAmount() {
        double PromotionDiscount = 0;
        for (OrderItem item : order.getOrderItems()) {
            double discount = item.getFreeQuantity() * item.getItemPrice(products);
            PromotionDiscount += discount;
        }
        return PromotionDiscount;
    }

    public double membershipDiscountAmount(double totalAmount, double promotionDiscount) {
        double discount = (totalAmount - promotionDiscount) * MEMBERSHIP_DISCOUNT_RATE;
        return Math.min(discount, MEMBERSHIP_DISCOUNT_MAX_AMOUNT);
    }
}
