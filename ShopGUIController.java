package gui;

import domain.Order;
import javafx.fxml.FXML;
import service.Service;
import src.settings.Settings;

import javax.swing.text.html.ListView;

public class ShopGUIController {

    private Service service;

    private Settings settings;

    public ShopGUIController(Service service, Settings settings){
        this.service = service;
        this.settings=settings;
    }




}
