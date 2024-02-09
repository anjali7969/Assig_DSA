package QuestionNo2;

//You are given an integer n representing the total number of individuals. Each individual is identified by a unique
//ID from 0 to n-1. The individuals have a unique secret that they can share with others.
//
//The secret-sharing process begins with person 0, who initially possesses the secret. Person 0 can share the secret
//with any number of individuals simultaneously during specific time intervals. Each time interval is represented by
// a tuple (start, end) where start and end are non-negative integers indicating the start and end times of the interval.
//
//You need to determine the set of individuals who will eventually know the secret after all the possible secret
//sharing intervals have occurred.



import java.util.*;

public class Secret_Sharing_b {
    public static List<Integer> getIndividuals(int n, int[][] intervals, int firstPerson) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> knownSecrets = new HashSet<>();
        knownSecrets.add(firstPerson);

        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];

            Set<Integer> newIndividuals = new HashSet<>();

            for (int i = start; i <= end; i++) {
                if (knownSecrets.contains(i)) {
                    // Add individuals who receive the secret during this interval
                    for (int j = 0; j < n; j++) {
                        if (!knownSecrets.contains(j)) {
                            newIndividuals.add(j);
                        }
                    }
                }
            }

            knownSecrets.addAll(newIndividuals);
        }

        result.addAll(knownSecrets);
        return result;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;

        List<Integer> result = getIndividuals(n, intervals, firstPerson);

        System.out.println("Individuals who will eventually know the secret: " + result);
    }
}

