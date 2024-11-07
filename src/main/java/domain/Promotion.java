package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final Date start_date;
    private final Date end_date;

    public Promotion(String name, int buy, int get,
                     Date start_date, Date end_date) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
