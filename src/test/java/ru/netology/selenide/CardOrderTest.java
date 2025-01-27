package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {
    private String dateGenerator(int addedDays, String pattern) {
        return LocalDate.now().plusDays(addedDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    void shouldRegisterCardWithDelivery() {

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");

        String planningDate = dateGenerator(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);

        $("[data-test-id='name'] input").setValue("Марфа Васильева");
        $("[data-test-id='phone'] input").setValue("+79119592305");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}
