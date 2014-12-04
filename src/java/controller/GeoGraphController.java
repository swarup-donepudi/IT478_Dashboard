/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GeoLocationDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.GeoLocationGraphBean;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author skdonep
 */
@ManagedBean(name = "geoGraphController")
@SessionScoped
public class GeoGraphController implements Serializable {

    private ArrayList<GeoLocationGraphBean> graphEntrees;
    private PieChartModel geoLocationGraph;
    private int geoYear;
    private int yearwiseBegin;
    private int yearwiseEnd;

    /**
     * Creates a new instance of GeoGraphController
     */
    public GeoGraphController() {
        this.graphEntrees = new ArrayList<>();
        this.geoLocationGraph = new PieChartModel();
    }

    public ArrayList<GeoLocationGraphBean> getGraphEntrees() {
        return graphEntrees;
    }

    public void setGraphEntrees(ArrayList<GeoLocationGraphBean> graphEntrees) {
        this.graphEntrees = graphEntrees;
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

    public PieChartModel getGeoLocationGraph() {
        return geoLocationGraph;
    }

    public void setGeoLocationGraph(PieChartModel geoLocationGraph) {
        this.geoLocationGraph = geoLocationGraph;
    }

    public int getGeoYear() {
        return geoYear;
    }

    public void setGeoYear(int geoYear) {
        this.geoYear = geoYear;
    }

    public void loadDefaulteGeoLocationGraph() throws SQLException {
        this.fetchGeoGraphEntrees(2014);
        for (GeoLocationGraphBean graphEntree : this.graphEntrees) {
            geoLocationGraph.set(graphEntree.getCountry(), graphEntree.getApplicationsCount());
        }
    }

    public void rebuildGeoLocationGraph() throws SQLException, IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        this.graphEntrees = new ArrayList<>();
        int graphEntreesCount = 0;
        this.geoLocationGraph = new PieChartModel();
        this.fetchGeoGraphEntrees(this.geoYear);
        for (GeoLocationGraphBean graphEntree : this.graphEntrees) {
            graphEntreesCount += 1;
            this.geoLocationGraph.set(graphEntree.getCountry(), graphEntree.getApplicationsCount());
        }
        if (graphEntreesCount == 0) {
            this.geoLocationGraph.clear();
        }
        externalContext.redirect("GeoLocationTrends.xhtml");
    }

    private void fetchGeoGraphEntrees(int geoYear) throws SQLException {
        GeoLocationDAO geoLocationDB = new GeoLocationDAO();
        geoLocationDB.fetchGraphEntreesFromDB(this.graphEntrees, geoYear);
    }

}
