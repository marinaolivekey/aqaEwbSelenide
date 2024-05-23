import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class TestCardDelivery {
    //./gradlew clean test -Dselenide.headless=true

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    public void shouldTestHappyPath() {
        // java -jar app-card-delivery.jar
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Пе");
        $$(".menu-item__control").findBy(Condition.text("Пермь")).click();
        String planningDate = generateDate(7,"dd.MM.yyyy");
        // $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").click();
        if (!generateDate(3,"MM").equals(generateDate(7,"MM"))){
            $(".calendar__arrow_direction_right").click();
        }
        $$("[data-day]").findBy(Condition.text(generateDate(7,"d"))).click();
        $("[data-test-id='name'] input").setValue("Перов-Водкин Коля");
        $("[data-test-id='phone'] input").setValue("+79001112233");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на "+ planningDate));
    }
}

/*
@Test
    public void shouldTestHappyPath() {
        // java -jar app-card-delivery.jar
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Пермь");
        String planningDate = generateDate(4,"dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Перов-Водкин Коля");
        $("[data-test-id='phone'] input").setValue("+79001112233");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на "+ planningDate));
 */
