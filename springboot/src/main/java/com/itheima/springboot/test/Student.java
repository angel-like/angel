package com.itheima.springboot.test;

import lombok.Data;

@Data
public class Student {
 public Student() {
 }

 public Student(String name, Integer age) {
  this.name = name;
  this.age = age;
 }

 private  String name;
 private Integer age;
}
