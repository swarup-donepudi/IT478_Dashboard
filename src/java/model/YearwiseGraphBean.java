/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author skdonep
 */
@Named(value = "yearwiseGraphBean")
@SessionScoped
public class YearwiseGraphBean implements Serializable {
    private int year;
    private String country;
    private int applicationsCount;
    /**
     * Creates a new instance of YearwiseGraphBean
     */
    public YearwiseGraphBean() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getApplicationsCount() {
        return applicationsCount;
    }

    public void setApplicationsCount(int applicationsCount) {
        this.applicationsCount = applicationsCount;
    }
    
}
