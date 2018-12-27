package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {


    final static String filename = "studentDetails";
    static int hasSaved = 0;
    public static void main(String[] args){
	// write your code here
        Student student;
        SortedSet<Student> studentSortedSet = new TreeSet<>(new defaultCompare());

        SortedSet<Student> previousSet = new TreeSet<>(new defaultCompare());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int check = 5;


        // code to check if any previous records exist
        File f = new File(filename);
        if(f.exists() && !f.isDirectory()){
            System.out.println("Loading Previous Records ...");
            previousSet = new StudentToFile().readFromFile(filename);
        }
        if(previousSet!=null)
            studentSortedSet.addAll(previousSet);


        do{
            System.out.println("1. Add User Details \n2. Display User Details \n3. Delete User Details \n4. Save User Details \n5. Exit");
            System.out .println("Please select your option: ");


            try {
                check = Integer.parseInt(bufferedReader.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }

            switch (check){
                case 1:
                        try {
                            student = Student.readDetails();
                            if(student!=null){
                                if(studentSortedSet.add(student)) System.out.println("Added Successfully.");
                            }
                            else{
                                System.out.println("Error during reading student details.");
                            }
                        } catch (IOException e) {
                            System.out.println("Error during reading inputs:"+e.toString());
                        }
                        catch (NumberFormatException e){
                            System.out.println("Error with numbered inputs:"+e.toString());
                        }
                    break;
                case 2:
                    List<Student> temp = new ArrayList<>();
                    try {
                        System.out.println(" Sorted According to: 1.Roll Number\t2.Age\t3.Address\t4.Name");
                        System.out.println(" Select an attribute:");
                        int attribute = Integer.parseInt(bufferedReader.readLine());

                        System.out.println(" Select Sort Order: 1.Ascending Order\t2.Descending Order");
                        int sortOrder = Integer.parseInt(bufferedReader.readLine());
                        if(attribute==1 && sortOrder==1)    temp = studentSortedSet.stream().sorted(new rollNumComparator()).collect(Collectors.toList());
                        else if(attribute==2 && sortOrder==1) temp = studentSortedSet.stream().sorted(new ageComparator()).collect(Collectors.toList());
                        else if(attribute==3 && sortOrder==1) temp = studentSortedSet.stream().sorted(new addressComparator()).collect(Collectors.toList());
                        else if(attribute==4 && sortOrder==1) temp = studentSortedSet.stream().sorted(new nameComparator()).collect(Collectors.toList());
                        else if(attribute==1 && sortOrder==2)    temp = studentSortedSet.stream().sorted(new rollNumComparator().reversed()).collect(Collectors.toList());
                        else if(attribute==2 && sortOrder==2) temp = studentSortedSet.stream().sorted(new ageComparator().reversed()).collect(Collectors.toList());
                        else if(attribute==3 && sortOrder==2) temp = studentSortedSet.stream().sorted(new addressComparator().reversed()).collect(Collectors.toList());
                        else if(attribute==4 && sortOrder==2) temp = studentSortedSet.stream().sorted(new nameComparator().reversed()).collect(Collectors.toList());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                        System.out.println("Student Details");
                        System.out.println("-----------------------------------------------------------------");
                        System.out.println("\tName\tRoll Number\t\tAge\t\tAddress\t\tCourses");
                        System.out.println("-----------------------------------------------------------------");
                    for (Student s:temp) {
                        s.showDetails();
                    }
                        break;
                case 3: System.out.println("Enter roll number of the student:");
                    try {
                        int rno_del = Integer.parseInt(bufferedReader.readLine());
                        if(Validators.testRollNum(rno_del)){
                            for (Student s:studentSortedSet) {
                                if(s.getRollNumber()==rno_del){
                                    if(studentSortedSet.remove(s)){
                                        System.out.println("Student entry deleted successfully.");
                                    }
                                    else{
                                        System.out.println("Student entry deletion unsuccessful.");
                                    }
                                    break;
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4: hasSaved = 1;
                        new StudentToFile().writeToFile(studentSortedSet,filename);
                        break;
                case 5:
                        if(hasSaved==0){
                            System.out.println("Do you want to save the latest changes (y/n):");
                            try {
                                char save = (char) bufferedReader.read();
                                if(save == 'y' || save =='Y') new StudentToFile().writeToFile(studentSortedSet,filename);
                                else break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                        default:System.out.println("Invalid option selected.");
                                check=1;
            }

        }while(check < 5 && check >0);
    }
}
