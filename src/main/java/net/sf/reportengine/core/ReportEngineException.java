/*
 * Created on 01.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core;

/**
 * 
 * @author dragos balan
 * @since 0.2
 * @deprecated
 */
public class ReportEngineException extends Exception {
    
    
    public ReportEngineException(String message){
        super(message);
    }
    
    public ReportEngineException(Throwable cause){
        super(cause);
    }
    
    public ReportEngineException(String message, Throwable cause){
        super(message, cause);
    }
}
