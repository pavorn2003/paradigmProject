
package Project1;

import java.util.*;
import java.io.*;


public class project {
    
    
    public class Product {
        final String name;
        final int price;
        final int weight;
        protected int totSale = 0;
        protected int unitSold = 0;
        
        public Product(String n,int p,int w) {
            name = n;
            price = p;
            weight = w;
        }
        public void addSale(int n) {totSale+=n;}
        public void addUnit(int n) {unitSold+=n;}
        public int getUS() {return unitSold;}
        public int getTS() {return totSale;}
    }
    
    
    public class Customer {
        protected String name;
        protected int cashback = 0;
        public Customer(String n) {name = n;}
        public void setCashback(int n) {cashback =n;} 
    }
    
    
    public class Order extends Customer {
        protected int id;
        protected String shippingType = "S";
        protected int totWeight = 0;
        protected int totPrice = 0;
        public Order(String n,int i,String s) {
            super(n);
            id = i;
            shippingType=s;
        }
        public void addWeight(int n) {totWeight += n;}//calculate total weight
        public void addPrice(int n) {totPrice += n;}//calculate total price
    }
    
    
    public class ShippingCalculator {
        //leave for later
    }
    
    public static void main(String[] args) {
        
        
    }
    public void test(){
        //getting product and putting it into array
        
        String infile = "src/main/Java/project1/products.txt"; //CHANGE THIS FOR INPUT TO WORK
        Scanner rd = new Scanner(infile);
        Product[] products = new Product[5];
        int i = 0;
        while(rd.hasNext()) {
            String[] buf = rd.nextLine().split(",");
            products[i] = new Product(buf[0].trim(),
                                      Integer.parseInt(buf[1].trim()),
                                      Integer.parseInt(buf[2].trim())
            );
            i++;
        }
        rd.close();
        
        //reading order
        
        infile = "src/main/Java/project1/orders.txt"; //CHANGE THIS FOR INPUT TO WORK
        rd = new Scanner(infile);
        int[] orderPrice = new int[5];
        List<Order> orders = new ArrayList<Order>(); //USE ARRAYLIST
        int count = 0;
        while(rd.hasNext()){ //read each line from order.txt
            String[] buf = rd.nextLine().split(",");
            orders.add(new Order(buf[1].trim(),
                                 Integer.parseInt(buf[0].trim()),
                                 buf[2].trim()
            ));
            
            for(i=0;i<orderPrice.length;i++){  //add up price and  weight
                orders.get(count).addPrice(Integer.parseInt(buf[3+i]) * products[i].price);
                orders.get(count).addWeight(Integer.parseInt(buf[3+i]) * products[i].weight);
            }
            
            
            
        }
        
    
    }
    
}
