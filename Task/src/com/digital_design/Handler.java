package com.digital_design;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;
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
        Pattern p1 = Pattern.compile(regex);
        Matcher m1 = p1.matcher(str);
        // найденные по шаблону строки распаковываем, а затем
        // заменяем в исходной строке все вхождения строки вида число[строка] на распакованные
        while (m1.find()) {
            String replacement = multiplyValues(m1.group());
            str = str.replace(m1.group(), replacement);
            m1 = p1.matcher(str);
        }
        return str;
    }


    boolean isValid(String str) {
        // 1) после цифры всегда идет '['
        // 2) количество '[' равно количеству ']'
        // 3) после буквы не может быть '['
        // 4) не может быть ничего кроме цифр, латинских букв и скобок
        boolean numBracket = false;
        boolean bracketCnt = false; // для определения равенства количества скобок
        boolean letterBrackets = false;
        boolean validCharacters = true;

        boolean valid = false;

        int bracket1Cnt = 0; // количество открывающих скобок
        int bracket2Cnt = 0; // количество закрывающих скобок

        byte[] arr = str.getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arr);

        List<Character> list = new LinkedList<>();
        int tmp;
        while ((tmp = byteArrayInputStream.read()) != -1) {
            if ((char) tmp == '[') bracket1Cnt++;
            if ((char) tmp == ']') bracket2Cnt++;
            list.add((char) tmp);
        }
        for (int i = 0; i < list.size() - 1; i++) {
            if (Character.isDigit(list.get(i)) && list.get(i + 1).equals('[')) {
               numBracket = true;
            }
            if (Character.isLetter(list.get(i)) && list.get(i + 1).equals('[')) {
                letterBrackets = true;
            }
            if (!Character.isLetter(list.get(i)) && !Character.isDigit(list.get(i)) && !list.get(i).equals('[') && !list.get(i).equals(']')) {
                validCharacters = false;
            }
        }
        if (bracket1Cnt == bracket2Cnt) bracketCnt = true;

        if (numBracket && bracketCnt && !letterBrackets && validCharacters) valid = true;
        return valid;
    }
}
