import java.util.*;

public class Timetable {
    private final Map<DayOfWeek, TreeMap<TimeOfDay, Set<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, Set<TrainingSession>> sessionsAll = timetable.get(day);

        Set<TrainingSession> sessionsTime = sessionsAll.get(time);

        if (sessionsTime == null) {
            sessionsTime = new HashSet<>();
            sessionsAll.put(time, sessionsTime);
        }

        sessionsTime.add(trainingSession);
    }

    public Set<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, Set<TrainingSession>> sessionsDay = timetable.get(dayOfWeek);

        if (sessionsDay.isEmpty()) {
            System.out.println("В " + dayOfWeek + " занятий нет!");
            return new TreeSet<>();
        }

        TreeSet<TrainingSession> sessionsAll = new TreeSet<>();
        for (Set<TrainingSession> sessions : sessionsDay.values()) {
            sessionsAll.addAll(sessions);
        }
        return sessionsAll;
    }

    public Set<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Set<TrainingSession> sessionsAtTime = timetable.get(dayOfWeek).get(timeOfDay);

        if (sessionsAtTime == null) {
            System.out.println("В " + dayOfWeek + " в " + timeOfDay + " занятий нет!");
            return new TreeSet<>();
        }
        return sessionsAtTime;
    }

    NavigableSet<TimeOfDay> getKeysForDay(DayOfWeek dayofWeek) {
        return timetable.get(dayofWeek).navigableKeySet();
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> amountOfCoachSession = new HashMap<>();

        for (Map<TimeOfDay, Set<TrainingSession>> sessionsForDay : timetable.values()) {
            for (Set<TrainingSession> sessionsAtTime : sessionsForDay.values()) {
                for (TrainingSession session : sessionsAtTime) {
                    Coach coach = session.getCoach();
                    int currentCount = amountOfCoachSession.getOrDefault(coach, 0);
                    amountOfCoachSession.put(coach, currentCount + 1);
                }
            }
        }

        List<CounterOfTrainings> sorted = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : amountOfCoachSession.entrySet()) {
            sorted.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        Collections.sort(sorted);

        return sorted;
    }
}
