package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import ru.netology.delivery.user.UserInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class DataGenerator {

    private DataGenerator() {

    }

    public static class Registration {
        private Registration() {

        }

        public static  UserInfo generateUser(String ru) {
            return new UserInfo(generateCity(), generateName(), generatePhone());
        }

        public static String generateDate(int days) {
            String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return date;
        }

        public static String generateCity() {
            List<String> list = Arrays.asList("Кемерово", "Майкоп", "Москва", "Симферополь", "Смоленск", "Астрахань", "Кострома", "Краснодар", "Петропавловск-Камчатский", "Ростов-на-Дону", "Санкт-Петербург");
            Random rand = new Random();
            String city = list.get(rand.nextInt(list.size()));
            return city;
        }

        public static String generateName() {
            Faker faker = new Faker();
            return faker.name().firstName() + "" + faker.name().lastName();
        }

        public static String generatePhone() {
            Faker faker = new Faker();
            return faker.phoneNumber().phoneNumber();
        }
    }
}

