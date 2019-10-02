import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.StringJoiner;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRasterer {
    private static final double DOUBLE_THRESHOLD = 0.000000001;
    private static DecimalFormat df2 = new DecimalFormat(".#########");
    private static final String PARAMS_FILE = "raster_params.txt";
    private static final String RESULTS_FILE = "raster_results.txt";
    private static final int NUM_TESTS = 8;
    private static Rasterer rasterer;


    @Before
    public void setUp() throws Exception {
        rasterer = new Rasterer();
    }

    @Test
    public void testGetMapRaster() throws Exception {
        test6();
        /*
        List<Map<String, Double>> testParams = paramsFromFile();
        List<Map<String, Object>> expectedResults = resultsFromFile();

        for (int i = 0; i < NUM_TESTS; i++) {
            System.out.println(String.format("Running test: %d", i));
            Map<String, Double> params = testParams.get(i);
            Map<String, Object> expected = expectedResults.get(i);
            Map<String, Object> actual = rasterer.getMapRaster(params);

            System.out.println(mapToString(expected));
            String msg = "Your results did not match the expected results for input "
                         + mapToString(params) + ".\n";
            checkParamsMap(msg, expected, actual);
        }

        test4();
        test5();
        */


    }

    private void test5(){

        System.out.println("Test 5:");
        Map<String, Object> test5res = new HashMap<>();

        /*{raster_ul_lon=-122.2998046875, depth=1, raster_lr_lon=-122.2119140625,
        raster_lr_lat=37.82280243352756, render_grid=[[d1_x0_y0.png, d1_x1_y0.png],
        [d1_x0_y1.png, d1_x1_y1.png]], raster_ul_lat=37.892195547244356, query_success=true}*/

        String[][] temp = new String[][]{{"d1_x0_y0.png", "d1_x1_y0.png"},
                {"d1_x0_y1.png", "d1_x1_y1.png"}};
        test5res.put("depth", 1);
        test5res.put("raster_ul_lon", -122.2998046875);
        test5res.put("raster_lr_lon", -122.2119140625);
        test5res.put("raster_ul_lat", 37.892195547244356);
        test5res.put("raster_lr_lat", 37.82280243352756);
        test5res.put("query_success", true);
        test5res.put("render_grid",temp);

        Map<String, Double> test5 = new HashMap<>();
        /*{lrlon=-122.20908713544797, ullon=-122.3027284165759,
         w=305.0, h=300.0, ullat=37.88708748276975, lrlat=37.848731523430196}
         */
        test5.put("lrlon",-122.20908713544797);
        test5.put("ullon",-122.3027284165759);
        test5.put("w",305.0);
        test5.put("h",300.0);
        test5.put("ullat",37.88708748276975);
        test5.put("lrlat",37.848731523430196);

        Map<String, Object> test5act = rasterer.getMapRaster(test5);

        System.out.println(mapToString(test5act));
        String msg = "Your results did not match the expected results for input "
                + mapToString(test5) + ".\n";
        checkParamsMap(msg, test5act, test5res);
    }

    private void test4(){

        System.out.println("Test 4:");
        Map<String, Object> test4res = new HashMap<>();

        /*{raster_ul_lon=-122.24212646484375, depth=7, raster_lr_lon=-122.24006652832031,
                raster_lr_lat=37.87538940251607,
                render_grid=[[d7_x84_y28.png, d7_x85_y28.png, d7_x86_y28.png],
            [d7_x84_y29.png, d7_x85_y29.png, d7_x86_y29.png],
            [d7_x84_y30.png, d7_x85_y30.png, d7_x86_y30.png]],
            raster_ul_lat=37.87701580361881, query_success=true}*/

        String[][] temp = new String[][]{{"d7_x84_y28.png", "d7_x85_y28.png","d7_x86_y28.png"},
                {"d7_x84_y29.png", "d7_x85_y29.png","d7_x86_y29.png"},
                {"d7_x84_y30.png", "d7_x85_y30.png","d7_x86_y30.png"}};
        test4res.put("depth", 7);
        test4res.put("raster_ul_lon", -122.24212646484375);
        test4res.put("raster_lr_lon", -122.24006652832031);
        test4res.put("raster_ul_lat", 37.87701580361881);
        test4res.put("raster_lr_lat", 37.87538940251607);
        test4res.put("query_success", true);
        test4res.put("render_grid",temp);

        Map<String, Double> test4 = new HashMap<>();
        /*{lrlon=-122.24053369025242, ullon=-122.24163047377972,
        w=892.0, h=875.0, ullat=37.87655856892288, lrlat=37.87548268822065}
         */
        test4.put("lrlon",-122.24053369025242);
        test4.put("ullon",-122.24163047377972);
        test4.put("w",892.0);
        test4.put("h",875.0);
        test4.put("ullat",37.87655856892288);
        test4.put("lrlat",37.87548268822065);

        Map<String, Object> test4act = rasterer.getMapRaster(test4);

        System.out.println(mapToString(test4act));
        String msg = "Your results did not match the expected results for input "
                + mapToString(test4) + ".\n";
        checkParamsMap(msg, test4act, test4res);
    }

    private void test6(){

        System.out.println("Test 6:");
        Map<String, Object> test6res = new HashMap<>();

        /*{{raster_ul_lon=-122.2998046875, depth=2, raster_lr_lon=-122.2119140625,
        raster_lr_lat=37.82280243352756,
        render_grid=[[d2_x0_y1.png, d2_x1_y1.png, d2_x2_y1.png, d2_x3_y1.png],
        [d2_x0_y2.png, d2_x1_y2.png, d2_x2_y2.png, d2_x3_y2.png],
        [d2_x0_y3.png, d2_x1_y3.png, d2_x2_y3.png, d2_x3_y3.png]],
        raster_ul_lat=37.87484726881516, query_success=true}*/

        String[][] temp = new String[][]{{"d2_x0_y1.png", "d2_x1_y1.png","d2_x2_y1.png","d2_x3_y1.png"},
                {"d2_x0_y2.png", "d2_x1_y2.png","d2_x2_y2.png","d2_x3_y2.png"},
                {"d2_x0_y3.png", "d2_x1_y3.png","d2_x2_y3.png","d2_x3_y3.png"}};
        test6res.put("depth", 2);
        test6res.put("raster_ul_lon", -122.2998046875);
        test6res.put("raster_lr_lon", -122.2119140625);
        test6res.put("raster_ul_lat", 37.87484726881516);
        test6res.put("raster_lr_lat", 37.82280243352756);
        test6res.put("query_success", true);
        test6res.put("render_grid",temp);

        Map<String, Double> test6 = new HashMap<>();
        /*{lrlon=-122.2104604264636, ullon=-122.30410170759153,
         w=1091.0, h=566.0, ullat=37.870213571328854, lrlat=37.8318576119893}
         */
        test6.put("lrlon",-122.2104604264636);
        test6.put("ullon",-122.30410170759153);
        test6.put("w",1091.0);
        test6.put("h",566.0);
        test6.put("ullat",37.870213571328854);
        test6.put("lrlat",37.8318576119893);

        Map<String, Object> test6act = rasterer.getMapRaster(test6);

        System.out.println(mapToString(test6act));
        String msg = "Your results did not match the expected results for input "
                + mapToString(test6) + ".\n";
        checkParamsMap(msg, test6res, test6act);
    }

    private List<Map<String, Double>> paramsFromFile() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(PARAMS_FILE), Charset.defaultCharset());
        List<Map<String, Double>> testParams = new ArrayList<>();
        int lineIdx = 2; // ignore comment lines
        for (int i = 0; i < NUM_TESTS; i++) {
            Map<String, Double> params = new HashMap<>();
            params.put("ullon", Double.parseDouble(lines.get(lineIdx)));
            params.put("ullat", Double.parseDouble(lines.get(lineIdx + 1)));
            params.put("lrlon", Double.parseDouble(lines.get(lineIdx + 2)));
            params.put("lrlat", Double.parseDouble(lines.get(lineIdx + 3)));
            params.put("w", Double.parseDouble(lines.get(lineIdx + 4)));
            params.put("h", Double.parseDouble(lines.get(lineIdx + 5)));
            testParams.add(params);
            lineIdx += 6;
        }
        return testParams;
    }

    private List<Map<String, Object>> resultsFromFile() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(RESULTS_FILE), Charset.defaultCharset());
        List<Map<String, Object>> expected = new ArrayList<>();
        int lineIdx = 4; // ignore comment lines
        for (int i = 0; i < NUM_TESTS; i++) {
            Map<String, Object> results = new HashMap<>();
            results.put("raster_ul_lon", Double.parseDouble(lines.get(lineIdx)));
            results.put("raster_ul_lat", Double.parseDouble(lines.get(lineIdx + 1)));
            results.put("raster_lr_lon", Double.parseDouble(lines.get(lineIdx + 2)));
            results.put("raster_lr_lat", Double.parseDouble(lines.get(lineIdx + 3)));
            results.put("depth", Integer.parseInt(lines.get(lineIdx + 4)));
            results.put("query_success", Boolean.parseBoolean(lines.get(lineIdx + 5)));
            lineIdx += 6;
            String[] dimensions = lines.get(lineIdx).split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            lineIdx += 1;
            String[][] grid = new String[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = lines.get(lineIdx);
                    lineIdx++;
                }
            }
            results.put("render_grid", grid);
            expected.add(results);
        }
        return expected;
    }

    private void checkParamsMap(String err, Map<String, Object> expected,
                                            Map<String, Object> actual) {
        for (String key : expected.keySet()) {
            assertTrue(err + "Your results map is missing "
                       + key, actual.containsKey(key));
            Object o1 = expected.get(key);
            Object o2 = actual.get(key);

            if (o1 instanceof Double) {
                String errMsg = genDiffErrMsg(err, expected, actual);
                assertTrue(errMsg, Math.abs((Double) o1 - (Double) o2) < DOUBLE_THRESHOLD);
            } else if (o1 instanceof String[][]) {
                String errMsg = genDiffErrMsg(err, expected, actual);
                assertArrayEquals(errMsg, (String[][]) o1, (String[][]) o2);
            } else {
                String errMsg = genDiffErrMsg(err, expected, actual);
                assertEquals(errMsg, o1, o2);
            }
        }
    }

    /** Generates an actual/expected message from a base message, an actual map,
     *  and an expected map.
     */
    private String genDiffErrMsg(String basemsg, Map<String, Object> expected,
                                 Map<String, Object> actual) {
        return basemsg + "Expected: " + mapToString(expected) + ", but got\n"
                       + "Actual  : " + mapToString(actual);
    }

    /** Converts a Rasterer input or output map to its string representation. */
    private String mapToString(Map<String, ?> m) {
        StringJoiner sj = new StringJoiner(", ", "{", "}");

        List<String> keys = new ArrayList<>();
        keys.addAll(m.keySet());
        Collections.sort(keys);

        for (String k : keys) {

            StringBuilder sb = new StringBuilder();
            sb.append(k);
            sb.append("=");
            Object v = m.get(k);

            if (v instanceof String[][]) {
                sb.append(Arrays.deepToString((String[][]) v));
            } else if (v instanceof Double) {
                sb.append(df2.format(v));
            } else {
                sb.append(v.toString());
            }
            String thisEntry = sb.toString();

            sj.add(thisEntry);
        }

        return sj.toString();
    }

}
