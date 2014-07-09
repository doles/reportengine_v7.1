/**
 * 
 */
package net.sf.reportengine.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.sf.reportengine.config.ICrosstabHeaderRow;

/**
 * holder class for all distinct values found while parsing the header rows  
 * The main functionality if given by a hashmap having a string as a key and a HeaderDistinctValuesDescriptor as value.
 * This hashmap will hold the distinct values occuring the in the header like: 
 * 
 * 	key="0" 	distinctValues="North, South, Eash, West"
 * 	key="1"		distinctValues="Male, Female" 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com) 
 * @since 0.4
 */
public class DistinctValuesHolder implements IDistinctValuesHolder{
	
	/**
	 * a hashmap containing distinct values for each column declared to take part in the header
	 * 
	 * Ex: if the Religion and Sex are the columns to be displayed in the header of the 
	 * cross tab report then this hashmap will look like: 
	 * 
	 *  key= 0, 	value = "Orthodox,Catholic,Muslim"
	 *  key= 1, 	value="Male, Female"
	 */
	private HashMap<Integer, DistinctValuesRow> distinctValuesMap; 
	
	
	/**
	 * 
	 * @param headerRows
	 * @deprecated
	 */
	public DistinctValuesHolder(ICrosstabHeaderRow[] headerRows){
		this(Arrays.asList(headerRows)); 
	}
	
	/**
	 * 
	 * @param headerRows
	 */
	public DistinctValuesHolder(List<ICrosstabHeaderRow> headerRows){
		distinctValuesMap = new HashMap<Integer, DistinctValuesRow>(headerRows.size()); 
		for (int i = 0; i < headerRows.size(); i++) {
			distinctValuesMap.put(i, new DistinctValuesRow());
		}
	}
	
	/**
	 * adds a new value in the list of distinct values
	 * 
	 * @param headerRowId
	 * @param value
	 * @return the index of the new value into the array of distinct values
	 */
	public int addValueIfNotExist(int headerGroupingLevel, Object value){
		DistinctValuesRow distinctValuesDescr = distinctValuesMap.get(headerGroupingLevel);
		if(distinctValuesDescr == null){
			throw new IllegalArgumentException("header row "+headerGroupingLevel+" could not be found");
		}
		return distinctValuesDescr.addDistinctValueIfNotExists(value);
	}
	
	/**
	 * returns the index of the specified value in the array of distinct values for the given level
	 * 
	 * @param headerGroupingLevel
	 * @param value
	 * @return
	 */
	public int getIndexFor(int headerGroupingLevel, Object value){
		DistinctValuesRow distinctValuesDescr = distinctValuesMap.get(headerGroupingLevel);
		if(distinctValuesDescr == null){
			throw new IllegalArgumentException("header row "+headerGroupingLevel+
					" could not be found. Your crosstab has only "+distinctValuesMap.size()+
					" levels available");
		}
		return distinctValuesDescr.getDistinctValues().indexOf(value); 
	}
	
	/**
	 * 
	 * @param level
	 * @return
	 */
	public List<Object> getDistinctValuesForLevel(int level){
		List<Object> result = null ;
		DistinctValuesRow headerDistValDescr = distinctValuesMap.get(level);
		if(headerDistValDescr != null){
			result = headerDistValDescr.getDistinctValues();
		}
		return result; 
	}
	
	
	/**
	 * 
	 * @param level
	 * @return
	 */
	public int getDistinctValuesCountForLevel(int level){
		int result = -1;
		DistinctValuesRow headerDistValDescr = distinctValuesMap.get(level);
		if(headerDistValDescr != null){
			result = headerDistValDescr.getDistinctValues().size();
		}
		return result; 
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getRowsCount(){
		return distinctValuesMap.size(); 
	}
	
	
	/**
	 * 
	 */
	public String toString(){
		StringBuffer result = new StringBuffer(); 
		result.append("CrosstabMetadata[");
		result.append(distinctValuesMap.toString());
		result.append("]");
		return result.toString();
	}
}
