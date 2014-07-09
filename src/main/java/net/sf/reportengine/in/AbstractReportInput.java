/*
 * Created on 29.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import org.apache.log4j.Logger;


/**
 * Minimal implementation for some IReportInput methods.  
 * Use this class as starter for any other ReportInput implementation. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractReportInput implements IReportInput {
	
	/**
	 * the logger
	 */
	private static final Logger logger = Logger.getLogger(AbstractReportInput.class);
	
    /**
     * default header for columns
     */
    public final static String DEFAULT_COLUMN_HEADER = "Column";
    
    /**
     * open flag
     */
    private boolean isOpen = false;

    /**
     * marks the input as open. 
     */
    public void open(){
    	if(isOpen){
            throw new IllegalStateException("You cannot open twice the same input. Close it and then reopen it !");
        }
    	
    	if(logger.isDebugEnabled()){
    		logger.debug("opening the report input");
    	}
        isOpen = true;
    }

    /**
     * marks the input as closed
     */
    public void close(){
    	if(!isOpen){
            throw new IllegalStateException("You cannot close an input which is not open !");
        }
        isOpen = false;
        if(logger.isDebugEnabled()){
    		logger.debug("stream report input closed succesfully");
    	}
    }
    
    /**
     * @return true if the report is open or false otherwise
     */
    public boolean isOpen(){
    	return isOpen; 
    }
}
