/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.List;

import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessDataColumnFromOriginalDataColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;

import org.apache.log4j.Logger;

/**
 * displays the column headers for the crosstab report
 * 
 * @author dragos balan
 * @since 0.4
 */
public class CrosstabHeaderOutputInitStep implements IAlgorithmInitStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(CrosstabHeaderOutputInitStep.class);
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep#init(net.sf.reportengine.core.algorithm.IAlgorithmContext)
	 */
	public void init(IReportContext reportContext) {
		IReportOutput reportOutput = (IReportOutput)reportContext.getOutput();
		List<IDataColumn> dataColumns = (List<IDataColumn>)reportContext.get(ContextKeys.CONTEXT_KEY_DATA_COLUMNS);
		List<IGroupColumn> groupColumns = (List<IGroupColumn>)reportContext.get(ContextKeys.CONTEXT_KEY_GROUPING_COLUMNS); 
		//ICrosstabHeaderRow[] headerRows = (ICrosstabHeaderRow[])reportContext.get(ContextKeys.CONTEXT_KEY_CROSSTAB_HEADER_ROWS); 
		CtMetadata ctMetadata = (CtMetadata)reportContext.get(ContextKeys.CONTEXT_KEY_CROSSTAB_METADATA);
		
		//display header rows
		outputHeaderRows(reportOutput, ctMetadata, dataColumns, groupColumns); 
	}
	
	/**
	 * 
	 * @param reportOutput
	 * @param ctMetadata
	 * @param dataCols
	 * @param groupCols
	 */
	private void outputHeaderRows(	IReportOutput reportOutput, 
									CtMetadata ctMetadata, 
									List<IDataColumn> dataCols, 
									List<IGroupColumn> groupCols){
		//loop through all header rows
		for (int currHeaderRow = 0; currHeaderRow < ctMetadata.getHeaderRowsCount(); currHeaderRow++) {
			reportOutput.startRow(new RowProps(ReportContent.COLUMN_HEADER)); 
			
			boolean isLastHeaderRow = currHeaderRow == ctMetadata.getHeaderRowsCount()-1; 
			
			//1. handle grouping columns header first 
			displayHeaderForGroupingCols(	groupCols, 
											reportOutput,
											isLastHeaderRow);
			
			//2. now loop through data columns
			int currentColumn = 0; 
			while(currentColumn < dataCols.size()){
				IDataColumn currentDataColumn = dataCols.get(currentColumn);
				
				//if this column is a column created during 
				if(currentDataColumn instanceof SecondProcessDataColumn){
					int colspan = displayDataColumnHeader(	(SecondProcessDataColumn)currentDataColumn,
															reportOutput,
															ctMetadata, 
															currHeaderRow, 
															isLastHeaderRow);
					currentColumn += colspan; 
				}else{
					if(currentDataColumn instanceof SecondProcessTotalColumn){
						displayHeaderForTotalColumn((SecondProcessTotalColumn)currentDataColumn,
													reportOutput, 
													ctMetadata, 
													currHeaderRow);
						currentColumn++;
					}else{
						if(currentDataColumn instanceof SecondProcessDataColumnFromOriginalDataColumn){
							displayHeaderForOriginalDataColumn(	currentDataColumn, 
																reportOutput,
																isLastHeaderRow);
							currentColumn++; 
						}else{
							//no other type of data column is accepted
							throw new IllegalArgumentException("there's no handler for "+currentDataColumn.getClass());
						}
					}
				}
			}//end while 
			reportOutput.endRow(); 
		}
	}

	/**
	 * displays the headers for group columns
	 * @param groupCols
	 * @param reportOutput
	 * @param isLastHeaderRow
	 */
	private void displayHeaderForGroupingCols(	List<IGroupColumn> groupCols,
												IReportOutput reportOutput, 
												boolean isLastHeaderRow) {
		//if last header row write the normal column headers
		if(groupCols != null && groupCols.size() > 0){
			if(isLastHeaderRow){
				//for group columns only the last header row will contain something 
				// the first will be empty	
				for (int i = 0; i < groupCols.size(); i++) {
					reportOutput.output(new CellProps.Builder(groupCols.get(i).getHeader())
												.colspan(1)
												.contentType(ReportContent.COLUMN_HEADER)
												.horizAlign(HorizontalAlign.CENTER)
												.build()); 
				}
			}else{
				//first header rows will contain only spaces (for group headers):
				for (int i = 0; i < groupCols.size(); i++) {
					reportOutput.output(CellProps.EMPTY_CELL); 
				}
			}
		}else{
			if(logger.isDebugEnabled())logger.debug("no group columns headers found"); 
		}		
	}

	/**
	 * displays the header for the original data columns
	 * @param currentDataColumn
	 * @param reportOutput
	 * @param isLastHeaderRow
	 */
	private void displayHeaderForOriginalDataColumn(	IDataColumn currentDataColumn, 
														IReportOutput reportOutput,
														boolean isLastHeaderRow) {
		//only on the last header row we display the header values for the original data columns
		if(isLastHeaderRow){
			SecondProcessDataColumnFromOriginalDataColumn originalDataColumn = (SecondProcessDataColumnFromOriginalDataColumn)currentDataColumn; 
			reportOutput.output(new CellProps.Builder(originalDataColumn.getHeader())
											.colspan(1)
											.contentType(ReportContent.COLUMN_HEADER)
											.horizAlign(HorizontalAlign.CENTER)
											.build()); 
		}else{
			//first header rows will contain empty cells
			reportOutput.output(CellProps.EMPTY_CELL);
		}
	}

	/**
	 * displays the headers for data columns of type SecondProcessTotalColumn
	 * 
	 * @param secondProcessTotalCol
	 * @param reportOutput
	 * @param ctMetadata
	 * @param currHeaderRow
	 */
	private void displayHeaderForTotalColumn(	SecondProcessTotalColumn secondProcessTotalCol,
												IReportOutput reportOutput, 
												CtMetadata ctMetadata, 
												int currHeaderRow) {
		int[] position = secondProcessTotalCol.getPosition();
		
		if(position != null){
			if(currHeaderRow < position.length){
				Object value = ctMetadata.getDistincValueFor(currHeaderRow, position[currHeaderRow]);
				reportOutput.output(new CellProps.Builder(value)
											.colspan(1)
											.contentType(ReportContent.COLUMN_HEADER)
											.horizAlign(secondProcessTotalCol.getHorizAlign())
											.build());
			}else{
				//if there's no position for this header row then this is a hard-coded "TOTAL" 
				if(currHeaderRow == position.length){
					reportOutput.output(new CellProps.Builder("Total")
												.contentType(ReportContent.COLUMN_HEADER)
												.horizAlign(HorizontalAlign.CENTER)
												.build());
				}else{
					reportOutput.output(CellProps.EMPTY_CELL);
				}
			}
		}else{
			//the only data column that has null positions is the grand total column
			if(currHeaderRow == 0){
				reportOutput.output(new CellProps.Builder("Grand Total")
											.contentType(ReportContent.COLUMN_HEADER)
											.horizAlign(HorizontalAlign.LEFT)
											.build());
			}else{
				reportOutput.output(CellProps.EMPTY_CELL);
			}
		}
	}

	/**
	 * displays the column header for objects of type SecondProcessDataColumn
	 * 
	 * @param secondProcDataColumn
	 * @param reportOutput
	 * @param ctMetadata
	 * @param currHeaderRow
	 * @param isLastHeaderRow
	 * 
	 * @return	the colspan
	 */
	private int displayDataColumnHeader(SecondProcessDataColumn secondProcDataColumn,
										IReportOutput reportOutput,
										CtMetadata ctMetadata, 
										int currHeaderRow, 
										boolean isLastHeaderRow ) {
		int colspan = 1;
		if(!isLastHeaderRow){
			//for all rows except the last header row we read the colspan
			colspan = ctMetadata.getColspanForLevel(ctMetadata.getHeaderRowsCount()-2);
		}
		
		Object value = ctMetadata.getDistincValueFor(currHeaderRow, secondProcDataColumn.getPosition()[currHeaderRow]);
		reportOutput.output(new CellProps.Builder(value)
									.colspan(colspan)
									.contentType(ReportContent.COLUMN_HEADER)
									.horizAlign(secondProcDataColumn.getHorizAlign())
									.build());
		return colspan;
	}
}