package domain;

import java.io.Serializable;
import java.util.List;

public class Order implements Identifiable<Integer>, Serializable {
    Integer order_id;
    private List<Integer> cakeIds;
    private boolean isFinished;
    private String customer;

    public Order(Integer order_id, String customer, List<Integer> cakeIds) {
        this.order_id = order_id;
        this.cakeIds = cakeIds;
        this.isFinished = false;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", cakeIds=" + cakeIds +
                ", isFinished=" + isFinished +
                ", customer='" + customer + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return order_id;
    }

    @Override
    public void setId(Integer id) {
        this.order_id = id;
    }

    public List<Integer> getCakeIds() {
        return cakeIds;
    }

    public void setCakeIds(List<Integer> cakeIds) {
        this.cakeIds = cakeIds;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
