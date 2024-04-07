package repo;

import domain.Cake;

import java.io.*;

public class CakeRepoTextFile extends FileRepo<Cake, Integer> {
    public CakeRepoTextFile(String fileName){super(fileName);}
    @Override
    protected void readFromFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while((line=reader.readLine())!=null){
                String[] stringArray = line.split(";");
                if(stringArray.length != 4)
                    continue;
                else{
                    Cake c = new Cake(Integer.parseInt(stringArray[0].trim()), stringArray[1].trim(), Double.parseDouble(stringArray[2].trim()));
                    this.listOfItems.put(c.getId(), c);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Cake c: getAllItems())
            {
                writer.write(c.getId() + ";" +
                        c.getFlavor() + ";" +
                        c.getPrice()+ "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
