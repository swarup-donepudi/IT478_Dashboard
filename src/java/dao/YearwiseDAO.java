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
import model.YearwiseGraphBean;

/**
 *
 * @author skdonep
 */
public class YearwiseDAO extends AppDBInfoDAO {

    private Connection DBConn;

    public YearwiseDAO() {
        super();
    }

    public void fetchGraphEntreesFromDB(ArrayList<YearwiseGraphBean> yearwiseGraphEntrees, int yearwiseBegin, int yearwiseEnd) throws SQLException {
        String selectQuery = "select year(date_of_application) selectYear,zip_table.country,count(applicant.applicant_id) appcount from "
                + "application_info app,applicant_info applicant,zipcode zip_table where year(date_of_application)>=" + yearwiseBegin
                + "and year(date_of_application)<=" + yearwiseEnd + " and applicant.APPLICANT_ID= app.applicant_id and "
                + "applicant.zip_code=zip_table.zip_code  group by year(date_of_application),zip_table.country order "
                + "by year(date_of_application),country";
        ResultSet rs = null;
        Statement stmt = null;
        try {
            this.DBConn = this.openDBConnection(databaseURL, dbUserName, dbPassword);

            stmt = this.DBConn.createStatement();

            rs = stmt.executeQuery(selectQuery);

            while (rs.next()) {
                YearwiseGraphBean yearwiseBean = new YearwiseGraphBean();
                yearwiseBean.setYear(rs.getInt("SELECTYEAR"));
                yearwiseBean.setCountry(rs.getString("COUNTRY"));
                yearwiseBean.setApplicationsCount(rs.getInt("APPCOUNT"));
                yearwiseGraphEntrees.add(yearwiseBean);
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
