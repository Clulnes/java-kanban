import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.NavigableSet;
import java.util.Set;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());//Проверить, что за понедельник вернулось одно занятие
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());//Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        NavigableSet<TimeOfDay> keys = timetable.getKeysForDay(DayOfWeek.THURSDAY);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());// Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());// Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(new TimeOfDay(13, 0), keys.first());
        Assertions.assertEquals(new TimeOfDay(20, 0), keys.last());
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());// Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());//Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)).isEmpty());//Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testAddMultipleSessionsAtSameTimeToEnsureTheyAreAllStored() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Фитнес", Age.ADULT, 50);
        Group group2 = new Group("Игры с мячиком", Age.CHILD, 30);
        Coach coach1 = new Coach("Жмышенко", "Валерий", "Альбертович");
        Coach coach2 = new Coach("Пышненко", "Максим", "Романович");
        TrainingSession childSessionWithValera = new TrainingSession(group2, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession adultSessionWithValera = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession childSessionWithMaksimka = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(childSessionWithMaksimka);
        timetable.addNewTrainingSession(childSessionWithValera);
        timetable.addNewTrainingSession(adultSessionWithValera);

        Assertions.assertEquals(3, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)).size());
    }

    @Test
    void testGetTrainingSessionsForDayReturnsSessionsInCorrectTimeOrder() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Фитнес", Age.ADULT, 50);
        Group group2 = new Group("Игры с мячиком", Age.CHILD, 30);
        Coach coach1 = new Coach("Жмышенко", "Валерий", "Альбертович");
        Coach coach2 = new Coach("Пышненко", "Максим", "Романович");
        TrainingSession childSessionWithValera = new TrainingSession(group2, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));
        TrainingSession adultSessionWithValera = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession childSessionWithMaksimka = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(childSessionWithValera);
        timetable.addNewTrainingSession(adultSessionWithValera);
        timetable.addNewTrainingSession(childSessionWithMaksimka);

        NavigableSet<TimeOfDay> keys = timetable.getKeysForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(new TimeOfDay(10, 0), keys.first());
        Assertions.assertEquals(new TimeOfDay(20, 0), keys.last());
        Assertions.assertEquals(3, keys.size());
    }

    @Test
    void testAddingDuplicateSessionDoesNotIncreaseSessionCount() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Group group2 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach2 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession2 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
    }

    @Test
    void testCountByCoachesWithMultipleTrainersAndSessions() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("A", "A", "A");
        Coach coach2 = new Coach("B", "B", "B");
        Coach coach3 = new Coach("C", "C", "C");

        Group group1 = new Group("A", Age.ADULT, 1);
        Group group2 = new Group("B", Age.ADULT, 2);
        Group group3 = new Group("C", Age.CHILD, 3);

        TrainingSession trainingSession1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(12,0));
        TrainingSession trainingSession2 = new TrainingSession(group2, coach1, DayOfWeek.TUESDAY, new TimeOfDay(13,0));
        TrainingSession trainingSession3 = new TrainingSession(group3, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(14,0));
        TrainingSession trainingSession4 = new TrainingSession(group1, coach2, DayOfWeek.TUESDAY, new TimeOfDay(15,0));
        TrainingSession trainingSession5 = new TrainingSession(group1, coach2, DayOfWeek.FRIDAY, new TimeOfDay(16,0));
        TrainingSession trainingSession6 = new TrainingSession(group1, coach3, DayOfWeek.SATURDAY, new TimeOfDay(17,0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);
        timetable.addNewTrainingSession(trainingSession4);
        timetable.addNewTrainingSession(trainingSession5);
        timetable.addNewTrainingSession(trainingSession6);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(3, result.size());

        CounterOfTrainings firstCoachInList = result.get(0);

        Assertions.assertEquals(coach1, firstCoachInList.getCoach());
        Assertions.assertEquals(3, firstCoachInList.getCounter());

        CounterOfTrainings secondCoachInList = result.get(1);

        Assertions.assertEquals(coach2, secondCoachInList.getCoach());
        Assertions.assertEquals(2, secondCoachInList.getCounter());

        CounterOfTrainings thirdCoachInList = result.get(2);

        Assertions.assertEquals(coach3, thirdCoachInList.getCoach());
        Assertions.assertEquals(1, thirdCoachInList.getCounter());
    }

    @Test
    void testCountByCoachesWhenOneTrainerHasNoSessions() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("A", "A", "A");
        Coach coach2 = new Coach("B", "B", "B");
        Coach coach3 = new Coach("C", "C", "C");

        Group group1 = new Group("A", Age.ADULT, 1);
        Group group2 = new Group("B", Age.ADULT, 2);
        Group group3 = new Group("C", Age.CHILD, 3);

        TrainingSession trainingSession1 = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(12,0));
        TrainingSession trainingSession2 = new TrainingSession(group2, coach1, DayOfWeek.TUESDAY, new TimeOfDay(13,0));
        TrainingSession trainingSession3 = new TrainingSession(group3, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(14,0));
        TrainingSession trainingSession4 = new TrainingSession(group1, coach1, DayOfWeek.TUESDAY, new TimeOfDay(15,0));
        TrainingSession trainingSession5 = new TrainingSession(group1, coach3, DayOfWeek.FRIDAY, new TimeOfDay(16,0));
        TrainingSession trainingSession6 = new TrainingSession(group1, coach3, DayOfWeek.SATURDAY, new TimeOfDay(17,0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);
        timetable.addNewTrainingSession(trainingSession4);
        timetable.addNewTrainingSession(trainingSession5);
        timetable.addNewTrainingSession(trainingSession6);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertEquals(2, result.size());

        CounterOfTrainings firstCoach = result.get(0);
        CounterOfTrainings secondCoach = result.get(1);

        Assertions.assertEquals(coach1, firstCoach.getCoach());
        Assertions.assertEquals(coach3, secondCoach.getCoach());
    }

    @Test
    void testCountByCoachesWithEmptyTimetableReturnsEmptyList() {
        Timetable timetable = new Timetable();

        Assertions.assertEquals(0, timetable.getCountByCoaches().size());
    }

}

