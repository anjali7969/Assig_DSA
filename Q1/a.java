package Q1;

//You are a planner working on organizing a series of events in a row of n venues. Each venue can be decorated with
// one of the k available themes. However, adjacent venues should not have the same theme. The cost of decorating
// each venue with a certain theme varies.
//  The costs of decorating each venue with a specific theme are represented by an n x k cost matrix. For example,
//  costs [0][0] represents the cost of decorating venue 0 with theme 0, and costs[1][2] represents the cost of
//  decorating venue 1 with theme 2. Your task is to find the minimum cost to decorate all the venues while adhering
//  to the adjacency constraint.

//  For example, given the input costs = [[1, 5, 3], [2, 9, 4]], the minimum cost to decorate all the venues is 5. One
//  possible arrangement is decorating venue 0 with theme 0 and venue 1 with theme 2, resulting in a minimum cost of
//  1 + 4 = 5. Alternatively, decorating venue 0 with theme 2 and venue 1 with theme 0 also yields a minimum cost of
//  3 + 2 = 5.

//  Write a function that takes the cost matrix as input and returns the minimum cost to decorate all the venues while
//  satisfying the adjacency constraint.
//  Please note that the costs are positive integers.
//  Example: Input: [[1, 3, 2], [4, 6, 8], [3, 1, 5]] Output: 7
//  Explanation: Decorate venue 0 with theme 0, venue 1 with theme 1, and venue 2 with theme 0. Minimum cost: 1 +
//  6 + 1 = 7.

public class a {

    public static int findMinCost(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        //venues=v
        //themes=t
        int v = costs.length;
        int t = costs[0].length;

        int[][] dp = new int[v][t];

        for (int j = 0; j < t; j++) {
            dp[0][j] = costs[0][j];
        }

        for (int i = 1; i < v; i++) {
            for (int j = 0; j < t; j++) {
                dp[i][j] = Integer.MAX_VALUE;

                for (int prevJ = 0; prevJ < t; prevJ++) {
                    if (j != prevJ) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][prevJ] + costs[i][j]);
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int j = 0; j < t; j++) {
            minCost = Math.min(minCost, dp[v - 1][j]);
        }

        return minCost;
    }

    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        int result = findMinCost(costs);
        System.out.println("Minimum cost to decorate all venues: " + result);
    }
}
