/*
 * Created on 30.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm.steps;

import net.sf.reportengine.core.algorithm.IReportContext;


/**
 * abstract implementation for the IAlgorithmStep providing 
 * an algorithmContext and default implementations for init(), exit() methods.
 * 
 * @author dragos balan(dragos.balan@gmail.com)
 */
public abstract class AbstractAlgorithmStep implements IAlgorithmMainStep {
    
    /**
     * this is a reference to the report context
     */
    private IReportContext algorithmContext;
    
    /**
     * default implementation for IAlgorithmInitStep.init() method
     * which only sets the algorithm context  
     * 
     */
    public void init(IReportContext algoContext){
        this.algorithmContext = algoContext;        
    }
    
    /**
     * just an empty implementation for exit 
     * @see net.sf.reportengine.core.algorithm.IAlgorithmMainStep#exit()
     */
    public void exit() {}
    
    
    /**
     * getter for the context
     * @return
     */
    protected IReportContext getContext(){
    	return algorithmContext;
    }
    

}
