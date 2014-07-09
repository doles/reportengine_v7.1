/*
 * Created on Aug 7, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportContent;

/**
 * <p>properties of a report row</p>
 * <ol>
 * 	<li>content type</li>
 * 	<li>row number </li>
 * </ol>
 *  
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3 
 */
public class RowProps {
    
	/**
	 * 
	 */
    private final ReportContent reportContent;
    
    /**
     * row number
     */
    private final int rowNumber; 
    
    /**
     * 
     */
    public RowProps(){
    	this(ReportContent.DATA, 0);
    }
    
    /**
     * 
     * @param content
     */
    public RowProps(ReportContent content){
    	this(content, 0); 
    }
    
    /**
     * 
     * @param content
     * @param rowNumber
     */
    public RowProps(ReportContent content, int rowNumber){
    	this.reportContent = content; 
    	this.rowNumber = rowNumber; 
    }
    
    /**
     * getter for the content type stored in this row properties
     * @return
     */
    public ReportContent getContent() {
        return reportContent;
    }
    
    /**
     * retrieves the row number stored in this row properties
     * @return
     */
    public int getRowNumber(){
    	return rowNumber; 
    }
}
