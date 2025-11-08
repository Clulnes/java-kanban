import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay>{
    private final int hours;
    private final int minutes;

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        TimeOfDay timeOfDay = (TimeOfDay) o;
        return hours == timeOfDay.hours && minutes == timeOfDay.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    @Override
    public int compareTo(TimeOfDay time) {
        if (this.getHours() != time.getHours()) {
            return Integer.compare(this.getHours(), time.getHours());
        }
        return Integer.compare(this.getMinutes(), time.getMinutes());
    }

    @Override
    public String toString() {
        String hourString = String.valueOf(getHours());
        String minuteString = String.valueOf(getMinutes());

        if (getMinutes() < 10) {
            minuteString = "0" + minuteString;
        }

        return hourString + ":" + minuteString;
    }
}
