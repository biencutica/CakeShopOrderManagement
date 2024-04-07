package repo;

import domain.Cake;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CakeDBRepo extends DBRepo<Cake, Integer>{
    public CakeDBRepo(String tableName) {
        super(tableName);
    }

    @Override
    public Iterable<Cake> getAllItems() {
        ArrayList<Cake> cakes = new ArrayList<>();
        try
        {
            openConnection();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + ";"); ResultSet rs = ps.executeQuery();)
            {
                while (rs.next())
                {
                    Cake d = new Cake(rs.getInt("cid"), rs.getString("flavor"), rs.getDouble("price"));
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
    public Cake findItem(Integer id) {
        Cake cake = null;
        try {
            openConnection();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE cid=" + id); ResultSet rs = ps.executeQuery();) {
                cake = new Cake(rs.getInt("cid"), rs.getString("flavor"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return cake;
    }

    @Override
    public void addItem(Cake cake) throws src.exceptions.DuplicateItemException {
        try
        {
            openConnection();
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                ps.setInt(1, cake.getId());
                ps.setString(2, cake.getFlavor());
                ps.setDouble(3, cake.getPrice());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void removeItem(Integer integer) throws src.exceptions.ItemNotFound {
        try
        {
            openConnection();
            String deleteString = "DELETE FROM " + tableName + " WHERE cid=" + integer;
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
    public void updateItem(Integer integer, Cake newItem) throws src.exceptions.ItemNotFound {
        try
        {
            openConnection();
            String updateString = "UPDATE " + tableName +
                    " SET flavor = ?, price = ?, cid = ? WHERE cid=" + newItem.getId();
            try (PreparedStatement ps = conn.prepareStatement(updateString)) {

                ps.setInt(1, newItem.getId());
                ps.setString(2, newItem.getFlavor());
                ps.setDouble(3, newItem.getPrice());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}
