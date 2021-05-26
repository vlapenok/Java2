public class MainApp {
    public static void main(String[] args) {
        Athlete[] athlete = {
                new Human("Вадим", 100, 10),
                new Robot("Android", 1000, 300),
                new Cat("Барсик",300,50)
        };

        Sport[] sports = {
                new Run("Дорожка", 200),
                new Wall("Стена", 100)
        };

        // Первое решение
        for (int i = 0; i < athlete.length; i++) {
            athlete[i].info();
            for (int j = 0; j < sports.length; j++) {
                if(sports[j] instanceof Run) {
                    if(athlete[i].run1(sports[j])) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    if(athlete[i].jump1(sports[j])) {
                        continue;
                    } else {
                        break;
                    }
                }
            }
            System.out.println();
        }

//         Второе решение
//         Тут каст объекта не в самом методе.
//         Как правильнее? Первый вариант решения или этот?
//        for (int i = 0; i < athlete.length; i++) {
//            athlete[i].run((Run) sports[0]);
//            athlete[i].jump((Wall) sports[1]);
//        }

//        Третье решение
//        Тут наоборот, я передаю спортсмена в метод препятствия.
//        В этом варианте я не знаю как снять с дистанции спортсмена, если он где-то не проходит.
//        for (int i = 0; i < sports.length; i++) {
//            for (int j = 0; j < athlete.length; j++) {
//                sports[i].training(athlete[j]);
//            }
//            System.out.println();
//        }
    }
}