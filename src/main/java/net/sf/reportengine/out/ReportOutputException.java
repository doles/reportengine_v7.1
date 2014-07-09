/*
 * Created on Aug 7, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportEngineRuntimeException;

public class ReportOutputException extends ReportEngineRuntimeException{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8977998834641107249L;
	
	/**
	 * 
	 * @param message
	 */
	public ReportOutputException(String message){
        super(message);
    }
    
	/**
	 * 
	 * @param cause
	 */
    public ReportOutputException(Throwable cause){
        super(cause);
    }
    
    /**
     * 
     * @param message
     * @param cause
     */
    public ReportOutputException(String message, Throwable cause){
        super(message, cause);
    }

}
