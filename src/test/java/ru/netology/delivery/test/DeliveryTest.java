package ru.netology.delivery.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


class DeliveryTest {
    String name = DataGenerator.generateName();
    String phone = DataGenerator.generatePhone();
    String city = DataGenerator.generateCity();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(city);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        form.$("[data-test-id=name] input").setValue(name);
        form.$("[data-test-id=phone] input").setValue(phone);
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(".notification_status_ok").shouldBe(visible);
        $("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(3)));
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(7));
        form.$(".button").click();
        $("[data-test-id=replan-notification]").waitUntil(visible, 15000);
        $(withText("Перепланировать")).click();
        $(".notification_status_ok").shouldBe(visible);
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(7)));
    }

    @Test
    void shouldEnterAnIncorrectFirstAndLastName() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(city);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue("Andreeva Anna");
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldEnterAnIncorrectCity() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Гудаута");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldInterAnIncorrectDate() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(city);
        form.$("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(-3));
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date]").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

}
