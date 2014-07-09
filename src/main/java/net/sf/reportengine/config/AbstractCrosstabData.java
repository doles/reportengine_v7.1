/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;
import java.util.Map;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public abstract class AbstractCrosstabData implements ICrosstabData {
	
	/**
	 * 
	 */
	private Format formatter; 
	
	/**
	 * 
	 */
	private ICalculator calculator; 
	
	/**
	 * 
	 */
	private HorizontalAlign horizAlign; 
	

	
	private String label;
	
	private int formatterKeyRowIndex;
	
	private int calculatorKeyRowIndex;
	
	
	/**
	 * 
	 * @param calcFactory
	 * @param formatter
	 */
	public AbstractCrosstabData(ICalculator calc, 
								Format formatter, 
								HorizontalAlign horizAlign){
		setCalculator(calc);
		setFormatter(formatter);
		setHorizAlign(horizAlign); 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.ICrosstabData#getFormattedValue(java.lang.Object)
	 */
	public String getFormattedValue(Object unformattedValue) {		
		String result = "";
		if(unformattedValue != null){
			if(formatter != null){
				result = formatter.format(unformattedValue);
			}else{
				result = unformattedValue.toString();
			}
		}
		return result; 
	}
	
	public String getFormattedValue(Object unformattedValue, NewRowEvent row){
		String result = "";
		if(unformattedValue != null){
			if(formatters != null){
				Format f = formatters.get(row.getInputDataRow()[formatterKeyRowIndex]);
				if(f==null){
					result = getFormattedValue(unformattedValue);
				}
				else {
					try{
						result = f.format(unformattedValue);
					}
					catch(Exception ex){
						result = getFormattedValue(unformattedValue);
					}
				}
			}else{
				result = getFormattedValue(unformattedValue);
			}
		}
		return result; 		
	}

	public Format getFormatter() {
		return formatter;
	}

	public void setFormatter(Format formatter) {
		this.formatter = formatter;
	}
	
	public ICalculator getCalculator() {
		return calculator;
	}

	public ICalculator getCalculator(NewRowEvent row) {
		if(calculators != null){
			ICalculator c = calculators.get(row.getInputDataRow()[calculatorKeyRowIndex]);
			if(c==null){
				return getCalculator();
			}
			else {
				return c;
			}
		}		
		return getCalculator();
	}

	public void setCalculator(ICalculator calc) {
		this.calculator = calc;
	}

	/**
	 * @return the horizAlign
	 */
	public HorizontalAlign getHorizAlign() {
		return horizAlign;
	}

	/**
	 * @param horizAlign the horizAlign to set
	 */
	public void setHorizAlign(HorizontalAlign horizAlign) {
		this.horizAlign = horizAlign;
	}
	
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}	
	
	
	private Map<String,Format> formatters;
	
	public void setFormatters(Map<String, Format> formatters) {
		this.formatters = formatters;
	}
	
	public Map<String, Format> getFormatters() {
		return formatters;
	}	
	
	public int getFormatterKeyRowIndex() {
		return formatterKeyRowIndex;
	}
	
	public void setFormatterKeyRowIndex(int formatterKeyRowIndex) {
		this.formatterKeyRowIndex = formatterKeyRowIndex;
	}
	
	private Map<String, ICalculator> calculators;
	
	public void setCalculators(Map<String, ICalculator> calculators) {
		this.calculators = calculators;
	}
	
	public Map<String, ICalculator> getCalculators() {
		return calculators;
	}
	
	public void setCalculatorKeyRowIndex(int calculatorKeyRowIndex) {
		this.calculatorKeyRowIndex = calculatorKeyRowIndex;
	}
	
	public int getCalculatorKeyRowIndex() {
		return calculatorKeyRowIndex;
	}

}
