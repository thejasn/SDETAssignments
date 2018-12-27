package com.company;

import java.sql.SQLException;
import java.util.LinkedList;

public class ProcessItems {
    static LinkedList<Item> itemLinkedList = new LinkedList<>();    // list containing items read from the database.
    static int capacity = 5;
    static  int ITEM_READ_COUNT = DatabaseConnect.ITEM_COUNT;       // keeps a count of items read to know if all items have been read
                                                                    // required to terminate the threads while demo
    static  int TOTAL_ITEM_COUNT = DatabaseConnect.ITEM_COUNT;
    static LinkedList<Item> generatedItems = new LinkedList<>();    // list containing items precessed after reading from the database.

    public void readItems() throws InterruptedException, SQLException ,NullPointerException{

        while(true){

            synchronized (this){

                while (itemLinkedList.size()==capacity) {

                    wait();
                }
                Item new_item = DatabaseConnect.readItems(DatabaseConnect.ITEM_COUNT-ITEM_READ_COUNT+1);
                System.out.println("Reading Item"+new_item.getItemName());
                itemLinkedList.add(new_item);
                ITEM_READ_COUNT = (ITEM_READ_COUNT>0)?ITEM_READ_COUNT-1:ITEM_READ_COUNT;    // decrements the item count from max every time and item has been read
                                                                                            // from the database
                //tell consumer to start
                notify();

            }
            Thread.sleep(1000);
        }
    }

    public void processItems() throws InterruptedException {
        Item item;
        while (true){

            synchronized (this){

                while (itemLinkedList.size()==0) {
                    if(generatedItems.size()==TOTAL_ITEM_COUNT-1) throw new NullPointerException("Processed all Entries");  // all items have been read and processed
                    wait();
                }

                item = itemLinkedList.removeFirst();
                item.generateSalesTax();
                item.generateTotalPrize();
                generatedItems.add(item);       // adds items to another list containing all processed items
                System.out.println("Processed Item"+item.getItemName());
                notify();

            }
            Thread.sleep(1000);
        }

    }
}

