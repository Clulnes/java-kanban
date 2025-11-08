import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Timetable timetable = new Timetable();

        while (true) {
            printMenu();
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 1) {
                System.out.println("Введите по порядку параметры для занятия:");
                System.out.println("1. День недели(на английском языке):");

                String dayInput = scanner.nextLine().toUpperCase();
                DayOfWeek dayOfWeek = null;

                for (DayOfWeek days : DayOfWeek.values()) {
                    if (days.name().equals(dayInput)) {
                        dayOfWeek = days;
                        break;
                    }
                }

                if (dayOfWeek == null) {
                    System.out.println("Ошибка! " + dayInput + " не является днем недели. Попробуйте снова.");
                    continue;
                }

                System.out.println("2. Время занятия в часах:");

                int hour = scanner.nextInt();
                scanner.nextLine();

                System.out.println("3. Время занятия в минутах:");

                int minute = scanner.nextInt();
                scanner.nextLine();

                System.out.println("4. Название группы занятия:");

                String title = scanner.nextLine();

                System.out.println("5. Возраст занимающихся(Возможные варианты: ADULT, CHILD).");

                String ageInput = scanner.nextLine().toUpperCase();
                Age age = null;

                for (Age ages : Age.values()) {
                    if (ages.name().equals(ageInput)) {
                        age = ages;
                        break;
                    }
                }

                if (age == null) {
                    System.out.println("Ошибка! " + ageInput + " не является возрастной категорией. Попробуйте снова.");
                    continue;
                }

                System.out.println("6. Продолжительность группы:");

                int duration = scanner.nextInt();
                scanner.nextLine();

                System.out.println("7. Фамилию тренера:");

                String surname = scanner.nextLine();

                System.out.println("8. Имя тренера тренера:");

                String name = scanner.nextLine();

                System.out.println("9. Отчество тренера:");

                String middleName = scanner.nextLine();

                timetable.addNewTrainingSession(new TrainingSession(new Group(title, age, duration),
                        new Coach(surname, name, middleName), dayOfWeek, new TimeOfDay(hour, minute)));

                System.out.println("Занятие успешно добавлено!");
            } else if (input == 2) {
                System.out.println("За какой день недели вы хотите получить расписание?");
                String dayInput = scanner.nextLine().toUpperCase();
                DayOfWeek dayOfWeek = null;

                for (DayOfWeek days : DayOfWeek.values()) {
                    if (days.name().equals(dayInput)) {
                        dayOfWeek = days;
                        break;
                    }
                }

                if (dayOfWeek == null) {
                    System.out.println("Ошибка! " + dayInput + " не является днем недели. Попробуйте снова.");
                    continue;
                }

                System.out.println(timetable.getTrainingSessionsForDay(dayOfWeek));
            } else if (input == 3) {
                System.out.println("За какой день недели вы хотите получить расписание?");
                String dayInput = scanner.nextLine().toUpperCase();
                DayOfWeek dayOfWeek = null;

                for (DayOfWeek days : DayOfWeek.values()) {
                    if (days.name().equals(dayInput)) {
                        dayOfWeek = days;
                        break;
                    }
                }

                if (dayOfWeek == null) {
                    System.out.println("Ошибка! " + dayInput + " не является днем недели. Попробуйте снова.");
                    continue;
                }

                System.out.println("За какое время дня вы хотите увидеть расписание? Введите последовательно.");
                System.out.println("1. Время в часах.");
                System.out.println("2. Время в минутах.");

                int hour = scanner.nextInt();
                scanner.nextLine();
                int minute = scanner.nextInt();
                scanner.nextLine();

                System.out.println(timetable.getTrainingSessionsForDayAndTime(dayOfWeek, new TimeOfDay(hour, minute)));
            } else if (input == 4) {
                System.out.println("Список тренеров по убыванию количества проводимых ими занятий:");
                List<CounterOfTrainings> listOfCoaches = timetable.getCountByCoaches();

                if (listOfCoaches.isEmpty()) {
                    System.out.println("Расписание пока что пустое.");
                } else {
                    for (CounterOfTrainings coachLoad : listOfCoaches) {
                        System.out.println(coachLoad);
                    }
                }
            } else if (input == 5) {
                System.out.println("Выход.");
                break;
            } else {
                System.out.println("Введенная команда: " + input + " не существует, введите число от 1 до 4.");
            }
        }

    }

    public static void printMenu() {
        System.out.println("Вас приветствует программа Gym Master! Что вы хотите сделать?");
        System.out.println("1. Добавить занятие в расписание.");
        System.out.println("2. Вывести список занятий в выбранный день.");
        System.out.println("3. Вывести список занятий в выбранный день и время.");
        System.out.println("4. Вывести список тренеров и количества их занятий.");
        System.out.println("5. Выход.");
    }
}
