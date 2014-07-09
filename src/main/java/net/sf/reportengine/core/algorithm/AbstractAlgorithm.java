/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.LinkedList;
import java.util.List;

import net.sf.reportengine.core.algorithm.steps.IAlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmMainStep;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.out.IReportOutput;

import org.apache.log4j.Logger;

/**
 * @author dragos balan (dragos.balan@gmail.com)
 * @version $Revision$
 * $log$
 */
public abstract class AbstractAlgorithm implements IReportAlgorithm {
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(AbstractAlgorithm.class);
	
	/**
     * A list containing all the steps <code>net.sf.reportengine.algorithm.IAlgoritmStep</code>
     */
    private List<IAlgorithmMainStep> mainSteps;
    
    /**
     * A list containing <code>net.sf.reportengine.algorithm.IAlgorithmInitStep</cide>s 
     * to be performed only once (at the begining of the algorithm)
     */
    private List<IAlgorithmInitStep> initSteps;
    
    /**
     * A list containing <code>net.sf.reportengine.algorithm.IAlgorithmExitStep</code>s 
     * to be performed only once (at the begining of the algorithm)
     */
    private List<IAlgorithmExitStep> exitSteps;
    
    /**
     * the context of the report (holding important values)
     */
    private IReportContext algorithmContext;
    
    /**
     * 
     * @param context
     */
    public AbstractAlgorithm(){
        this.initSteps = new LinkedList<IAlgorithmInitStep>();
        this.mainSteps = new LinkedList<IAlgorithmMainStep>();
        this.exitSteps = new LinkedList<IAlgorithmExitStep>();
        this.algorithmContext = new DefaultReportContext();        
    }
    
    /**
     * implementation for IReportEngine.setIn
     */
    public void setIn(IReportInput input){
        this.algorithmContext.setInput(input);
    }
    
    /**
     * 
     */
    public void setOut(IReportOutput out){
        this.algorithmContext.setOutput(out);
    }
	
    /**
     * adds a new step to the algorithm
     * @param newStep   the new step to be added to the report algorithm
     */
    public void addMainStep(IAlgorithmMainStep newStep){
        mainSteps.add(newStep);        
    }
    
    /**
     * adds a new init step to the algorithm
     * @param initStep  the step to be added
     */
    public void addInitStep(IAlgorithmInitStep initStep){
        initSteps.add(initStep);
    }
    
    /**
     * adds a new exit step to the algorithm
     * @param exitStep  the step to be added
     */
    public void addExitStep(IAlgorithmExitStep exitStep){
        exitSteps.add(exitStep);
    }

    /**
     * getter for the context of the algorithm
     * @return
     */
    public IReportContext getContext(){
    	return algorithmContext;
    }
    
    
    protected void openInput(){
    	algorithmContext.getInput().open();
    }
    
    protected void closeInput(){
    	algorithmContext.getInput().close();
    }
    
    protected void openOutput(){
    	algorithmContext.getOutput().open();
    }
    
    protected void closeOutput(){
    	algorithmContext.getOutput().close();
    }
    
    public List<IAlgorithmInitStep> getInitSteps(){
    	return initSteps;
    }
    
    public List<IAlgorithmExitStep> getExitSteps(){
    	return exitSteps;
    }
    
    public List<IAlgorithmMainStep> getMainSteps(){
    	return mainSteps;
    }
    
    protected IReportInput getInput(){
    	return algorithmContext.getInput();
    }
}
