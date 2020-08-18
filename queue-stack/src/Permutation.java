import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        int numberProcessed = 0;

        while (!StdIn.isEmpty()) {
            try {
                String input = StdIn.readString();
                if (randomizedQueue.size() < k) {
                    randomizedQueue.enqueue(input);
                } else {
                    int random = StdRandom.uniform(k + 1 + numberProcessed);
                    if (random < k) {
                        randomizedQueue.dequeue();
                        randomizedQueue.enqueue(input);
                    }
                    numberProcessed++;
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }

    }
}
