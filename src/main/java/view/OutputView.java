package view;

import domain.Order;
import domain.OrderItem;
import domain.Product;
import domain.Products;

public class OutputView {
    public void printStockStatus(Products updatedProducts) {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");

        for (Product product : updatedProducts.getProducts()) {
            System.out.println(product.getStockStatus());
        }
        System.out.println();
    }

    public void printReceipt(Order order, Products products,
                             double totalAmount, double discountAmount, double membershipDiscount) {
        printProductPart(order, products);
        printPromotionPart(order);
        printSummeryPart(totalAmount, discountAmount, membershipDiscount);
    }

    private void printProductPart(Order order, Products products) {
        System.out.println("\n===========W 편의점=============");
        for (OrderItem item : order.getOrderItems()) {
            double itemTotalPrice = item.getQuantity() * item.getItemPrice(products);
            System.out.printf("%-10s%s\t%2d\t%,.0f\n",
                    item.getName(), addSpaces(item.getName()), item.getQuantity(), itemTotalPrice);
        }
    }

    private void printPromotionPart(Order order) {
        System.out.println("===========증   정=============");
        for (OrderItem item : order.getOrderItems()) {
            if (item.getFreeQuantity() > 0) {
                System.out.printf("%-10s%s\t%2d\n", item.getName(), addSpaces(item.getName()), item.getFreeQuantity());
            }
        }
    }

    private void printSummeryPart(double totalAmount, double discountAmount, double membershipDiscount) {
        System.out.println("==============================");
        System.out.printf("%-10s%s %,.0f\n", "총구매액", addSpaces("총구매액"), totalAmount);
        System.out.printf("%-10s%s %,.0f\n", "행사할인", addSpaces("행사할인"), -discountAmount);
        System.out.printf("%-10s%s %,.0f\n", "멤버십할인", addSpaces("멤버십할인"), -membershipDiscount);
        System.out.printf("%-10s%s %,.0f\n", "내실돈", addSpaces("내실돈"),
                totalAmount - discountAmount - membershipDiscount);
    }

    private String addSpaces(String itemName) {
        int maxLen = 8;
        int spacesToAdd = maxLen - itemName.length();
        return " ".repeat(spacesToAdd > 0 ? spacesToAdd : 0);
    }
}
