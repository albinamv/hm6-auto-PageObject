package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("h1.heading_size_xl");
    private SelenideElement transferTo = $("[data-test-id = to] input");
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement error = $("[data-test-id = error-notification]");

    public void checkError() {
        error.shouldBe(visible);
    }

    public TransferPage(String cardNumber) {
        transferTo.shouldHave(value(cardNumber)); // есть ли в поле "куда" нужный номер карты
    }

    public DashboardPage transferFrom(int amount, String cardNumber) {
        clearTheForm();
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(cardNumber);
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage transferFrom(double amount, String cardNumber) {
        clearTheForm();
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(cardNumber);
        transferButton.click();
        return new DashboardPage();
    }

    private void clearTheForm() {
        amountField.sendKeys(Keys.CONTROL + "A");
        amountField.sendKeys(Keys.BACK_SPACE);

        fromField.sendKeys(Keys.CONTROL + "A");
        fromField.sendKeys(Keys.BACK_SPACE);
    }
}
