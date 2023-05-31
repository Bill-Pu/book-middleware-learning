package backtracking;

import java.util.*;


class LetterCombinations {
    static List<String> result = new ArrayList<>();
    static HashMap<Integer, char[]> keyMapping = new HashMap<>();
    static LinkedList<Character> path = new LinkedList<>();
    public static List<String> letterCombinations(String digits) {
        if(Objects.equals(digits, "")){
            return new ArrayList();
        }
        keyMapping.put(2, new char[] { 'a', 'b', 'c' });
        keyMapping.put(3, new char[] { 'd', 'e', 'f' });
        keyMapping.put(4, new char[] { 'g', 'h', 'i' });
        keyMapping.put(5, new char[] { 'j', 'k', 'l' });
        keyMapping.put(6, new char[] { 'm', 'n', 'o' });
        keyMapping.put(7, new char[] { 'p', 'q', 'r', 's' });
        keyMapping.put(8, new char[] { 't', 'u', 'v' });
        keyMapping.put(9, new char[] { 'w', 'x', 'y', 'z' });
        char[] chars = digits.toCharArray();
        int i = 0;
        int k = chars.length;
        backTracking1(i,k,chars);
        return result;
    }
    public static void backTracking1(int i, int k, char[] digitsChars) {

        if (path.size() == k) {
            String s = path.stream().map(Object::toString).reduce("", (str1, str2) -> str1 + str2);;
            result.add(s);
            return;
        }
        char digitsChar = digitsChars[i];

        char[] chars = keyMapping.get(Integer.valueOf(String.valueOf(digitsChar)));
        for (char c : chars) {
            path.add(c);
            backTracking1(i +1,k,digitsChars);
            path.removeLast();
        }
    }
    public static void main(String[] args) {

            char key = '2';

//        System.out.println(Integer.valueOf(String.valueOf(key)));
//        char[] chars = keyMapping.get(Integer.valueOf(key));
//        List<String> strings = letterCombinations("23");
        System.out.println(new ArrayList<String>().toString());
    }
}