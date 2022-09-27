package com.zzx.mq.domain;


import java.io.Serializable;

/**
 * @author ZZX
 * @version 1.0.0
 * @date 2022:09:27 09:56:12
 */

public class Student implements Serializable {
    private static final long serialVersionUID = -928014524532295254L;
    private String name;
    private Integer age;
    private String address;

    public Student() {
    }

    public Student(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
