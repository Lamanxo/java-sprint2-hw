package tasks;

import java.util.Objects;

public class SubTask extends Task {
    private long epicNumber;

    public SubTask(long id, String name, String description, Status status, int epicNumber) {
        super(id, name, description, status);
        this.epicNumber = epicNumber;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicNumber=" + epicNumber +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return epicNumber == subTask.epicNumber;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), epicNumber);
    }

    public void setEpicNumber(long epicNumber) {
        this.epicNumber = epicNumber;
    }

    public long getEpicNumber() {

        return epicNumber;
    }
}