package domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    @Test
    void discountApplicableQuantity() {
    }

    @Test
    void discountApplicableDate() {
        Promotion promotion = new Promotion("1+1", 1, 1,
                LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-30"));

        int i = promotion.discountApplicableQuantity("1+1");
        System.out.println(i);
    }

    @Test
    void getBuy() {
    }
}
