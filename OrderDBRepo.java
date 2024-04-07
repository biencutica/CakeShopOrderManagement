package repo;

import domain.Order;
import src.exceptions.DuplicateItemException;
import src.exceptions.ItemNotFound;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDBRepo extends DBRepo<Order, Integer>{

    public OrderDBRepo(String tableName) {
        super(tableName);
    }
    @Override
    public Iterable<Order> getAllItems() {
        ArrayList<Order> cakes = new ArrayList<>();
        try
        {
            openConnection();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName +";"); ResultSet rs = ps.executeQuery();)
            {
                while (rs.next())
                {
                    String text = rs.getString("cakes");
                    String[] textv = text.split(",");
                    List<Integer> c = new ArrayList<>();
                    for(String s : textv)
                        c.add(Integer.parseInt(s));
                    Order d = new Order(rs.getInt("oid"), rs.getString("customer"), c);
                    cakes.add(d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return cakes;
    }
    @Override
    public Order findItem(Integer id) {
        Order cake = null;
        try {
            openConnection();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE Oid=" + id); ResultSet rs = ps.executeQuery();) {
                String text = rs.getString("cakes");
                String[] textv = text.split(",");
                List<Integer> c = new ArrayList<>();
                for(String s : textv)
                    c.add(Integer.parseInt(s));
                cake = new Order(rs.getInt("oid"), rs.getString("customer"), c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return cake;
    }

    @Override
    public void addItem(Order o) throws DuplicateItemException {
        try
        {
            openConnection();
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                ps.setInt(1, o.getId());

                String text = o.getCakeIds().toString();
                text = text.replace("[", "");
                text = text.replace("]", "");
                text = text.replace(" ", "");
                ps.setString(2, text);
                ps.setBoolean(3, o.isFinished() );
                ps.setString(4, o.getCustomer());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void removeItem(Integer integer) throws ItemNotFound {
        try
        {
            openConnection();
            String deleteString = "DELETE FROM " + tableName + " WHERE oid=" + integer;
            try (PreparedStatement ps = conn.prepareStatement(deleteString)) {
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void updateItem(Integer integer, Order newItem) throws ItemNotFound {
        try
        {
            openConnection();
            String text = newItem.getCakeIds().toString();
            text = text.replace("[", "");
            text = text.replace("]", "");
            text = text.replace(" ", "");

            String updateString = "UPDATE " + tableName +
                    " SET customer = ?, cakes = ?, status = ? WHERE oid=" + integer;

            try (PreparedStatement ps = conn.prepareStatement(updateString)) {
                ps.setString(2, text);
                ps.setBoolean(3, newItem.isFinished());
                ps.setString(1, newItem.getCustomer());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}

