/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author skdonep
 */
public class LoginDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public boolean validCredentialsInDB(String username, String password) {
        String selectQuery = "SELECT * FROM IT478PROJECT.LOGIN ";
        selectQuery += "WHERE LOGIN_ID = '" + username + "' and PASSWORD = '" + password + "'";
        try {
            this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);
            Statement stmt;
            stmt = this.DBConn.createStatement();

            ResultSet rs = stmt.executeQuery(selectQuery);

            if (rs.next()) {
                rs.close();
                this.DBConn.close();
                stmt.close();
                return true;
            } else {
                rs.close();
                this.DBConn.close();
                stmt.close();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        return false;
    }
}
