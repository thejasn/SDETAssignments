package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here

        if(args.length == 0 || args.length <= 4)
        {
            System.out.println("Commandline Usage : -name <first item name> " +
                    "-price <price of first item> " +
                    "-quantity <quantity of first item> " +
                    "-type <type of first item>");
            System.exit(-1);
        }
        ArrayList<Item> itemList = new ArrayList<>();
        char checkMore='n';
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        // args contains the values sent using the command line
        Item obj = Validators.testAndGenerateInputs(args);
        if(obj == null)
        {
            System.out.println("Item not created successfully due to either missing attributes or incorrect values.");
            System.exit(-1);
        }
        else{
            obj.generateSalesTax();
            obj.generateTotalPrize();
            itemList.add(obj);


            //check if more items are to be added
            System.out.println("Do you want to enter more items (y/n):");
            try {
                checkMore = (char) bufferedReader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(checkMore == 'y' || checkMore == 'Y') {
                try {
                    if (!obj.readDetails()) {
                        System .out.println("asdasd");
                        return;
                    }
                    obj.generateSalesTax();
                    obj.generateTotalPrize();
                    itemList.add(obj);
                    System.in.read(new byte[System.in.available()]);
                    System.out.println("Do you want to enter more items (y/n):");
                    bufferedReader.read();
                    checkMore = (char) bufferedReader.read();
                } catch (IOException e) {
                    System.out.println("ERROR while parsing user input.");
                    e.printStackTrace();
                }

            }

            for(Item i : itemList){
                System.out.println("Item no: "+itemList.indexOf(i));
                i.showDetails();
            }

        }
    }
}
