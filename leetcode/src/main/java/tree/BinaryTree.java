package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author PYB
 * @Date 2023/5/25 21:03
 * @Version 1.0
 */
public class BinaryTree {
    public List<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        reaversal(root, res);
        return res;
    }

    private void reaversal(TreeNode root, ArrayList<Integer> res) {
        if (root == null)
        { return; }
        res.add(root.val);
        reaversal(root.left, res);
        reaversal(root.right, res);
    }
}
