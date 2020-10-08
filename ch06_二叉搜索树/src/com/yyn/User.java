package com.yyn;

public class User implements Comparable {

    private Integer age;
    private String name;

    public User(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }
    public User(Integer age, String name){
        this.age = age;
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "age=" + age + "," + name;
    }

    @Override
    public int compareTo(Object o) {
        return this.age - ((User)o).age;
    }
}
