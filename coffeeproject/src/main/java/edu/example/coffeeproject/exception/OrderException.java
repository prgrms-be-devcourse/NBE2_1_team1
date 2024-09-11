package edu.example.coffeeproject.exception;

public enum OrderException {
    NOT_FOUND("Order NOT FOUND", 404),
    ORDER_ITEM_NOT_FOUND("Order ITEM NOT FOUND", 404),
    NOT_REGISTERED("Order NOT Registered", 400),
    NOT_MODIFIED("Order NOT Modified", 400),
    NOT_REMOVED("Order NOT Removed", 400),
    NOT_FETCHED("Order NOT Fetched", 400),
    REGISTER_ERR("No Authenticated user", 403),
    FAIL_MODIFY("OrderItem Modify Fail", 400);

    private OrderTaskException orderTaskException;

    OrderException(String message, int code) {
        orderTaskException = new OrderTaskException(message, code);
    }

    public OrderTaskException get() {
        return orderTaskException;
    }
}
