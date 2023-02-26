package com.example.ecommerce;

public class Order {
    public static boolean placeOrder(Customer customer,Product product){
        try {
            String placeOrder="INSERT INTO orders(customer_id, product_id, status) VALUES (" +customer.getId() + "," + product.getId() + ",'Ordered')";
            DatabaseConnection dbConn=new DatabaseConnection();
            return dbConn.insertUpdate(placeOrder);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
