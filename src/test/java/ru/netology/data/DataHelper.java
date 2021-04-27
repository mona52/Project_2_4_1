package ru.netology.data;

import lombok.Value;
import lombok.val;
import ru.netology.page.DashboardPage;
import ru.netology.page.TransactionPage;

public class DataHelper {

        private DataHelper() {}
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
        @Value
        public static class VerificationCode {
            private String code;
        }
        public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
            return new VerificationCode("12345");
        }

    @Value
    public static class Cards {
        private String cardNumber;
        private String id;
        private int cardBalance;
    }

    public static Cards firstCard() {
        return new Cards(
                "5559 0000 0000 0001",
                "[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']",
                10000);
    }

    public static Cards secondCard() {
        return new Cards(
                "5559 0000 0000 0002",
                "[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']",
                10000);
    }

    public static void replaceBalance(DashboardPage dashboardPage, int firstCardBalance, int secondCardBalance) {

        if ((firstCardBalance == firstCard().getCardBalance()) && (secondCardBalance == secondCard().getCardBalance())) {
            return;
        }

        if (firstCardBalance < firstCard().getCardBalance()) {
            String delta = Integer.toString(firstCard().getCardBalance() - firstCardBalance);
            val transferPage = dashboardPage.transaction(firstCard().getId());
            transferPage.transactionValue(delta, secondCard().getCardNumber().strip());

        }
        if (secondCardBalance < secondCard().getCardBalance()) {
            String delta = Integer.toString(secondCard().getCardBalance() - secondCardBalance);
            val transferPage = dashboardPage.transaction(secondCard().getId());
            transferPage.transactionValue(delta, firstCard().getCardNumber().strip());

        }
    }


}
