package repo;

import domain.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepoTextFile extends FileRepo<Order, Integer> {

    public OrderRepoTextFile(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile()  {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = reader.readLine())!= null) {
                String[] stringArray = line.split(";");
                if (stringArray.length != 4) {
                    continue;
                } else {
                    String text = stringArray[2].trim();
                    text = text.replace("[", "");
                    text = text.replace("]", "");
                    text = text.replace(" ", "");
                    String[] textv = text.split(",");
                    List<Integer> cakes = new ArrayList<>();
                    for(String s : textv)
                        cakes.add(Integer.parseInt(s));
                    Order order = new Order(Integer.parseInt(stringArray[0].trim()), stringArray[1].trim(), cakes);
                    order.setFinished(Boolean.parseBoolean(stringArray[3].trim()));
                    this.listOfItems.put(order.getId(), order);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Order o: getAllItems())
            {
                writer.write(o.getId() + "," +
                        o.getCustomer() + "," +
                        o.getCakeIds() + ";" +
                        o.isFinished()+ "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
