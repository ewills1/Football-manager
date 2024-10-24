package uk.ac.sheffield.com1003.assignment;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;
import java.util.*;

/**
 * Class that reads in a list of queries divided up into single tokens and returns a list of parsed queries
 */
public class QueryParser extends AbstractQueryParser
{
    // Default implementation to be provided
    @Override
    public List<Query> readQueries(List<String> queryTokens) throws IllegalArgumentException {
    	
    	List<Query> queriesList = new ArrayList<>();
    	
    	List<String> singleQuery = new ArrayList<>();
    	ListIterator<String> iterator = queryTokens.listIterator();
    	
    	League queryLeague;
    	
    	//push iterator forward by 1 to avoid the first instance of "select"
    	iterator.next();
    	
    	while (iterator.hasNext()) {
    		
    		//create ArrayList for subqueries and clear it
        	List<SubQuery> subQueriesList = new ArrayList<>();
    		singleQuery.clear();
    		
    		
    		//use iterator to create an arraylist that includes the next individual query line 
    		while (iterator.hasNext()) {
    			String nextToken = iterator.next();
    			
    			//"select" signifies the start of the next query, so the loop breaks here
    			if (nextToken.equals("select")) {
    				break;
    			}
    			singleQuery.add(nextToken);
        	}
    	
    		if (singleQuery.size()<4) {
    			throw new IllegalArgumentException("Data entered is not correct");
    		}
    		
    		//determine the league of the query
    		if (singleQuery.indexOf("epl") == -1) {
    			queryLeague = League.LIGA;
    		}else if (singleQuery.indexOf("or")== -1) {
    			queryLeague = League.EPL;
    		}else {
    			queryLeague = League.ALL;
    		}
    		
    		//use index of "where" in the query to find the first player property, operator and integer
    		int whereIndex = singleQuery.indexOf("where");
    		String firstProperty = singleQuery.get(whereIndex+1);
    		String operator = singleQuery.get(whereIndex+2);
    		String value = singleQuery.get(whereIndex+3);
    		Double valueDouble = Double.parseDouble(value);
    		
    		if (SubQuery.isValidOperator(operator)!= true) {
    			throw new IllegalArgumentException("Incorrect Operator in data file");
    		}
    		
    		PlayerProperty property = PlayerProperty.fromName(firstProperty);
    		SubQuery firstQuery = new SubQuery(property, operator, valueDouble);
    		subQueriesList.add(firstQuery);
    		
    		
    		ListIterator<String> nextIterator = singleQuery.listIterator();
    		int andIndex;
    		String findProperty;
    		String nextOperator;
    		String nextValue;
    		Double nextValueDouble;
    		int counter = 0;
    		
    		while (nextIterator.hasNext()) {
    			String token = nextIterator.next();
    			//Find values of "and" in the single query arraylist to add more sub queries
    			if (token.equals("and")) {
    				
    				andIndex = singleQuery.indexOf(token);
    				findProperty = singleQuery.get(andIndex+1+counter);
    				nextOperator = singleQuery.get(andIndex+2+counter);
    				nextValue = singleQuery.get(andIndex+3+counter);
    				nextValueDouble = Double.parseDouble(nextValue);
    				
    				if (SubQuery.isValidOperator(nextOperator)!= true) {
    	    			throw new IllegalArgumentException("Incorrect Operator in data file");
    	    		}
    				
    				PlayerProperty nextProperty = PlayerProperty.fromName(findProperty);
    	    		SubQuery nextQuery = new SubQuery(nextProperty, nextOperator, nextValueDouble);
    	    		subQueriesList.add(nextQuery);
    	    		
    	    		//counter increases by 4 for the next instance of a subquery
    	    		counter += 4;
    	    		
    			}
    		}
    		Query query = new Query(subQueriesList, queryLeague);
    		queriesList.add(query);
    	}
    	  return queriesList;
    }

}

