package manager;

import tasks.Node;
import tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Long, Node> viewedTasks = new HashMap<>();
    private Node nodeHead = null;
    private Node nodeTail = null;

    private void linkLast(Task task) {
        if (nodeHead == null) {
            nodeHead = new Node(task);
            nodeTail = nodeHead;
            viewedTasks.put(task.getId(), nodeHead);
        } else {
            Node newNode = new Node(task);
            nodeTail.setNextNode(newNode);
            newNode.setPrevNode(nodeTail);
            nodeTail = newNode;
            viewedTasks.put(task.getId(), nodeTail);
        }
    }

    @Override
    public void add(Task task) {
        if (viewedTasks.containsKey(task.getId())) {
            removeNode(viewedTasks.get(task.getId()));
            linkLast(task);
        } else {
            linkLast(task);
        }
    }

    private ArrayList<Task> getTasks() {
        Node tempHead = nodeHead;
        ArrayList<Task> taskList = new ArrayList<>();
        while (tempHead != null) {
            taskList.add(tempHead.getTask());
            if (tempHead.getNextNode() == null)
                tempHead = null;
            else
                tempHead = tempHead.getNextNode();

        }
        return taskList;
    }

    private void removeNode(Node node) {
        if (nodeHead.equals(node)) {
            if (nodeHead.equals(nodeTail)) {
                nodeHead = null;
                nodeTail = null;
                viewedTasks.remove(node.getTask().getId());
            } else {
                viewedTasks.remove(node.getTask().getId());
                nodeHead = node.getNextNode();
                nodeTail.setPrevNode(null);
            }
        } else if (nodeTail.equals(node)) {
            viewedTasks.remove(node.getTask().getId());
            nodeTail = node.getPrevNode();
            nodeTail.setNextNode(null);
        } else {
            node.getNextNode().setPrevNode(node.getPrevNode());
            node.getPrevNode().setNextNode(node.getNextNode());
            viewedTasks.remove(node.getTask().getId());
        }
    }

    @Override
    public void removeId(long id) {
        if (viewedTasks.containsKey(id)) {
            removeNode(viewedTasks.get(id));
            viewedTasks.remove(id);
        }
    }

    public List<Task> getHistory() {
        return getTasks();
    }


}