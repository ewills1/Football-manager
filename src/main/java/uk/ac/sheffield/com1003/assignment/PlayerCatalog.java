package uk.ac.sheffield.com1003.assignment;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;

import java.util.*;

/**
 * Class that creates a player catalog of both leagues that can be used to find averages, minimums, maximums etc
 */
public class PlayerCatalog extends AbstractPlayerCatalog
{
    /**
     * Constructor
     */
    public PlayerCatalog(String eplFilename, String ligaFilename) {
        super(eplFilename, ligaFilename);
    }

    @Override
    public PlayerPropertyMap parsePlayerEntryLine(String line) throws IllegalArgumentException
    {
    	PlayerPropertyMap parsedPlayerMap = new PlayerPropertyMap();
    	
    	//Create array with enum types of player property
    	PlayerProperty properties[] = PlayerProperty.values();
    	
    	//Simplify the string input to avoid errors and place each value separately in an array
    	line.replaceAll(" ", "");
    	String values[] = line.split(",");
 
    	
    	try {
    		if (values.length == 0) {
    			throw new IllegalArgumentException("This line is empty, try again");
    		}
    		if (values.length!=29) {
    			throw new IllegalArgumentException("The number of columns is not correct, try again");
    		}
    		//First, add the player details from the array into the map
    		parsedPlayerMap.putDetail(PlayerDetail.PLAYER, values[0]);
    		parsedPlayerMap.putDetail(PlayerDetail.NATION, values[1]);
    		parsedPlayerMap.putDetail(PlayerDetail.POSITION, values[2]);
    		parsedPlayerMap.putDetail(PlayerDetail.TEAM, values[3]);
    		
        	//Start at 4 to avoid the player details 
        	int counter = 4;
        	
    		//iterate through the double values and place them in the required position 
    		while ((values.length > 0) && (counter<29)) {
    			for (PlayerProperty property : properties) {
    	   			parsedPlayerMap.put(property, Double.parseDouble(values[counter]));
    	   			counter++;
    	   			}
				}       
    	}finally {
    		
    	}return parsedPlayerMap;
    }

    @Override
    public void updatePlayerCatalog() {
        // Use the existing playerEntriesMap to create a new row for all leagues
    	List<PlayerEntry> allPlayers = new ArrayList<>();
    	allPlayers.addAll(playerEntriesMap.get(League.EPL));
    	allPlayers.addAll(playerEntriesMap.get(League.LIGA));
    	
        playerEntriesMap.put(League.ALL, allPlayers);
    }

    @Override
    public double getMinimumValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList)
            throws NoSuchElementException {
        if (playerProperty != null) {
        	List<Double> propertiesList = new ArrayList<>();
        	
        	//Create a list of all the values for the required property
        	for (PlayerEntry player : playerEntryList) {
        		propertiesList.add(player.getProperty(playerProperty));
        	}
        	
        	//use the existing method to find and return the minimum value
        	return Collections.min(propertiesList);
        }
        else throw new NoSuchElementException ("This property does not exist.");
    }

    @Override
    public double getMaximumValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList)
            throws NoSuchElementException {
    	if (playerProperty != null) {
    		List<Double> propertiesList = new ArrayList<>();
    		
    		//Create a list of all the values for the required property
    		for (PlayerEntry player : playerEntryList) {
    			propertiesList.add(player.getProperty(playerProperty));
    		}
    		
    		//use the existing method to find and return the maximum value
    		return Collections.max(propertiesList);
    	}
    	else throw new NoSuchElementException("This property does not exist");
    }

    @Override
    public double getMeanAverageValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList)
            throws NoSuchElementException {
    	
    	double totalValues = 0;
    	int counter = 0;
    	
    	if (playerProperty != null) {
    		List<Double> propertiesList = new ArrayList<>();
    		
    		//Create a list of all the values for the required property
    		for (PlayerEntry player : playerEntryList) {
    			propertiesList.add(player.getProperty(playerProperty));
    			counter++;
    		}
    		
    		//Add up all the values within a property type to find the total values
    		for (Double value : propertiesList) {
    			totalValues += value;
    		}
    		
    		//Find the average by dividing by the number of values
    		double average = (totalValues/counter);
    		double roundedAverage = Math.round(average);
    		
    		return roundedAverage;
    	}
    	else throw new NoSuchElementException("This property does not exist");
    }
    

    @Override
    public List<PlayerEntry> getFirstFivePlayerEntries(League type)
    {
        // Create a new arraylist with all player entries in the required league
    	List<PlayerEntry> playerList = getPlayerEntriesList(type);
    	List<PlayerEntry> firstFiveEntries = new ArrayList<>();
    	
    	for (int i=0; i<5; i++) {
    		firstFiveEntries.add(i, playerList.get(i));
    	}
        return firstFiveEntries;
    }

}
