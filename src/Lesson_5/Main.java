/*
1. Необходимо написать два метода, которые делают следующее:
        1) Создают одномерный длинный массив, например:
static final int size = 10000000;
static final int h = size / 2;
        float[] arr = new float[size];
        2) Заполняют этот массив единицами;
        3) Засекают время выполнения: long a = System.currentTimeMillis();
        4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
        arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        5) Проверяется время окончания метода System.currentTimeMillis();
        6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
        Отличие первого метода от второго:
        Первый просто бежит по массиву и вычисляет значения.
        Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.

        Пример деления одного массива на два:
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Пример обратной склейки:
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        Примечание:
        System.arraycopy() копирует данные из одного массива в другой:
        System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
        По замерам времени:
        Для первого метода надо считать время только на цикл расчета:
        for (int i = 0; i < size; i++) {
        arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
*/
package Lesson_5;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        final int SIZE = 10000000;                  // Размер всего массива
        final int THREADS_COUNT = 10;           // Количество потоков 1, либо четное количество
        final int PART_SIZE = SIZE / THREADS_COUNT; // Размер части массива

        float[] arr_origin = new float[SIZE];
        float[][] arr_part = new float[THREADS_COUNT][PART_SIZE];
        Arrays.fill(arr_origin, 1f);            // Заполнение массива 1-ми

        Thread[] threads = new Thread[THREADS_COUNT];            // Массив с потоками

        long a = System.currentTimeMillis();

        // Разбиение исходного массива  и создание потоков
        for (int i = 0; i < THREADS_COUNT; i++) {
            System.arraycopy(arr_origin, i * PART_SIZE, arr_part[i], 0, PART_SIZE);

            final int u = i; // i не простаскивается во вложенный класс поэтому создали доп переменную u, с final не понятно, работает с ним и без него
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    // считаем массив со сдвигом
                    int n = u * PART_SIZE;
                    for (int j = 0; j < PART_SIZE; j++, n++) {
                        arr_part[u][j] *= (Math.sin(0.2f + n / 5) * Math.cos(0.2f + n / 5) * Math.cos(0.4f + n / 2));
                    }
                }
            });
            threads[i].start(); // Запуск потока
        }

        // Ждем завершения всех потоков
        for (int i = 0; i < THREADS_COUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Объединение частей массивов в исходный
        for (int i = 0; i < THREADS_COUNT; i++) {
            System.arraycopy(arr_part[i], 0, arr_origin, i * PART_SIZE, PART_SIZE);
        }
        System.out.println("Working time: " + (System.currentTimeMillis() - a));
    }
}