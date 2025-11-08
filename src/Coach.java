import java.util.Objects;

public class Coach {
    private final String surname;
    private final String name;
    private final String middleName;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public String toString() {
        return getSurname() + " " + getName() + " " + getMiddleName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return Objects.equals(getSurname(), coach.getSurname()) && Objects.equals(getName(), coach.getName()) && Objects.equals(getMiddleName(), coach.getMiddleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSurname(), getName(), getMiddleName());
    }
}
