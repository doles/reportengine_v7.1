/*
 * Created on 14.01.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * Average calculator. 
 * This is just a SumCalculator that divides the sum to the count of the elements passed 
 * as arguments to {@link #compute(Object)} method
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
class AvgCalculator extends SumCalculator {
    
    /**
	 * serial version id
	 */
	private static final long serialVersionUID = -2717104274824984991L;
	
	/**
     * counter for computed elements 
     */
    int elementsCount;
    
    /**
     * average calculator constructor
     * calls super() -> init()
     */
    AvgCalculator(){
        super();
    }
    
    /**
     * initializer
     */
    public void init(){
        super.init();
        elementsCount = 0;
    }

    /**
     * compute
     */
    public void compute(Object newValue){   
        super.compute(newValue);
        elementsCount ++;
    }
    
    @Override
    public void compute(IDataColumn column, NewRowEvent row) {
    	super.compute(column, row);
    	//elementsCount ++;
    }

    /**
     * result getter
     */
    public Object getResult(){
    	BigDecimal sum = (BigDecimal)super.getResult();
    	return sum.doubleValue() / elementsCount; 
        //return ((BigDecimal)super.getResult()).divide(new BigDecimal(elementsCount), BigDecimal.ROUND_HALF_EVEN);
    }
    
    /**
     * creates a new instance
     */
	@Override public ICalculator newInstance() {
		return new AvgCalculator();
	}
}
