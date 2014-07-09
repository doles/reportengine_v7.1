/*
 * Created on 06.04.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * <p>
 * Simple count calculator.
 * Keeps a counter of the objects passed to {@link #compute(Object)} until the 
 * counter is re-initialized through {@link #init()}
 *</p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class CountCalculator extends AbstractNumericCalculator {
    
    /**
	 * serial version id
	 */
	private static final long serialVersionUID = -722184855794011072L;

	/**
     * the constructor makes a call to init() method
     */
    CountCalculator(){
        super();
    }
    
    /**
     *	this method increases the value of the internal counter 
     * @param newValue
     */
    public void compute(Object newValue) {
        if(newValue != null){
            value = value.add(new BigDecimal(1));
        }
    }
    
    /**
     * creates a new instance of this object
     */
	public ICalculator newInstance() {
		return new CountCalculator();
	}
    
}
