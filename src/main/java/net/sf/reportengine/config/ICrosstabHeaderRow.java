/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public interface ICrosstabHeaderRow {
	
	
	
	//public int getGroupingLevel();
	
	/**
	 * this method returns the value for the current data row (taken from newRowEvent)
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
}
