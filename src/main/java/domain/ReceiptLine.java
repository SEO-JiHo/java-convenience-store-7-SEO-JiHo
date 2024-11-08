package domain;

public class ReceiptLine {
    private final String name;
    private final int quantity;
    private final double price;

    public ReceiptLine(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
