/**
 * 
 */
package net.sf.reportengine.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author dragos balan
 * @since 0.4
 */
public class CtMetadata {
	
	
	/**
	 * this is the number of data columns 
	 * ( without taking into account the total columns even if the total column is also a IDataColumn)
	 */
	private int dataColumnsCount = -1; 
	
	/**
	 * 
	 */
	private int[] distinctValuesPerLevel = null;
	
	private int[] spanPerLevel = null;
	
	private int[] spanWhenTotalsPerLevel = null; 
	
	private int headerRowsCount = -1; 
	
	private IDistinctValuesHolder distinctValuesHolder = null ; 
	
	public CtMetadata(IDistinctValuesHolder distValuesHolder){
		this.distinctValuesHolder = distValuesHolder; 
	}
	
	public void computeCoefficients(){
		
		//first we compute the number of columns
		dataColumnsCount = 1; 
		headerRowsCount = distinctValuesHolder.getRowsCount();
		distinctValuesPerLevel = new int[headerRowsCount];
		spanPerLevel = new int[headerRowsCount];
		spanWhenTotalsPerLevel = new int[headerRowsCount]; 
		
		//step 0 done manually
		int distValueCntInCurrentRow = distinctValuesHolder.getDistinctValuesCountForLevel(0);
		dataColumnsCount *= distValueCntInCurrentRow; 
		distinctValuesPerLevel[0] = distValueCntInCurrentRow; 
		spanPerLevel[headerRowsCount-1] = 1;
		spanWhenTotalsPerLevel[headerRowsCount-1] = 1; 
		
		//steps 1 to headerRowsCount
		int auxDistinctValues = 0; 
		for(int i=1; i< headerRowsCount; i++){
			int distValuesCnt = distinctValuesHolder.getDistinctValuesCountForLevel(i);
			dataColumnsCount *= distValuesCnt; 
			distinctValuesPerLevel[i] = distValuesCnt;
					
			//the distance between same values is inversely calculated (bottom to top)
			auxDistinctValues = distinctValuesHolder.getDistinctValuesCountForLevel(headerRowsCount-i);
			spanPerLevel[headerRowsCount-i-1] = spanPerLevel[headerRowsCount-i] * auxDistinctValues;
			
			spanWhenTotalsPerLevel[headerRowsCount-i-1] = (auxDistinctValues * spanWhenTotalsPerLevel[headerRowsCount-i]) + 1;
		}
	}
	
	public int getDataColumnCount(){
		return dataColumnsCount; 
	}
	
	public int[] getColspan(){
		return spanPerLevel; 
	}
	
	public int getColspanForLevel(int level){
		return spanPerLevel[level];
	}
	
	public int getColspanWhenTotals(int level){
		return spanWhenTotalsPerLevel[level];
	}
	
	public int getHeaderRowsCount(){
		return headerRowsCount; 
	}
	
	public int getDistinctValuesCountForLevel(int level){
		return distinctValuesPerLevel[level];
	}
	
	public List<Object> getDistinctValuesForLevel(int level){
		return distinctValuesHolder.getDistinctValuesForLevel(level);
	}
	
	public Object getDistincValueFor(int level, int distValuePosition){
		return distinctValuesHolder.getDistinctValuesForLevel(level).get(distValuePosition); 
	}
	
	/**
	 * 
	 */
	public String toString(){
		StringBuffer result = new StringBuffer(); 
		result.append("CtMetadata[");
		result.append("distValuesCnt=");
		result.append(Arrays.toString(distinctValuesPerLevel));
		result.append(", span=");
		result.append(Arrays.toString(spanPerLevel)); 
		result.append("]");
		return result.toString();
	}
}
