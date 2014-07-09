/*
 * Created on 04.12.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core;


/**
 * the root exception for all report engine API
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class ReportEngineRuntimeException extends RuntimeException {

    /**
	 * serial version id
	 */
	private static final long serialVersionUID = -7645378899218415130L;

	public ReportEngineRuntimeException(){
        super();
    }
    
    public ReportEngineRuntimeException(Throwable source){
        super(source);
    }
    
    public ReportEngineRuntimeException(String message){
        super(message);
    }
    
    public ReportEngineRuntimeException(String message, Throwable cause){
        super(message, cause);
    }
    

}
