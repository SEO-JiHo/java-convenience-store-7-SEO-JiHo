package domain;

import java.util.List;

public class Promotions {
    private final List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public int getDiscountQuantityIfApplicable(String name) {
        for (Promotion promotion : promotions) {
            return promotion.discountApplicableQuantity(name);
        }

        return 0;
    }
}
