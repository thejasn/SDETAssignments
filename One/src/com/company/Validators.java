package com.company;


import java.util.HashMap;
import java.util.Map;

// class responsible for handling all validation of users input
public class Validators {

    static boolean testPrice(double price){
        if( price >= 0) return true;
        else return false;
    }

    static boolean testQuantity(int qty){
        if( qty >= 0 )return true;
        else return false;
    }

    static Item.Type testType(String type){
        try{


            switch(type) {
                case "RAW":
                case "raw":
                    return Item.Type.RAW;
                case "MANUFACTURED":
                case "manufactured":
                    return Item.Type.MANUFACTURED;
                case "IMPORTED":
                case "imported":
                    return Item.Type.IMPORTED;
                default:
                    throw new Exception("Wrong Type of item");
            }
        }
        catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    static boolean testPriceFormat(String num){
        try{
            Double.parseDouble(num);
            return true;
        }
        catch (NumberFormatException e)
        {
            System.out.println("Number Format Exception for Price");
            return false;
        }
    }
    static boolean testQuantityFormat(String num){
        try{
            Integer.parseInt(num);
            return true;
        }
        catch (NumberFormatException e)
        {
            System.out.println("Number Format Exception for Quantity");
            return false;
        }
    }
    static Item testAndGenerateInputs(String[] args){
        Map<String,String> inputs = new HashMap<>();
        for(int i = 0;i<args.length;i+=2){
            inputs.put(args[i],args[i+1]);
        }
        if(!inputs.containsKey("-name") || !inputs.containsKey("-type")) {
            System.out.println("Mandatory attributes name and type missing.");
            return null;
        }
        else if(!args[0].equals("-name")){
            System.out.println("Arguments order incorrect.");
            return null;
        }
        String name = inputs.get("-name");
        double price=0.0;
        int qty=0;
        Item.Type type;

        if(testPriceFormat(inputs.get("-price"))){
            price = Double.parseDouble(inputs.get("-price"));
            if( !testPrice(price) ){
                System.out.println("Invalid price");
                return null;
            }
        }
        else if(testQuantityFormat(inputs.get("-quantity"))) {
            qty = Integer.parseInt(inputs.get("-quantity"));
            if(!testQuantity(qty)){
                System.out.println("Invalid quantity");
                return null;
            }
        }
        type = testType(inputs.get("-type"));
        if(type == null){
            return null;
        }


        return new Item(name,price,qty,type);
    }
}
