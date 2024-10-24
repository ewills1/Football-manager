package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.PlayerCatalog;
import uk.ac.sheffield.com1003.assignment.codeprovided.AbstractPlayerCatalog;
import uk.ac.sheffield.com1003.assignment.codeprovided.League;
import uk.ac.sheffield.com1003.assignment.codeprovided.PlayerEntry;
import uk.ac.sheffield.com1003.assignment.codeprovided.PlayerProperty;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChart;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.RadarAxisValues;

import java.util.*;

/**
 * Class that plots axis points and properties for the radar chart
 */

public class RadarChart extends AbstractRadarChart
{
    public RadarChart(AbstractPlayerCatalog playerCatalog, List<PlayerEntry> filteredPlayerEntriesList,
                      List<PlayerProperty> playerRadarChartProperties)
    {
        super(playerCatalog, filteredPlayerEntriesList, playerRadarChartProperties);
    }

    @Override
    public void updateRadarChartContents(List<PlayerProperty> radarChartPlayerProperties,
                                         List<PlayerEntry> filteredPlayerEntriesList) {
        // TODO implement
    }

    @Override
    public List<PlayerProperty> getPlayerRadarChartProperties() throws NoSuchElementException {
        // TODO implement
        return null;
    }

    @Override
    public Map<PlayerProperty, RadarAxisValues> getRadarPlotAxesValues() throws NoSuchElementException {
        // TODO implement
        return null;
    }

    @Override
    public AbstractPlayerCatalog getPlayerCatalog() {
        PlayerCatalog p = new PlayerCatalog(null, null);
        return p;
    }

    @Override
    public List<PlayerEntry> getFilteredPlayerEntries() {
        // TODO implement
        return null;
    }

}

