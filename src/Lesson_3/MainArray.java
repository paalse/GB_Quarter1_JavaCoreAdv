/*
1. Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся).
        Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем). Посчитать, сколько раз встречается каждое слово.
*/
package Lesson_3;

import java.util.*;

public class MainArray {
    public static void main(String[] args) {
        String[] words = {"дом", "машина", "банан", "машина", "баян", "монитор", "стол", "дом", "мир", "дом", "машина"};

        Map<String, Integer> wordsCount = new HashMap();
        Integer cnt = null;

        for (String word : words) {
            cnt = wordsCount.get(word);
            if(cnt == null) wordsCount.put(word, 1);
            else wordsCount.put(word, cnt+1);
        }
        System.out.println(wordsCount);
    }
}