/**
 * 
 */
package net.sf.reportengine.config;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Arrays;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.crosstab.IntermComputedTotalsList;

/**
 * this is only for internal use
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessTotalColumn extends AbstractDataColumn {
	
	/**
	 * 
	 */
	private int[] positionRelativeToHeader; 
	
	/**
	 * 
	 * @param header
	 * @param calculator
	 * @param formatter
	 */
	public SecondProcessTotalColumn(int[] positionRelativeToHeader, 
									ICalculator calc,
									Format formatter, 
									String debugHeader) {
		super(debugHeader +" "+Arrays.toString(positionRelativeToHeader), calc, formatter);
		this.positionRelativeToHeader = positionRelativeToHeader; 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IDataColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		//according to the contract the forth object in each row array is an 
		//instance of IntermCtDataList
		Object[] newRow = newRowEvent.getInputDataRow(); 
		IntermComputedTotalsList intermTotalsList = (IntermComputedTotalsList)newRow[3]; 
		Object result = intermTotalsList.getValueFor(positionRelativeToHeader); 
		Object toReturn = BigDecimal.ZERO; 
		if(result != null ){
			toReturn = result; 
		}
		return toReturn; 
	}
	
	public int[] getPosition(){
		return positionRelativeToHeader; 
	}
}
