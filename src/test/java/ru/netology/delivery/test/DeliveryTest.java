package ru.netology.delivery.test;

import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void ShouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.Registration.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.Registration.generateDate(daysToAddForSecondMeeting);

    }

    @Test
    @DisplayName("Should less than three from the current date")
    void shouldLessThanThreeFromTheCurrentDate() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 5;
        var firstMeetingDate = DataGenerator.Registration.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 2;
        var secondMeetingDate = DataGenerator.Registration.generateDate(daysToAddForFirstMeeting);
    }
}