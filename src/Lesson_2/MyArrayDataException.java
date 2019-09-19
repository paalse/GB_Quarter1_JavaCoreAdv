package Lesson_2;

/**
 * Исключение при некорректных данных в массиве
 */
public class MyArrayDataException extends Exception {
    private int number;

    public MyArrayDataException(String string) {
        super("В массиве содержатся не корректные данные: " + string);
        this.number = 101;
    }

    public int getNumber() {
        return number;
    }
}