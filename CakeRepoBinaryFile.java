package repo;


import domain.Cake;

import java.io.*;
import java.util.Map;

public class CakeRepoBinaryFile extends FileRepo<Cake,Integer>{

    public CakeRepoBinaryFile(String fileName) {super(fileName);}

    @Override
    protected void readFromFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            this.listOfItems = (Map<Integer, Cake>) ois.readObject();
        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    protected void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(listOfItems);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
