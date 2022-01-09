
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Muhammad Ahmad Khan FA18-BCS-053 
 * @author Muneeb Ahmad FA18-BCS-079
 * @author Usama Abbasi FA18-BSE-096
 */
public class Ecommerce_BakeryShop {

    static ArrayList<Integer> productIds = readData("product_ids.txt");
    static ArrayList<String> productNames = readData("product_names.txt");
    static ArrayList<String> productUnit = readData("product_unit.txt");
    static ArrayList<Integer> productQuantity = readData("product_quan.txt");
    static ArrayList<Double> productUnitPrice = readData("product_uprice.txt");
    // Cart
    static ArrayList<Integer> CartItems = new ArrayList<>();
    static ArrayList<Integer> CartItemQuantities = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.println("*-*-*-*-*-*-* Welcome to Ecommerce Bakery Shop *-*-*-*-*-*-*-*");
                System.out.println("1.Admin area");
                System.out.println("2.Customer Area");
                System.out.println("0.Exit");
                System.out.println("Enter Your Choice : ");
                int a = input.nextInt();
                switch (a) {
                    case 1:
                        System.out.println("*-*-*-*-*-*-*-*-*");
                        System.out.println("1.Add Product");
                        System.out.println("2.Delete Product");
                        System.out.println("3.View All Products");
                        System.out.println("0.Back");
                        System.out.println("*-*-*-*-*-*-*-*-*\n");
                        System.out.println("Enter Your Choice : ");
                        int b = input.nextInt();
                        switch (b) {
                            case 1:
                                System.out.print("Product Name : ");
                                String name = input.next();
                                System.out.print("Quantity : ");
                                int quantity = input.nextInt();
                                System.out.print("Unit : ");
                                String unit = input.next();
                                System.out.print("Unit Price : ");
                                double price = input.nextDouble();
                                AddProduct(name, unit, quantity, price);
                                System.out.println("Product Added !");
                                break;
                            case 2:
                                System.out.println("Enter Product ID : ");
                                int id = input.nextInt();
                                DeleteProduct(id);
                                System.out.println("Product Deleted !");
                                break;
                            case 3:
                                ViewProducts();
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Wrong Choice !");
                                break;
                        }
                        break;
                    case 2:
                        System.out.println("*-*-*-*-*-*-*-*-*");
                        System.out.println("1.View Products");
                        System.out.println("2.Add to Cart");
                        System.out.println("3.View Cart");
                        System.out.println("4.Checkout");
                        System.out.println("0.Back");
                        System.out.println("*-*-*-*-*-*-*-*-*\n");
                        System.out.println("Enter Your Choice : ");
                        int c = input.nextInt();
                        switch (c) {
                            case 1:
                                ViewProducts();
                                break;
                            case 2:
                                System.out.println("Enter Product ID : ");
                                int id = input.nextInt();
                                System.out.println("Quantity : ");
                                int quantity = input.nextInt();
                                if (isQuantityAvailable(id, quantity)) {
                                    AddToCart(id, quantity);
                                    System.out.println("Product Added to Cart !");
                                } else {
                                    System.out.println("Please Enter Less Quantity !");
                                }
                                break;
                            case 3:
                                ViewCartItems();
                                break;
                            case 4:
                                generateRecipt();
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Wrong Choice !");
                                break;
                        }
                        break;
                    case 0:
                        exit(0);
                        break;
                    default:
                        System.out.println("Wrong Choice !");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void AddProduct(String name, String unit, int quantity, double unitPrice) {
        int id;
        if (productIds.isEmpty()) {
            id = 0;
        } else {
            id = productIds.get(productIds.size() - 1) + 1;
        }
        productIds.add(id);
        productNames.add(name);
        productUnit.add(unit);
        productQuantity.add(quantity);
        productUnitPrice.add(unitPrice);
        //writing to files
        writeData(productIds, "product_ids.txt");
        writeData(productNames, "product_names.txt");
        writeData(productUnit, "product_unit.txt");
        writeData(productQuantity, "product_quan.txt");
        writeData(productUnitPrice, "product_uprice.txt");

    }

    public static void DeleteProduct(int id) {
        int index = productIds.indexOf(id);
        productIds.remove(index);
        productNames.remove(index);
        productUnit.remove(index);
        productQuantity.remove(index);
        productUnitPrice.remove(index);
        //writing to files
        writeData(productIds, "product_ids.txt");
        writeData(productNames, "product_names.txt");
        writeData(productUnit, "product_unit.txt");
        writeData(productQuantity, "product_quan.txt");
        writeData(productUnitPrice, "product_uprice.txt");
    }

    public static boolean isQuantityAvailable(int id, int quantity) {
        int index = productIds.indexOf(id);
        return productQuantity.get(index) >= quantity;
    }

    public static void decreaseQuantity(int id, int quantity) {
        int index = productIds.indexOf(id);
        int actualQuan = productQuantity.get(index);
        if ((actualQuan - quantity) == 0) {
            DeleteProduct(id);
        } else {
            int newQuan = actualQuan - quantity;
            productQuantity.set(id, newQuan);
        }
        writeData(productQuantity, "product_quan.txt");
    }

    public static void ViewProducts() {
        for (int i = 0; i < productIds.size(); i++) {
            System.out.println("Product ID: " + productIds.get(i) + " Name : " + productNames.get(i) + " Quantity : " + productQuantity.get(i) + " Unit : " + productUnit.get(i) + " Price : " + productUnitPrice.get(i));
        }
    }

    public static void AddToCart(int id, int quantity) {
        CartItems.add(id);
        CartItemQuantities.add(quantity);

    }

    public static void ViewCartItems() {
        for (int i = 0; i < CartItems.size(); i++) {
            int index = productIds.indexOf(CartItems.get(i));
            System.out.println("Product ID: " + CartItems.get(i) + " Name : " + productNames.get(index) + " Quantity : " + CartItemQuantities.get(i) + " Price : " + productUnitPrice.get(index) * CartItemQuantities.get(i));
        }
    }

    public static void generateRecipt() {
        if (!CartItems.isEmpty()) {
            System.out.println("*-*-*-*-* Order Placed *-*-*-*-*");
            double subTotal = 0;
            for (int i = 0; i < CartItems.size(); i++) {
                int index = productIds.indexOf(CartItems.get(i));
                subTotal = subTotal + productUnitPrice.get(index) * CartItemQuantities.get(i);
                System.out.println("Product ID: " + CartItems.get(i) + " Name : " + productNames.get(index) + " Quantity : " + CartItemQuantities.get(i) + " Price : " + productUnitPrice.get(index) * CartItemQuantities.get(i));
                decreaseQuantity(CartItems.get(i), CartItemQuantities.get(i));
            }
            CartItems.clear();
            CartItemQuantities.clear();
            System.out.println("Total Bill : " + subTotal);
        } else {
            System.out.println("Your Cart is Empty Please Add some Products to cart");
        }
    }

    public static ArrayList readData(String filename) {
        ArrayList aList = new ArrayList();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                aList.add(myReader.nextLine());
            }
            myReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return aList;
    }

    public static void writeData(ArrayList aList, String filename) {
        File fileName = new File(filename);
        try {
            FileWriter fw = new FileWriter(fileName);
            Writer output = new BufferedWriter(fw);
            for (int i = 0; i < aList.size(); i++) {
                output.write(aList.get(i) + "\n");
            }
            output.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
