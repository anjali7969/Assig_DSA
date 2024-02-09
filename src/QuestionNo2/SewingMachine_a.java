package QuestionNo2;

public class SewingMachine_a {

    public static int minMovesToEqualize(int[] dresses) {
        int sum = 0;
        int n = dresses.length;

        for (int i = 0; i < n; i++) {
            sum += dresses[i];
        }

        if (sum % n != 0) {
            return -1; // If the sum cannot be equally distributed, return -1
        }

        int target = sum / n; // Target number of dresses in each machine
        int moves = 0;
        int[] diff = new int[n];

        for (int i = 0; i < n - 1; i++) {
            diff[i] = dresses[i] - target;
            dresses[i + 1] += diff[i]; // Move dresses from left to right
            moves += Math.abs(diff[i]); // Count moves
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] dresses = {2, 1, 3, 0, 2};
        int moves = minMovesToEqualize(dresses);
        System.out.println("Minimum moves required: " + moves);
    }
}

