/*
 * Created on 19.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * <p>
 * Minimum calculator. 
 * 
 * </p> 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
class MinCalculator extends AbstractNumericCalculator {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1001415188212673503L;

	/**
     * min calculator constructor
     *
     */
    MinCalculator(){
        super();
    }
    
    /**
     * 
     */
    public void init() {
        value = new BigDecimal(Integer.MAX_VALUE);
    }
    
    /**
     * compute method
     */
    public void compute(Object newValue) {
        
        if(newValue != null){
            BigDecimal valueToCompare = null;
            if(newValue instanceof BigDecimal){
                valueToCompare = (BigDecimal)newValue;
            }else{
                try{
                    valueToCompare = new BigDecimal(newValue.toString());
                }catch(NumberFormatException ex){
                    throw new CalculatorException("Cannot use "+newValue+" for MinCalculator !",ex);
                }
            }
            //min computation 
            if(valueToCompare.compareTo(value) < 0){
                value = valueToCompare;
            }
        }

    }
    
    
    /**
     * creates a new instance
     */
	public ICalculator newInstance() {
		return new MinCalculator();
	}
}
