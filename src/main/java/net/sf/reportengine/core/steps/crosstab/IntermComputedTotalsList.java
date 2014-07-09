/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * this class holds total values computed by intermediate report on CrosstabData and 
 * destined for special total-data columns in the final report
 * 
 * The order is not important because all IntermediateTotalInfo objects have a position inside
 * 
 * @author dragos balan
 * @since 0.4
 */
public class IntermComputedTotalsList implements Serializable{
	
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = 3388048740067218435L;
	
	private int crosstabSize;
	
	public IntermComputedTotalsList(int crosstabSize){
		this.crosstabSize = crosstabSize;
		this.totalsDataList = new ArrayList<IntermediateTotalInfo>(); 
	}
	
	/**
	 * the total values holder
	 */
	private List<IntermediateTotalInfo> totalsDataList;
	
	/**
	 * the one and only constructor
	 */
	public IntermComputedTotalsList(){
		totalsDataList = new ArrayList<IntermediateTotalInfo>(); 
	}
	
	/**
	 * adds the specified totalsData into the existing list
	 * @param totalsData
	 */
	public void addTotalsData(IntermediateTotalInfo totalsData){
		totalsDataList.add(totalsData); 
	}
	
	/**
	 * retrieves the totalInfo for the specified position (relative to the header)
	 * 
	 * @param position	an array of indexes ( position relative to header)
	 * @return
	 */
	public Object getValueFor(int[] position, int crosstabDataIndex){
		Object result = null; 
		boolean allPositionsAreEqual =  true; 
		
		if(position != null){
			//iterate over dataList
			for (IntermediateTotalInfo totalInfo : totalsDataList) {
				int[] positionRelativeToHeader = totalInfo.getPositionRelativeToHeader();
			
				if(	positionRelativeToHeader != null 
					&& positionRelativeToHeader.length == position.length){
				
					allPositionsAreEqual =  true; 
					
					//iterate over positions
					for (int i = 0; i < positionRelativeToHeader.length && allPositionsAreEqual; i++) {
						if(positionRelativeToHeader[i] != position[i]){
							
							//if found one position not equal to the header row index then 
							//mark not all positions are equal in order to skip this 
							//IntermediateDataInfo and pass to the next one
							allPositionsAreEqual = false; 
						}
					}
				
					if(allPositionsAreEqual){
						//the first time we find that all positions are equal we exit the 
						//dataInfo loop and return
						result = totalInfo.getComputedValues()[crosstabDataIndex]; 
						break; 
					}
				}else{
					//if position relative to header is null then 
					//this is a grand total
				}
			}//end loop totalsInfo 
		}else{
			//we know the last columns are crosstab grand total columns
			/*int totalsForAllColsIndex = totalsDataList.size()-1;
			//the total we need it fullIndex - crosstabDataIndex 
			IntermediateTotalInfo totalInfo = totalsDataList.get(totalsForAllColsIndex-(crosstabSize-1)+crosstabDataIndex);
			result = totalInfo.getComputedValues()[crosstabDataIndex];
			*/
			IntermediateTotalInfo totalInfo = totalsDataList.get(totalsDataList.size()-1);
			result = totalInfo.getComputedValues()[crosstabDataIndex];
		}
		return result;
	}
	
	/**
	 * clears the list 
	 */
	public void empty(){
		totalsDataList.clear(); 
	}
	
	/**
	 * returns the last value in the list
	 * @return
	 */
	public Object getLastValueInTotalList(){
		return totalsDataList.get(totalsDataList.size()-1);
	}
	
	/**
	 * retrieves the list behind this class
	 * @return
	 */
	public List<IntermediateTotalInfo> getTotalsDataList(){//TODO: try to remove this
		return totalsDataList; 
	}
	
	@Override
	public boolean equals(Object another){
		boolean result = false;
		if(another instanceof IntermComputedTotalsList){
			result = this.totalsDataList.equals(((IntermComputedTotalsList)another).getTotalsDataList()); 
		}
		return result; 
	}
	
	@Override
	public String toString(){
		StringBuffer result = new StringBuffer("IntermCtTotalList"); 
		result.append(totalsDataList.toString());
		return result.toString(); 
	}
}
