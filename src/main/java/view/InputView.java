package view;

import java.util.ArrayList;
import java.util.List;

import camp.nextstep.edu.missionutils.Console;
import domain.Order;
import domain.OrderItem;
import domain.Products;
import validation.Validator;

public class InputView {
    public String purchaseOrder() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return inputValue();
    }

    public boolean requestAddFreeProduct(String name, int quantity) {
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n",
                name, quantity);

        return getYesNoInputRecursive();
    }

    public boolean requestPurchaseWithoutPromotion(String name, int quantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n",
                name, quantity);

        return getYesNoInputRecursive();
    }

    public boolean requestApplyMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");

        return getYesNoInputRecursive();
    }

    public Order getOrder(String input, Products products) {
        List<OrderItem> orders = new ArrayList<>();
        while (true) {
            try {
                parseOrderItems(input, orders, products);
                return new Order(orders);
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
                input = purchaseOrder();
            }
        }
    }

    private boolean getYesNoInputRecursive() {
        try {
            return convertYesNoToBoolean(inputValue());
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
            return getYesNoInputRecursive();
        }
    }

    private boolean convertYesNoToBoolean(String input) {
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }
        throw new IllegalArgumentException();
    }

    private void parseOrderItems(String userInput, List<OrderItem> orders, Products products) {
        String[] parts = userInput.split(",");
        for (String part : parts) {
            validateAndAddOrderItem(part, orders, products);
        }
    }

    private void validateAndAddOrderItem(String part, List<OrderItem> orders, Products products) {
        String formattedPart = Validator.validateWrappedWithBracket(part);
        String[] nameAndQuantity = Validator.validateSplittableWithDash(formattedPart);
        orders.add(createOrderItem(nameAndQuantity, products));
    }

    private OrderItem createOrderItem(String[] nameAndQuantity, Products products) {
        String name = nameAndQuantity[0];
        int quantity = Integer.parseInt(nameAndQuantity[1]);

        if (!products.containsProduct(name)) {
            throw new IllegalArgumentException();
        }

        return new OrderItem(name, quantity, products);
    }

    public String inputValue() {
        return Console.readLine();
    }
}
