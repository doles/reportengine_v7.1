/*
 * Created on 20.03.2005
 * Author: dragos balan
 *
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.ContextKeys;

import org.apache.log4j.Logger;

/**
 * <p>
 * this computes the aggregation level for the current dataEvent. 
 * The protocol is : 
 * <ul>
 * 		<li>
 * 			when returning the level -1 then a simple data row has been found 
 * 			( none of the aggregation columns has been changed )
 * 		</li>
 * 		<li>
 * 			any level > 0 returned is the index of the first element changed in the 
 * 			aggregation columns.
 * 		</li>
 * </ul>
 * 
 * Example: <br>
 * Let's assume that we have a data matrix like : <br/>
 * <table>
 * 	<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
 * 	<tr><td>1</td><td>2</td><td>3</td><td>5</td></tr>
 * 	<tr><td>1</td><td>1</td><td>6</td><td>7</td></tr>
 *  <tr><td>2</td><td>8</td><td>6</td><td>7</td></tr>
 * </table>
 * 
 * and all the columns {0,1,2} as grouping columns
 * 
 * then : <br/>
 * 	when passing the first row to the execute method the result level will be -1 <br>
 *  when passing the second row to the same method the result will be -1 (because none of the columns 0,1,2 have changed)<br>
 *  when passing the third  row to the same method the result will be 1 (because this is the index of the first changed column)<br>
 *  when passing the second row to the same method the result will be 0  <br>
 * </p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class GroupingLevelDetectorStep extends AbstractReportStep{
    
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(GroupingLevelDetectorStep.class);
	
    
    /**
     * the aggregation level
     */
	private int aggregationLevel = -1; 
	
    /**
     * Recommended constructor
     * @param aggregationColumns
     */
    public GroupingLevelDetectorStep(){}
    
	/**
     * registers some new values into the context
     * @see net.sf.reportengine.core.algorithm.IAlgorithmInitStep#init(IReportContext)()
     */
    public void init(IReportContext reportContext){
        super.init(reportContext);
    }
    
	/**
	 * execute method
	 */
	public void execute(NewRowEvent newRowEvent) {
        
        List<IGroupColumn> groupingCols = getGroupingColumns();
        
		//first time we cannot make any comparison so the return level is zero
		if(getPreviousRowOfGroupingValues() == null){
			//handle last row for grouping columns 
			//lastRowOfGroupingColumnValues = new Object[groupingCols.length];
			//copyGroupingValuesToLastRowOfGroupingColumnValues(groupingCols, newRowEvent);
			
			aggregationLevel = -1;
			logger.trace("evaluating first data row. ");			
		}else{
			aggregationLevel = checkLevelChangedInGroupingColumns(groupingCols, getPreviousRowOfGroupingValues(), newRowEvent); 
			
			if(logger.isDebugEnabled()){
				logger.trace("For newRow="+newRowEvent+" the grouping level found is "+aggregationLevel);
			}
			
//			if(aggregationLevel > -1){
//				copyGroupingValuesToLastRowOfGroupingColumnValues(groupingCols, newRowEvent);
//			}
		}
		
		//set the result in context
		getContext().set(ContextKeys.CONTEXT_KEY_NEW_GROUPING_LEVEL, aggregationLevel);
		
//		if(logger.isTraceEnabled()){
//    		logger.trace("after copying the lastRow is "+Arrays.toString(lastRowOfGroupingColumnValues));
//    	}
	}
    
    
//    private void copyGroupingValuesToLastRowOfGroupingColumnValues(IGroupColumn[] groupingCols, NewRowEvent newRowEvent){
//    	for (int i = 0; i < groupingCols.length; i++) {
//    		lastRowOfGroupingColumnValues[i] = groupingCols[i].getValue(newRowEvent);
//    	}    	
//    }
    
    
    private int checkLevelChangedInGroupingColumns(List<IGroupColumn> groupingColumns, Object[] lastRowOfGroupingValues, NewRowEvent newRowEvent){
		boolean aggregationLevelFound = false;
		int i = 0;
		
		//TODO: groupings assumed ordered by grouping order: make sure they are ordered
		//iterate through last row for comparison with the new row of data
		IGroupColumn currentGroupColumn = null; 
		while (!aggregationLevelFound && i < groupingColumns.size()){
			currentGroupColumn = groupingColumns.get(i); 
			Object valueToBeCompared = currentGroupColumn.getValue(newRowEvent);
			if(logger.isTraceEnabled()){
				logger.trace(	"checking column "+currentGroupColumn.getClass()+ 
								" having grouping order "+currentGroupColumn.getGroupingLevel()+
								" and value "+valueToBeCompared + " against "+lastRowOfGroupingValues[i]);
			}
			if (!lastRowOfGroupingValues[i].equals(valueToBeCompared)) {
				//condition to exit from for loop
				aggregationLevelFound = true;
			}else{
				i++;
			}           
		}// end while
		return aggregationLevelFound ? i:-1; 
    }
    
}