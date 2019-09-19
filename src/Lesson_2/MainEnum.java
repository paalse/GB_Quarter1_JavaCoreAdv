/*
Требуется реализовать enum DayOfWeek, который будет представлять дни недели.
С его помощью необходимо решить задачу определения кол-ва рабочих часов до конца недели по заднному текущему дню.

        Возвращает кол-во оставшихся рабочих часов до конца
        недели по заданному текущему дню. Считается, что
        текущий день ещё не начался, и рабочие часы за него
        должны учитываться.
*/

package Lesson_2;

public class MainEnum {

    enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public static String getWorkingHours(DayOfWeek day) {
        if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
            return day + " - часов до конца рабочей недели осталось " + String.valueOf(40 - 8 * day.ordinal());
        } else {
            return day + " - выходной";
        }
    }

    public static void main(final String[] args) {
        System.out.println(getWorkingHours(DayOfWeek.TUESDAY));
    }
}
