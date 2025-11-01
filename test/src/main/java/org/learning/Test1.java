package org.learning;

import org.junit.jupiter.api.Test;

import java.util.*;
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
                if (o1.getAge()>o2.getAge())
                return 1;
                else {
                    return 0;
                }
            }
        });
    }
    @Test
    public void test2(){

        TreeMap<Person, Object> personObjectTreeMap = new TreeMap<>((Person p1, Person p2) -> {
            Integer num = p1.getAge() - p2.getAge();
            return Integer.compare(num, 0);
        });
    }
    @Test
    public void test3(){
        HashMap<String, Person> objectObjectHashMap = new HashMap<>();
        Person person = new Person(12,"111");
        Person person2 = new Person(112,"222");
        Person person3 = new Person(14322,"333");
        Person person4 = new Person(124562,"444");
        Person person5 = new Person(14322,"555");
        objectObjectHashMap.put(person.getName(), person);
        objectObjectHashMap.put(person2.getName(), person2);
        objectObjectHashMap.put(person3.getName(), person3);
        objectObjectHashMap.put(person4.getName(), person4);
        objectObjectHashMap.put(person5.getName(), person5);
        Map<String, Person> collect = Arrays.asList(
                person, person2, person3, person4, person5
        ).stream().collect(Collectors.toMap(Person::getName,p -> p));
    }
}
