/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * this class holds values belonging to group columns in the final report 
 * (declared as group columns by the user)
 * 
 * The order of the values is exactly as the order of the group columns in the final report1
 * 
 * @author dragos balan
 * @since 0.4
 */
public class IntermOriginalGroupValuesList implements Serializable {

	/**
	 * serial version
	 */
	private static final long serialVersionUID = 5509029486650665830L;
	
	/**
	 * the list holding the values
	 */
	private List<Object> groupValues; 
	
	/**
	 * the one and only constructor
	 */
	public IntermOriginalGroupValuesList(){
		groupValues = new ArrayList<Object>(); 
	}
	
	/**
	 * adds the given values to the list
	 * 
	 * @param grpValue
	 */
	public void addGroupValue(Object grpValue){
		groupValues.add(grpValue); 
	}
	
	/**
	 * clears the list
	 */
	public void empty(){
		groupValues.clear(); 
	}
	
	/**
	 * getter for the group values
	 * @return
	 */
	public List<Object> getGroupValues(){
		return groupValues; 
	}
	
	/**
	 * retrieves the last group value
	 * @return
	 */
	public Object getLastGroupValue(){
		return groupValues.get(groupValues.size()-1);
	}
	
	@Override
	public boolean equals(Object another){
		boolean result = false; 
		if(another instanceof IntermOriginalGroupValuesList){
			IntermOriginalGroupValuesList anotherAsIntermCtGroupList = (IntermOriginalGroupValuesList)another; 
			result = groupValues.equals(anotherAsIntermCtGroupList.getGroupValues()); 
		}
		return result; 
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder("IntermOriginalGroupValuesList["); 
		result.append(groupValues.toString());
		result.append("]"); 
		return result.toString(); 
	}
	
}
