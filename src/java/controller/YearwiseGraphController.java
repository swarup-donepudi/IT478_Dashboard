/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.YearwiseDAO;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.YearwiseGraphBean;
import model.YearwiseSelectionBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author skdonep
 */
@Named(value = "yearwiseGraphController")
@SessionScoped
public class YearwiseGraphController implements Serializable {
    private YearwiseSelectionBean ywSelection;
    private HorizontalBarChartModel yearwiseGraph;
    private ArrayList<YearwiseGraphBean> yearwiseGraphEntrees;

    /**
     * Creates a new instance of YearwiseGraphController
     */
    public YearwiseGraphController() {
        this.ywSelection = new YearwiseSelectionBean();
        this.yearwiseGraphEntrees = new ArrayList<>();
        this.yearwiseGraph = new HorizontalBarChartModel();
    }
    
    public ArrayList<YearwiseGraphBean> getYearwiseGraphEntrees() {
        return yearwiseGraphEntrees;
    }

    public void setYearwiseGraphEntrees(ArrayList<YearwiseGraphBean> yearwiseGraphEntrees) {
        this.yearwiseGraphEntrees = yearwiseGraphEntrees;
    }

    public HorizontalBarChartModel getYearwiseGraph() {
        return yearwiseGraph;
    }

    public void setYearwiseGraph(HorizontalBarChartModel yearwiseGraph) {
        this.yearwiseGraph = yearwiseGraph;
    }

    public ArrayList<YearwiseGraphBean> yearwiseGraphEntrees() {
        return yearwiseGraphEntrees;
    }

    public void setGraphEntrees(ArrayList<YearwiseGraphBean> yearwiseGraphEntrees) {
        this.yearwiseGraphEntrees = yearwiseGraphEntrees;
    }

    public YearwiseSelectionBean getYwSelection() {
        return ywSelection;
    }

    public void setYwSelection(YearwiseSelectionBean ywSelection) {
        this.ywSelection = ywSelection;
    }

    public void loadDefaultYearwiseGraph() throws IOException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        this.yearwiseGraphEntrees = new ArrayList<>();
        this.yearwiseGraph = new HorizontalBarChartModel();
        YearwiseDAO yearwiseDB = new YearwiseDAO();
        this.yearwiseGraphEntrees = new ArrayList<>();
        yearwiseDB.fetchGraphEntreesFromDB(this.yearwiseGraphEntrees, 2000, 2015);
        if (this.yearwiseGraphEntrees.size() > 0) {
            this.yearwiseGraph = this.buildYearwiseGraph(this.yearwiseGraphEntrees);
        }
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    public void rebuildYearwiseGraph() throws IOException, SQLException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        this.yearwiseGraphEntrees = new ArrayList<>();
        int beginYear = this.ywSelection.getYearwiseBegin();
        int endYear = this.ywSelection.getYearwiseEnd();
        YearwiseDAO yearwiseDB = new YearwiseDAO();
        yearwiseDB.fetchGraphEntreesFromDB(this.yearwiseGraphEntrees, beginYear, endYear);
        this.yearwiseGraph = this.buildYearwiseGraph(this.yearwiseGraphEntrees);

        externalContext.redirect("YearwiseTrends.xhtml");
    }

    public HorizontalBarChartModel buildYearwiseGraph(ArrayList<YearwiseGraphBean> graphEntrees) {
        HorizontalBarChartModel graph = new HorizontalBarChartModel();
        Set<String> countries = this.getCountriesFromGraphEntrees(graphEntrees);
        Iterator it = countries.iterator();
        it.next();
        while (it.hasNext()) {
            int totalAppCount = 0;
            String countryName = (String) it.next();
            ChartSeries chartSeries = new ChartSeries();
            chartSeries.setLabel(countryName);
            for (YearwiseGraphBean graphEntree : graphEntrees) {
                if (graphEntree.getCountry().equals(countryName)) {
                    int year = graphEntree.getYear();
                    int appCount = graphEntree.getApplicationsCount();
                    chartSeries.set(year, appCount);
                }
            }
            graph.addSeries(chartSeries);
        }

        graph.setTitle("Application Trends Yearwise");
        graph.setLegendPosition("e");
        graph.setStacked(false);

        Axis xAxis = graph.getAxis(AxisType.X);
        xAxis.setLabel("Applications");
        xAxis.setMin(0);
        xAxis.setTickInterval("1");
        xAxis.setMax(10);

        Axis yAxis = graph.getAxis(AxisType.Y);
        yAxis.setLabel("Year");
        return graph;
    }

    public Set<String> getCountriesFromGraphEntrees(ArrayList<YearwiseGraphBean> graphEntrees) {
        int countryCount = 0;
        String[] countries = new String[30];
        for (YearwiseGraphBean graphEntree : graphEntrees) {
            countries[countryCount] = graphEntree.getCountry();
            countryCount++;
        }
        int end = countries.length;
        Set<String> set = new HashSet<>();

        for (int i = 0; i < end; i++) {
            set.add(countries[i]);
        }
        return set;
    }
}
