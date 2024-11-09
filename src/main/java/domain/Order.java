package domain;

import view.InputView;

import java.util.Collections;
import java.util.List;

public class Order {
    private final List<OrderItem> items;
    InputView inputView = new InputView();

    public Order(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(items);
    }

    public void setOrderItemQuantity(Products products, Promotions promotions) {
        for (OrderItem item : items) {
            if (!applyPromotionToItem(item, products, promotions)) {
                continue;
            }
            Product promotionProduct = products.getPromotionAppliedProductByName(item.getName());
            int promotionAppliedQuantity = promotionProduct.getQuantity();
            int paidQuantity = promotions.getPromotionByName(promotionProduct.getPromotion()).getPaidQuantity();
            int freeQuantity = promotions.getPromotionByName(promotionProduct.getPromotion()).getFreeQuantity();

            handleNoDiscountQuantity(item, promotionAppliedQuantity, paidQuantity, freeQuantity, products, promotions);
            handleFreeProductOffer(item, promotionAppliedQuantity, paidQuantity, freeQuantity, products, promotions);
        }
    }

    private boolean applyPromotionToItem(OrderItem item, Products products, Promotions promotions) {
        int discountQuantity = promotions.getDiscountQuantityIfApplicable(item.getName());
        if (discountQuantity > 0) {
            item.addFreeItemQuantity(item.getQuantity() / (promotions.getPromotionByName(
                    products.getPromotionAppliedProductByName(item.getName()).getPromotion()
            ).getPaidQuantity() + promotions.getPromotionByName(
                    products.getPromotionAppliedProductByName(item.getName()).getPromotion()
            ).getFreeQuantity()));
            return true;
        }
        return false;
    }

    private void handleNoDiscountQuantity(OrderItem item, int promotionAppliedQuantity, int paidQuantity, int freeQuantity, Products products, Promotions promotions) {
        if (item.getQuantity() > promotionAppliedQuantity) {
            int noDiscountQuantity = item.getQuantity() - promotionAppliedQuantity +
                    promotionAppliedQuantity % (paidQuantity + freeQuantity);
            if (inputView.requestPurchaseWithoutPromotion(item.getName(), noDiscountQuantity)) {
                item.addOrderItemQuantity(-noDiscountQuantity);
            }
        }
    }

    private void handleFreeProductOffer(OrderItem item, int promotionAppliedQuantity, int paidQuantity, int freeQuantity, Products products, Promotions promotions) {
        if (item.getQuantity() <= promotionAppliedQuantity &&
                item.getQuantity() % (paidQuantity + freeQuantity) == paidQuantity) {
            if (promotionAppliedQuantity - item.getQuantity() >= freeQuantity &&
                    inputView.requestAddFreeProduct(item.getName())) {
                item.addOrderItemQuantity(freeQuantity);
                item.addFreeItemQuantity(freeQuantity);
            }
        }
    }

    public int getTotalItemCount() {
        int totalCount = 0;
        for (OrderItem item : items) {
            totalCount += item.getQuantity();
        }
        return totalCount;
    }
}
