package ru.netology.delivery.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
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
    @DisplayName("Should successful plan and replan meeting")
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
    @DisplayName("Should entering last name with hyphen")
    void shouldEnteringLastNameWithHyphen() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue("Андреева-Хашиг Анна");
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
    @DisplayName("Should name must be entered with hyphen")
    void shouldNameMustBeEnteredWithHyphen() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue("Андреева Анна-Стефания");
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
    @DisplayName("Should entering the city in latin")
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
    @DisplayName("Should entering the last name and first name in latin")
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
    @DisplayName("Should less than three from the current date")
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
    @DisplayName("shouldCheckboxValidation")
    void shouldCheckboxValidation() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id=agreement] .checkbox__text").should(exactText("Я соглашаюсь с условиями обработки и использования  моих персональных данных"));
    }

    @Test
    @DisplayName("Should first and last name fields are empty")
    void shouldFirstAndLastNameFieldsAreEmpty() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue(phone);
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id='name'] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Should phone number is not filled")
    void shouldPhoneNumberIsNotFilled() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys(DataGenerator.generateDate(3));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue("");
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id='phone'] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Should inserting zeros in date")
    void shouldInsertingZerosInDate() {
        $x("//input[@placeholder='Город']").setValue(city);
        $("[data-test-id=date] input").doubleClick().sendKeys("00.00.0000");
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $x("//*[contains(text(),'Запланировать')]").click();
        $("[data-test-id='date'] .input__sub").should(exactText("Неверно введена дата"));
    }
}