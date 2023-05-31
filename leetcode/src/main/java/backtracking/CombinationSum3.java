package backtracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


class CombinationSum3 {
     static List<List<Integer>> result = new ArrayList<>();
     static LinkedList<Integer> path = new LinkedList<>();
    public static List<List<Integer>> combinationSum3(int k, int n) {
            combinationSum3Function(n,k,1);
            return result;
    }
    private static void combinationSum3Function(int n, int k, int startIndex) {
        if (path.size() == k) {
            if (path.stream().reduce(Integer::sum).get() == n)
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i <= 9; i++) {
            path.add(i);
            combinationSum3Function(n, k, i +1);
            path.removeLast();
        }

    }
    public static void main(String[] args) {
        List<List<Integer>> combine = combinationSum3(3, 7);
        System.out.println();
//        string.contains()
    }
}