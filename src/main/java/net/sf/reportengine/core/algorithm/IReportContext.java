/*
 * Created on 28.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.util.ContextKeys;


/**
 * report context interface
 *
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public interface IReportContext {
    
    /**
     * registers the pair (key, object) in the report context
     * @param key		the identifier of the given object
     * @param obj		the object to be registered
     */
    public void set(ContextKeys key, Object obj);
    
    /**
     * gets a value from the context
     * @param key	the indentifier 
     * @return		the requested value
     */
    public Object get(ContextKeys key);
    
    
    /**
     * sets the input 
     * @param input
     */
    public void setInput(IReportInput input);
    
    /**
     * getter for input
     * @return  the input 
     */
    public IReportInput getInput();
    
    /**
     * setter for output
     * @param output
     */
    public void setOutput(IReportOutput output);
    
    /**
     * getter for output
     * @return  the output
     */
    public IReportOutput getOutput();
}
