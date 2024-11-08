package domain;

import java.time.LocalDateTime;

import camp.nextstep.edu.missionutils.DateTimes;

public class Promotion {
    private final String name;
    private final int paidQuantity;
    private final int freeQuantity;
    private final LocalDateTime start_date;
    private final LocalDateTime end_date;
    private LocalDateTime today = DateTimes.now();

    public Promotion(String name, int paidQunatity, int freeQunatity,
                     LocalDateTime start_date, LocalDateTime end_date) {
        this.name = name;
        this.paidQuantity = paidQunatity;
        this.freeQuantity = freeQunatity;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int discountApplicableQuantity(String promotionName) {
        if (promotionName.equals(name)
                && (DateTimes.now().isEqual(start_date) || today.isAfter(start_date))
                && (today.isEqual(end_date) || today.isBefore(end_date))) {
            return paidQuantity;
        }

        return 0;
    }

    public int getBuyQuantity() {
        return paidQuantity;
    }
}
