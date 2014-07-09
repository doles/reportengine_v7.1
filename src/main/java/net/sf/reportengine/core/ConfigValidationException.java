/*
 * Created on 29.01.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.core;

/**
 * <p>
 *  This is a configuration exception 
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 *
 */
public class ConfigValidationException extends ReportEngineRuntimeException {
    
    public ConfigValidationException(String message){
        super(message);
    }
    
    public ConfigValidationException(Throwable cause){
        super(cause);
    }
    
    public ConfigValidationException(String message, Throwable cause){
        super(message, cause);
    }
}
