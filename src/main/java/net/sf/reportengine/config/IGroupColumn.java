/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * Group column for flat and crosstab reports
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public interface IGroupColumn {
	
	/**
	 * this appears in the final report as the title/header of the column
	 * @return
	 */
	public String getHeader(); 
	
	
	/**
	 * the level of grouping. 
	 * The grouping levels start with 0 (the most important), 1, 2, 3 ..
	 *  
	 * @return an integer representing the level of grouping
	 */
	public int getGroupingLevel();
	
	/**
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
	/**
	 * returns the formatted value for the given object according to the 
	 * formatting rules of this grouping column
	 * @param object
	 */
	public String getFormattedValue(Object object);
	
	/**
	 * returns the horizontal alignment for this column
	 */
	public HorizontalAlign getHorizAlign(); 
	
	/**
	 * whether or not this group column should display duplicate group values. 
	 * Usually there are many group values displayed one after the other 
	 * 
	 * <table>
	 * <tr>
	 * 	<td>Europe</td><td>South</td><td>100</td>
	 * </tr>
	 * <tr>
	 * 	<td>Europe</td><td>North</td><td>207</td>
	 * </tr>
	 * <tr>
	 * 	<td>Europe</td><td>East</td><td>103</td>
	 * </tr>
	 * <tr>
	 * 	<td>Europe</td><td>West</td><td>120</td>
	 * </tr>
	 * </table>
	 * 
	 * This method controls whether the duplicated Europe value should be displayed multiple times or not: 
	 * 
	 * <table>
	 * <tr>
	 * 	<td>Europe</td><td>South</td><td>100</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>North</td><td>207</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>East</td><td>103</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>West</td><td>120</td>
	 * </tr>
	 * <tr>
	 * 	<td>Asia</td><td>South</td><td>300</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>North</td><td>267</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>East</td><td>564</td>
	 * </tr>
	 * <tr>
	 * 	<td>&nbsp;</td><td>West</td><td>122</td>
	 * </tr>
	 * </table>
	 * 
	 * @return
	 */
	public boolean showDuplicates(); 
}
