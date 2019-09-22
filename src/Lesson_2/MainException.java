/*
1. Напишите метод, на вход которого подается двумерный строковый массив размером 4х4, при подаче массива другого размера необходимо бросить исключение MyArraySizeException.
2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать. Если в каком-то элементе массива
преобразование не удалось (например, в ячейке лежит символ или текст вместо числа), должно быть брошено исключение MyArrayDataException – с детализацией,
в какой именно ячейке лежат неверные данные.
3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException и MyArrayDataException и вывести результат расчета.
*/

package Lesson_2;

public class MainException {

    /**
     * Проверка размера массива и подсуммирование его элементов
     *
     * @param array     - массив с данными
     * @param arraySide - размер стороны массива
     * @return - сумма чисел в массиве
     * @throws MySizeArrayException
     * @throws MyArrayDataException
     */
    public static int checkArray(String[][] array, int arraySide) throws MySizeArrayException, MyArrayDataException {
        int result = 0;

        // Проверка количества строк массива
        if (array.length != arraySide)
            throw new MySizeArrayException("Количество строк массива не равно " + arraySide);

        //Конвертация строк массива в числа и их подсуммирование
        for (int i = 0; i < array.length; i++) {
            if (array[i].length != arraySide) // Проверка количества элементов в строке массива
                throw new MySizeArrayException("В строке " + i + " массива, количество элементов не равно " + arraySide);
            for (int j = 0; j < array[i].length; j++) {
                try {
                    result += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("В ячейке [" + i + "][" + j + "] содержится символ, который не может быть преобразован в число");
                }
            }
        }
        return result;
    }

    
    public static void main(String[] args) {
        int arraySide = 4; // Рзмер стороны массива

        String[][] mass = {{"1", "2", "3", "34"}, {"2", "3", "4", "6"}, {"2", "3", "0", "21"}, {"2", "3", "10", "633"}}; // Правлиный массив
        // String[][] mass = {{"1", "2", "3", "34"}, {"2", "3", "4", "6"}, {"2", "3", "0", "21"}, }; // Не хватает строк
        // String[][] mass = {{"1", "2", "3", "34"}, {"2"}, {"2", "3", "0", "21"}, {"2", "3", "10", "633"}}; // Не хватает элементов в одной из строк
        //String[][] mass = {{"1", "2", "3", "34"}, {"2", "3", "4", "ZZ"}, {"2", "3", "0", "21"}, {"2", "3", "10", "633"}}; // Массив содержит буквы

        try {
            System.out.println("Сумма элементов массива: " + checkArray(mass, arraySide));
        } catch (MySizeArrayException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }
}