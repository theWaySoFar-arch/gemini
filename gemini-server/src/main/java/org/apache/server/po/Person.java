package org.apache.server.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author TheWaySoFar
 * @version 1.0
 * @describe TODO
 * @date 2023/9/10 7:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    private Integer id;
    private String name;
    private Integer age;
    private Integer cost;
}
