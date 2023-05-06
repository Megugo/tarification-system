package IntegrationTests.api_tests.DataGenerator;

import java.util.Random;

public class DataGenerator {
    public String generatePhoneNumber(int numberLength) {

        long m = (long) Math.pow(10, numberLength - 1);
        return String.valueOf(new Random().nextLong(m,10 * m));
    }
}
