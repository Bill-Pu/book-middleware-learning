package org.learning;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author puyb
 * @date 2025/11/1
 * @apiNote
 */


public class Test1 {
    // public static void main(String[] args) {
    //     String[] stringArr = new String[]{"hello", "world", "！"};
    //     stringArr[0] = "goodbye";
    //     System.out.println(Arrays.toString(stringArr));
    //     System.out.println(stringArr.length);
    //     for (int i = 0; i < stringArr.length; i++) {
    //
    //     }
    //     Person person = new Person();
    //     Integer age = person.getAge();
    //
    // }

    @Test
    public void test1() {
        TreeMap<Person, Object> objectObjectTreeMap = new TreeMap<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.getAge()>o2.getAge())
                return 1;
                else {
                    return 0;
                }
            }
        });

    }
}
