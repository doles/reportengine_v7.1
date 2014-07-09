/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalDataColsList;

/**
 * This is only for internal usage !
 * 
 * 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessDataColumnFromOriginalDataColumn implements IDataColumn {
	
	/**
	 * 
	 */
	private IDataColumn originalDataColumn;
	
	/**
	 * 
	 */
	private int indexInOriginalDataColsArray; 
	
	
	/**
	 * 
	 * @param origDataColumn
	 * @param indexInDataColsArray
	 */
	public SecondProcessDataColumnFromOriginalDataColumn(	IDataColumn origDataColumn, 
															int indexInDataColsArray){
		this.originalDataColumn = origDataColumn; 
		this.indexInOriginalDataColsArray = indexInDataColsArray; 
	}

	public String getHeader() {
		return originalDataColumn.getHeader();
	}

	public String getFormattedValue(Object value) {
		return originalDataColumn.getFormattedValue(value); 
	}

	public Object getValue(NewRowEvent newRowEvent) {
		//according to the contract the second object in each row array is an 
		//instance of IntermOrigGroupValuesList
		Object[] newRow = newRowEvent.getInputDataRow(); 
		IntermOriginalDataColsList intermGroupValues = (IntermOriginalDataColsList)newRow[1]; 
				
		//we get the group value according to the group level
		return intermGroupValues.getDataValues().get(indexInOriginalDataColsArray); 
	}

	public ICalculator getCalculator() {
		return originalDataColumn.getCalculator();
	}

	public HorizontalAlign getHorizAlign() {
		return originalDataColumn.getHorizAlign(); 
	}
}
