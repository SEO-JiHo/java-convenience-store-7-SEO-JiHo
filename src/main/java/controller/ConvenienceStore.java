package controller;

import view.InputView;
import view.OutputView;
import domain.Order;
import domain.ProductAndPromotionLoader;
import domain.Products;
import domain.Promotions;
import domain.PaymentService;

public class ConvenienceStore {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();
    ProductAndPromotionLoader loader = new ProductAndPromotionLoader();

    public void open() {
        Products products = loader.loadProducts("src/main/resources/products.md");
        Promotions promotions = loader.loadPromotions("src/main/resources/promotions.md");
        while (true) {
            Order order = customerPick(products);
            processPayment(products, order, promotions);
            if (!inputView.requestAdditionalPurchase()) {
                break;
            }
            System.out.println();
        }
    }

    private Order customerPick(Products products) {
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
                ? paymentService.membershipDiscountAmount() : 0;
        int totalCount = order.getTotalItemCount();
        outputView.printReceipt(order, products, totalAmount, promotionalDiscount, membershipDiscount, totalCount);
    }
}
