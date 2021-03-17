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
        // 7) скобки всегда есть
        // 8) внутри повторяющейся подстроки не могут быть исключительно ,erds


        // Поиск и выбор подстроки, заданной шаблоном
        // 1) определяем, исключительно ли цифры внутри квадратных скобок
        // 2) определяем, всегда ли после числа повторений идёт '[' (идём от противного, т.е. проверяем, что нет выражений типа
        // 'числоБуква', 'число]', '[]' или число на конце строки)
        // 3) определяем, идёт ли после латинских букв '['
        // 4) опредлеяем, не пустое ли выражение в квадратных скобках
        // 5 опредлеяем, исключительно ли буквы внутри квадратных скобок
        String regex = "(\\[\\d+])|(\\d+[a-zA-Z]+)|(\\d+]+)|(\\[])|(\\d$+)|([a-zA-Z]+\\[)|(^(\\[[a-zA-Z]+]))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
            return false;
        int balance = 0; // разность открывающих и закрывающих скобок
        int bracketCnt = 0; // количество скобок
        char[] arr = str.toCharArray();
        // определяем все допустимые символы на вход
        for (char c : arr) {
            if ((!Character.isLetter(c)) && (!Character.isDigit(c)) && (c != '[') && (c != ']')) {
                return false;
            }
            if (c == '[') {
                balance++;
                bracketCnt++;
            }
            if (c == ']') {
                balance--;
                bracketCnt++;
            }
        }
        // определяем равенство количества открывающих и закрывающих скобок, а также то, имеются ли в принципе скобки
        return balance == 0 && bracketCnt != 0;
    }
}
