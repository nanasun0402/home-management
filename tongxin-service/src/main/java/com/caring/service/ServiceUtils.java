package com.caring.service;

import com.caring.dao.config.DaoUtils;
import java.util.Random;

public class ServiceUtils extends DaoUtils {

    public static String getRandomNumberByLength(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
