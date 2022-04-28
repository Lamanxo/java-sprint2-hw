package manager;

import tasks.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class InMemoryHistoryManager<E extends Task> implements HistoryManager {
    private final Map<Integer, Node<E>> nodesMap = new HashMap<>();
    private Node<E> first;
    private Node<E> last;

    @Override
    public void add(Task task) {
        if (task == null)
            return;
        if (nodesMap.containsKey(task.getTaskId())) {
            remove(task.getTaskId());
        }
        nodesMap.put(task.getTaskId(), linkLast((E) task));
    }

    @Override
    public List<Task> getHistory() {
        final List<Task> history = new ArrayList<>();
        Node<E> taskNode = first;
        while (taskNode != null) {
            history.add(taskNode.item);
            taskNode = taskNode.next;
        }
        return history;
    }

    @Override
    public void remove(int id) {
        if (nodesMap.containsKey(id)) {
            removeNode(nodesMap.get(id));
            nodesMap.remove(id);
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }

    private Node<E> linkLast(E element) {
        final Node<E> newLast = last;
        final Node<E> newNode = new Node<>(newLast, element, null);
        last = newNode;
        if (newLast == null)
            first = newNode;
        else
            newLast.setNext(newNode);
        return newNode;
    }

    private void removeNode(Node<E> node) {
        final Node<E> nextElement = node.getNext();
        final Node<E> prevElement = node.getPrev();
        if (prevElement == null) {
            first = nextElement;
        } else {
            prevElement.setNext(nextElement);
            node.setPrev(null);
        }
        if (nextElement == null) {
            last = prevElement;
        } else {
            nextElement.setPrev(prevElement);
            node.setNext(null);
        }
        node.setItem(null);
    }
}