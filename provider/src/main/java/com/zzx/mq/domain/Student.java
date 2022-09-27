package com.zzx.mq.domain;

import java.io.Serializable;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:26 16:12:37
 */

public class Student implements Serializable {
    private static final long serialVersionUID = 5488784892721693115L;
    private String name;
    private Integer age;
    private char sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Student(String name, Integer age, char sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
