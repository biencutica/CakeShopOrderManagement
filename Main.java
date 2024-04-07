package main;

import domain.Cake;
import domain.Order;
import gui.ShopGUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repo.*;
import service.Service;
import src.settings.Settings;


import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public void start(Stage stage) throws IOException {

        Settings settings = new Settings();
        Repository<Order, Integer> orderRepo = null;
        Repository<Cake, Integer> cakeRepo = null;
        if(Objects.equals(settings.getProperty("Repository"), "text")){
            orderRepo = new OrderRepoTextFile(settings.getProperty("orderFilePath"));
            cakeRepo = new CakeRepoTextFile(settings.getProperty("cakeFilePath"));
        }
        else if(Objects.equals(settings.getProperty("Repository"), "binary")){
            orderRepo = new OrderRepoBinaryFile(settings.getProperty("orderFilePath"));
            cakeRepo = new CakeRepoBinaryFile(settings.getProperty("cakeFilePath"));
        }
        else if(Objects.equals(settings.getProperty("Repository"), "memory")){
            orderRepo = new MemoryRepo<>();
            cakeRepo = new MemoryRepo<>();
        }
        else if(Objects.equals(settings.getProperty("Repository"), "database")){
            orderRepo = new OrderDBRepo(settings.getProperty("orderFilePath"));
            cakeRepo = new CakeDBRepo(settings.getProperty("cakeFilePath"));
        }
        Service service = new Service(orderRepo,cakeRepo);
        /*UI ui = new UI(service);
        ui.run();*/
        ShopGUIController controller = new ShopGUIController(service,settings);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ShopGUI.fxml"));
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();


    }
}