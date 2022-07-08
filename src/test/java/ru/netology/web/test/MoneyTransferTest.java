package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MoneyTransferTest {
    DashboardPage dashboardPage;

    @BeforeAll
    void login() {
        open("http://localhost:9999");
        var loginPage = new LoginPage(); // создаём новый PO со страницей авторизации
        var authInfo = DataHelper.getAuthInfo(); // получаем данные подготовленного пользователя
        var verificationPage = loginPage.validLogin(authInfo); // логинимся
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo); // получаем зашитый код верификации
        dashboardPage = verificationPage.validVerify(verificationCode); // вводим код и попадаем на страницу с картами
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        val cardInitialBalance = dashboardPage.getCardBalance(0);
        val secondCardInitialBalance = dashboardPage.getCardBalance(1);

        int amount = 200;
        var transferPage = dashboardPage.transferTo(0);
        var successDashboardPage = transferPage.transferFrom(amount, DataHelper.getSecondCardNumber().toString());

        // проверка, что на одной карте баланс увеличился, а на другой - уменьшился
        assertEquals(cardInitialBalance + amount, successDashboardPage.getCardBalance(0));
        assertEquals(secondCardInitialBalance - amount, successDashboardPage.getCardBalance(1));
    }

    @Test
    void shouldNotTransferFromTheSameCard() {
        val cardInitialBalance = dashboardPage.getCardBalance(0);
        val secondCardInitialBalance = dashboardPage.getCardBalance(1);

        int amount = 200;
        var transferPage = dashboardPage.transferTo(0);
        var successDashboardPage = transferPage.transferFrom(amount, DataHelper.getFirstCardNumber().toString());

        // проверка, что баланс обеих карт не изменился
        assertEquals(cardInitialBalance, successDashboardPage.getCardBalance(0));
        assertEquals(secondCardInitialBalance, successDashboardPage.getCardBalance(1));
    }

    @Test
    void shouldNotTransferWhenInsufficientBalance() {
        val secondCardInitialBalance = dashboardPage.getCardBalance(1);

        double amount = secondCardInitialBalance + 500;
        var transferPage = dashboardPage.transferTo(0);
        transferPage.transferFrom(amount, DataHelper.getSecondCardNumber().toString());

        // должно быть видно уведомление об ошибке
        transferPage.getError().shouldBe(visible);
    }

    @Test
    void shouldTransferKopecks() {
        val cardInitialBalance = dashboardPage.getCardBalance(0);
        val secondCardInitialBalance = dashboardPage.getCardBalance(1);

        double amount = 200.99;
        var transferPage = dashboardPage.transferTo(0);
        var successDashboardPage = transferPage.transferFrom(amount, DataHelper.getSecondCardNumber().toString());

        // проверка, что на одной карте баланс увеличился, а на другой - уменьшился
        assertEquals(cardInitialBalance + amount, successDashboardPage.getCardBalance(0));
        assertEquals(secondCardInitialBalance - amount, successDashboardPage.getCardBalance(1));
    }
}

