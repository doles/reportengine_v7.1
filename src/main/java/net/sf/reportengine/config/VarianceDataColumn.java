/**
 * 
 */
package net.sf.reportengine.config;

import java.math.BigDecimal;
import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;
import net.sf.reportengine.core.steps.crosstab.IntermComputedTotalsList;

public class VarianceDataColumn extends AbstractDataColumn {
	
	/**
	 * zero based index of the input column
	 */
	private int inputColumnIndex1;
	
	private int inputColumnIndex2;
	
	
	/**
	 * default constructor. 
	 * 
	 * Assumes 
	 * 	inputColumn=0
	 * 	no calculator
	 *  header=Column 0 
	 *  no formatter
	 */
	public VarianceDataColumn(){
		this(0,0); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public VarianceDataColumn(int inputColumnIndex1,int inputColumnIndex2){
		this("Variance", inputColumnIndex1, inputColumnIndex2);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 */
	public VarianceDataColumn(String header, int inputColumnIndex1, int inputColumnIndex2){
		this(header, inputColumnIndex1, inputColumnIndex2, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculatorsFactory
	 */
	public VarianceDataColumn(String header, int inputColumnIndex1, int inputColumnIndex2, ICalculator calculator){
		this(header, inputColumnIndex1, inputColumnIndex2, calculator, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculator
	 * @param formatter
	 */
	public VarianceDataColumn(	String header,
								int inputColumnIndex1,
								int inputColumnIndex2,
								ICalculator calculator, 
								Format formatter){
		this(header, inputColumnIndex1, inputColumnIndex2, calculator, formatter, HorizontalAlign.CENTER);
	}
	
	
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculator
	 * @param formatter
	 * @param horizAlign	horizontal alignment 
	 */
	public VarianceDataColumn(	String header,
								int inputColumnIndex1,
								int inputColumnIndex2,
								ICalculator calculator, 
								Format formatter, 
								HorizontalAlign horizAlign){
		
		super(header, calculator, formatter, horizAlign);
		setInputColumnIndex1(inputColumnIndex1);
		setInputColumnIndex2(inputColumnIndex2);
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IDataColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		return (((Number)newRowEvent.getInputDataRow()[inputColumnIndex1]).doubleValue()/((Number)newRowEvent.getInputDataRow()[inputColumnIndex2]).doubleValue() * 100) - 100;
	}

	public int getInputColumnIndex1() {
		return inputColumnIndex1;
	}

	public void setInputColumnIndex1(int inputColumnIndex1) {
		this.inputColumnIndex1 = inputColumnIndex1;
	}

	public int getInputColumnIndex2() {
		return inputColumnIndex2;
	}

	public void setInputColumnIndex2(int inputColumnIndex2) {
		this.inputColumnIndex2 = inputColumnIndex2;
	}


}
