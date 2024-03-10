package ru.netology;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    static final int FIRST_AMOUNT = 1;
    static final int ROUTE_AMOUNT = 1000;

    public static void main(String[] args) {
        Runnable runnable = () -> {
            String route = generateRoute("RLRFR", 100);
            int repeatFrequency = 0;
            for (int i = 0; i < route.length(); i++) {
                if (route.charAt(i) == 'R') {
                    repeatFrequency++;
                }
            }
            synchronized (sizeToFreq) {
                if (!sizeToFreq.containsKey(repeatFrequency)) {
                    sizeToFreq.put(repeatFrequency, FIRST_AMOUNT);
                } else {
                    sizeToFreq.replace(repeatFrequency, sizeToFreq.get(repeatFrequency) + 1);
                }
            }
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < ROUTE_AMOUNT; i++) {
            threads.add(new Thread(runnable));
            threads.get(i).start();
        }

        int keyValue = 0;
        int maxValue = 0;
        for (Integer value : sizeToFreq.values()) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key).equals(maxValue)) {
                keyValue = key;
            }
        }
        System.out.printf("Самое частое количество повторений %d (Встречается %d раз)\n", keyValue , maxValue);
        System.out.println("Другие размеры:");
        for (Integer key : sizeToFreq.keySet()) {
            if (!sizeToFreq.get(key).equals(maxValue)) {
                System.out.printf("- %d (%d раз)\n", key, sizeToFreq.get(key));
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}