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
        String condition = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}";

        if (password.matches(condition)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите пароль: ");
        String password = in.next();
        System.out.println(checkPassword(password));
        in.close();
    }
}