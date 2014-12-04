/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.GeoLocationGraphBean;

/**
 *
 * @author skdonep
 */
public class GeoLocationDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public GeoLocationDAO() {
        super();
    }

    public void fetchGraphEntreesFromDB(ArrayList<GeoLocationGraphBean> graphEntries, int geoYear) throws SQLException {
        int applicantID;
        String zipCode = null;
        ResultSet rs = null;
        Statement stmt = null;

        String applicantIdsQuery = "SELECT APPLICANT_ID FROM IT478PROJECT.APPLICATION_INFO WHERE YEAR(DATE_OF_APPLICATION)=" + geoYear;
        String zipCodesQuery = "SELECT ZIP_CODE FROM IT478PROJECT.APPLICANT_INFO WHERE APPLICANT_ID IN (" + applicantIdsQuery + ")";
        String countryCountQuery = "SELECT COUNTRY,COUNT(*) COUNT FROM ZIPCODE WHERE ZIP_CODE IN(" + zipCodesQuery + ") GROUP BY COUNTRY";
        try {
            this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);

            stmt = this.DBConn.createStatement();

            rs = stmt.executeQuery(countryCountQuery);

            while (rs.next()) {
                GeoLocationGraphBean graphEntree = new GeoLocationGraphBean();
                graphEntree.setCountry(rs.getString("COUNTRY"));
                graphEntree.setApplicationsCount(rs.getInt("COUNT"));
                graphEntries.add(graphEntree);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        if (this.DBConn != null) {
            this.DBConn.close();
        }
    }
}
