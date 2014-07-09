/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.RowProps;

/**
 * This is used only for debug 
 * 
 * @author dragos balan 
 * 
 */
public class IntermediateRowDisplayStep extends AbstractCrosstabStep {
	
	
	
	public IntermediateRowDisplayStep(){
		
	}
	
	
	public void init(IReportContext context){
		super.init(context);
		
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	@Override
	public void execute(NewRowEvent newRowEvent) {
		int groupingLevel = getGroupingLevel(); 
		
		if(groupingLevel >= 0){
			//if grouping level changed
			if(groupingLevel < getGroupingColumns().size()){
				//if grouping level changed for the GROUPING COLUMNS
				displayIntermediateDebugInfo(getIntermediateRow());
			}else{
				//if grouping level changed for the crosstabHeaderRows 
				
			}
		}else{
			//grouping level not changed
			
		}
	}
	
	
	public void exit(){
		displayIntermediateDebugInfo(getIntermediateRow());
	}
	
	private void displayIntermediateDebugInfo(IntermediateReportRow intermediateRow){
		IReportOutput output = getOutput(); 
		
		output.startRow(new RowProps(ReportContent.DATA));
		output.output(new CellProps.Builder("Intermediate row:").build());
		for (IntermediateDataInfo element : intermediateRow.getIntermComputedDataList().getDataList()) {
			output.output(new CellProps.Builder(element.toString()).build());
		}		
		output.endRow(); 
	}
}
