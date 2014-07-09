/**
 * 
 */
package net.sf.reportengine.filter;


/**
 * interface for data filters
 * 
 * @author dragos balan
 *
 */
public interface DataOutputFilter {
	
	/**
	 * filters the new row of data and returns false if the new row should not be 
	 * taken into account when outputting 
	 * 
	 * @param formattedColumnValues		the formatted values (on which you can filter)
	 * @return							true if the row should be output, false if not output needed
	 */
	public boolean isDisplayable(String[] formattedColumnValues);
}
