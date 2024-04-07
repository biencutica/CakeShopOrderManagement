package repo;

import domain.Order;

import java.io.*;
import java.util.Map;

public class OrderRepoBinaryFile extends FileRepo<Order, Integer> {

    public OrderRepoBinaryFile(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            this.listOfItems = (Map<Integer, Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            oos.writeObject(this.listOfItems);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
