package domain;

import java.io.Serializable;

public class Cake implements Identifiable<Integer>, Serializable {
    Integer cake_id;
    private String flavor;
    private double price;

    public Cake(Integer cake_id, String flavor, double price) {
        this.cake_id = cake_id;
        this.flavor = flavor;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "cake_id=" + cake_id +
                ", flavor='" + flavor + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public Integer getId() {
        return cake_id;
    }
    @Override
    public void setId(Integer cake_id) {
        this.cake_id = cake_id;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
