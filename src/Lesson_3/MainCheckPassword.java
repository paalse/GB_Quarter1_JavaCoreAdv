/*Необходимо из консоли считать пароль и проверить валидность,
        результат будет true или false
        Требования:
        1. Пароль должен быть не менее 8ми символов.
        2. В пароле должно быть число
        3. В пароле должна быть хотя бы 1 строчная буква
        4. В пароле должна быть хотя бы 1 заглавная буква
        5. Не должно быть пробелов
*/
package Lesson_3;

import java.util.Scanner;

public class MainCheckPassword {
    public static boolean checkPassword(String password) {
        boolean resDigit = false; // Результат проверки  по наличию цифр
        boolean resLowCase = false; // Результат проверки наличия букв в нижнем регистре
        boolean resHighCase = false; // Результат проверки наличия букв в верхнем регистре

        // Проверка длины и наличия пробелов
        if ((password.length() < 8) || password.contains(" ")) return false;

        // Проверка регистра букв и наличия цифр
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i)) && resLowCase == false) resLowCase = true;
            if (Character.isUpperCase(password.charAt(i)) && resHighCase == false) resHighCase = true;
            if (Character.isDigit(password.charAt(i)) && resDigit == false) resDigit = true;
        }
        return (resDigit && resLowCase && resHighCase);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите пароль: ");
        String password = in.next();
        System.out.println(checkPassword(password));
        in.close();
    }
}