
package Project1;

import java.util.*;
import java.io.*;


public class project {
    
    
    public class Product {
        final String name;
        final int price,weight;
        protected int totSale = 0,unitSold = 0;
        
        public Product(String n,int p,int w) {
            name = n;
            price = p;
            weight = w;
        }
        public void addSale(int n) {totSale+=n;}
        public void addUnit(int n) {unitSold+=n;}
        public int getUS() {return unitSold;}
        public int getTS() {return totSale;}
        public String getName() {return name;}
    }
    
    public class Customer {
        protected String name;
        protected int cashback = 0;
        public Customer(String n) {name = n;}
        public void setCashback(int n) {cashback =n;} 
        public String getName() {return name;}
        public int getCashback() {return cashback;}
    }
    
    public class Order {
        protected int id;
        protected String shippingType = "S";
        protected int totWeight = 0;
        protected int totPrice = 0;
        public Order(String n,int i,String s) {
            id = i;
            shippingType=s;
        }
        public void addWeight(int n) {totWeight += n;}//calculate total weight
        public void addPrice(int n) {totPrice += n;}//calculate total price
        public int getPrice() {return totPrice;}
        public int getWeight() {return totWeight;}
    }
    
    public class ShippingCalculator {
        protected int eMax1, eMax2, eMax3;
        protected int eFee1, eFee2, eFee3;
        protected int sMax, sFee;
        protected int vFee,vMax;
        public ShippingCalculator() {}
        public void ef1(int n1,int n2) {
            eMax1 = n1;
            eFee1 = n2;
        }
        public void ef2(int n1,int n2) {
            eMax2 = n1;
            eFee2 = n2;
        }
        public void ef3(int n1,int n2) {
            eMax3 = n1;
            eFee3 = n2;
        }
        public void sf(int n1,int n2) {
            sMax = n1;
            sFee = n2;
        }
        public void sv(int n1,int n2) {
            vMax = n1;
            vFee = n2;
        }
        public int calculate(int tw,String shipType) {
            int shipPrice = 0;
            if(tw>eMax3 || shipType.compareTo("S")==0) {
                if(tw<sMax) return shipPrice = sFee;
                else {
                    tw-=sMax;
                    shipPrice+=sFee+Math.ceil((double)tw/vMax)*vFee;
                    return shipPrice;
                }
            } else {
                if(tw<eMax1) return eFee1;
                else if(tw<eMax2) return eFee2;
                else return eFee3;
            }
        }
    }
    
    public static void main(String[] args) {
        project main = new project();
        main.test();
    }
    public void test(){
        //getting product and putting it into array
        try {
            String infile = "src/main/Java/project1/products.txt"; //CHANGE THIS FOR INPUT TO WORK
            Scanner rd = new Scanner(new File(infile));
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

            //read shipping.txt
            infile = "src/main/Java/project1/shipping.txt";
            rd = new Scanner(new File(infile));
            ShippingCalculator calc = new ShippingCalculator();
            for(i=0;i<5;i++){ 
                String[] buf = rd.nextLine().split(",");
                int var1=Integer.parseInt(buf[3].trim()),var2 = Integer.parseInt(buf[4].trim());
                switch(i) {
                    case 0:calc.ef1(var1,var2);break;
                    case 1:calc.ef2(var1,var2);break;
                    case 2:calc.ef3(var1,var2);break;
                    case 3:calc.sf(var1,var2);break;
                    case 4:calc.sv(var1,var2);break;
                }
            }
            rd.close();
            
            //reading order

            infile = "src/main/Java/project1/orders.txt";
            rd = new Scanner(new File(infile));
            
            List<Order> orders = new ArrayList<Order>(); //USE ARRAYLIST
            List<Customer> customers = new ArrayList<Customer>();
            List<String> cusName = new ArrayList<String>();
            int count = 0;
            while(rd.hasNext()){ //read each line from order.txt
                String[] buf = rd.nextLine().split(",");
                orders.add(new Order(buf[1].trim(),
                                    Integer.parseInt(buf[0].trim()),
                                    buf[2].trim()
                ));
                customers.add(new Customer(buf[1].trim()));
                cusName.add(buf[1].trim());
                int id = Integer.parseInt(buf[0].trim());
                for(i=0;i<5;i++){  //add up price and  weight
                    orders.get(count).addPrice(Integer.parseInt(buf[3+i].trim()) * products[i].price);
                    orders.get(count).addWeight(Integer.parseInt(buf[3+i].trim()) * products[i].weight);
                }

                int shipping = calc.calculate(orders.get(count).getWeight(),buf[2].trim()),cashback=0;
                if(cusName.contains(buf[1].trim())) {
                    cashback = customers.get(cusName.indexOf(buf[1].trim())).getCashback();
                    if(cashback>100){
                        customers.get(cusName.indexOf(buf[1].trim())).setCashback(cashback-100);
                        cashback=100;
                    }
                }

                //print here
                System.out.printf(" Order %d (%s) ,  %s >> %15s %s %15s %s %15s %s %15s %s %15s %s\n",
                        id,
                        buf[2].trim(),
                        buf[1].trim(),
                        products[0].getName(),
                        buf[3].trim(),
                        products[1].getName(),
                        buf[4].trim(),
                        products[2].getName(),    
                        buf[5].trim(),
                        products[3].getName(),
                        buf[6].trim(),
                        products[4].getName(),
                        buf[7].trim()
                        );
                String blank = " ".repeat(26);
                System.out.printf("%26s>> Available Cashback = %d\n",blank,cashback);
                System.out.printf("%26s>> Total Price  = %,-9d\n",blank,orders.get(count).getPrice());
                System.out.printf("%26s>> Total Weight = %,-9d  Shipping Fee = %,d\n",blank,orders.get(count).getWeight(),shipping);
                System.out.printf("%26s>> Final Bill   = %,-9d  Cashback for next order = %d\n\n ",blank,orders.get(count).getPrice()+shipping-cashback, (int)(orders.get(count).getPrice() * 0.01));
                //set next order's cashback
                customers.get(cusName.indexOf(buf[1].trim())).setCashback((int)(orders.get(count).getPrice() * 0.01));
                    
                count++;
            }
            rd.close();
        } catch(Exception e){
            System.err.println(e);
        }
        
    
    }
}
    

