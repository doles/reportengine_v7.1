/*
 * Created on 01.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import net.sf.reportengine.core.ReportEngineRuntimeException;

/**
 * <p>
 *  calculator exception
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public class CalculatorException extends ReportEngineRuntimeException {
    
    /**
	 * serial version id
	 */
	private static final long serialVersionUID = 1460825389292666755L;

	/**
     * constructor
     * @param message   the message to be shown
     */
    public CalculatorException(String message){
        super(message);
    }
    
    /**
     * constructor
     * @param message   the message
     * @param cause     the cause
     */
    public CalculatorException(String message, Throwable cause){
        super(message, cause);
    }
}
