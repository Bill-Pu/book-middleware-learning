package org.learning;

import java.util.Arrays;

/**
 * @author puyb
 * @date 2025/11/1
 * @apiNote
 */
public class Test {
    public static void main(String[] args) {
        String[] stringArr = new String[]{"hello","world","！"};
        stringArr[0] = "goodbye";
        System.out.println(Arrays.toString(stringArr));
        System.out.println(stringArr.length);
        for (int i = 0;i < stringArr.length;i++){

        }
    }
}
