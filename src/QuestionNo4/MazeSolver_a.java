package QuestionNo4;

import java.util.LinkedList;
import java.util.Queue;

public class MazeSolver_a {

    static class Point {
        int x, y, keys;

        public Point(int x, int y, int keys) {
            this.x = x;
            this.y = y;
            this.keys = keys;
        }
    }

    public static int minMovesToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int targetKeys = 0;
        int startX = -1, startY = -1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startX = i;
                    startY = j;
                } else if (grid[i][j] >= 'a' && grid[i][j] <= 'f') {
                    targetKeys |= (1 << (grid[i][j] - 'a')); // Set bit for each key
                }
            }
        }

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        Queue<Point> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[m][n][1 << 6]; // to store visited states with keys

        queue.offer(new Point(startX, startY, 0));
        visited[startX][startY][0] = true;

        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Point current = queue.poll();
                int x = current.x;
                int y = current.y;
                int keys = current.keys;

                if (grid[x][y] == 'E' && keys == targetKeys) {
                    return steps;
                }

                for (int[] dir : directions) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];
                    int newKeys = keys;

                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && grid[newX][newY] != 'W') {
                        char cell = grid[newX][newY];
                        if (cell >= 'A' && cell <= 'F' && ((keys >> (cell - 'A')) & 1) == 0) {
                            continue; // Cannot pass through locked door without key
                        }
                        if (cell >= 'a' && cell <= 'f') {
                            newKeys |= (1 << (cell - 'a')); // Collect key
                        }

                        if (!visited[newX][newY][newKeys]) {
                            queue.offer(new Point(newX, newY, newKeys));
                            visited[newX][newY][newKeys] = true;
                        }
                    }
                }
            }
            steps++;
        }

        return -1; // If exit cannot be reached
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'S', 'P', 'a', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'P', 'P', 'P', 'E', 'P'},
                {'P', 'b', 'P', 'P', 'P'}
        };
        int result = minMovesToCollectKeys(grid);
        System.out.println("Minimum moves required: " + result);
    }
}
