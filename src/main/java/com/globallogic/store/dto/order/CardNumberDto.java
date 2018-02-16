package com.globallogic.store.dto.order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CardNumberDto {

    @NotNull
    @Pattern(regexp = "\\\\b(?:\\\\d[ -]*?){13,16}\\\\b")
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
