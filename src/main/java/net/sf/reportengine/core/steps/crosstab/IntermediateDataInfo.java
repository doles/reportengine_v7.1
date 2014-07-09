/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.Arrays;

import net.sf.reportengine.core.algorithm.NewRowEvent;

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
	private NewRowEvent row;
	
	public IntermediateDataInfo(Object value, int[] position, NewRowEvent row){
		this.value = value; 
		this.positionRelativeToHeader = position;
		this.row = row;
	}
	
	public Object getValue(){
		return value; 
	}
	
	public int[] getPositionRelativeToHeaderRows(){
		return positionRelativeToHeader;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("value="+value+"=["+Arrays.toString(positionRelativeToHeader)+"]]");
		return sb.toString();
	}
	
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof IntermediateDataInfo){
			IntermediateDataInfo anotherAsICDI = (IntermediateDataInfo)another;
			result = value.equals(anotherAsICDI.getValue()) && Arrays.equals(positionRelativeToHeader, anotherAsICDI.getPositionRelativeToHeaderRows());
		}
		return result; 
	}
	
	public NewRowEvent getRow() {
		return row;
	}
}