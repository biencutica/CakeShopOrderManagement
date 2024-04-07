package ui;

import domain.Cake;
import domain.Order;
import src.exceptions.DuplicateItemException;
import src.exceptions.ItemNotFound;
import repo.CakeRepoTextFile;
import repo.OrderRepoTextFile;

import service.Service;
import src.settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    Settings settings = new Settings();
    private final Service orderService;
    private final Scanner scanner = new Scanner(System.in);

    public UI(Service service) {
        this.orderService = service;

    }


    public void printMenu() {
        System.out.println("~Cake Shop~");
        System.out.println("Orders:");
        System.out.println("1. Add order");
        System.out.println("2. Add cake to order");
        System.out.println("3. Delete order");
        System.out.println("4. Update order");
        System.out.println("5. See all orders");
        System.out.println("Cakes:");
        System.out.println("6. Add cake");
        System.out.println("7. Delete cake");
        System.out.println("8. Update cake");
        System.out.println("Reports:");
        System.out.println("9. See all finished orders");
        System.out.println("10. See all cakes in order of price");
        System.out.println("11. See all cakes with price lower than");
        System.out.println("12. See all orders sorted by number of cake orders");
        System.out.println("13. Get all cakes");
        System.out.println("0. Exit.");
    }

    public void addOrder() {
        System.out.println("Add order: ");
        System.out.println("Order id: ");
        Integer orderId = scanner.nextInt();

        System.out.println("Customer name: ");
        String name = scanner.next();

        System.out.println("Enter cake id: ");
        List<Integer> cakes = new ArrayList<>();
        Integer cakeId = scanner.nextInt();
        cakes.add(cakeId);
        Order order = new Order(orderId, name, cakes);

        try {
            orderService.addOrder(order);
            System.out.println("Order added successfully!");
        } catch (DuplicateItemException e) {
            System.out.println("Error adding order: " + e.getMessage());
        }
    }

    public void addCaketoOrder() {
        System.out.println("Enter order id: ");
        Integer id = scanner.nextInt();
        System.out.println("Enter Cake id: ");
        Integer cakeId = scanner.nextInt();
        try {
            Order o = orderService.getOrder(id);
            List<Integer> cakes = o.getCakeIds();
            cakes.add(cakeId);
        } catch (ItemNotFound e) {
            System.out.println("Error adding cake: " + e.getMessage());
        }
    }

    public void removeOrder() {
        System.out.println("Delete order: ");
        System.out.println("Enter order id: ");
        int orderId = scanner.nextInt();
        try {
            orderService.removeOrder(orderId);
            System.out.println("Order deleted successfully!");
        } catch (ItemNotFound e) {
            System.out.println("Error deleting order: " + e.getMessage());
        }
    }

    public void updateOrder() {
        System.out.println("Update order: ");
        System.out.println("Enter order id: ");
        int orderId = scanner.nextInt();

        System.out.println("Enter new customer name: ");
        String name = scanner.next();

        System.out.println("Enter new cake id: ");
        List<Integer> cakes = new ArrayList<>();
        Integer cakeId = scanner.nextInt();
        cakes.add(cakeId);
        Order o = new Order(orderId, name, cakes);

        try {
            orderService.updateOrder(orderId, o);
            System.out.println("Order updated successfully!");
        } catch (ItemNotFound e) {
            System.out.println("Error updating order: " + e.getMessage());
        }
    }



    public void addCake(){
        System.out.println("Please enter the following data:\n");
        System.out.println("Cake id: ");
        Integer id = scanner.nextInt();
        System.out.println("Flavour of the cake: ");
        String flavor = scanner.next();
        System.out.println("Price of the cake: ");
        double price = scanner.nextDouble();
        try{
            Cake c = new Cake(id,flavor,price);
            orderService.addCake(c);
        } catch (DuplicateItemException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCake() {
        System.out.println("Enter the id of the cake: ");
        Integer id = scanner.nextInt();
        try {
            orderService.removeCake(id);
        } catch (ItemNotFound e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCake(){
        System.out.println("Enter the id of the cake: ");
        Integer id = scanner.nextInt();
        System.out.println("Please enter the following data:\n");
        System.out.println("Flavour of the cake: ");
        String flavor = scanner.next();
        System.out.println("Price of the cake: ");
        double price = scanner.nextDouble();
        Cake c = new Cake(id,flavor,price);
        try{
            orderService.updateCake(id,c);
        } catch (ItemNotFound e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllCakes(){
        Iterable<Cake> cakes = orderService.getCakes();
        for (Cake c : cakes)
            System.out.println(c);
    }

    public void MemoryMenu() {
        printMenu();
        int ok = 1;
        while (ok == 1) {
            printMenu();
            System.out.println("Choice: ");
            int command = scanner.nextInt();
            switch (command) {
                case 0:
                    ok = 0;
                    break;
                case 1:
                    addOrder();
                    break;
                case 2:
                    addCaketoOrder();
                    break;
                case 3:
                    removeOrder();
                    break;
                case 4:
                    updateOrder();
                    break;
                case 5:
                    System.out.println(orderService.getOrders());
                    break;
                case 6:
                    addCake();
                    break;
                case 7:
                    deleteCake();
                    break;
                case 8:
                    updateCake();
                    break;
                case 9: {
                    //List<Order> orders = orderService.finishedOrders();
                    System.out.println(orderService.finishedOrders());
                }
                    break;
                case 10: {
                    List<Cake> cakes1 = orderService.cakebyPrice();
                    System.out.println(cakes1);
                }
                    break;
                case 11:
                    System.out.println("Price: ");
                    int price = scanner.nextInt();
                    List<Cake> cakes = orderService.cakesLowerPrice(price);
                    System.out.println(cakes);
                    break;
                case 12:
                    List<Order> orders2 = orderService.ordersByCakes();
                    System.out.println(orders2);
                    break;
                case 13:
                    getAllCakes();
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    /*public void FileMenu() {
        int ok = 1;
        while (ok == 1) {
            printMenu();
            System.out.println("Choice: ");
            int command = scanner.nextInt();
            switch (command) {
                case 0:
                    ok = 0;
                    break;
                case 1:
                    System.out.println("Add order: ");
                    System.out.println("Order id: ");
                    Integer orderId = scanner.nextInt();

                    System.out.println("Customer name: ");
                    String name = scanner.next();

                    System.out.println("Enter cake id: ");
                    List<Integer> cakes = new ArrayList<>();
                    Integer cakeId = scanner.nextInt();
                    cakes.add(cakeId);
                    Order order = new Order(orderId, name, cakes);

                    try {
                        orderService.addItem(order);
                        System.out.println("Order added successfully!");
                    } catch (DuplicateItemException e) {
                        System.out.println("Error adding order: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Enter order id: ");
                    Integer id = scanner.nextInt();
                    System.out.println("Enter Cake id: ");
                    Integer cakeId2 = scanner.nextInt();
                    try {
                        Order o = orderfService.findItem(id);
                        List<Integer> cakes2 = o.getCakeIds();
                        cakes2.add(cakeId2);
                    } catch (ItemNotFound e) {
                        System.out.println("Error adding cake: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Delete order: ");
                    System.out.println("Enter order id: ");
                    int orderId2 = scanner.nextInt();
                    try {
                        orderfService.removeItem(orderId2);
                        System.out.println("Order deleted successfully!");
                    } catch (ItemNotFound e) {
                        System.out.println("Error deleting order: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Update order: ");
                    System.out.println("Enter order id: ");
                    int orderId3 = scanner.nextInt();

                    System.out.println("Enter new customer name: ");
                    String name3 = scanner.next();

                    System.out.println("Enter new cake id: ");
                    List<Integer> cakes3 = new ArrayList<>();
                    Integer cakeId3 = scanner.nextInt();
                    cakes3.add(cakeId3);
                    Order o = new Order(orderId3, name3, cakes3);

                    try {
                        orderfService.updateItem(orderId3, o);
                        System.out.println("Order updated successfully!");
                    } catch (ItemNotFound e) {
                        System.out.println("Error updating order: " + e.getMessage());
                    }
                    break;
                case 5:
                    Iterable<Order> orders = orderfService.getAllItems();
                    for (Order ord : orders)
                        System.out.println(ord);
                    break;
                case 6:
                    System.out.println("Please enter the following data:\n");
                    System.out.println("Cake id: ");
                    Integer id4 = scanner.nextInt();
                    System.out.println("Flavour of the cake: ");
                    String flavor = scanner.next();
                    System.out.println("Price of the cake: ");
                    double price = scanner.nextDouble();
                    try{
                        Cake c = new Cake(id4,flavor,price);
                        cakefService.addItem(c);
                    } catch (DuplicateItemException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 7:
                    System.out.println("Enter the id of the cake: ");
                    Integer id3 = scanner.nextInt();
                    try {
                        cakefService.removeItem(id3);
                    } catch (ItemNotFound e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 8:
                    System.out.println("Enter the id of the cake: ");
                    Integer id2 = scanner.nextInt();
                    System.out.println("Please enter the following data:\n");
                    System.out.println("Flavour of the cake: ");
                    String flavor2 = scanner.next();
                    System.out.println("Price of the cake: ");
                    double price2 = scanner.nextDouble();
                    Cake c = new Cake(id2,flavor2,price2);
                    try{
                        cakefService.addItem(c);
                    } catch (DuplicateItemException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 9:
                    Iterable<Cake> cakes2 = cakefService.getAllItems();
                    for (Cake c2 : cakes2)
                        System.out.println(c2);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }*/

    public void run(){
        MemoryMenu();
    }
}
