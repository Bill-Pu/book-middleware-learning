package dynamicprogramming;

/**
 * 爬楼梯问题 (Climbing Stairs)
 * 动态规划解法
 * <p>
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 */
class ClimbStairs {

    /**
     * 计算爬到第 n 阶楼梯的方法总数。
     *
     * @param n 楼梯阶数
     * @return 方法总数
     */
    public int climbStairs(int n) {
        // 以此类推，第0阶和第1阶都只有1种状态（第0阶不动也算1种或者理解为斐波那契数列的起始项）
        if (n <= 1) {
            return 1;
        }

        // 优化空间复杂度为 O(1)
        // prev 记录 f(i-2) 的值
        int prev = 1;
        // curr 记录 f(i-1) 的值
        int curr = 1;

        // 从第 2 阶开始计算
        for (int i = 2; i <= n; i++) {
            // f(i) = f(i-1) + f(i-2)
            int next = prev + curr;
            // 滚动更新
            prev = curr;
            curr = next;
        }
        return curr;
    }
}