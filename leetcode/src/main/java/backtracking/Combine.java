package backtracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Combine {
    static List<List<Integer>> result = new ArrayList<>();
    static LinkedList<Integer> path = new LinkedList<>();
    public static List<List<Integer>> combine(int n, int k) {
        backTracking(n,k,1);
        return result;
    }

    public static void backTracking(int n, int k, int startIndex) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i <= n; i++) {
            path.add(i);
            backTracking(n, k, i + 1);
            path.removeLast();
        }


    }

//    static List<List<Integer>> result = new ArrayList<>();
//    static LinkedList<Integer> path = new LinkedList<>();
//    public static List<List<Integer>> combine(int n, int k) {
//        combineHelper(n, k, 1);
//        return result;
//    }
//
//    private static void combineHelper(int n, int k, int startIndex){
//        //终止条件
//        if (path.size() == k){
//            result.add(new ArrayList<>(path));
//            return;
//        }
//        for (int i = startIndex; i <= n ; i++){
//            path.add(i);
//            combineHelper(n, k, i + 1);
//            path.removeLast();
//        }
//    }
    public static void main(String[] args) {
        List<List<Integer>> combine = combine(4, 2);
        System.out.println();
    }
}