package tasks;

import static tasks.Status.*;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subTaskslist = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> getSubTaskslist() {
        return subTaskslist;
    }

    public void setSubTaskslist(ArrayList<Subtask> subTaskslist) {
        this.subTaskslist = subTaskslist;
    }

    public void addSubtaskInList(Subtask subtask) {
        subTaskslist.add(subtask);
    }

    public void setStatus() {
        int countDoneStatus = 0;
        int countNewStatus = 0;
        for (Subtask subtask : subTaskslist) {
            if (subtask.getStatus().equals(IN_PROGRESS)) {
                setStatus(IN_PROGRESS);
            }
            if (subtask.getStatus().equals(DONE)) {
                countDoneStatus++;
            }
            if (subtask.getStatus().equals(NEW)) {
                countNewStatus++;
            }
        }
        if (countDoneStatus == subTaskslist.size() && !subTaskslist.isEmpty()) {
            setStatus(DONE);
        } else if (countDoneStatus > 0 && countDoneStatus < subTaskslist.size()) {
            setStatus(IN_PROGRESS);
        } else if (subTaskslist.isEmpty() || countNewStatus == subTaskslist.size()) {
            setStatus(NEW);
        }
    }

    @Override
    public String toString() {
        return super.getTaskId() + "," + TaskType.EPIC + "," + super.getName() + "," + super.getStatus() +
                "," + super.getDescription() + "," + "Subtasks:" + subTaskslist;
    }


}
