package backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author PYB
 * @Date 2023/6/1 14:47
 * @Version 1.0
 */
public class EightQueens {
    static List<List<String>> result = new ArrayList<List<String>>();
    static char[][] path;
    public List<List<String>> solveNQueens(int n) {
        //初始化
        path = new char[n][n];
        for (char [] ch : path) {
            Arrays.fill(ch,'.');
        }

        return result;
    }
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        System.out.println(

        );
    }


}
