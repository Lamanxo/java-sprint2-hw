package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static tasks.Status.*;

public class Epic extends Task{

    private ArrayList<Subtask> subTaskList = new ArrayList<>();

    public Epic(String name, String description) {

        super(name, description);

    }

    public Epic(int id, String name, String description, Status status) {

        super(id, name, description, status);
    }

    public void setStatus() {
        long statusDone = 0;
        long statusNew = 0;
        for (Subtask subTask : subTaskList) {
            if (subTask.getStatus().equals(IN_PROGRESS)) {
                setStatus(IN_PROGRESS);
            }
            if (subTask.getStatus().equals(DONE)) {
                statusDone++;
            }
            if (subTask.getStatus().equals(NEW)) {
                statusNew++;
            }
        }
        if (statusDone > 0 && statusDone < subTaskList.size()) {
            setStatus(IN_PROGRESS);
        } else if (statusDone == subTaskList.size() && !subTaskList.isEmpty()) {
            setStatus(DONE);
        } else if (subTaskList.isEmpty() || statusNew == subTaskList.size()) {
            setStatus(NEW);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskList, epic.subTaskList);
    }

    public ArrayList<Subtask> getEpicSublist() {

        return subTaskList;
    }

    public void setEpicSublist(ArrayList<Subtask> subTaskList) {

        this.subTaskList = subTaskList;
    }

    public void addSubtaskInList(Subtask subtask) {

        subTaskList.add(subtask);
    }

    @Override
    public LocalDateTime getStartTime() {
        if (subTaskList.isEmpty()) {
            return null;
        }
        LocalDateTime timeStart = LocalDateTime.MAX;
        for (Subtask subtask : subTaskList) {
            if (subtask.getStartTime() == null) {
                continue;
            }
            if (timeStart.isAfter(subtask.getStartTime()) && subtask.getStartTime() != null ) {
                timeStart = subtask.getStartTime();
            }
        }
        return timeStart;
    }

    @Override
    public LocalDateTime getEndTime() {
        if (subTaskList.isEmpty()) {
            return null;
        }
        LocalDateTime doneTime = LocalDateTime.MIN;
        for (Subtask subtask : subTaskList) {
            if (subtask.getEndTime() == null) {
                continue;
            }
            if (doneTime.isBefore(subtask.getEndTime()) && subtask.getEndTime() != null) {
                doneTime = subtask.getEndTime();
            }
        }
        return doneTime;
    }

    @Override
    public Duration getDuration() {
        if (subTaskList.isEmpty()) {
            return null;
        }
        Duration duration = Duration.ZERO;
        for (Subtask subtask : subTaskList) {
            if (subtask.getDuration() != null) {
                duration = duration.plus(subtask.getDuration());
            }
        }
        return duration;
    }


    @Override
    public String toString() {
        return super.getTaskId() + "," + TaskType.EPIC + "," + super.getName() + "," + super.getStatus() +
                "," + super.getDescription() + "," + getStartTime() + "," +
                getDuration() + "," + getEndTime();
    }

}
