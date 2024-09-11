package edu.example.coffeeproject.entity;

public enum Category {
    COFFEE_BEAN_PACKAGE,
    DRINK_PACKAGE

    // 왜 eunm에 클래스에 값이 바로 추가 안되는지 -> 추가해서 바꿀려고 하면 ".SQLException 예외 발생"
    // DB에 해당 필드가 "ENUM" 컬럼으로 설정되어 있다면,
    // ALTER TABLE "테이블명" MODIFY COLUMN "컬럼명" ENUM('ENUM에 추가할 문자열');
    // 문제는 추가를 하기 위해선 원래 있는 모든 값들도 다 써줘야함
}