package view;

import domain.Product;
import domain.Products;

public class OutputView {
    public void printStockStatus(Products updatedProducts) {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");

        for (Product product : updatedProducts.getProducts()) {
            System.out.println(product.getStockStatus());
        }
    }

    public void printReceipt() {

    }
}
