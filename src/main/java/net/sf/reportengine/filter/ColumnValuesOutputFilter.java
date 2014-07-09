/**
 * 
 */
package net.sf.reportengine.filter;

import java.util.List;

/**
 * @author dragos balan
 *
 */
public class ColumnValuesOutputFilter extends AbstractDataOutputFilter {
	
	private List<String> excludedValues = null;
	private int columnIndex; 
	
	public ColumnValuesOutputFilter(){
		
	}
	
	
	public boolean isDisplayable(String[] formattedColumnValues) {
		return !excludedValues.contains(formattedColumnValues[columnIndex]);
	}

	/**
	 * @return the columnNumber
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * @param columnNumber the columnNumber to set
	 */
	public void setColumnIndex(int columnNumber) {
		this.columnIndex = columnNumber;
	}
	
	public void setExcludedValue(List<String> excludedValuesList){
		this.excludedValues = excludedValuesList;
	}
	
}
