package org.example;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger prettyNicksLength3 = new AtomicInteger();
    public static AtomicInteger prettyNicksLength4 = new AtomicInteger();
    public static AtomicInteger prettyNicksLength5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }


        new Thread(() -> {
            for (String text : texts) {
                if (isPalindromeNick(text)) {
                    findCounterAndIncrement(text.length());
                }
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (isSameLetterNick(text))
                    findCounterAndIncrement(text.length());
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (isSortedLetterNick(text))
                    findCounterAndIncrement(text.length());
            }
        }).start();


        System.out.println("Красивых слов с длиной 3: " + prettyNicksLength3);
        System.out.println("Красивых слов с длиной 4: " + prettyNicksLength4);
        System.out.println("Красивых слов с длиной 5: " + prettyNicksLength5);
    }

    private static void findCounterAndIncrement(int length) {
        AtomicInteger counter = switch (length) {
            case 3 -> prettyNicksLength3;
            case 4 -> prettyNicksLength4;
            case 5 -> prettyNicksLength5;
            default -> null;
        };

        if (Objects.nonNull(counter)) {
            counter.getAndIncrement();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isSameLetterNick(String nick) {
        char firstChar = nick.charAt(0);
        for (int i = 1; i < nick.length(); i++) {
            if (nick.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSortedLetterNick(String nick) {
        for (int i = 1; i < nick.length(); i++) {
            if (nick.charAt(i) < nick.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPalindromeNick(String word) {
        int left = 0;
        int right = word.length() - 1;

        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}