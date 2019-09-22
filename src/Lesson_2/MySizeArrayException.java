package Lesson_2;

/**
 * Исключение при не правильном размере массива
 */
public class MySizeArrayException extends Exception {
    private int number;

    public MySizeArrayException(String string) {
        super("Не корректрый размер массива: " + string);
        this.number = 100;
    }

    public int getNumber() {
        return number;
    }
}