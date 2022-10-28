package ru.netology.delivery.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class DataGenerator {
    private DataGenerator() {
    }
    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        List<String> list = Arrays.asList("Кемерово", "Майкоп", "Москва", "Симферополь",
                "Смоленск", "Тамбов", "Санкт-Петербург", "Астрахань", "Кострома",
                "Краснодар", "Самара", "Петропавловск-Камчатский");
        Random random = new Random();
        String city = list.get(random.nextInt(list.size()));
        return city;
    }

    public static String generateName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }

    }


