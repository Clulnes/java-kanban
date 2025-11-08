public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private final Coach coach;
    private final int counter;

    public CounterOfTrainings(Coach coach, int counter) {
        this.coach = coach;
        this.counter = counter;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public int compareTo(CounterOfTrainings c) {
        return Integer.compare(c.getCounter(), this.getCounter());
    }

    @Override
    public String toString() {
        return "У тренера: " + getCoach() + ", Тренировок в неделю: " + getCounter();
    }
}
