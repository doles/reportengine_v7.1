/*
 * Created on 12.02.2005
 * Author : dragos balan
 */
package net.sf.reportengine.in;



/**
 * <p>This is the top interface for report input.</p> 
 * 
 * @author dragos balan (dragos dot balan at gmail.com)
 * @since 0.2
 */
public interface IReportInput {
	
	/**
     * prepares the input for reading. 
     * With this method you can open streams, database connections etc.
     */
    public void open();
    
    /**
     * use this method to release all resources used during the reading 
     * of the input lines
     */
    public void close();
	
    /**
     * retrieves the next row of data 
     * @return an array of data objects
     */
    public Object[] nextRow();
    
    /**
     * returns true if there are rows left to read otherwise false
     * @return  true if the input has more rows to return
     */
    public boolean hasMoreRows();
}
