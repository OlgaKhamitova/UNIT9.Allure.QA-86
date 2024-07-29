package utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RegistrationDataGenerator {
    public static String generateRegistrationDate(int daysToAdd) {
        LocalDate date = LocalDate.now().plusDays(daysToAdd);
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static RegistrationByCardInfo generateByCard() {
        Faker faker = new Faker(new Locale("ru"));
        return new RegistrationByCardInfo(
                faker.address().city(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber()
        );
    }
}
