package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Student implements Serializable {

    private String fullName,address;
    private int age,rollNumber;
    private Set<Course> courses = new HashSet<>();
    enum Course { A,B,C,D,E }



    public void showDetails() {
        System.out.println("\t"+fullName+"\t"+rollNumber+"\t\t\t\t"+age+"\t\t"+address+"\t\t"+courses);
    }

    public Student() {
    }


    public static Student readDetails() throws IOException,NumberFormatException {

        Student student = new Student();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter full name:");
        String temp = bufferedReader.readLine();
        if(!Validators.testBlanks(temp))
            student.setFullName(temp);
        else {System.out.println("Empty string."); return null;}

        System.out.println("Enter roll number:");
        int rno = Integer.parseInt(bufferedReader.readLine());
        if(Validators.testRollNum(rno)) student.setRollNumber(rno);
        else { System.out.println("Roll number incorrect."); return null; }

        System.out.println("Enter age:");
        int sage = Integer.parseInt(bufferedReader.readLine());
        if(Validators.testAge(sage)) student.setAge(sage);
        else { System.out.println("Age incorrect."); return null; }

        System.out.println("Enter address:");
        temp = bufferedReader.readLine();
        if(!Validators.testBlanks(temp))
            student.setAddress(temp);
        else {System.out.println("Empty string."); return null;}


        System.out.println("Enter course names (A/B/C/D/E eg:- A,B,D,E):");
        String scourse[] = bufferedReader.readLine().split(",");
        Set<Course> s_courses=new HashSet<>();
        if( !(scourse.length >=4 && scourse.length <6)){ System.out.println("Minimum number of courses not met."); return null; }
        for (String s: scourse) {
            switch (s){
                case "A":
                case "a": s_courses.add(Course.A);
                    break;
                case "B":
                case "b": s_courses.add(Course.B);
                    break;
                case "C":
                case "c": s_courses.add(Course.C);
                    break;

                case "D":
                case "d": s_courses.add(Course.D);
                    break;
                case "E":
                case "e": s_courses.add(Course.E);
                    break;
                    default: return null;

            }
        }
        student.setCourses(s_courses);

        return student;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
class defaultCompare implements Comparator<Student> {

    @Override
    public int compare(Student student, Student t1) {
        // if the roll number already exists, we return 0 as to indicate the entry is a duplicate entry
        // so the new entry isn't added into the set
        if(student.getRollNumber() == t1.getRollNumber()){
            return 0;
        }
        // check if the full names are the same, if yes we check the roll numbers
        else if(student.getFullName().compareTo(t1.getFullName())==0){
            return (student.getRollNumber() - t1.getRollNumber());
        }
        // since the full names aren't same
        else return student.getFullName().compareTo(t1.getFullName());
    }
}
class nameComparator implements Comparator<Student>{

    @Override
    public int compare(Student student, Student t1) {
        return student.getFullName().compareTo(t1.getFullName());
    }
}
class rollNumComparator implements Comparator<Student>{

    @Override
    public int compare(Student student, Student t1) {
        return student.getRollNumber()-t1.getRollNumber();
    }
}

class ageComparator implements Comparator<Student>{

    @Override
    public int compare(Student student, Student t1) {
        return student.getAge()-t1.getAge();
    }
}
class addressComparator implements Comparator<Student>{

    @Override
    public int compare(Student student, Student t1) {
        return student.getAddress().compareTo(t1.getAddress());
    }
}

