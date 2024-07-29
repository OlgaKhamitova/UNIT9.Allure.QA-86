import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import utils.RegistrationByCardInfo;
import utils.RegistrationDataGenerator;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestReplan {
    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void testCardDelivery() {
        int initialDaysToWait = 10;
        String replanDate = RegistrationDataGenerator.generateRegistrationDate(initialDaysToWait + 1);
        RegistrationByCardInfo generatedData = RegistrationDataGenerator.generateByCard();

        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue(generatedData.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(RegistrationDataGenerator.generateRegistrationDate(initialDaysToWait));
        $("[data-test-id=name] input").setValue(generatedData.getName() + " " + generatedData.getLastName());
        $("[data-test-id=phone] input").setValue(generatedData.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(replanDate);
        $(withText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + replanDate));
    }
}