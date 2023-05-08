package IntegrationTests.api_tests.DataGenerator;

import java.util.Random;

public class DataGenerator {
    public String generatePhoneNumber(int numberLength) {

        long m = (long) Math.pow(10, numberLength - 1);
        return String.valueOf(new Random().nextLong(m,10 * m));
    }

    public String randomString(int length) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
