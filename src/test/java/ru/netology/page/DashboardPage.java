package ru.netology.page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement header = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement reloadButton = $("[data-test-id='action-reload']");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        header.shouldBe(Condition.visible);
    }

    //1й метод поиска карты - по маскировнному номеру
    public int getCardBalance(String maskedCardNumber) {
        var text = cards.findBy(Condition.text(maskedCardNumber)).getText();
        return extractBalance(text);
    }

    //2й метод поиска карты - по индексу 0 или 1
    public int getCardBalance(int index) {
        var text = cards.get(index).getText();
        return extractBalance(text);
    }

    //3й метод поиска карты - по id из вебтулса
    public TransferMoneyPage selectCardToTransfer(DataHelper.CardInfo cardInfo){
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId())).$("button").click();
        return new TransferMoneyPage();
    }

    public void reloadDashboardPage() {
        reloadButton.click();
        header.shouldBe(Condition.visible);
    }


    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
