/**
 * 
 */
package net.sf.reportengine.util;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface IDistinctValuesHolder {
	
	/**
	 * adds a new value in the list of distinct values
	 * 
	 * @param headerRowId
	 * @param value
	 * @return the index of the new value into the array of distinct values
	 */
	public int addValueIfNotExist(int headerGroupingLevel, Object value);
	
	/**
	 * returns the index of the specified value in the array of distinct values for the given level
	 * 
	 * @param headerGroupingLevel
	 * @param value
	 * @return
	 */
	public int getIndexFor(int headerGroupingLevel, Object value);
	
	/**
	 * 
	 * @param level
	 * @return
	 */
	public List<Object> getDistinctValuesForLevel(int level);
	
	
	/**
	 * 
	 * @param level
	 * @return
	 */
	public int getDistinctValuesCountForLevel(int level);
	
	
	/**
	 * 
	 * @return
	 */
	public int getRowsCount();
}
