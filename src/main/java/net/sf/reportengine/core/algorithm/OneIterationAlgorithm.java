/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.List;

import net.sf.reportengine.core.algorithm.steps.IAlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmMainStep;

import org.apache.log4j.Logger;

/**
 * <p>
 * This is a basic implementation for <code>IReportEngine</code>
 * and its main purpose it's to make things simpler for the classes 
 * derived from it
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2 
 */
public class OneIterationAlgorithm extends AbstractAlgorithm{
    
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(OneIterationAlgorithm.class);
	
    
    
    /**
     * constructor
     * @param context   the report context
     */
    public OneIterationAlgorithm(){
        super();
    }    
    
    
    /**
     * implementation for net.sf.reportengine.core.IReportEngine.execute();
     */
    public void executeAlgorithm() {
    	if(logger.isDebugEnabled()){
    		logger.debug(" executing one way iteration ");
    	}
        
    	if(getContext().getInput() == null){
    		throw new RuntimeException("Null input exception");
        }
                     
        //opening input
        openInput();
        
        openOutput();
            
        //execution of the init steps
        executeInitSteps();
            
        executeMainSteps();
        
        //calling the exit for all registered steps
        executeExitSteps();
        
        closeOutput();
        
        closeInput();
    } 
    
    /**
     * execution of init method for each init step
     */
    protected void executeInitSteps() {
    	IReportContext context = getContext();
    	List<IAlgorithmInitStep> initSteps = getInitSteps();
    	
        for(IAlgorithmInitStep initStep: initSteps){
            initStep.init(context);
        } 
    } 
    
    /**
     * 1. executes the init method for each main step 
     * 2. for each row of data ( from input) executes the execute method
     * 3. calls the exit method for each main step
     */
    protected void executeMainSteps() {
    	IReportContext context = getContext();
    	List<IAlgorithmMainStep> mainSteps = getMainSteps();
    	
    	//call init for each step
        for(IAlgorithmMainStep mainStep: mainSteps){
            mainStep.init(context);
        } 
        
        //iteration through input data (row by row)
        while(context.getInput().hasMoreRows()){
        	
            //get the current data row 
            Object[] currentRow = context.getInput().nextRow();
                
            //then we pass the dataRow through all the report steps
            for(IAlgorithmMainStep algoStep: mainSteps){
            	algoStep.execute(new NewRowEvent(currentRow));
            }
        }
        
        //call exit
        for(IAlgorithmMainStep mainStep: mainSteps){
           mainStep.exit();
        }
    }
    
    /**
     * calls the exit method
     */
    protected void executeExitSteps(){
    	List<IAlgorithmExitStep> exitSteps = getExitSteps();
    	for(IAlgorithmExitStep exitStep: exitSteps){
    	   exitStep.exit();
    	}    
    } 
}
