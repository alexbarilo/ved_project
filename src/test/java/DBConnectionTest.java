import junit.framework.Assert;
import org.junit.Test;
import util.DBConnection;

import java.sql.Connection;

public class DBConnectionTest {

    private Connection connection;
    @Test
    public void getConnectionTest() {
        connection = DBConnection.getConnection();
        Assert.assertTrue(connection != null);
    }
}
