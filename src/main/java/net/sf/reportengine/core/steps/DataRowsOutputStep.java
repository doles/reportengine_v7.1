/*
 * Created on 30.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.RowProps;

/**
 * <p>
 *  Output step used mainly on Flat reports
 * </p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class DataRowsOutputStep extends AbstractReportStep {
    
	private int finalReportGroupCount = -1;
	private List<IGroupColumn> groupCols = null;
	private List<IDataColumn> dataColumns = null; 
			
	
	
	/**
	 * this step's init method
	 */
	public void init(IReportContext context){
		super.init(context);
		
		groupCols = getGroupingColumns();
		dataColumns = getDataColumns();
		
		finalReportGroupCount = groupCols != null ? groupCols.size() : 0;
	}
    
	/**
     * execute. Constructs a cell for each value and sends it to output
     */
    public void execute(NewRowEvent newRowEvent) {
    	IReportOutput output = getOutput();
    	Object[] previousRowGrpValues = getPreviousRowOfGroupingValues();
		
		Object valueForCurrentColumn = null; 
		CellProps.Builder cellPropsBuilder = null; 
		
		//start the row
		output.startRow(new RowProps(ReportContent.DATA));
		
		//handle the grouping columns first
		IGroupColumn currentGrpCol = null; 
		for(int i=0; i<finalReportGroupCount; i++){
			currentGrpCol = groupCols.get(i);
			valueForCurrentColumn = currentGrpCol.getValue(newRowEvent);
			
			if(	currentGrpCol.showDuplicates() 
				|| previousRowGrpValues == null 
				|| !valueForCurrentColumn.equals(previousRowGrpValues[i])){
				cellPropsBuilder = new CellProps.Builder(currentGrpCol.getFormattedValue(valueForCurrentColumn));
			}else{
				cellPropsBuilder = new CellProps.Builder(IReportOutput.WHITESPACE);
			}
			cellPropsBuilder.horizAlign(currentGrpCol.getHorizAlign());
			output.output(cellPropsBuilder.build()); 
		}
		
		//then handle the data columns
		for(IDataColumn dataColumn : dataColumns){
			valueForCurrentColumn = dataColumn.getValue(newRowEvent);
			cellPropsBuilder = new CellProps.Builder(dataColumn.getFormattedValue(valueForCurrentColumn));
			cellPropsBuilder.horizAlign(dataColumn.getHorizAlign());
			output.output(cellPropsBuilder.build()); 
		}
    	
		//end row
		output.endRow();
    }
}
