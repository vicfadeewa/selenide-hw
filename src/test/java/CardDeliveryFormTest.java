import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryFormTest {
    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:9999");
    }
    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        $("[data-test-id='city'] input").setValue("Иркутск");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE).setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Сидоров-Петров Игнат");
        $("[data-test-id='phone'] input").setValue("+79991234567");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}
