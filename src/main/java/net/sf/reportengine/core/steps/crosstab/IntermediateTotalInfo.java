/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author dragos balan
 * @since 0.4
 */
public class IntermediateTotalInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8034845051062664366L;
	
	
	private Object[] computedValues;
	
	private int[] positionRelativeToHeader; 
	
	
	/**
	 * this represents the header distinct values for which this total is computed
	 */
	private Object[] distinctValues; //TODO this is just for debug. please remove
	
	/**
	 * 
	 * @param computedValue
	 * @param position
	 * @param distinctValues
	 */
	public IntermediateTotalInfo(	Object[] computedValues, 
									int[] position, 
									Object[] distinctValues){
		
		this.computedValues = computedValues; 
		this.positionRelativeToHeader = position; 
		this.distinctValues = distinctValues; 
	}
	
	public Object[] getComputedValues() {
		return computedValues;
	}

	public void setValue(Object[] computedValue) {
		this.computedValues = computedValue;
	}
	
	
	public int[] getPositionRelativeToHeader(){
		return positionRelativeToHeader; 
	}
	
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof IntermediateTotalInfo){
			IntermediateTotalInfo anotherAsITI = (IntermediateTotalInfo)another; 
			result = Arrays.equals(computedValues,anotherAsITI.getComputedValues()) 
					&& Arrays.equals(this.positionRelativeToHeader, anotherAsITI.getPositionRelativeToHeader());
		}
		return result; 
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer("Total");
		result.append("[value=").append(computedValues);
		result.append(", position=");
		result.append(Arrays.toString(positionRelativeToHeader));
		result.append(",distinctValues=");
		result.append(Arrays.toString(distinctValues)); 
		result.append("]");
		return result.toString();
	}
	
}
