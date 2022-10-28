package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
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
    void ShouldSuccessfulPlanAndReplanMeeting() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Запланировать')]").click();
        $x("//*[contains(text(),'Успешно!')]").should(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(3)));
        $x("//input[@type='tel']").doubleClick().sendKeys(DataGenerator.generateDate(7));
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id=replan-notification]").should(visible, Duration.ofSeconds(15));
        $x("//*[contains(text(),'Перепланировать')]").click();
        $("[data-test-id=success-notification] .notification__content").should(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(7)));

    }
    @Test
    void shouldEnteringTheCityInLatin() {
        $x("//input[@placeholder='Город']").setValue("Гудаута");
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id='city'] .input__sub").should(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldEnteringTheLastNameAndFirstNameInLatin() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue("Andreeva Anna");
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id='name'] .input__sub").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldLessThanThreeFromTheCurrentDate() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(1));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id='date'] .input__sub").should(exactText("Заказ на выбранную дату невозможен"));
    }


    @Test
    void shouldCheckboxValidation() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id=agreement] .checkbox__text").should(exactText("Я соглашаюсь с условиями обработки и использования  моих персональных данных"));
    }

    @Test
    void shouldFirstAndLastNameFieldsAreEmpty() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue(phone);
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id='name'] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldPhoneNumberIsNotFilled() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue("");
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id='phone'] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }
}