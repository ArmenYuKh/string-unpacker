package com.digital_design;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Handler {
    private StringBuilder multiplyValues(String str) {
        String[] strings = str.split("[\\[\\]]");
        // определяем число повторений
        int count = Integer.parseInt(strings[0]);
        // определяем повторяемую величину
        String multipliedValue = strings[1];
        // распаковываем строку
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(multipliedValue);
        }
        return result;
    }

    String transform(String str) {
        // определяем шаблон, по которому будем находить строку вида число[строка]
        String regex = "\\d+\\[[a-zA-Z]+]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        // найденные по шаблону строки распаковываем, а затем
        // заменяем в исходной строке все вхождения строки вида число[строка] на распакованные
        while (matcher.find()) {
            String subString = matcher.group();
            StringBuilder replacement = multiplyValues(subString);
            str = str.replace(subString, replacement);
            matcher = pattern.matcher(str);
        }
        return str;
    }

    // Метод 1 проверки валидации
    boolean isValid(String str) {
        // Критерии для определения валидности
        // 1) после числа повторений всегда идет '['
        // 2) количество '[' равно количеству ']'
        // 3) после буквы не может быть '['
        // 4) не может быть ничего кроме цифр, латинских букв и скобок
        // 5) скобки не могут быть пустые
        // 6) внутри повторяющейся подстроки не могут быть исключительно цифры
        // 7) скобки всегда есть
        // 8) не могут быть скобки, перед которыми нет числа и внутри которых исключительно буквы


        // Поиск и выбор подстроки, заданной шаблоном
        // 1) определяем, исключительно ли цифры внутри квадратных скобок
        // 2) определяем, всегда ли после числа повторений идёт '[' (идём от противного, т.е. проверяем, что нет выражений типа
        // 'числоБуква', 'число]' или число в конце строки)
        // 3) определяем, идёт ли после латинских букв '['
        // 4) опредлеяем, не пустое ли выражение в квадратных скобках
        // 5) опредлеяем, исключительно ли буквы внутри квадратных скобок, перед которыми нет числа
        String regex = "(\\[\\d+])" +
                "|(\\d+[a-zA-Z]+)|(\\d+]+)|(\\d$+)" +
                "|([a-zA-Z]+\\[)" +
                "|(\\[])" +
                "|(^(\\[[a-zA-Z]+]))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
            return false;
        int balance = 0; // разность открывающих и закрывающих скобок
        boolean hasBracket = false; // обнаружены ли скобки
        char[] arr = str.toCharArray();
        // определяем все допустимые символы на вход
        for (char c : arr) {
            if ((!Character.isLetter(c)) && (!Character.isDigit(c)) && (c != '[') && (c != ']')) {
                return false;
            }
            if (c == '[') {
                balance++;
                hasBracket = true;
            }
            if (c == ']') {
                balance--;
            }
        }
        // определяем равенство количества открывающих и закрывающих скобок, а также то, имеются ли в принципе скобки
        return balance == 0 && hasBracket;
    }

    // Метод 2 проверки валидации
    boolean isValid2(String str) {
        char[] arr = str.toCharArray();
        List<Character> chars = new LinkedList<>();
        for (int i = 0; i < str.length(); i++) {
            chars.add(arr[i]);
        }
        char tmpFirst = 0;
        char tmpLast = 0;
        ListIterator<Character> characterListIterator2 = chars.listIterator(1);
        for (int i = 1; i < chars.size(); i++) {
            if (characterListIterator2.hasNext()) {
                char previousChar = characterListIterator2.previous();
                characterListIterator2.next();
                char nextChar = characterListIterator2.next();
                // определяем, всегда ли после цифры идёт либо цифра, либо '['
                if (Character.isDigit(previousChar) && (!Character.isDigit(nextChar) && nextChar != '[')) {
                    return false;
                }
                // после буквы может быть или буква, или цифра, или ']'
                if (Character.isLetter(previousChar)
                        && (!Character.isLetter(nextChar) && !Character.isDigit(nextChar) && nextChar != ']')) {
                    return false;
                }
                // не может быть в конце строки цифры
                if (i == (chars.size() - 1) && Character.isDigit(nextChar)) {
                    return false;
                }

                // не может быть пустых скобок
                if (previousChar == '[' && nextChar == ']')
                    return false;

                // не могут быть скобки, перед которыми нет числа и внутри которых исключительно буквы
                if (i == 1)
                    tmpFirst = previousChar;
                if (i == chars.size() - 1)
                    tmpLast = nextChar;
                if (tmpFirst == '[' && tmpLast == ']')
                    return false;
            }
        }

        int balance1 = 0; // разность открывающих и закрывающих скобок
        boolean hasBracket1 = false; // обнаружены ли скобки
        char[] arr1 = str.toCharArray();
        // определяем все допустимые символы на вход
        for (char c : arr1) {
            if ((!Character.isLetter(c)) && (!Character.isDigit(c)) && (c != '[') && (c != ']')) {
                return false;
            }
            if (c == '[') {
                balance1++;
                hasBracket1 = true;
            }
            if (c == ']') {
                balance1--;
            }
        }
        // определяем равенство количества открывающих и закрывающих скобок, а также то, имеются ли в принципе скобки
        return balance1 == 0 && hasBracket1;
    }
}
