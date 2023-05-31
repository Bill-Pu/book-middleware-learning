package hash;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author PYB
 * @Date 2023/5/24 20:46
 * @Version 1.0
 */
//242. 有效的字母异位词
public class IsAnagram {
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length())
            return false;
        char[] chars = s.toCharArray();
        char[] chars1 = t.toCharArray();
        Integer[] hash = new Integer[26];
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char aChar = chars[i];
            int sn = (int) aChar - 97;
            if ((hash[sn] == null)) {
                hash[sn] = 1;
            }else {
                hash[sn] += 1;
            }
            char bChar = chars1[i];
            int tn = (int) bChar - 97;
            if ((hash[tn] == null)) {
                hash[tn] = -1;
            }else {
                hash[tn] -= 1;
            }
        }
        boolean result = true;
        for (int i = 0; i < hash.length; i++) {
            if(!(hash[i] == null || hash[i] == 0)) {
                result = false;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        boolean anagram = isAnagram("rat", "car");
        System.out.println(anagram);

    }
}
