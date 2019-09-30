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

public class Main {

    static final int size = 10000000;
    static final int h = size / 2;

    /**
     * Метод обработки массива без потоков
     */
    public static void methodNoThread() {
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("No thread method: " + (System.currentTimeMillis() - a));
    }

    /**
     * Метод обработки массива с помощь потоков
     */
    public static void methodThread() {
        float[] arr = new float[size];
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();

        //Разбиение массива
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        // Поток1
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        // Поток2
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        // Запуск потоков
        t1.start();
        t2.start();

        // Ожидание выполнения обоих потоков
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Склейка массива
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println("Thread method: " + (System.currentTimeMillis() - a));
    }


    public static void main(String[] args) {
        methodNoThread();
        methodThread();
    }
}