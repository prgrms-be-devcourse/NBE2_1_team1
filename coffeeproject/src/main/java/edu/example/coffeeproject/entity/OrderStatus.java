package edu.example.coffeeproject.entity;

public enum OrderStatus {
    ACCEPTED,           // 주문확인
    PAYMENT_CONFIRMED,  // 결제 확인
    READY_FOR_DELIVERY, // 배송 준비
    SHIPPED,    //배송
    SETTLED,    // 구매 확정
    CANCELED    // 구매 취소
}
