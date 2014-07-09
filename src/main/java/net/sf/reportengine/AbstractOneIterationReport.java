/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.core.algorithm.IReportAlgorithm;
import net.sf.reportengine.core.algorithm.OneIterationAlgorithm;

/**
 * abstract parent class for reports relying on one single iteration over the provided data
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 * @see FlatReport
 */
public abstract class AbstractOneIterationReport extends AbstractReport {
	
	/**
     * the algorithm behind flat reports
     */
    private OneIterationAlgorithm algorithm = new OneIterationAlgorithm();
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.AbstractReport#executeAlgorithm()
	 */
	@Override protected void executeAlgorithm(){
    	algorithm.executeAlgorithm();
    }
	
	public IReportAlgorithm getAlgorithm(){
		return algorithm; 
	}	
	
}
