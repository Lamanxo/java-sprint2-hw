package tasks;

public class Node {
    private Task task;
    private Node nextNode;
    private Node prevNode;

    public Node(Task task) {
        this.task = task;
        this.nextNode = null;
        this.prevNode = null;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }
}