package domain;

import java.util.List;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public int getDiscountQuantityIfApplicable(String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.discountApplicableQuantity(promotionName) > 0) {
                return promotion.discountApplicableQuantity(promotionName);
            }
        }
        return 0;
    }

    public Promotion getPromotionByName(String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(promotionName)) {
                return promotion;
            }
        }
        return null;
    }
}
