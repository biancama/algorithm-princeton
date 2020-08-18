import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int numberOfItems;
    private Item[] storage;

    // construct an empty randomized queue
    public RandomizedQueue() {
        numberOfItems = 0;
        storage = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (numberOfItems == 0);

    }

    // return the number of items on the randomized queue
    public int size() {
        return numberOfItems;
    }

    private void resizedIfNeed() {
        if (storage.length == numberOfItems) {
            resize(storage.length * 2);
        } else if (storage.length >= numberOfItems * 4) {
            resize(storage.length / 2);
        }
    }
    private void resize(int size) {
        Item[] newStorage = (Item[]) new Object[size];
        for (int i = 0; i < numberOfItems; i++) {
            newStorage[i] = storage[i];
        }
        storage = newStorage;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        storage[numberOfItems] = item;
        numberOfItems++;
        resizedIfNeed();
    }

    // remove and return a random item
    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("dequeue on an empty queue");
        }
        int randomIndex = StdRandom.uniform(numberOfItems);
        Item result = storage[randomIndex];
        storage[randomIndex] = storage[numberOfItems - 1];
        storage[numberOfItems - 1] = null;
        numberOfItems--;
        resizedIfNeed();
        return result;

    }


    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("sample on an empty queue");
        }
        return storage[StdRandom.uniform(numberOfItems)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int size = numberOfItems;
        private final Item[] items;
        RandomizedQueueIterator() {
            items = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                items[i] = storage[i];
            }
            StdRandom.shuffle(items);
        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("next on an empty random deque");
            }
            return items[--size];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        StdOut.println("round 1");

        randomizedQueue.enqueue(5);
        StdOut.println(String.format("sample: %d", randomizedQueue.sample()));
        StdOut.println(String.format("size: %d", randomizedQueue.size()));
        StdOut.println(String.format("deque: %d", randomizedQueue.dequeue()));
        StdOut.println(String.format("size: %d", randomizedQueue.size()));
        StdOut.println("round 2");
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(7);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(10);
        StdOut.println(String.format("size: %d", randomizedQueue.size()));

        for (Integer integer : randomizedQueue) {
            StdOut.println(integer);
        }
        StdOut.println(String.format("deque: %d", randomizedQueue.dequeue()));
        StdOut.println(String.format("size: %d", randomizedQueue.size()));


        StdOut.println("round 4 2 inner iterator");

        for (Integer integer : randomizedQueue) {
            StdOut.println("Outer");
            StdOut.println(integer);
            StdOut.println("Inner");

            for (Integer integer2 : randomizedQueue) {
                StdOut.println(integer2);
            }
            StdOut.println("End Inner");
        }
    }


}
