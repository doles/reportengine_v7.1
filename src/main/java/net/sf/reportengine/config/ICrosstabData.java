/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;
import java.util.Map;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * Crosstab data
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public interface ICrosstabData {
	
	/**
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent);
	
	/**
	 * 
	 * @param unformattedValue
	 * @return
	 */
	public String getFormattedValue(Object unformattedValue);
	
	/**
	 * 
	 * @return
	 */
	public ICalculator getCalculator();
	
	/**
	 * 
	 * @return
	 */
	public HorizontalAlign getHorizAlign();
	
	
	/*
	 * Extra features 
	 */
	public String getLabel(); 
	
	public String getFormattedValue(Object unformattedValue, NewRowEvent row);
	
	public Map<String, Format> getFormatters();
	
	public int getFormatterKeyRowIndex();
	
	public Format getFormatter();
	
	public Map<String, ICalculator> getCalculators();
	
	public int getCalculatorKeyRowIndex();
	
	public ICalculator getCalculator(NewRowEvent row);
	
}
