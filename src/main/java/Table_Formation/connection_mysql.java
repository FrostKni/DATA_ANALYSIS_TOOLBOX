package Table_Formation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class connection_mysql {

    private static Connection con;

    private static ResultSet rs;

    public static int connection_status(String server, String user, String password, String db) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + db + "?useTimezone==true&serverTimezone=UTC", user, password);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("show tables");
            if (rs != null) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet getRs() {
        return rs;
    }


    public ResultSet execute_query(String qry) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            return  stmt.executeQuery(qry);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}






