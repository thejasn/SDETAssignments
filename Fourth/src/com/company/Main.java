package com.company;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Main {

     static void addingDefaultItems(){

        try {
            DatabaseConnect.writeItem(new Item("Bottles",34.6,2,Item.Type.MANUFACTURED));
            DatabaseConnect.writeItem(new Item("Pens",34.6,2,Item.Type.MANUFACTURED));
            DatabaseConnect.writeItem(new Item("Boots",34.6,2,Item.Type.IMPORTED));
            DatabaseConnect.writeItem(new Item("Apples",34.6,2,Item.Type.RAW));
            DatabaseConnect.writeItem(new Item("Tables",34.6,2,Item.Type.MANUFACTURED));
            DatabaseConnect.writeItem(new Item("Chairs",34.6,2,Item.Type.MANUFACTURED));
            DatabaseConnect.writeItem(new Item("Laptops",34.6,2,Item.Type.IMPORTED));


            System.out.println("Storing default object");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // write your code here
        File f = new File("items.db");
        if (f.exists() && f.isFile()){
            f.delete();
            DatabaseConnect.connect("testing");

        addingDefaultItems();
         }
        else {
            DatabaseConnect.connect("testing");
            addingDefaultItems();
        }

        final ProcessItems processItems = new ProcessItems();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processItems.readItems();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }   catch (NullPointerException e){
                    System.out.println("All items have been read.");
                    return;
                }
            }
        });

        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    processItems.processItems();
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException e){
                    System.out.println(e.getMessage());
                    return;
                }

            }
        });

        //start both the threads
        producer.start();
        consumer.start();


        // producer should finish before consumer
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LinkedList<Item> a = ProcessItems.generatedItems;
        for(Item item:a){
            item.showDetails();
        }
    }
}
