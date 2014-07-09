/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * this class holds values computed by the intermediate report (coming from CrosstabData) and arranged as a single object
 * in the intermediate row
 * 
 * The order of the objects is not important because any IntermediateDataInfo object has the position specified as an array
 * 
 * @author dragos balan
 * @since 0.4
 */
public class IntermComputedDataList implements Serializable{
	
	
	/**
	 * serial version id
	 */
	private static final long serialVersionUID = -308220872583199439L;
	
	/**
	 * the values list
	 */
	private List<IntermediateDataInfo[]> dataList; 
	
	
	/**
	 * the one and only constructor
	 */
	public IntermComputedDataList(){
		dataList = new ArrayList<IntermediateDataInfo[]>();
	}
	
	/**
	 * adds the specified data into the list
	 * @param info
	 */
	public void addData(IntermediateDataInfo[] info){
		dataList.add(info);
	}
	
	/**
	 * returns the data list
	 * @return
	 */
	public List<IntermediateDataInfo[]> getDataList(){
		return dataList; 
	}
	
	/**
	 * clears the values of data
	 */
	public void empty(){
		dataList.clear(); 
	}
	
	/**
	 * returns the value for the given position
	 * 
	 * @param headerRowIndex	the position relative to header
	 * @return
	 */
	public Object getValueFor(int[] headerRowIndex, IDataColumn dataColumn){
		Object result = null; 
		List<NewRowEvent> values = new ArrayList<NewRowEvent>(1);
		boolean allPositionsAreEqual =  true; 
		
		//iterate over dataList
		if(headerRowIndex!=null) {
			for (IntermediateDataInfo[] dataInfos : dataList) {
				for(IntermediateDataInfo dataInfo : dataInfos){
					int[] positionRelativeToHeader  = dataInfo.getPositionRelativeToHeaderRows();
					if(	positionRelativeToHeader != null 
						&& positionRelativeToHeader.length == headerRowIndex.length){ //last one are metrics
						
						allPositionsAreEqual =  true; 
						
						//iterate over positions
						for (int i = 0; i < positionRelativeToHeader.length && allPositionsAreEqual; i++) {
							if(positionRelativeToHeader[i] != headerRowIndex[i]){
								
								//if found one position not equal to the header row index then 
								//mark not all positions are equal in order to skip this 
								//IntermediateDataInfo and pass to the next one
								allPositionsAreEqual = false; 
							}
						}
						
						if(allPositionsAreEqual){
							//the first time we find that all positions are equal we exit the 
							//dataInfo loop and return
							
							//last number in headerRowIndex point to index of the value
							//int valueIndex = headerRowIndex[headerRowIndex.length-1];
							values.add(dataInfo.getRow());
						}
					}else{
						throw new IllegalArgumentException("Invalid position array : "+Arrays.toString(headerRowIndex));
					}
				}
	
			}
			
			dataColumn.getCalculator().init();
			for(NewRowEvent value : values){
				if(null!=dataColumn){
					dataColumn.getCalculator(value).init();
					System.out.println("Clearing value: "+dataColumn.getCalculator(value).getResult());
				}
			}
			for(NewRowEvent value : values){
				if(null!=dataColumn){
					dataColumn.getCalculator(value).compute(dataColumn, value);
					result = dataColumn.getCalculator(value).getResult();
				}
			}
		}
		else {
			dataColumn.getCalculator().init();
			for(IntermediateDataInfo[] dataInfos : dataList){
				for(IntermediateDataInfo dataInfo : dataInfos){					
					dataColumn.getCalculator(dataInfo.getRow()).init();	
					System.out.println("Clearing value: "+dataColumn.getCalculator(dataInfo.getRow()).getResult());
				}
			}
			for(IntermediateDataInfo[] dataInfos : dataList){
				for(IntermediateDataInfo dataInfo : dataInfos){					
					dataColumn.getCalculator(dataInfo.getRow()).compute(dataColumn, dataInfo.getRow());
					result = dataColumn.getCalculator(dataInfo.getRow()).getResult();					
				}
			}			
		}
		
		
		
		return result;
	}
	
		
	@Override
	public boolean equals(Object another){
		boolean result = false;
		if(another instanceof IntermComputedDataList){
			result = this.dataList.equals(((IntermComputedDataList)another).getDataList()); 
		}
		return result; 
	}
	
	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer("IntermComputedDataList");
		buffer.append(dataList.toString());
		return buffer.toString(); 
	}
}
