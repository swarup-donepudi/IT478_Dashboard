/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
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
    
    public void fetchGraphEntreesFromDB(ArrayList<YearwiseGraphBean> yearwiseGraphEntrees,int yearwiseBegin,int yearwiseEnd){
        
    }
    
}


