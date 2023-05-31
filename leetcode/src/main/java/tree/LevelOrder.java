package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author PYB
 * @Date 2023/5/29 16:25
 * @Version 1.0
 */
public class LevelOrder {
    List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> levelOrder(TreeNode root) {
        levelFunction(root, 0);
        return result;
    }

    private void levelFunction(TreeNode root, Integer level) {
        if (root == null) { return;}
        level++;
        if (result.size() < level) {
            result.add(new ArrayList<>());
        }
        result.get(level -1).add(root.val);
        levelFunction(root.left,level);
        levelFunction(root.right,level);
    }
}
