/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * Data column for flat and crosstab reports. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public interface IDataColumn{
	
	/**
	 * returns the header of the column. 
	 * The header will be displayed in the final report as the header of this column and 
	 * it shouldn't be confused with the column name in case the report input is an SQL query
	 * 
	 * @return the header of the report column
	 */
	public String getHeader();
	
	
	/**
	 * returns the formatted value ready to be displayed on the report
	 * 
	 * @param value	the unformatted value
	 * @return	the formatted value
	 */
	public String getFormattedValue(Object value);
	
	
	/**
	 * retrieves the value for this column. 
	 * This is the most important method as it retrieves the data for the row-column combination. 
	 *  
	 * 
	 * @param newRowEvent the event containing the new row of data as an array
	 * @return	the computed value for this column
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
	
	/**
	 * returns the calculator (if any) to be used on this column 
	 */
	public ICalculator getCalculator();
	
	
	/**
	 * returns the horizontal alignment to be used for this column
	 */
	public HorizontalAlign getHorizAlign();
	
}
