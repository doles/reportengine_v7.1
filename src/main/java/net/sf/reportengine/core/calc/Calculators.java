/*
 * Created on 08.02.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

/**
 * utility collection of calculators 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class Calculators {
    
    /**
     * 
     */
    public static final ICalculator SUM = new SumCalculator();
    
    /**
     * 
     */
    public static final ICalculator COUNT = new CountCalculator();
    
    /**
     * 
     */
    public static final ICalculator AVG = new AvgCalculator();
    
    /**
     * 
     */
    public static final ICalculator MIN = new MinCalculator();
    
    /**
     * 
     */
    public static final ICalculator MAX = new MaxCalculator();
    
    /**
     * 
     */
    public static final ICalculator FIRST = new FirstCalculator();
    
    /**
     * 
     */
    public static final ICalculator LAST = new LastCalculator();
    
    
    private Calculators(){
        //dummy impl just to prevent inheritance
    }
}
