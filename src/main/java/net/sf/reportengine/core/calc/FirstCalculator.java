/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * This calculator will return the first value passed after calling init. 
 * No matter how many times you call {@link #compute(Object)} it will always 
 * return the first value
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class FirstCalculator extends AbstractCalculator {
	
	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 6437488441945365316L;

	private String INIT_VALUE = "net.sf.reportengine.impossible";
	
	/**
	 * the first value
	 */
	private Object value;
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculator#compute(java.lang.Object)
	 */
	public void compute(Object newValue) {
		//remember only the first value
		if(INIT_VALUE.equals(value) && !value.equals(newValue)){
			value = newValue;
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculator#getResult()
	 */
	public Object getResult() {
		return value;
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculator#init()
	 */
	public void init() {
		value = INIT_VALUE;
	}

	/**
     * creates a new instance
     */
	public ICalculator newInstance() {
		return new FirstCalculator();
	}
}
