package net.sf.reportengine.core.calc;


/**
 * this is just a holder of the last value passed to {@link #compute(Object)} therefore 
 * it will always return the last value passed 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class LastCalculator extends AbstractCalculator {
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = -8775227212750608867L;
	
	/**
	 * last value passed to compute
	 */
	private Object last;
	
	/**
	 * keeps the value passed as parameter for later usage
	 */
	public void compute(Object value) {
		last = value;
	}
	
	/**
	 * returns the last value passed to {@link #compute(Object)}
	 */
	public Object getResult() {
		return last;
	}
	
	/**
	 * empty implementation of #ICalculator.init()
	 */
	public void init() {}
	
	/**
     * creates a new instance
     */
	public ICalculator newInstance() {
		return new LastCalculator();
	}
	
}
