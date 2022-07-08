package ru.netology.web.data;

import lombok.Value;

// хорошая практика — использовать отдельный класс для генерации тестовых данных
public class DataHelper {
    private DataHelper() {
    }

    // вложенный класс
    // для LoginPage
    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    // вложенный класс
    // для VerificationPage
    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    // вложенный класс
    // данные карт
    @Value
    public static class CardsData {
        private String cardNumber;
    }

    public static CardsData getFirstCardNumber() {
        return new CardsData("5559 0000 0000 0001");
    }

    public static CardsData getSecondCardNumber() {
        return new CardsData("5559 0000 0000 0002");
    }
}
