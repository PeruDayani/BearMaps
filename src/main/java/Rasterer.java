//import sun.nio.cs.ext.MacHebrew;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    //Constants:
    private static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;

    //Helpers:
    private int divisions;
    private double latDiv;
    private double lonDiv;
    private int zoom;

    //Return values:
    private char prefix;
    private double rasterullon;
    private double rasterlrlon;
    private double rasterullat;
    private double rasterlrlat;
    private boolean querysuccess;
    private String[][] rendergrid;

    //Query Inputs
    private double qlrlon;
    private double qullon;
    private double qullat;
    private double qlrlat;
    private double qw;
    private double qLonDPP;

    public Rasterer() {
        rasterullon = 0;
        rasterlrlon = 0;
        rasterullat = 0;
        qlrlat = 0;
        rasterlrlat = 0;
        rendergrid = null;
        prefix = '0';
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     * <li>The tiles collected must cover the most longitudinal distance per pixel
     * (LonDPP) possible, while still covering less than or equal to the amount of
     * longitudinal distance per pixel in the query box for the user viewport size. </li>
     * <li>Contains all tiles that intersect the query bounding box that fulfill the
     * above condition.</li>
     * <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        qlrlon = params.get("lrlon");
        qullon = params.get("ullon");
        qullat = params.get("ullat");
        qlrlat = params.get("lrlat");
        qw = params.get("w");

        querysuccess = check();

        qLonDPP = (qlrlon - qullon) / qw;
        zoom = setPrefix();
        prefix = (char) zoom;
        divisions = getDivisions();

        assignData();

        return setData();
    }

    private boolean check() {
        //System.out.println(qullat + " : <" + ROOT_ULLAT);
        //System.out.println(qullon + " : <" + ROOT_ULLON);
        //System.out.println(qlrlat + " : >" + ROOT_LRLAT);
        //System.out.println(qlrlon + " : >" + ROOT_LRLON);
        if (qullat > ROOT_ULLAT) {
            qullat = ROOT_ULLAT;
        }
        if (qullon < ROOT_ULLON) {
            qullon = ROOT_ULLON;
        }
        if (qlrlat < ROOT_LRLAT) {
            qlrlat = ROOT_LRLAT;
        }
        if (qlrlon > ROOT_LRLON) {
            qlrlon = ROOT_LRLON;
        }

        if (qlrlon < ROOT_ULLON || qlrlat > ROOT_ULLAT
                || qullon > ROOT_LRLON || qullat < ROOT_LRLAT) {
            return false;
        }

        return true;
    }

    private int getDivisions() {
        int div = (int) Math.pow(2.0, zoom);

        latDiv = (ROOT_ULLAT - ROOT_LRLAT) / div;
        lonDiv = (ROOT_LRLON - ROOT_ULLON) / div;

        return div;
    }

    private int setPrefix() {
        double[] lonDPP = new double[8];
        lonDPP[1] = 0.000171661376953125;
        for (int j = 2; j < 8; j += 1) {
            lonDPP[j] = lonDPP[j - 1] / 2;
        }

        int z = 1;
        while (z < 7) {
            if (lonDPP[z] < qLonDPP) {
                return z;
            }
            z += 1;
        }
        return z;
    }

    private void assignData() {

        //System.out.println("Divisons: "+divisions);
        //System.out.println("Zoom(final): " + zoom);
        //System.out.println("lat_Div: "+ lat_Div);
        //System.out.println("lon_Div: "+ lon_Div);

        int ulnumberlatDivs = (int) Math.ceil((qullat - ROOT_LRLAT) / latDiv);
        //System.out.println("ul_number_latDivs: "+ul_number_latDivs);
        int ulnumberlonDivs = (int) Math.floor((qullon - ROOT_ULLON) / lonDiv);
        //System.out.println("ul_number_lonDivs: "+ul_number_lonDivs);
        int lrnumberlatDivs = (int) Math.floor((qlrlat - ROOT_LRLAT) / latDiv);
        //System.out.println("lr_number_latDivs: "+lr_number_latDivs);
        int lrnumberlonDivs = (int) Math.ceil((qlrlon - ROOT_ULLON) / lonDiv);
        //System.out.println("lr_number_lonDivs: "+lr_number_lonDivs);

        rasterullat = ROOT_LRLAT + (ulnumberlatDivs * latDiv);
        rasterullon = ROOT_ULLON + (ulnumberlonDivs * lonDiv);

        rasterlrlat = ROOT_LRLAT + (lrnumberlatDivs * latDiv);
        rasterlrlon = ROOT_ULLON + (lrnumberlonDivs * lonDiv);

        int xstart = ulnumberlonDivs;
        //System.out.println("x_start: " +x_start);
        int xend = lrnumberlonDivs;
        //System.out.println("x_end: "+x_end);
        int ystart = divisions - ulnumberlatDivs;
        //System.out.println("y_start: "+y_start);
        int yend = divisions - lrnumberlatDivs;
        //System.out.println("y_end: "+y_end);

        int xdim = xend - xstart;
        //System.out.println("x_dim: "+x_dim);
        int ydim = yend - ystart;
        //System.out.println("y_dim: "+y_dim);

        rendergrid = new String[ydim][xdim];
        for (int i = 0; i < ydim; i++) {
            for (int j = 0; j < xdim; j++) {
                rendergrid[i][j] = convertCoord(j + xstart, i + ystart);
            }
        }

    }

    private String convertCoord(int x, int y) {
        return "d" + (int) zoom + "_x" + x + "_y" + y + ".png";
    }

    private Map<String, Object> setData() {
        Map<String, Object> results = new HashMap<>();

        results.put("depth", (int) zoom);
        results.put("raster_ul_lon", rasterullon);
        results.put("raster_lr_lon", rasterlrlon);
        results.put("raster_ul_lat", rasterullat);
        results.put("raster_lr_lat", rasterlrlat);
        results.put("query_success", querysuccess);
        results.put("render_grid", rendergrid);

        return results;
    }

}
