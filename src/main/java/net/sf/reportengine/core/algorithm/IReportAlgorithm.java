/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import net.sf.reportengine.core.algorithm.steps.IAlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmMainStep;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.out.IReportOutput;

/**
 * <p>
 * 	this is the base interface for all report algorithm
 * </p> 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public interface IReportAlgorithm {
    
    /**
     * sets the input of the report.
     * @param input	
     */     
    public void setIn(IReportInput input);
    
    /**
     * add an init step to the algorithm
     * @param initStep
     */
    public void addInitStep(IAlgorithmInitStep initStep);
    
    /**
     * adds a main step to the algorithm
     * @param step  the main step to be added
     */
    public void addMainStep(IAlgorithmMainStep step);
    
    /**
     * 
     * @param exitStep
     */
    public void addExitStep(IAlgorithmExitStep exitStep);
    
    /**
     * executes the report and displays it
     */
    public void executeAlgorithm();
    
    /**
     * sets the output of the report
     * @param out
     */
    public void setOut(IReportOutput out);
    
    
    public IReportContext getContext();
}
