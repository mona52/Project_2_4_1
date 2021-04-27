package ru.netology.page;


import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.*;

public class TransactionPage {


    private SelenideElement heading = $("h1").shouldHave(exactText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id=amount] .input__control");
    private SelenideElement fromCard = $("[data-test-id=from] .input__control");
    private static SelenideElement toCard = $("[data-test-id=to] .input__control");
    private SelenideElement submitTransaction = $("[data-test-id=action-transfer]");
    private SelenideElement cancelTransaction = $("[data-test-id=action-cancel]");


    public SelenideElement getFromCard() {
        return fromCard;
    }

    public TransactionPage() {
        heading.shouldBe(visible);
    }


    public DashboardPage transactionValue(String sum, String from) {
        amount.sendKeys(SHIFT, ARROW_UP, DELETE);
        amount.setValue(sum);
        fromCard.sendKeys(SHIFT, ARROW_UP, DELETE);
        fromCard.setValue(from);
        submitTransaction.click();
        return new DashboardPage();
    }


}
