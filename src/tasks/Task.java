package tasks;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private long id;
    private Status status;


    public Task(long id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {

        return name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {

        return description;
    }

    public long getId() {

        return id;
    }

    public Status getStatus() {

        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name)
                && Objects.equals(description, task.description)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (name != null) {
            hash = hash + name.hashCode();
        }
        hash = hash * 31;
        if (description != null) {
            hash = hash + Objects.hash(name, description, id, status);
        }

        return hash;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
