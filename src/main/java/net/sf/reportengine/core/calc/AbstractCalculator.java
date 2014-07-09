/**
 * 
 */
package net.sf.reportengine.core.calc;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * abstract implementation for ICalculator interface
 * 
 * @author dragos dragos (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractCalculator implements ICalculator {

	

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 955527292301539906L;
	
	/**
	 * deep copy of this object
	 */
	public ICalculator clone(){
		ICalculator result = null; 
		try {
			result = (ICalculator)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CalculatorException("Cloning is not supported", e);
		}
		return result; 
	}
	
	
	public void compute(IDataColumn column, NewRowEvent row){
		Object value = column.getValue(row);
		this.compute(value);
	}
	
}
