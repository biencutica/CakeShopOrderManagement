package service;

import domain.Cake;
import domain.Identifiable;
import domain.Order;
import src.exceptions.DuplicateItemException;
import src.exceptions.ItemNotFound;
import repo.Repository;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private Repository<Order, Integer> orderRepo;
    private Repository<Cake, Integer> cakeRepo;


    public Service(Repository<Order, Integer> orderRepo, Repository<Cake, Integer> cakeRepo) {
        this.orderRepo = orderRepo;
        this.cakeRepo = cakeRepo;
    }

    //Order operations
    public Order getOrder(Integer integer) throws ItemNotFound {
        return orderRepo.findItem(integer);
    }
    public Iterable<Order> getOrders() {
        return orderRepo.getAllItems();
    }

    public void removeOrder(Integer integer) throws ItemNotFound {
        orderRepo.removeItem(integer);
    }

    public void updateOrder(Integer integer, Order newItem) throws ItemNotFound {
        orderRepo.updateItem(integer, newItem);
    }
    //Cake operations

    public Cake getCake(Integer integer) throws ItemNotFound {
        return cakeRepo.findItem(integer);
    }
    public Iterable<Cake> getCakes() {
        return cakeRepo.getAllItems();
    }

    public void addOrder(Order item) throws DuplicateItemException {
        orderRepo.addItem(item);
    }

    public void addCake(Cake item) throws DuplicateItemException {
        cakeRepo.addItem(item);
    }

    public void removeCake(Integer integer) throws ItemNotFound {
        cakeRepo.removeItem(integer);
    }
    public void updateCake(Integer integer, Cake newItem) throws ItemNotFound {
        cakeRepo.updateItem(integer, newItem);
    }


    public List<Order> finishedOrders(){
        List<Order> orders = new ArrayList<>();
        getOrders().forEach(orders::add);
        orders = orders.stream().filter(Order::isFinished).toList();
        return orders;
    }

    public List<Cake> cakebyPrice(){
        List<Cake> cakes = new ArrayList<>();
        getCakes().forEach(cakes::add);
        cakes = cakes.stream().sorted((x,y)-> Double.compare(y.getPrice(), x.getPrice())).toList();
        return cakes;
    }
    public List<Cake> cakesLowerPrice(double price){
        List<Cake> cakes = new ArrayList<>();
        getCakes().forEach(cakes::add);
        cakes = cakes.stream().filter(x->{
            return x.getPrice() < price;
        }).toList();
        return cakes;
    }

    public List<Order> ordersByCakes(){
        List<Order> orders = new ArrayList<>();
        getOrders().forEach(orders::add);
        orders = orders.stream().sorted((x,y)->{
            if (x.getCakeIds().size() < y.getCakeIds().size())
                return 1;
            else if (x.getCakeIds().size() > y.getCakeIds().size())
                return -1;
            return 0;
        }).toList();
        return orders;
    }
}
