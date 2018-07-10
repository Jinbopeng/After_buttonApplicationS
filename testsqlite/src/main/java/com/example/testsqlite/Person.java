package com.example.testsqlite;

public class Person {
    int _id;
    String name;
    int age;

    public Person() {
       // id=0;
        //name=null;
        //age=0;
    }

    public Person(int id, String name, int age) {
        this._id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
