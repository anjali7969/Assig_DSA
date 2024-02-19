

//You are given an integer n representing the total number of individuals. Each individual is identified by a unique
//ID from 0 to n-1. The individuals have a unique secret that they can share with others.
//
//The secret-sharing process begins with person 0, who initially possesses the secret. Person 0 can share the secret
//with any number of individuals simultaneously during specific time intervals. Each time interval is represented by
// a tuple (start, end) where start and end are non-negative integers indicating the start and end times of the interval.
//
//You need to determine the set of individuals who will eventually know the secret after all the possible secret
//sharing intervals have occurred.

package QuestionNo2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Secret_Sharing_b {
    public static List<Integer> findIndividuals(int n, int[][] intervals, int firstPerson) {
        Set<Integer> knownSet = new HashSet<>();
        knownSet.add(firstPerson);

        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            // If the person who originally possessed the secret is in the set, add the individuals in the interval
            if (knownSet.contains(firstPerson)) {
                for (int i = start; i <= end; i++) {
                    knownSet.add(i % n); // Use modulo to handle the circular nature of individuals
                }
            }
        }

        return new ArrayList<>(knownSet);
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;

        List<Integer> result = findIndividuals(n, intervals, firstPerson);
        System.out.println(result); // Output: [0, 1, 2, 3, 4]
    }
}

