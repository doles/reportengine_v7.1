/*
 * Created on 19.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * Maximum calculator 
 * @author dragos balan(dragos dot balan at gmail dot com)
 */
class MaxCalculator extends AbstractNumericCalculator {
    
    /**
	 * serial version id
	 */
	private static final long serialVersionUID = -3090912975869267129L;

	/**
     * default constructor
     */
    MaxCalculator(){
        super();
    }
    
    /**
     * counts the maximum
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
                    throw new CalculatorException("Cannot use "+newValue+" for MaxCalculator !",ex);
                }
            }
            //max computation 
            if(valueToCompare.compareTo(value) > 0){
                value = valueToCompare;
            }
        }

    }
    
    
    /**
     * creates a new instance
     */
	public ICalculator newInstance() {
		return new MaxCalculator();
	}
    
}
