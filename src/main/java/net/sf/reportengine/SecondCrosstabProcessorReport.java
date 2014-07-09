/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.IReportAlgorithm;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.core.steps.crosstab.CrosstabHeaderOutputInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;

/**
 * This is for internal use only. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
class SecondCrosstabProcessorReport extends AbstractOneIterationReport {
	
	private CtMetadata ctMetadata = null; 
	
	public SecondCrosstabProcessorReport(CtMetadata metadata){
		this.ctMetadata = metadata; 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.AbstractReport#configAlgorithmSteps()
	 */
	@Override
	protected void configAlgorithmSteps() {
		IReportAlgorithm algorithm = getAlgorithm();
    	IReportContext context = algorithm.getContext();
    	
    	//setting the input/output
    	algorithm.setIn(getIn());
    	algorithm.setOut(getOut());
    	
    	//context keys specific to a flat report
		context.set(ContextKeys.CONTEXT_KEY_DATA_COLUMNS, getDataColumns());
		context.set(ContextKeys.CONTEXT_KEY_GROUPING_COLUMNS, getGroupColumns()); 
		context.set(ContextKeys.CONTEXT_KEY_SHOW_TOTALS, getShowTotals());
    	context.set(ContextKeys.CONTEXT_KEY_SHOW_GRAND_TOTAL, getShowGrandTotal());
    	context.set(ContextKeys.CONTEXT_KEY_CROSSTAB_METADATA, ctMetadata); 
    	
    	//adding steps to the algorithm
    	algorithm.addInitStep(new FlatReportExtractDataInitStep());
    	algorithm.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//algorithm.addInitStep(new ColumnHeaderOutputInitStep("Second process "));
        
    	algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new GroupingLevelDetectorStep());
    	
    	if(getShowTotals() || getShowGrandTotal()){
    		algorithm.addMainStep(new FlatReportTotalsOutputStep());
    		algorithm.addMainStep(new TotalsCalculatorStep());
    	}
    	
        algorithm.addMainStep(new DataRowsOutputStep());
        
        if(getGroupColumns() != null && getGroupColumns().size() > 0){
        	algorithm.addMainStep(new PreviousRowManagerStep());
        }
	}
}
