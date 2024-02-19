package QuestionNo4;

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
        this.left = this.right = null;
    }
}

public class ClosestValuesInBST_b {
    public List<Integer> closestValues(TreeNode root, double target, int x) {
        List<Integer> result = new ArrayList<>();
        if (root == null || x <= 0) {
            return result;
        }

        Deque<Integer> deque = new ArrayDeque<>();
        inorder(root, target, deque, x);

        while (!deque.isEmpty()) {
            result.add(deque.pollFirst());
        }

        return result;
    }

    private void inorder(TreeNode root, double target, Deque<Integer> deque, int x) {
        if (root == null) {
            return;
        }

        inorder(root.left, target, deque, x);

        if (deque.size() < x) {
            deque.offerLast(root.val);
        } else {
            if (Math.abs(root.val - target) < Math.abs(deque.peekFirst() - target)) {
                deque.pollFirst();
                deque.offerLast(root.val);
            } else {
                // Stop the traversal if the current node is farther from the target than the values in the deque
                return;
            }
        }

        inorder(root.right, target, deque, x);
    }

    public static void main(String[] args) {
        ClosestValuesInBST_b solution = new ClosestValuesInBST_b();

        // Construct the binary search tree
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        double target = 3.8;
        int x = 2;
        List<Integer> result = solution.closestValues(root, target, x);

        System.out.println("Closest values to " + target + ": " + result); //output 3.8:[3,4]
    }
}
