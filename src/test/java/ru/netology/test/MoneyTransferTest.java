package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

class MoneyTransferTest {
    private DashboardPage dashboardPage;

    int transactionValue = 3000;


    @BeforeEach
    void setUp() {

        open("http://localhost:9999");
        val login = new LoginPage();
        dashboardPage = login.validLogin(getAuthInfo()).validVerify(getVerificationCodeFor(getAuthInfo()));
        val balanceFirstCard = dashboardPage.getCardBalance(firstCard().getId());
        val balanceSecondCard = dashboardPage.getCardBalance(secondCard().getId());
        replaceBalance(dashboardPage, balanceFirstCard, balanceSecondCard);

    }


    @Test
    void shouldTransactionFromFirstToSecond() {

        val transferPage = dashboardPage.transaction(secondCard().getId());
        transferPage.transactionValue(Integer.toString(transactionValue), firstCard().getCardNumber().strip());

        val actualFirst = dashboardPage.getCardBalance(firstCard().getId());
        val expectedFirst = firstCard().getCardBalance() - transactionValue;
        assertEquals(expectedFirst, actualFirst);

        val actualSecond = dashboardPage.getCardBalance(secondCard().getId());
        val expectedSecond = secondCard().getCardBalance() + transactionValue;
        assertEquals(expectedSecond, actualSecond);

    }

    @Test
    void shouldTransactionFromSecondToFirst() {

        val transferPage = dashboardPage.transaction(firstCard().getId());
        transferPage.transactionValue(Integer.toString(transactionValue), secondCard().getCardNumber().strip());

        val actualFirst = dashboardPage.getCardBalance(firstCard().getId());
        val expectedFirst = firstCard().getCardBalance() + transactionValue;
        assertEquals(expectedFirst, actualFirst);

        val actualSecond = dashboardPage.getCardBalance(secondCard().getId());
        val expectedSecond = secondCard().getCardBalance() - transactionValue;
        assertEquals(expectedSecond, actualSecond);
    }

    private String overBalance(int cardBalance) {

        int value = cardBalance + 1;
        return Integer.toString(value);

    }

    @Test
    void shouldNotTransactionWithOverdraft() {

        Cards toCard = firstCard();
        Cards fromCard = secondCard();
        val transferPage = dashboardPage.transaction(toCard.getId());
        String overdraft = overBalance(fromCard.getCardBalance());
        transferPage.transactionValue(overdraft, fromCard.getCardNumber().strip());

        val actualToCardBalance = dashboardPage.getCardBalance(toCard.getId());
        val expectedToCardBalance = toCard.getCardBalance();
        assertEquals(expectedToCardBalance, actualToCardBalance);

        val actualFromCardBalance = dashboardPage.getCardBalance(fromCard.getId());
        val expectedFromCardBalance = fromCard.getCardBalance();
        assertEquals(expectedFromCardBalance, actualFromCardBalance);
    }


}





