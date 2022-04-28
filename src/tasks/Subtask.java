package tasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return super.getTaskId() + "," + TaskType.SUBTASK + "," + super.getName() + "," +
                super.getStatus() + "," + super.getDescription() + "," + "Epic_ID: " + epicId;
    }


}
