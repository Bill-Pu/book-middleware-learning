import java.util.ArrayList;

class LeetCode6 {
    public String convert(String s, int numRows) {
        String[][] rows = new String[numRows][1000];
        char[] chars = s.toCharArray();
        int n = 0;
        int i = 0;
        int j = 0;
        for (int k = 0; k < chars.length; k++) {
            if (i == 0) {
                rows[i][j] = String.valueOf(chars[k]);
            }
        }
        ArrayList<Object> objects = new ArrayList<>();
        return null;
    }
}