package org.learning;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author puyb
 * @date 2025/11/1
 * @apiNote
 */

@Data
@EqualsAndHashCode
@ToString
public class Person {
    private Integer age;
    private String name;

    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Person() {
    }
}
