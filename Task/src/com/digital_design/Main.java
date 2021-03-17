package com.digital_design;
// Написать на Java программу распаковывания строки.
// На вход поступает строка вида число[строка], на выход - строка, содержащая повторяющиеся подстроки.

// Пример:
// Вход: 3[xyz]4[xy]z
// Выход: xyzxyzxyzxyxyxyxyz
//
// Ограничения:
// - одно повторение может содержать другое. Например: 2[3[x]y]  = xxxyxxxy
// - допустимые символы на вход: латинские буквы, числа и скобки []
// - числа означают только число повторений
// - скобки только для обозначения повторяющихся подстрок
// - входная строка всегда валидна.
//
// Дополнительное задание:
// Проверить входную строку на валидность.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Handler handler = new Handler();
        while (true) {
            System.out.println("Введите строку, которую необходимо распаковать, или нажмите ENTER для выхода из программы:");
            String str = in.nextLine();
            if (str.equals("")) {
                System.err.println("Программа прервана пользователем!\n");
                return;
            }
//            if (handler.isValid(str))
            if (handler.isValid2(str))
                System.out.println("Результат распаковки строки: \n" + handler.transform(str) + "\n");
            else
                System.err.println("Входная строка невалидна! Попробуйте снова!\n");
        }
    }
}
