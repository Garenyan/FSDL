package MyTools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by garen on 2019/3/16.
 */
public class H2DataBase {
    public static void insertData(){

    }

    public static void getDataSetFromH2(){

    }

    public static void getConnectionWithH2(){

    }

    private static void releaseConnection(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }


}
