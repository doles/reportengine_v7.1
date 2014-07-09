/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.util.ContextKeys;

/**
 * extracts some useful data to be used by the next steps in the report. 
 * 
 * 1.The distribution of calculators among data columns
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class FlatReportExtractDataInitStep implements IAlgorithmInitStep {
	
	/**
	 * 
	 */
	public final static int NO_CALCULATOR_ON_THIS_POSITION = -1; 
	
	/**
     * this array keeps track of the distribution of calculators in the dataColumns array. 
     * For example: We have a report with 6 data columns but only 3 of them have calculators (let's say the 2nd and the 4th and the 5th) 
     * In order to be able to retrieve the right calculator value for a given dataColumn index ( as set in the configuration of the report)
     * we will fill this array with 
     * 
     * array index:		0				1		2				3		4		5
     * array value: 	NO_CALCULATOR	0		NO_CALCULATOR	1		2		NO_CALCULATOR
     * 
     * Next time when we have the data column index and we need the result of the calculator (in a specific row of the matrix)
     * we will call  calculatorsDistributionInDataColumnsArray[dataColumIndex] and we will get the index of the column in the calculator matrix
     */
    private int[] calculatorsDistributionInDataColumnsArray; 
	
    
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep#init(net.sf.reportengine.core.algorithm.IAlgorithmContext)
	 */
	public void init(IReportContext reportContext) {
		List<IDataColumn> dataCols = 
				(List<IDataColumn>)reportContext.get(ContextKeys.CONTEXT_KEY_DATA_COLUMNS);
		calculatorsDistributionInDataColumnsArray = extractDistributionOfCalculatorsAcrossColumns(dataCols); 
		reportContext.set(	ContextKeys.CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS, 
							calculatorsDistributionInDataColumnsArray);
	}
	
	/**
	 * 
	 * @param dataCols
	 * @return
	 */
	private int[] extractDistributionOfCalculatorsAcrossColumns(List<IDataColumn> dataCols){
		int[] result = new int[dataCols.size()];
    	
    	int columnWithCalculatorsCount = 0;
    	for(int i=0; i<dataCols.size(); i++){
    		ICalculator calculator = dataCols.get(i).getCalculator();
    		if(calculator != null){
    			result[i] = columnWithCalculatorsCount; 
    			columnWithCalculatorsCount++;
    		}else{
    			result[i] = NO_CALCULATOR_ON_THIS_POSITION; 
    		}
    	}
		return result; 
	}

}
