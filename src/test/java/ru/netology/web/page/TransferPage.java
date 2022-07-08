package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("h1.heading_size_xl");
    private SelenideElement transferTo = $("[data-test-id = to] input");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement error = $("[data-test-id = error-notification]");

    public SelenideElement getError() {
        return error;
    }

    public TransferPage(String cardNumber) {
        transferTo.shouldHave(value(cardNumber)); // есть ли в поле "куда" нужный номер карты
    }

    public DashboardPage transferFrom(int amount, String cardNumber) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(cardNumber);
        transferButton.click();
        return new DashboardPage();
    }
}
