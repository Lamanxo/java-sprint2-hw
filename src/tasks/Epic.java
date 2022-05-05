package tasks;

import static tasks.Status.*;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> epicSublist = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> getEpicSublist() {
        return epicSublist;
    }

    public void setEpicSublist(ArrayList<Subtask> epicSublist) {
        this.epicSublist = epicSublist;
    }

    public void addSubtaskInList(Subtask subtask) {
        epicSublist.add(subtask);
    }

    public void setStatus() {
        int countDoneStatus = 0;
        int countNewStatus = 0;
        for (Subtask subtask : epicSublist) {
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
        if (countDoneStatus == epicSublist.size() && !epicSublist.isEmpty()) {
            setStatus(DONE);
        } else if (countDoneStatus > 0 && countDoneStatus < epicSublist.size()) {
            setStatus(IN_PROGRESS);
        } else if (epicSublist.isEmpty() || countNewStatus == epicSublist.size()) {
            setStatus(NEW);
        }
    }

    @Override
    public String toString() {
        return super.getTaskId() + "," + TaskType.EPIC + "," + super.getName() + "," + super.getStatus() +
                "," + super.getDescription() + "," + "Subtasks:" + epicSublist;
    }


}
