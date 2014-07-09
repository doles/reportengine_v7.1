/**
 * 
 */
package net.sf.reportengine.util;

/**
 * Enumeration for context keys
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public enum ContextKeys {
	
	
	/**
	 * the key used to identify the configuration columns inside the report context
	 */
	CONTEXT_KEY_DATA_COLUMNS, //= "net.sf.reportengine.dataColumns";
	
	/**
	 * the identifier of grouping columns inside the report context
	 */
	CONTEXT_KEY_GROUPING_COLUMNS, //= "net.sf.reportengine.groupingColumns";
	
	/**
	 * 
	 */
	CONTEXT_KEY_SHOW_TOTALS, // = "net.sf.reportengine.showTotals";
	CONTEXT_KEY_SHOW_GRAND_TOTAL, // = "net.sf.reportengine.showGrandTotal";
	CONTEXT_KEY_SUBTOTALS_OUTPUT, // = "net.sf.reportengine.subtotaltOut";
	CONTEXT_KEY_TOTAL_OUT_FILTERS, // = "net.sf.reportengine.totalFilters";
	
	/**
	 * 
	 */
	CONTEXT_KEY_CROSSTAB_GROUP_COLS, // = "net.sf.reportengine.crosstab.groupCols";
	CONTEXT_KEY_CROSSTAB_HEADER_ROWS, // = "net.sf.reportengine.crosstab.headerRows"; 
	CONTEXT_KEY_CROSSTAB_DATA, // = "net.sf.reportengine.crosstab.data"; 
	CONTEXT_KEY_CROSSTAB_METADATA, // = "net.sf.reportengine.crosstab.metadata";
	CONTEXT_KEY_CROSSTAB_HEADER_HAS_TOTALS, // = "net.sf.reportengine.crosstab.headerHasTotals";
	
	
	/**
	 * 
	 */
	CONTEXT_KEY_ORIGINAL_CT_GROUP_COLS_COUNT, // = "net.sf.reportengine.intermediate.origCtGroupColsCnt";
	CONTEXT_KEY_ORIGINAL_CT_DATA_COLS_COUNT, // = "net.sf.reportengine.intermediate.origCtDataColsCnt";

	/**
	 * context identifier (key) for aggregation level
	 */
	CONTEXT_KEY_NEW_GROUPING_LEVEL, // = "net.sf.reportengine.newGroupingLevel";

	/**
	 * the context key for the calculator matrix
	 */
	CONTEXT_KEY_CALCULATORS, // = "net.sf.reportengine.calculators";
	
	/**
	 * context key for distribution of calculators in the data columns
	 */
	CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS, // = "net.sf.reportengine.calcDistribution";

	/**
	 * the context key for computed cell values
	 */
	CONTEXT_KEY_COMPUTED_CELL_VALUES, // =  "net.sf.reportengine.compCellsValues";

	/**
	 * the context key for computed cell values
	 */
	CONTEXT_KEY_FORMATTED_CELL_VALUES, // =  "net.sf.reportengine.formattedCellValues";

	/**
	 * the context key for the header columns array
	 */
	CONTEXT_KEY_HEADER_COLUMNS, // = "net.sf.reportentinge.headerColumns";

	/**
	 * context key for last grouping values
	 */
	CONTEXT_KEY_LAST_GROUPING_VALUES, // = "net.sf.reportengine.prevGroupingValues";

	/**
	 * context key for the distinct values holder
	 */
	CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER, // = "net.sf.reportengine.intermediate.distinctValuesHolder";

	/**
	 * context key for intermediate crosstab info
	 */
	CONTEXT_KEY_INTERMEDIATE_CROSSTAB_DATA_INFO, // = "net.sf.reportengine.intermediate.ctDataInfo";

	/**
	 * context key for intermediate row
	 */
	CONTEXT_KEY_INTERMEDIATE_ROW, // = "net.sf.reportengine.int.intermediateRow";
}
