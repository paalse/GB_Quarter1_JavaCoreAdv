package Lesson_3;

import java.util.*;

/**
 * Справочник телефонных номеров
 */
public class PhonesBook {
    private Map<String, List<String>> phonesBook; // Справочник телефонных номеров

    public PhonesBook() {
        this.phonesBook = new HashMap<String, List<String>>();
    }

    /**
     * Добавление нового абонента, либо телефонного номера уже существующему абоненту в телефонный справочник
     *
     * @param alias       - имя абонента
     * @param phoneNumber - телефонный номер
     */
    public void add(String alias, String phoneNumber) {
        List<String> phoneList = this.phonesBook.get(alias);

        if (phoneList == null) {
            phoneList = new ArrayList<>(Arrays.asList(phoneNumber));
        } else {
            if (phoneList.contains(phoneNumber)) {
                System.out.println("Номер " + phoneNumber + " у абонента " + alias + " уже есть в справочнике.");
            } else {
                phoneList.add(phoneNumber);
            }
        }
        this.phonesBook.put(alias, phoneList);
    }

    /**
     * Получение списка телефонов из телефонного справочника по имени абонента
     *
     * @param alias - имя абонента
     * @return - список телефонных номеров
     */
    public List<String> get(String alias) {
        return this.phonesBook.get(alias);
    }
}