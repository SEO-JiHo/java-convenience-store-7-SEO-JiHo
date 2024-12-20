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
        validateItemInStock();
        validateHasSufficientStock();
    }

    private void validateItemInStock() {
        if (products.findProductByName(name) == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    public void validateHasSufficientStock() {
        if (!products.hasSufficientStock(name, itemQuantity)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public void setOrderItemQuantity(Promotions promotions) {
        Product promotionProduct = products.getPromotionAppliedProductByName(name);
        int promotionAppliedQuantity = promotionProduct.getQuantity();
        int paidQuantity = promotions.getPromotionByName(promotionProduct.getPromotion()).getPaidQuantity();
        int freeQuantity = promotions.getPromotionByName(promotionProduct.getPromotion()).getFreeQuantity();

        if (promotionAppliedQuantity > 0) {
            handleNoDiscountQuantity(promotionAppliedQuantity, paidQuantity, freeQuantity);
        }
        handleFreeProductOffer(promotionAppliedQuantity, paidQuantity, freeQuantity);
    }

    public boolean isPromotionEligibleItem(Promotions promotions) {
        Product product = products.getPromotionAppliedProductByName(name);
        int paidQuantity = promotions.getDiscountQuantityIfApplicable(product.getPromotion());
        int promotionEligibleQuantity = (promotions.getPromotionByName(product.getPromotion()).getPaidQuantity() +
                promotions.getPromotionByName(product.getPromotion()).getFreeQuantity());
        if (paidQuantity > 0) {
            if (product.getQuantity() > promotionEligibleQuantity) {
                int applicableQuantity = Math.min(itemQuantity, product.getQuantity());
                addFreeItemQuantity(applicableQuantity /
                        (promotions.getPromotionByName(product.getPromotion()).getPaidQuantity() +
                                promotions.getPromotionByName(product.getPromotion()).getFreeQuantity()));
            }
            return true;
        }
        return false;
    }

    private void handleNoDiscountQuantity(int promotionAppliedQuantity, int paidQuantity, int freeQuantity) {
        if (itemQuantity >= promotionAppliedQuantity) {
            int noDiscountQuantity = itemQuantity - promotionAppliedQuantity +
                    promotionAppliedQuantity % (paidQuantity + freeQuantity);
            if (!inputView.requestPurchaseWithoutPromotion(name, noDiscountQuantity)) {
                addOrderItemQuantity(-noDiscountQuantity);
            }
        }
    }

    private void handleFreeProductOffer(int promotionAppliedQuantity, int paidQuantity, int freeQuantity) {
        if (itemQuantity <= promotionAppliedQuantity &&
                itemQuantity % (paidQuantity + freeQuantity) == paidQuantity) {
            if (promotionAppliedQuantity - itemQuantity >= freeQuantity &&
                    inputView.requestAddFreeProduct(name)) {
                addOrderItemQuantity(freeQuantity);
                addFreeItemQuantity(freeQuantity);
            }
        }
    }

    public double getItemPrice() {
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

    public double getMembershipEligibleAmount(Promotions promotions) {
        Product product = products.getPromotionAppliedProductByName(name);
        if (product == null) {
            return getItemPrice() * itemQuantity;
        }
        Promotion promotion =
                promotions.getPromotionByName(product.getPromotion());
        int paidQuantity = promotion.getPaidQuantity();
        int freeQuantity = promotion.getFreeQuantity();
        return getItemPrice() * (itemQuantity - freeItemQuantity * (paidQuantity + freeQuantity));
    }

    public void addFreeItemQuantity(int newFreeQuantity) {
        this.freeItemQuantity += newFreeQuantity;
    }

    public void addOrderItemQuantity(int addedQuantity) {
        this.itemQuantity += addedQuantity;
    }
}
