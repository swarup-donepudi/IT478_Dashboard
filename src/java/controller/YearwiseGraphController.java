/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.YearwiseDAO;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.YearwiseGraphBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.HorizontalBarChartModel ;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author skdonep
 */
@Named(value = "yearwiseGraphController")
@SessionScoped
public class YearwiseGraphController implements Serializable {

    private HorizontalBarChartModel  yearwiseGraph;
    private int yearwiseBegin;
    private int yearwiseEnd;
    private ArrayList<YearwiseGraphBean> yearwiseGraphEntrees;

    /**
     * Creates a new instance of YearwiseGraphController
     */
    public YearwiseGraphController() {
    }

    public int getYearwiseBegin() {
        return yearwiseBegin;
    }

    public void setYearwiseBegin(int yearwiseBegin) {
        this.yearwiseBegin = yearwiseBegin;
    }

    public int getYearwiseEnd() {
        return yearwiseEnd;
    }

    public void setYearwiseEnd(int yearwiseEnd) {
        this.yearwiseEnd = yearwiseEnd;
    }

    public ArrayList<YearwiseGraphBean> getYearwiseGraphEntrees() {
        return yearwiseGraphEntrees;
    }

    public void setYearwiseGraphEntrees(ArrayList<YearwiseGraphBean> yearwiseGraphEntrees) {
        this.yearwiseGraphEntrees = yearwiseGraphEntrees;
    }

    public HorizontalBarChartModel  getYearwiseGraph() {
        return yearwiseGraph;
    }

    public void setYearwiseGraph(HorizontalBarChartModel   yearwiseGraph) {
        this.yearwiseGraph = yearwiseGraph;
    }

    public ArrayList<YearwiseGraphBean> yearwiseGraphEntrees() {
        return yearwiseGraphEntrees;
    }

    public void setGraphEntrees(ArrayList<YearwiseGraphBean> yearwiseGraphEntrees) {
        this.yearwiseGraphEntrees = yearwiseGraphEntrees;
    }

    public void loadDefaultYearwiseGraph() throws IOException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        this.yearwiseGraphEntrees = new ArrayList<>();
        this.yearwiseGraph = new HorizontalBarChartModel ();
        this.fetchYearwiseGraphEntrees(this.yearwiseBegin, this.yearwiseEnd);
        ChartSeries India = new ChartSeries();
        India.setLabel("China");
        India.set("2004", 60);
        India.set("2005", 100);
        India.set("2006", 44);
        India.set("2007", 150);
        India.set("2008", 25);

        ChartSeries China = new ChartSeries();
        China.setLabel("India");
        China.set("2004", 62);
        China.set("2005", 60);
        China.set("2006", 110);
        China.set("2007", 135);
        China.set("2008", 120);

        this.yearwiseGraph.addSeries(India);
        this.yearwiseGraph.addSeries(China);
        

        yearwiseGraph.setTitle("Applications TrendsYearwise");
        yearwiseGraph.setLegendPosition("e");
        yearwiseGraph.setStacked(true);
         
        Axis xAxis = yearwiseGraph.getAxis(AxisType.X);
        xAxis.setLabel("Applications");
        xAxis.setMin(0);
        xAxis.setMax(200);
         
        Axis yAxis = yearwiseGraph.getAxis(AxisType.Y);
        yAxis.setLabel("Year"); 
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    public void rebuildYearwiseGraph() throws IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        this.yearwiseGraphEntrees = new ArrayList<>();
        int graphEntrees = 0;
        this.yearwiseGraph = new HorizontalBarChartModel ();
        this.fetchYearwiseGraphEntrees(this.yearwiseBegin, this.yearwiseEnd);
        ChartSeries India = new ChartSeries();
        India.setLabel("China");
        India.set("2004", 120);
        India.set("2005", 100);
        India.set("2006", 44);
        India.set("2007", 150);
        India.set("2008", 25);

        ChartSeries China = new ChartSeries();
        China.setLabel("India");
        China.set("2004", 52);
        China.set("2005", 60);
        China.set("2006", 110);
        China.set("2007", 135);
        China.set("2008", 120);

        this.yearwiseGraph.addSeries(India);
        this.yearwiseGraph.addSeries(China);
        yearwiseGraph.setTitle("Applications TrendsYearwise");
        yearwiseGraph.setLegendPosition("e");
        yearwiseGraph.setStacked(true);
         
        Axis xAxis = yearwiseGraph.getAxis(AxisType.X);
        xAxis.setLabel("Applications");
        xAxis.setMin(0);
        xAxis.setMax(200);
         
        Axis yAxis = yearwiseGraph.getAxis(AxisType.Y);
        yAxis.setLabel("Year");
        
        externalContext.redirect("YearwiseTrends.xhtml");
    }

    private void fetchYearwiseGraphEntrees(int beginYear, int endYear) throws SQLException {
        YearwiseDAO geoLocationDB = new YearwiseDAO();
        geoLocationDB.fetchGraphEntreesFromDB(this.yearwiseGraphEntrees, this.yearwiseBegin, this.yearwiseEnd);
    }
}
