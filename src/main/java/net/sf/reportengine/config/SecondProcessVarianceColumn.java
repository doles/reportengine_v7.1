/**
 * 
 */
package net.sf.reportengine.config;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Arrays;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;
import net.sf.reportengine.core.steps.crosstab.IntermComputedTotalsList;

/**
 * this is only for internal use
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessVarianceColumn extends AbstractDataColumn {
	
	/**
	 * 
	 */
	private int[] positionRelativeToHeader; 
	
	private int rowIndex1;
	
	private int rowIndex2;
	
	
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param formatter
	 */
	public SecondProcessVarianceColumn(int[] positionRelativeToHeader, 
									ICalculator calc,
									Format formatter, 
									String debugHeader, int rowIndex1,int rowIndex2) {
		super(debugHeader +" "+Arrays.toString(positionRelativeToHeader), calc, formatter);
		this.positionRelativeToHeader = positionRelativeToHeader; 
		this.rowIndex1 = rowIndex1;
		this.rowIndex2 = rowIndex2;
		this.setFormatter(formatter);
		this.setHeader(debugHeader);
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IDataColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		//according to the contract the forth object in each row array is an 
		//instance of IntermCtDataList
		Object[] newRow = newRowEvent.getInputDataRow(); 
		IntermComputedDataList intermDataList = (IntermComputedDataList)newRow[2];
		//we assume there are just 2 headers (for compare years and metrics)
		Object result = intermDataList.getValueFor(null, this);	
	
		Object toReturn = BigDecimal.ZERO; 
		if(result != null ){
			toReturn = result; 
		}
		return toReturn; 
	}
	
	public int[] getPosition(){
		return positionRelativeToHeader; 
	}
	
	public int getRowIndex1() {
		return rowIndex1;
	}
	
	public int getRowIndex2() {
		return rowIndex2;
	}
}
