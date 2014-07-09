/**
 * 
 */
package net.sf.reportengine.util;

import java.util.ArrayList;

/**
 * 
 * 
 * @author dragos balan
 * @since 0.4
 */
public class DistinctValuesRow {
	
	
	/**
	 * the distinct values 
	 */
	private ArrayList<Object> distinctValues;
	
	/**
	 * 
	 * @param level
	 */
	public DistinctValuesRow(){
		this.distinctValues = new ArrayList<Object>(); 
	}
	

	/**
	 * @return the distinctValues
	 */
	public ArrayList<Object> getDistinctValues() {
		return distinctValues;
	}
	
	
	
	
	/**
	 * tries to add a distinct value to the list. 
	 * 
	 * @param value		the new distinct value to be added in the list
	 * @return			the index of the value into the list
	 */
	public int addDistinctValueIfNotExists(Object value){
		int index = distinctValues.indexOf(value);
		if(index < 0){
			//the item does not exist in the list
			distinctValues.add(value);
			index = distinctValues.size()-1;
		}
		return index; 
	}
	
	
	public String toString(){
		StringBuffer result = new StringBuffer(); 
		result.append("HeaderDistinctValuesDescriptor[");
		result.append(distinctValues.toString());
		result.append("]");
		return result.toString();
	}
}
