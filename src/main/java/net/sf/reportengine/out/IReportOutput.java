/*
 * Created on 29.01.2005
 * Author : dragos balan
 */
package net.sf.reportengine.out;



/**
 * <p>
 *  definition for the report output 
 * </p>
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.1
 *  
 */
public interface IReportOutput {
	
	/**
	 * the whitespace character
	 */
	public static final String WHITESPACE = " ";
	
	/**
	 * prepares the output for usage.
	 * One possible usage is to open the output streams 
	 */
	public void open();
	
	/**
	 * starts a new row in the report
	 * 
	 * @param rowProperties the properties of the row
	 */
	public void startRow(RowProps rowProperties);
    
    /**
     * ends of a row in the report 
     */
    public void endRow();

	/**
	 * outputs the specified value taking into account the 
     * cell properties imposed by the algorithm 
	 * 
	 * @param cellProp  the properties of the cell to be output    
	 */
	public void output(CellProps cellProps);
	
	
	/**
	 * closes the output (frees any resources used during the output)
	 */
	public void close();
    
}
