package controller;

import domain.*;
import view.InputView;
import view.OutputView;


public class ConvenienceStore {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();

    public void open() {
        ProductAndPromotionLoader loader = new ProductAndPromotionLoader();
        Products products = loader.loadProducts("src/main/resources/products.md");
        Promotions promotions = loader.loadPromotions("src/main/resources/promotions.md");
        while (true) {
            Order order = customerPicksItems(products);
            processPayment(products, order, promotions);
            if (!inputView.requestAdditionalPurchase()) {
                break;
            }
        }
    }

    private Order customerPicksItems(Products products) {
        outputView.printStockStatus(products);
        String customerOrder = inputView.getCustomerOrder();

        return inputView.getOrder(customerOrder, products);
    }

    private void processPayment(Products products, Order order, Promotions promotions) {
        PaymentService paymentService = new PaymentService(products, order, promotions);
        paymentService.applyPromotion();
        paymentService.adjustStock();

        double totalAmount = paymentService.totalPurchaseAmount();
        double promotionalDiscount = paymentService.promotionalDiscountAmount();
        double membershipDiscount = inputView.requestApplyMembershipDiscount()
                ? paymentService.membershipDiscountAmount(totalAmount, promotionalDiscount) : 0;
        int totalCount = order.getTotalItemCount();
        outputView.printReceipt(order, products, totalAmount, promotionalDiscount, membershipDiscount, totalCount);
    }
}
