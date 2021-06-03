package lesson3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MainApp {

    public static HashMap<String, HashSet<Long>> phones = new HashMap<>();

    public static void main(String[] args) {
        // Первое задание
        String[] words = {
                "Каша", "Макароны", "Овсянка", "Бутерброд", "Молоко", "Огурцы", "Помидоры",
                "Вермишель", "Капуста", "Овсянка", "Кетчуп", "Суп", "Борщ", "Молоко", "Сметана",
                "Блины", "Сковорода", "Кастрюля", "Вилка", "Нож", "Яйца", "Макароны", "Повар"
        };

        Set<String> set = new HashSet<>();
        for(String str : words) {
            set.add(str);
        }
        System.out.println("Список уникальных слов:\n" + set + "\nРазмер списка(количество слов): " + set.size());

        System.out.println();

        // Второе задание
        add("Vadim", 89043330888L);
        add("Vadim", 89043330999L);
        add("Vadim", 89110889181L);
        add("Natalia", 89111677717L);

        get("natalia");
        get("Vadim");
}

    public static void add(String name, Long number) {
        if(!(phones.containsKey(name))) {
            phones.put((name), new HashSet<>());
        }
        phones.get(name).add(number);
    }

    public static void get(String name) {
        if(phones.containsKey(name)) {
            System.out.println(phones.get(name));
        } else {
            System.out.println("Такого имени нет в справочнике");
        }
    }
}