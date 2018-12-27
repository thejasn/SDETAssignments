package com.company;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnect {

    static int ITEM_COUNT = 1;
    static Connection connection;
    static String sql = "CREATE TABLE IF NOT EXISTS Items (\n"
            + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
            + "	object blob\n"
            + ");";

    static final String WRITE_OBJECT_SQL = "INSERT INTO Items(object) VALUES (?)";


    public static void connect(String databaseName){

        try{
            String url = "jdbc:sqlite:items.db";
            connection = DriverManager.getConnection(url);
            DatabaseMetaData meta = connection.getMetaData();
            System.out.println("The driver name is " + meta.getDriverName());

            Statement stmt = connection.createStatement();
                // create a new table
                stmt.execute(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean writeItem(Item item) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(WRITE_OBJECT_SQL);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(item);
            byte[] itemBytes = byteArrayOutputStream.toByteArray();
            preparedStatement.setBytes(1,itemBytes);
            //System.out.println(ITEM_COUNT);
            ITEM_COUNT++;
        } catch (IOException e) {
            e.printStackTrace();
        }



        if(preparedStatement.executeUpdate()==1){
            return true;
        }
        else return false;
    }
    public static Item readItems(int i) throws SQLException {
        Item item=null;
        String READ_OBJECT_SQL = "SELECT object FROM Items WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(READ_OBJECT_SQL);
        preparedStatement.setInt(1,i);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            byte[] itemBytes = resultSet.getBytes(1);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(itemBytes);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                item = (Item) objectInputStream.readObject();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        resultSet.close();
        return item;
    }
}
