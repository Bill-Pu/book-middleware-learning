package org.learning;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                return Integer.compare(o1.getAge(), o2.getAge());
            }

        });
    }

    @Test
    public void test2() {

        TreeMap<Person, Object> personObjectTreeMap = new TreeMap<>((Person p1, Person p2) -> {
            Integer num = p1.getAge() - p2.getAge();
            return Integer.compare(num, 0);
        });
    }

    @Test
public void test3() {
    Person person = new Person(12, "111");
    Person person2 = new Person(112, "222");
    Person person3 = new Person(14322, "333");
    Person person4 = new Person(124562, "444");
    Person person5 = new Person(14322, "555");
    // 使用Stream一次性完成Map构建，并处理键冲突情况（保留第一个值）
    Map<String, Person> collect = Arrays.asList(person, person2, person3, person4, person5)
            .stream()
            .collect(Collectors.toMap(
                Person::getName,
                p -> p,
                (existing, replacement) -> existing  // 处理键冲突，保留已存在的值
            ));
}


    @Test
    public void test4() {
        ConcurrentMap<String, Person> stringPersonConcurrentMap = new ConcurrentHashMap<>();
    }

    @Test
    public void test5() {

    }

    @Test
    public void test6() {

    }
}
