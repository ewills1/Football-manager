package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.PlayerCatalog;
import uk.ac.sheffield.com1003.assignment.codeprovided.*;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractPlayerDashboardPanel;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

/**
 * Class that populates the gui with combo boxes and text boxes and allows users to filter through the list of players
 * and access their statistics
 */

public class PlayerDashboardPanel extends AbstractPlayerDashboardPanel
{

    // Constructor
    public PlayerDashboardPanel(AbstractPlayerCatalog playerCatalog) {
        super(playerCatalog);
    }

    @Override
    public void populatePlayerDetailsComboBoxes() {
    	
        // Create lists for each player detail
    	List<String> playerNamesList = new ArrayList<>();
    	List<String> playerTeamsList = new ArrayList<>();
    	List<String> playerPositionsList = new ArrayList<>();
    	List<String> playerNationsList = new ArrayList<>();
    	
    	//Create a list that contains all player entries
    	List<PlayerEntry> playersEntries = playerCatalog.getPlayerEntriesList(League.ALL);
    	
    	//Add the details of every player entry to the relevant lists
        for (PlayerEntry player:playersEntries) {
        	playerNamesList.add(player.getPlayerName());
        	
        	//check if the relevant list already contains the detail
        	if (playerTeamsList.contains(player.getTeam())==false) {
        		playerTeamsList.add(player.getTeam());
        	}
        	
        	if (playerPositionsList.contains(player.getPosition())==false) {
        		playerPositionsList.add(player.getPosition());
        	}
        	
        	if (playerNationsList.contains(player.getNation())==false) {
        		playerNationsList.add(player.getNation());
        	}
        	
        	
        }
        
        //Create combo boxes for each player detail and add the relevant details
        DefaultComboBoxModel<String> namesModel = (DefaultComboBoxModel<String>) comboPlayerNames.getModel();
        namesModel.addAll(playerNamesList); 
        DefaultComboBoxModel<String> nationsModel = (DefaultComboBoxModel<String>) comboNations.getModel();
        nationsModel.addAll(playerNationsList);
        DefaultComboBoxModel<String> teamsModel = (DefaultComboBoxModel<String>) comboTeams.getModel();
        teamsModel.addAll(playerTeamsList);
        DefaultComboBoxModel<String> positionsModel = (DefaultComboBoxModel<String>) comboPositions.getModel();
        positionsModel.addAll(playerPositionsList);
        
        executeQuery();
    }

    /**
     * addListeners method - adds relevant actionListeners to the GUI components
     */
    @SuppressWarnings("unused")
    @Override
    public void addListeners() {

        buttonAddFilter.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
            	addFilter();  
            }  
            });  
        
        buttonClearFilters.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                clearFilters();  
            }  
            });
        
        //update the gui whenever a new player name is selected
        comboPlayerNames.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		//find the name selected from the combo box
        		selectedPlayerName = (String) comboPlayerNames.getSelectedItem();
        		
        		filteredPlayerEntriesList = 
        				playerCatalog.getPlayerEntriesList(filteredPlayerEntriesList, PlayerDetail.PLAYER, selectedPlayerName);
        		updateStatistics();
				updatePlayerCatalogDetailsBox();
        	}
        });
        
        comboLeagueTypes.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		//find league selected
        		selectedLeagueType = League.fromName((String) comboLeagueTypes.getSelectedItem());
        		executeQuery();
        			
        	}	
        });
        
        comboNations.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		//find league selected
        		selectedPlayerNation = (String) comboNations.getSelectedItem();
        		filteredPlayerEntriesList = 
        				playerCatalog.getPlayerEntriesList(filteredPlayerEntriesList, PlayerDetail.NATION, selectedPlayerNation);
        		
        		//update relevant text boxes
        		updateStatistics();
				updatePlayerCatalogDetailsBox();
        			
        	}	
        });
        
        comboPositions.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		//find position selected
        		selectedPlayerPosition = (String) comboPositions.getSelectedItem();
        		filteredPlayerEntriesList = 
        				playerCatalog.getPlayerEntriesList(filteredPlayerEntriesList, PlayerDetail.POSITION, selectedPlayerPosition);
        		
        		//update relevant text boxes
        		updateStatistics();
				updatePlayerCatalogDetailsBox();
        			
        	}	
        });
        
        comboTeams.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		//find team selected
        		selectedTeam = (String) comboTeams.getSelectedItem();
        		filteredPlayerEntriesList = 
        				playerCatalog.getPlayerEntriesList(filteredPlayerEntriesList, PlayerDetail.TEAM, selectedTeam);
        		
        		//update relevant text boxes
        		updateStatistics();
				updatePlayerCatalogDetailsBox();
        			
        	}	
        });
    	
    }


	/**
     * clearFilters method - clears all filters from the subQueryList ArrayList and updates
     * the relevant GUI components
     */
    @Override
    public void clearFilters() {
    	
    	//clear list
        subQueryList.clear();
        
        //clear text areas
        subQueriesTextArea.setText("");
        filteredPlayerEntriesTextArea.setText("");
        statisticsTextArea.setText("");
        comboPlayerNames.setToolTipText(null);
        
        executeQuery();
        

    }

    @Override
    public void updateRadarChart() {
        // TODO implement

    }

    /**
     * updateStats method - updates the table with statistics after any changes which may
     * affect the JTable which holds the statistics
     */
    @Override
    public void updateStatistics() {
    	
    	//start by clearing text area
    	statisticsTextArea.setText("");
    	
    	//create an array of all player property values
    	PlayerProperty properties[] = PlayerProperty.values();
    	
    	//list player property values along the top row of the text box
    	statisticsTextArea.append("		");
    	for (int i = 0; i<properties.length; i++) {
    		statisticsTextArea.append(properties[i].getName()+"		");
    	}
    	
    	//Print out minimum values of each property in a row
    	statisticsTextArea.append("\nMinimum:");
    	for (int i = 0; i<properties.length; i++) {
    		
    		//find minimum value of the current property
    		double valueMin = playerCatalog.getMinimumValue(properties[i],filteredPlayerEntriesList);
    		
    		//convert to string
    		String valueMinStr = valueMin+"";
    		
    		//add to text area
    		statisticsTextArea.append("		"+valueMinStr);
    	}
    	
    	statisticsTextArea.append("\nMaximum:");
    	for (int i = 0; i<properties.length; i++) {
    		
    		//find maximum value of current property
    		double valueMax = playerCatalog.getMaximumValue(properties[i],filteredPlayerEntriesList);
    		
    		//convert to string
    		String valueMaxStr = valueMax+"";
    		
    		//add to text area
    		statisticsTextArea.append("		"+valueMaxStr);
    	}
    	
    	statisticsTextArea.append("\nAverage:");
    	for (int i = 0; i<properties.length; i++) {
    		
    		//find average value of current property
    		double valueAverage = playerCatalog.getMeanAverageValue(properties[i],filteredPlayerEntriesList);
    		
    		//convert to string
    		String valueAverageStr = valueAverage+"";
    		
    		//add to text area
    		statisticsTextArea.append("		"+valueAverageStr);
    	}
    	

    }

    /**
     * updatePlayerCatalogDetailsBox method - updates the list of players when changes are made
     */
    @Override
    public void updatePlayerCatalogDetailsBox() {
        
    	// Clear text box
    	filteredPlayerEntriesTextArea.setText("");
    	
    	filteredPlayerEntriesTextArea.append("         				");
    	PlayerProperty[] playerProperties = PlayerProperty.values();
    	for (PlayerProperty property: playerProperties) {
    		filteredPlayerEntriesTextArea.append(property+"		");
    	}
    	
    	//Print out players from filtered list to text box
    	for (PlayerEntry player:filteredPlayerEntriesList) {
    		filteredPlayerEntriesTextArea.append("\n"+player.getPlayerName()+", "+player.getTeam());
    		
    		for (PlayerProperty property: playerProperties) {
        		filteredPlayerEntriesTextArea.append("      		  "+ player.getProperty(property));
        	}
    	}
    }

    /**
     * executeQuery method - applies chosen query to the relevant list
     */
    @Override
    public void executeQuery() {
        // TODO implement
    	Query query = new Query(subQueryList, selectedLeagueType);
    	filteredPlayerEntriesList = query.executeQuery(playerCatalog);
    	
    	updatePlayerCatalogDetailsBox();
        updateStatistics();
    }

    /**
     * addFilters method - adds filters input into GUI to subQueryList ArrayList
     */
    @Override
    public void addFilter() {
    	
    	//Create a SubQuery object from the selected options of the relevant combo boxes
    	String operator = (String) comboOperators.getSelectedItem();
    	PlayerProperty property = PlayerProperty.fromPropertyName((String) comboQueryProperties.getSelectedItem());
    	Double queryValue = Double.parseDouble(value.getText());
    	SubQuery sub = new SubQuery(property, operator, queryValue);
    	
    	if (subQueryList.contains(sub)!= true){
    		subQueryList.add(sub);
    	}
          
        //Clear text area first
        subQueriesTextArea.setText("");
        //Add the subQuery to the text area on the gui
        subQueriesTextArea.append(subQueryList.toString());
        
        executeQuery();
        

    }
    @Override
    public boolean isMinCheckBoxSelected() {
        if (minCheckBox.isSelected()) {
        	return true;
        }else
        	return false;    
    }

    @Override
    public boolean isMaxCheckBoxSelected() {
        if (maxCheckBox.isSelected()) {
        	return true;
        }else
        	return false;
    }

    @Override
    public boolean isAverageCheckBoxSelected() {
        if (averageCheckBox.isSelected()) {
        	return true;
        }else
        	return false;
    }

}
