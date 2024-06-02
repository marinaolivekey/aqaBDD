package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferMoneyPage {
    private SelenideElement TransferButton = $("[data-test-id='action-transfer']");
    private SelenideElement amountInput = $("[data-test-id=amount] input");
    private SelenideElement fromInput = $("[data-test-id=from] input");
    private SelenideElement TransferHead = $(byText("Пополнение карты"));
    private SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    public TransferMoneyPage(){
        TransferHead.shouldBe(Condition.visible);
    }

    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return  new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        TransferButton.click();
    }

    public void findErrorMessage (String expectedText) {
        errorMessage.shouldHave(Condition.text(expectedText), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}
