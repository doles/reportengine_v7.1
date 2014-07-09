/*
 * Created on 02.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.ICrosstabData;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.CalculatorMatrix;
import net.sf.reportengine.util.ContextKeys;

import org.apache.log4j.Logger;

/**
 * this step is responsible for :
 * 	1. initializing / reinitializing the totals
 * 	2. computing the totals
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class TotalsCalculatorStep extends AbstractReportStep{
    
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(TotalsCalculatorStep.class);
	
	/**
     * the calculators matrix
     */
    private CalculatorMatrix calculatorMatrix;
    
    /**
     * 
     */
    int groupColsCnt = -1;
    
    /**
     * 
     * @param columns
     */
    public TotalsCalculatorStep(){
    	
    }
    
    /**
     * init
     */
    public void init(IReportContext reportContext){
        super.init(reportContext);
        
        //ICalculator[] prototypesCalc = extractCalculators(getDataColumns());
        
        //FOR FLAT REPORT groupingColumns are just grouping columns
        //FOR CROSSTAB REPORT groupingColums are grouping + data + header (not incl crosstab data, so we must add it below)   
        groupColsCnt = getGroupingColumns() != null ? getGroupingColumns().size() : 0;
        
        int dataColsCnt = getDataColumns() != null ? getDataColumns().size() : 0;
        
        //sometimes the SecondReportProcessReport (if doing crosstab) won't have any group columns, 
        //just data cols, from original data cols and totals will be messed up, so treat data cols as groups here 
        if(groupColsCnt == 0){
        	logger.warn("Dangerous setting: TotalsCalculatorStep was set in a report not having group columns");
        	groupColsCnt = dataColsCnt;
        }
        
        //groupColsCnt +crosstabData.size() (for Grand Total)
        List<ICrosstabData> crosstabData =(List<ICrosstabData>)reportContext.get(ContextKeys.CONTEXT_KEY_CROSSTAB_DATA);
        int crosstabDataLength = crosstabData == null ? 0 : crosstabData.size();        
        if(crosstabDataLength>0){
        	dataColsCnt = crosstabDataLength;
        }
        calculatorMatrix = new CalculatorMatrix(groupColsCnt + dataColsCnt, getDataColumns());
        calculatorMatrix.initAllCalculators();
        
        reportContext.set(ContextKeys.CONTEXT_KEY_CALCULATORS, calculatorMatrix.getCalculators());
    }
    
    /**
     * execute
     */
    public void execute(NewRowEvent newRowEvent){
		int aggLevel = getGroupingLevel();
		if(aggLevel >= 0){
			//if the current row is not a simple data row but contains a change in the grouping level 
			int rowsToBeReinitialized = groupColsCnt - aggLevel ;
			logger.trace("reinitializing totals for "+rowsToBeReinitialized+" rows in the totals table (aggLevel = "+aggLevel+")");
			calculatorMatrix.initFirstXRows(rowsToBeReinitialized);
			
			//if this is change in less than crosstab header row, init also grand totals
			List<ICrosstabData> crosstabData = (List<ICrosstabData>)getContext().get(ContextKeys.CONTEXT_KEY_CROSSTAB_DATA);
			int crosstabDataCols = crosstabData == null ? 0 : crosstabData.size();
			Integer originalGroupsCols = (Integer)getContext().get(ContextKeys.CONTEXT_KEY_ORIGINAL_CT_DATA_COLS_COUNT);
			Integer originalDataCols = (Integer)getContext().get(ContextKeys.CONTEXT_KEY_ORIGINAL_CT_GROUP_COLS_COUNT);	
			int originCols = (originalGroupsCols==null ? 0 : originalGroupsCols) + (originalDataCols==null ? 0 : originalDataCols);
			if(aggLevel<originCols) {
				int allCols = calculatorMatrix.getCalculators().length;
				for(int c = allCols - 1; c >= allCols - crosstabDataCols ;c--)
				calculatorMatrix.initRow(c);
			}
		}    		
		//add values to all calculators
		calculatorMatrix.addValuesToEachRow(newRowEvent);    		
    } 
}
