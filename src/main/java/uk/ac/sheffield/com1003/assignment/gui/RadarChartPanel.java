package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChart;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChartPanel;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractPlayerDashboardPanel;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Class that implements the radar chart into the gui for the user to see
 */

public class RadarChartPanel extends AbstractRadarChartPanel
{
    public RadarChartPanel(AbstractPlayerDashboardPanel parentPanel, AbstractRadarChart radarPlot) {
        super(parentPanel, radarPlot);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO remove code below and implement
        super.paintComponent(g);
        Dimension d = getSize();
        Graphics2D g2 = (Graphics2D) g;

        Line2D l = new Line2D.Double(
                0,
                0,
                d.width,
                d.height
        );
        
        Polygon p = new Polygon();
        for (int j = 1; j <6; j++ ) {
        	
            for (int i = 0; i < 5; i++) {
            	p.addPoint((int) (400 + 50 * Math.cos(i * 2 * Math.PI / 5)),
                        (int) (300 + 50* Math.sin(i* 2 * Math.PI / 5)));
            	
            }
            g.drawPolygon(p);     
        }


        

        System.out.println("getHeight(): " + getHeight());

    }



}

