/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Administrator
 *
 */
public class IntermediateDataInfo implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6779896030883957024L;
	
	private Object value; 
	private int[] positionRelativeToHeader; 
	
	public IntermediateDataInfo(Object value, int... position){
		this.value = value; 
		this.positionRelativeToHeader = position;
	}
	
	public Object getValue(){
		return value; 
	}
	
	public int[] getPositionRelativeToHeaderRows(){
		return positionRelativeToHeader;
	}
	
	public String toString(){
		return ""+value+"["+Arrays.toString(positionRelativeToHeader)+"]";
	}
	
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof IntermediateDataInfo){
			IntermediateDataInfo anotherAsICDI = (IntermediateDataInfo)another;
			result = anotherAsICDI.getValue().equals(value) && Arrays.equals(positionRelativeToHeader, anotherAsICDI.getPositionRelativeToHeaderRows());
		}
		return result; 
	}
}