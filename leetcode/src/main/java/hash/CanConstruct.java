package hash;

import java.util.HashMap;

class CanConstruct {
    public static boolean canConstruct(String ransomNote, String magazine) {
        char[] chars = magazine.toCharArray();
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>(chars.length);
        for (int i = 0; i < chars.length; i++) {
            stringIntegerHashMap.merge(String.valueOf(chars[i]), 1, Integer::sum);
        }
        char[] chars1 = ransomNote.toCharArray();
        for (int i = 0; i < chars1.length; i++) {
            Integer integer = stringIntegerHashMap.get(String.valueOf(chars1[i]));
            if (integer == null || integer < 0) {
                return false;
            }else {
                stringIntegerHashMap.put(String.valueOf(chars1[i]), integer-1);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        boolean b = canConstruct("asdf", "asdffda");
        System.out.println();
    }
}