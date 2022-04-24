package org.example.helpers;

import java.util.Random;

public class TestDataGen {

    public static int generateInteger(String value, int length) {

        if (value.contains("random")) {
            char[] chars = "0123456789".toCharArray();
            Random rnd = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++)
                sb.append(chars[rnd.nextInt(chars.length)]);

            value = sb.toString();
        }

        return Integer.parseInt(value);
    }
}
