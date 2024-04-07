package repo;

import domain.Identifiable;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBRepo<T extends Identifiable<U>,U> extends repo.MemoryRepo<T,U> {
    protected final String URL = "jdbc:sqlite:C:/Users/Bianca/Documents/Java/a4-biencutica/identifier.sqlite";//"jdbc:sqlite:identifier.sqlite";

    protected String tableName;
    protected Connection conn = null;

    public DBRepo(String tableName) {
        this.tableName = tableName;
    }

    protected void openConnection() {
        try {

            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(URL);
            if (conn == null || conn.isClosed()){
                conn = ds.getConnection();
                System.out.println("Connected!");
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    protected void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
