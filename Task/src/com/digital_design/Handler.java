package com.digital_design;

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
            StringBuilder replacement = multiplyValues(matcher.group());
            str = str.replace(matcher.group(), replacement);
            matcher = pattern.matcher(str);
        }
        return str;
    }


    boolean isValid(String str) {
        // Критерии для определения валидности
        // 1) после числа повторений всегда идет '['
        // 2) количество '[' равно количеству ']'
        // 3) после буквы не может быть '['
        // 4) не может быть ничего кроме цифр, латинских букв и скобок
        // 5) скобки не могут быть пустые
        // 6) внутри повторяющейся подстроки не могут быть исключительно цифры

        boolean numBracket = true; // для определения того, после числа повторений всегда ли идет '['
        boolean bracketCnt = false; // для определения равенства количества скобок
        boolean letterBracket = false; // для определения того, не идёт ли после латинских букв '['
        boolean validCharacters = false; // для опредлеения допустимых символов на вход
        boolean emptyBrackets = false; // для определения того, не пустое ли выражение в квадратных скобках
        boolean onlyDigits = false; // для определения того, что не исключительно цифры внутри квадратных скобок

        boolean valid = false; // для определения валидности строки с учётом всех критериев

        // Поиск и выбор подстроки, заданной шаблоном
        // определяем, исключительно ли цифры внутри квадратных скобок
        String regex1 = "\\[\\d+]";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(str);
        while (matcher1.find()) {
            onlyDigits = true;
        }

        // определяем, всегда ли после числа повторений идёт '[' (идём от противного, т.е. проверяем, что нет выражений типа
        // 'числоБуква', 'число]' или число на конце строки)
        String regex2 = "(\\d+[a-zA-Z]+)|(\\d+]+)|(\\d$+)";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(str);
        while (matcher2.find()) {
            numBracket = false;
        }

        // определяем, идёт ли после латинских букв '['
        String regex3 = "[a-zA-Z]+\\[";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(str);
        while (matcher3.find()) {
            letterBracket = true;
        }

        // опредлеяем, не пустое ли выражение в квадратных скобках
        String regex4= "\\[]";
        Pattern pattern4 = Pattern.compile(regex4);
        Matcher matcher4 = pattern4.matcher(str);
        while (matcher4.find()) {
            emptyBrackets = true;
        }
        int balance = 0; // количество скобок
        int tmp;
        char[] arr = str.toCharArray();
        // определяем все допустимые символы на вход
        for (char c : arr) {
            if (Character.isLetter(c) || Character.isDigit(c) || c == '[' || c == ']') {
                validCharacters = true;
            }
            if (c == '[')
                balance++;
            if (c == ']')
                balance--;
        }

        // определяем равенство количества открывающих и закрывающих скобок
        if (balance == 0)
            bracketCnt = true;

        // определяем валидность строки с учётом всех критериев
        if (numBracket && bracketCnt && !letterBracket && validCharacters && !emptyBrackets && !onlyDigits)
            valid = true;
        return valid;
    }
}
