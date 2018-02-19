package com.globallogic.store.dto.order;

public class CardNumberDto {

    private String cardNumber;

    public CardNumberDto() {
    }

    public CardNumberDto(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
