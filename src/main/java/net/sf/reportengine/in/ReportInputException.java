/*
 * Created on 15.01.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import net.sf.reportengine.core.ReportEngineRuntimeException;

public class ReportInputException extends ReportEngineRuntimeException {
    
    /**
     * serial version id
     */
    private static final long serialVersionUID = -1816028173384648736L;

    public ReportInputException(String message){
        super(message);
    }
    
    public ReportInputException(Throwable cause){
        super(cause);
    }
    
    public ReportInputException(String message, Throwable cause){
        super(message, cause);
    }
}
