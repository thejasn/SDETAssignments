package com.company;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// class to hold all details of the items along with their functionality
public class Item {

    private String itemName;
    private double itemPrice;
    private int itemQuantity;
    public enum Type { RAW,MANUFACTURED,IMPORTED };
    private Type itemType;

    private double totalPrize,salesTax;


    public Item(String itemName, double itemPrice, int itemQuantity, Type itemType) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemType = itemType;
    }

    void generateSalesTax(){

        switch (itemType){
            case RAW: this.salesTax =  (0.125*itemPrice);
                break;
            case MANUFACTURED: this.salesTax = (( 0.125 * this.itemPrice ) + 0.002 * (this.itemPrice + (0.125 * this.itemPrice)));
                break;
            case IMPORTED:
                        this.salesTax = ((0.1 * this.itemPrice) );
                        if(this.salesTax <= 100)
                            this.salesTax += 5.0;
                        else if(this.salesTax > 100 && this.salesTax <= 200)
                            this.salesTax += 10.0;
                        else if(this.salesTax > 200)
                            this.salesTax += (this.salesTax*0.05);
                break;
            default: this.salesTax = 0.0;
        }

    }

    void generateTotalPrize(){ totalPrize = (this.itemPrice+this.salesTax); }

    boolean readDetails() throws IOException {

        String input;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter item name:");
        itemName = bufferedReader.readLine();


        System.out.println("Enter item price:");
        input = bufferedReader.readLine();
        if(Validators.testPriceFormat(input)){
            itemPrice = Double.parseDouble(input);
            if( !Validators.testPrice(itemPrice) ){
                System.out.println("Invalid price");
                return false;
            }
        }

        System.out.println("Enter item quantity:");
        input = bufferedReader.readLine();
        if(Validators.testQuantityFormat(input)) {
            itemQuantity = Integer.parseInt(input);
            if(!Validators.testQuantity(itemQuantity)){
                System.out.println("Invalid quantity");
                return false;
            }
        }

        System.out.println("Enter item type:");
        input = bufferedReader.readLine();
        itemType = Validators.testType(input);
        if(itemType == null){
            return false;
        }

        return true;
    }

    void showDetails(){
        System.out.println("Name        : "+itemName);
        System.out.println("Price       : "+itemPrice);
        System.out.println("Quantity    : "+itemQuantity);
        System.out.println("Type        : "+itemType);
        System.out.println("Sales Tax   : "+salesTax);
        System.out.println("Total Price : "+totalPrize);

    }

}
