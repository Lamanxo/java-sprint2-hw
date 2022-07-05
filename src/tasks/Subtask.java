package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private int epicNumber;



    public Subtask(String name, String description, Status status, int epicNumber) {
        super(name, description, status);
        this.epicNumber = epicNumber;
    }

    public Subtask(int id, String name, String description, Status status, int epicNumber) {
        super(id ,name, description, status);
        this.epicNumber = epicNumber;
    }

    public Subtask(String name, String description, Status status, int epicNumber,
                   LocalDateTime startTime, Duration duration) {
        super(name, description, status, startTime, duration);
        this.epicNumber = epicNumber;
    }


    @Override
    public String toString() {
        return super.getTaskId() + "," + TaskType.SUBTASK + "," + super.getName() + "," +
                super.getStatus() + "," + super.getDescription() + "," + "Epic_ID: " + epicNumber + "," +
                super.getStartTime() + "," + super.getDuration() + "," + super.getEndTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subTask = (Subtask) o;
        return epicNumber == subTask.epicNumber;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), epicNumber);
    }

    public void setEpicNumber(int epicNumber) {
        this.epicNumber = epicNumber;
    }

    public int getEpicNumber() {

        return epicNumber;
    }
}
