package domain;

import view.InputView;

public class OrderItem {
    private final String name;
    private int itemQuantity;
    private int freeItemQuantity;
    private final Products products;
    InputView inputView = new InputView();

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

    public void setOrderItemQuantity(Products products, Promotions promotions) {
        Product promotionProduct = products.getPromotionAppliedProductByName(name);
        int promotionAppliedQuantity = promotionProduct.getQuantity();

        if (promotionAppliedQuantity > 0) {
            int paidQuantity = promotions.getPromotionByName(promotionProduct.getPromotion()).getPaidQuantity();
            int freeQuantity = promotions.getPromotionByName(promotionProduct.getPromotion()).getFreeQuantity();

            handleFreeProductOffer(promotionAppliedQuantity, paidQuantity, freeQuantity, products, promotions);
            handleNoDiscountQuantity(promotionAppliedQuantity, paidQuantity, freeQuantity, products, promotions);
        }
    }

    public boolean applyPromotionToItem(Products products, Promotions promotions) {
        String promotionName = products.getPromotionAppliedProductByName(name).getPromotion();
        int discountQuantity = promotions.getDiscountQuantityIfApplicable(promotionName);
        if (discountQuantity > 0) {
            addFreeItemQuantity(itemQuantity /
                    (promotions.getPromotionByName(promotionName).getPaidQuantity() +
                     promotions.getPromotionByName(promotionName).getFreeQuantity()));
            return true;
        }
        return false;
    }

    private void handleNoDiscountQuantity(int promotionAppliedQuantity, int paidQuantity, int freeQuantity, Products products, Promotions promotions) {
        if (itemQuantity > promotionAppliedQuantity) {
            int noDiscountQuantity = itemQuantity - promotionAppliedQuantity +
                    promotionAppliedQuantity % (paidQuantity + freeQuantity);
            if (!inputView.requestPurchaseWithoutPromotion(name, noDiscountQuantity)) {
                addOrderItemQuantity(-noDiscountQuantity);
            }
        }
    }

    private void handleFreeProductOffer(int promotionAppliedQuantity, int paidQuantity, int freeQuantity, Products products, Promotions promotions) {
        if (itemQuantity <= promotionAppliedQuantity &&
                itemQuantity % (paidQuantity + freeQuantity) == paidQuantity) {
            if (promotionAppliedQuantity - itemQuantity >= freeQuantity &&
                    inputView.requestAddFreeProduct(name)) {
                addOrderItemQuantity(freeQuantity);
                addFreeItemQuantity(freeQuantity);
            }
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
