
package Project1;

import java.util.*;
import java.io.*;


public class project {
    public class product {
        protected String name;
        protected int price;
        protected int weight;
        protected int totSale = 0;
        protected int unitSold = 0;
        public product(String n,int p,int w) {
            name = n;
            price = p;
            weight = w;
        }
        public void addSale(int n) {totSale+=n;}
        public void addUnit(int n) {unitSold+=n;}
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
        public Order(String n,int i,String s) {
            super(n);
            id = i;
            shippingType=s;
        }
    }
    public class ShippingCalculator {
        //leave for later
    }
    
    public static void main(String[] args) {
        String product = "src/main/Java/project1/products.txt";
        Scanner rd = new Scanner(product);
        while(rd.hasNext()) {
            
        }
        
    }
    
}
