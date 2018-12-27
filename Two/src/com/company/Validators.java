package com.company;

public class Validators {
    static boolean testAge(int a){
        return (a>0);
    }
    static boolean testRollNum(int num){
        return (num>=0);
    }
    static boolean testBlanks(String line){
        return (line.length() == 0);
    }
}
