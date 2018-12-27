package com.company;

import java.io.*;
import java.util.SortedSet;
import java.util.TreeSet;

public class StudentToFile {


    // Serialization and De-Serialization of Student objects


    public void writeToFile(SortedSet<Student> studentSortedSet,String filename){

        // Serialization of a set of student objects
        // filename is the name of the file stored on disk

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            for(Student s:studentSortedSet){
                objectOutputStream.writeObject(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SortedSet<Student> readFromFile(String filename){

        //de-serialization of the file on disk and retrieval of stored data
        //returns a SortedSet of student data

        SortedSet<Student> studentSortedSet = new TreeSet<>(new defaultCompare());
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            while(true) {
                Student student = (Student) objectInputStream.readObject();
                studentSortedSet.add(student);
            }

        } catch (EOFException e) {
            return studentSortedSet;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return studentSortedSet;
    }
}
