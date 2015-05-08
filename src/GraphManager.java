import com.googlecode.charts4j.*;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Shape;


import javax.imageio.ImageIO;
import javax.swing.*;

import static com.googlecode.charts4j.Color.*;
import static com.googlecode.charts4j.UrlUtil.normalize;
import static org.junit.Assert.assertEquals;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * Created by royhermann on 5/7/15.
 */


//TODO: Add axis range/bounds/scale to match data that is being used

public class GraphManager {

    public GraphManager() {

    }


    public static void createLineChart(String title, String xTitle, String yTitle, double[] dataPoints) {
        createLineChart(title, xTitle, yTitle, null, null, dataPoints);
    }

    public static void createLineChart(String title, String xTitle, String yTitle, String[] xLabels, String[] yLabels, double[] dataPoints) {
        // Defining lines
        final int NUM_POINTS = dataPoints.length;

        Line line1 = Plots.newLine(Data.newData(dataPoints));
        line1.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        line1.addShapeMarkers(Shape.DIAMOND, Color.BLACK, 12);
        line1.addShapeMarkers(Shape.DIAMOND, Color.BLUE, 8);

        // Defining chart.
        LineChart chart = GCharts.newLineChart(line1);
        chart.setSize(600, 450);
        chart.setTitle(title, Color.BLUE, 14);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.BLACK, 12, AxisTextAlignment.CENTER);

        //X AXIS
        if (xLabels != null) {
            AxisLabels xAxis = AxisLabelsFactory.newAxisLabels(Arrays.asList(xLabels)); //labels
            xAxis.setAxisStyle(axisStyle); //style
            chart.addXAxisLabels(xAxis);
        }
        AxisLabels xAxis2 = AxisLabelsFactory.newAxisLabels(xTitle, 50.0); //labels
        xAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.BLACK, 14, AxisTextAlignment.CENTER)); //style


        //Y AXIS//
        if (yLabels != null) {
            AxisLabels yAxis = AxisLabelsFactory.newAxisLabels(Arrays.asList(yLabels)); //labels
            yAxis.setAxisStyle(axisStyle); //style
            chart.addYAxisLabels(yAxis);
        }
        AxisLabels yAxis2 = AxisLabelsFactory.newAxisLabels(yTitle, 50.0); //title
        yAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.BLACK, 14, AxisTextAlignment.CENTER)); //style
        yAxis2.setAxisStyle(axisStyle); //style

        // Adding axis info to chart.


        chart.addXAxisLabels(xAxis2);
        chart.addYAxisLabels(yAxis2);

        // Defining background and chart fills.
        chart.setBackgroundFill(Fills.newSolidFill(Color.WHITE));
        String url = chart.toURLString();
        System.out.println(url); //print url to manually check
        displayImageFromURL(url);

    }

    public void createBarChart(String title, String xTitle, String yTitle, String[] xLabels, String[] yLabels, double[] dataPoints) {
        // EXAMPLE CODE START
        // Defining data plots.
        if (dataPoints.length >= 8) {
            System.out.println("too many data points");
            return;
        }
        Color[] colors = new Color[]{BLUEVIOLET, ORANGERED, LIMEGREEN, ORANGE, RED, BISQUE, PALEGOLDENROD, YELLOWGREEN, BURLYWOOD};


        BarChartPlot team = Plots.newBarChartPlot(Data.newData(dataPoints));
        //color each bar
        for (int i = 0; i < dataPoints.length; i++)
            team.setColor(colors[i], i);


//        BarChartPlot team1 = Plots.newBarChartPlot(Data.newData(25, 43, 12, 30), BLUEVIOLET, "Team A");
//        BarChartPlot team2 = Plots.newBarChartPlot(Data.newData(8, 35, 11, 5), ORANGERED, "Team B");
//        BarChartPlot team3 = Plots.newBarChartPlot(Data.newData(10, 20, 30, 30), LIMEGREEN, "Team C");

        // Instantiating chart.
        BarChart chart = GCharts.newBarChart(team);
        chart.setDataStacked(false);




        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
        AxisLabels xAxisTitle = AxisLabelsFactory.newAxisLabels(xTitle, 50.0);
        xAxisTitle.setAxisStyle(axisStyle);
        AxisLabels yAxisTitle = AxisLabelsFactory.newAxisLabels(yTitle, 50.0);
        yAxisTitle.setAxisStyle(axisStyle);
        AxisLabels yAxisRange = AxisLabelsFactory.newNumericRangeAxisLabels(0,25);



        // Adding axis info to chart.
        chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(xLabels));
        chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(0, 100));
        chart.addYAxisLabels(xAxisTitle);
        chart.addXAxisLabels(yAxisTitle);
        chart.addYAxisLabels(yAxisRange);


        //axis range


        chart.setSize(600, 450);
        chart.setBarWidth(100);
        chart.setSpaceWithinGroupsOfBars(20);
        chart.setDataStacked(true);
        chart.setTitle(title, BLACK, 16);
        chart.setGrid(100, 10, 3, 2);
        chart.setBackgroundFill(Fills.newSolidFill(ALICEBLUE));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, LAVENDER, 100);
        fill.addColorAndOffset(WHITE, 0);
        chart.setAreaFill(fill);
        String url = chart.toURLString();
        displayImageFromURL(url);


    }

    public static void displayImageFromURL(final String url) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                try {
                    String path = url;
                    System.out.println("Get Image from " + path);
                    URL url = new URL(path);
                    BufferedImage image = ImageIO.read(url);
                    System.out.println("Load image into frame...");
                    JLabel label = new JLabel(new ImageIcon(image));
                    JFrame f = new JFrame();
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.getContentPane().add(label);
                    f.pack();
                    f.setLocation(200, 200);
                    f.setVisible(true);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }

            }
        });
    }

}
