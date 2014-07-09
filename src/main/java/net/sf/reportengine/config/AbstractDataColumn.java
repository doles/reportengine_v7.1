package net.sf.reportengine.config;

import java.text.Format;
import java.util.ArrayList;
import java.util.Map;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalGroupValuesList;

/**
 * Abstract implementation for IDataColumn. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3 
 * @see IDataColumn, DefaultDataColumn
 */
public abstract class AbstractDataColumn implements IDataColumn {
	
	/**
	 *  the column header
	 */
	private String header; 
	
	/**
	 * the formatter
	 */
	private Format formatter; 
	
	/**
	 * the calculator of this column
	 */
	private ICalculator calculator; 
	
	/**
	 * the horizontal alignment
	 */
	private HorizontalAlign horizAlign; 
	

	private int formatterKeyRowIndex;
	
	private int calculatorKeyRowIndex;
	
	/**
	 * 
	 * @param header
	 */
	public AbstractDataColumn(String header){
		this(header, null); 
	}
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 */
	public AbstractDataColumn(String header, ICalculator calculator){
		this(header, calculator, null); 
	}
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param formatter
	 */
	public AbstractDataColumn(	String header, 
								ICalculator calculator, 
								Format formatter){
		this(header, calculator, formatter, HorizontalAlign.CENTER);
	}
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param formatter
	 * @param horizAlign
	 */
	public AbstractDataColumn(	String header, 
								ICalculator calculator, 
								Format formatter, 
								HorizontalAlign horizAlign){
		setHeader(header); 
		setFormatter(formatter); 
		setCalculator(calculator); 
		setHorizAlign(horizAlign); 
	}
	
	/**
	 * getter for the column header
	 */
	public String getHeader() {
		return header;
	}
	
	/**
	 * setter for this column's header
	 * @param header
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	
	
	/**
	 * returns the formatted value
	 */
	public String getFormattedValue(Object value) {
		String result = "";
		if(value != null){
			if(formatter != null){
				result = formatter.format(value);
			}else{
				result = value.toString();
			}
		}
		return result; 
	}
	
	public String getFormattedValue(Object unformattedValue, NewRowEvent row){
		String result = "";
		if(unformattedValue != null){
			if(formatters != null){
				Object key = row.getInputDataRow()[0];
				Format f = null;
				if(key instanceof IntermOriginalGroupValuesList){
					//this is crosstab group, so get key from group, groups will be at the begining
					f = formatters.get(((IntermOriginalGroupValuesList) key).getGroupValues().get(0));
				}
				else {
					f = formatters.get(row.getInputDataRow()[formatterKeyRowIndex]);	
				}
				
				if(f==null){
					result = getFormattedValue(unformattedValue);
				}
				else {
					result = f.format(unformattedValue);	
				}
			}else{
				result = getFormattedValue(unformattedValue);
			}
		}
		return result; 		
	}
	
	/**
	 * getter for this column's calculator (if any)
	 */
	public ICalculator getCalculator() {		
		return calculator;
	}
	
	/**
	 * 
	 * @param calculator
	 */
	public void setCalculator(ICalculator calculator) {
		this.calculator = calculator;
	}
	
	
	/**
	 * sets a formatter for this column. 
	 * All values of this column will be formatted using this formatter when 
	 * the report engine calls {@link #getFormattedValue(Object)}
	 * 
	 * @param formatter
	 */
	public void setFormatter(Format formatter) {
		this.formatter = formatter;
	}
	
	/**
	 * getter for the formatter
	 * @return
	 */
	public Format getFormatter(){
		return formatter; 
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
	
	private Map<String,ICalculator> calculators;
	
	
	public Map<String, ICalculator> getCalculators() {
		return calculators;
	}
	
	public void setCalculators(Map<String, ICalculator> calculators) {
		this.calculators = calculators;
	}
	
	public int getCalculatorKeyRowIndex() {
		return calculatorKeyRowIndex;
	}
	
	public void setCalculatorKeyRowIndex(int calculatorKeyRowIndex) {
		this.calculatorKeyRowIndex = calculatorKeyRowIndex;
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
}
