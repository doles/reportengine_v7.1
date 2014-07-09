/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import net.sf.reportengine.core.algorithm.IReportContext;

/**
 * <p>
 *   Definition interface for an algorithm init step
 * </p>
 * @author dragos balan(dragos.balan@gmail.com)
 * @since 0.2
 */
public interface IAlgorithmInitStep {
    
    /**
     * this method is called only once for a report 
     * and represents the construction step where you can define the keys and 
     * values used inside the execute method.
     * <b>Warning: this is the only place where you can add keys to the report context
     *  
     * @param reportContext     the context of the report
     */
    public void init(IReportContext reportContext);
    
    

}