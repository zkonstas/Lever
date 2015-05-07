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
import java.util.Arrays;
import java.util.logging.Logger;



/**
 * Created by royhermann on 5/7/15.
 */
public class GraphManager {

    public GraphManager(){

    }

    public static void graphLineChartWithTitle(String title, String xTitle, String yTitle, String[] xLabels, String[] yLabels, double[] dataPoints){
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
        AxisLabels xAxis = AxisLabelsFactory.newAxisLabels(Arrays.asList(xLabels)); //labels
        xAxis.setAxisStyle(axisStyle); //style
        AxisLabels xAxis2 = AxisLabelsFactory.newAxisLabels(xTitle, 50.0); //labels
        xAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.BLACK, 14, AxisTextAlignment.CENTER)); //style


        //Y AXIS//
        AxisLabels yAxis = AxisLabelsFactory.newAxisLabels(Arrays.asList(yLabels)); //labels
        yAxis.setAxisStyle(axisStyle); //style
        AxisLabels yAxis2 = AxisLabelsFactory.newAxisLabels(yTitle, 50.0); //title
        yAxis2.setAxisStyle(AxisStyle.newAxisStyle(Color.BLACK, 14, AxisTextAlignment.CENTER)); //style
        yAxis2.setAxisStyle(axisStyle); //style

        // Adding axis info to chart.
        chart.addXAxisLabels(xAxis);
        chart.addXAxisLabels(xAxis2);
        chart.addYAxisLabels(yAxis);
        chart.addYAxisLabels(yAxis2);

        // Defining background and chart fills.
        chart.setBackgroundFill(Fills.newSolidFill(Color.WHITE));
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
