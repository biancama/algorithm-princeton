import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int numberOfItems;

    private class Node {
        Item item;
        Node next;
        Node prev;

        public Node(Item item) {
            this.item = item;
        }
    }
    // construct an empty deque
    public Deque() {
        numberOfItems = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (numberOfItems == 0);
    }

    // return the number of items on the deque
    public int size() {
        return numberOfItems;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        if (head == null) {
            head = new Node(item);
            tail = head;
        } else {
            Node newNode = new Node(item);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        numberOfItems++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        if (head == null) {
            head = new Node(item);
            tail = head;
        } else {
            Node newNode = new Node(item);
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        numberOfItems++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("remove on an empty deque");
        }
        numberOfItems--;
        Item result = head.item;
        if (head == tail) {
            tail = null;
            head = null;
        } else {
            head.next.prev = null;
            Node currentHead = head;
            head = head.next;
            currentHead.next = null;
        }
        return result;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("remove on an empty deque");
        }
        numberOfItems--;
        Item result = tail.item;
        if (head == tail) {
            tail = null;
            head = null;
        } else {
            tail.prev.next = null;
            Node currentHead = tail;
            tail = tail.prev;
            currentHead.prev = null;
        }
        return result;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

        return new Iterator<>() {
            Node current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("next on an empty deque");
                }
                Item result = current.item;
                current = current.next;
                return result;
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        StdOut.println("round 1");

        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(7);
        deque.addLast(8);
        for (Integer integer : deque) {
            StdOut.println(integer);
        }
        StdOut.println("round 2");
        StdOut.println(String.format("removed: %d ", deque.removeFirst()));

        for (Integer integer : deque) {
            StdOut.println(integer);
        }

        StdOut.println("round 3");
        StdOut.println(String.format("removed: %d ", deque.removeLast()));
        StdOut.println(String.format("removed: %d ", deque.removeFirst()));
        StdOut.println(String.format("removed: %d ", deque.removeLast()));

        for (Integer integer : deque) {
            StdOut.println(integer);
        }

        StdOut.println("round 4 2 inner iterator");
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(7);
        deque.addLast(8);
        for (Integer integer : deque) {
            for (Integer integer2 : deque) {
                StdOut.println(integer2);
            }
            StdOut.println(integer);
        }
    }

}