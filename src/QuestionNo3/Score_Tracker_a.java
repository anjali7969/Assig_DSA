package QuestionNo3;

import java.util.Collections;
import java.util.PriorityQueue;

public class Score_Tracker_a {
    private PriorityQueue<Double> minHeap; // Stores the second half of the scores (greater half)
    private PriorityQueue<Double> maxHeap; // Stores the first half of the scores (smaller half)

    public Score_Tracker_a() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    }

    public void addScore(double score) {
        maxHeap.offer(score);
        minHeap.offer(maxHeap.poll());

        // Ensure minHeap is not larger than maxHeap
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double getMedianScore() {
        if (minHeap.size() == maxHeap.size()) {
            return (minHeap.peek() + maxHeap.peek()) / 2.0;
        } else {
            return maxHeap.peek();
        }
    }

    public static void main(String[] args) {
        Score_Tracker_a scoreTracker = new Score_Tracker_a();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1); // Output: 88.9

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median 2: " + median2); // Output: 86.95
    }
}

