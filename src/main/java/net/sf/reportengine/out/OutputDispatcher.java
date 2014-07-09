/*
 * Created on Aug 8, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * dispatches the output to the outputters
 * </p> 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.3
 */
public class OutputDispatcher implements IReportOutput{
    
    private List<IReportOutput> outputtersList;
    
    public OutputDispatcher(){
        outputtersList = new ArrayList<IReportOutput>(3);
    }

    public void open(){
        for(IReportOutput output: outputtersList){
            output.open();
        }
    }
    
    public void close(){
    	for(IReportOutput output: outputtersList){
            output.close();
        }
    }
    
    public void startRow(RowProps rowProperties){
    	for(IReportOutput output: outputtersList){
            output.startRow(rowProperties);
        }
    }
    
    public void endRow(){
    	for(IReportOutput output: outputtersList){
            output.endRow();
        }
    }
    
    public void output(CellProps cellProps){
    	for(IReportOutput output: outputtersList){
            output.output(cellProps);
        }
    }
    
//    public void emptyCell(int colspan, int contentType){
//        ListIterator outputIter = outputtersList.listIterator();
//        CellProps emptyValuedCellProps = null;
//        while(outputIter.hasNext()){
//            IReportOutput output = (IReportOutput)outputIter.next();
//            emptyValuedCellProps = new CellProps(output.getWhiteSpace(), colspan, contentType);
//            output.output(emptyValuedCellProps);
//        }
//    }
    
//    public void emptyCell(int colspan){
//    	emptyCell(colspan, ReportConstants.CONTENT_DATA);
//    }
    
    public void registerOutput(IReportOutput out){
        this.outputtersList.add(out);
    }
    
    public boolean hasOutputters(){
        return outputtersList.size() > 0;
    }

	public String getWhiteSpace() {
		return " ";
	}
}
