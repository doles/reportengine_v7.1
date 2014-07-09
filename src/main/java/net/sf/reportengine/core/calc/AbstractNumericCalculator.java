/*
 * Created on 05.04.2005
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;


/**
 * abstract implementation for ICalculator.
 * You can use this as basis for your own implementations
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public abstract class AbstractNumericCalculator extends AbstractCalculator {
    
    /**
	 * serial version id
	 */
	private static final long serialVersionUID = 1342651474160546835L;
	
	/**
     * the value to be computed
     */
    protected BigDecimal value;
    
    /**
     * simple constructor that calls init() method in order to prepare the
     * calculator for future usage
     */
    public AbstractNumericCalculator(){
        init();
    }
    
    public void init() {
        value = BigDecimal.ZERO;
    }
    
    
    /* (non-Javadoc)
     * @see net.sf.reportengine.core.calc.ICalculator#getResult()
     */
    public Object getResult(){
        return value;
    }
    
    /**
     * returns the name of the class and the value
     * @return a string
     */
    public String toString(){
        int lastIndexOfDot = this.getClass().toString().lastIndexOf(".");
        String className = this.getClass().toString().substring(lastIndexOfDot+1);
        return className+" [val="+value+"]";
    }    
}
