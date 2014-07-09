/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <p> Output based on freemarker templates.</p>
 * 
 * This report output has a template for every step of the report: 
 * <ol>
 * 	<li>startReport.ftl</li>
 *  <li>startRow.ftl</li>
 *  <li>cell.ftl</li>
 *  <li>endRow.ftl</li>
 *  <li>endReport.ftl</li>
 *  </ol>
 *  
 *  The default provided templates  will output html but you are strongly encouraged 
 *  to create your own templates for whatever format you like. Keep in mind that the template names 
 *  should remain the same but you can always change the location whether it's file system or classpath
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.7
 */
public class FreemarkerOutput extends AbstractCharacterOutput {
	
	/**
	 * freemarker configuration class
	 */
	private Configuration freemarkerConfig = null;
	
	/**
	 * report templates
	 */
	private Template startReportTemplate = null; 
	private Template endReportTemplate = null; 
	
	/**
	 * row templates
	 */
	private Template startRowTemplate = null;
	private Template endRowTemplate = null; 
	
	/**
	 * cell template
	 */
	private Template cellTemplate = null; 
	
	/**
	 * root data for templates
	 */
	private Map<String, ReportProps> rootDataForReportTemplate = null;
	private Map<String, RowProps> rootDataForRowTemplate = null;
	private Map<String, CellProps> rootDataForCellTemplate = null; 
	
	
	/**
	 * the default templates class path
	 */
	public final static String DEFAULT_HTML_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/freemarker/html"; 
	public final static String START_REPORT_TEMPLATE = "startReport.ftl";
	public final static String END_REPORT_TEMPLATE = "endReport.ftl";
	public final static String START_ROW_TEMPLATE = "startRow.ftl";
	public final static String END_ROW_TEMPLATE = "endRow.ftl";
	public final static String CELL_TEMPLATE = "cell.ftl";
	
	
	/**
	 * Output into memory (using a StringWriter). 
	 * To change the default output writer one can use this constructor together 
	 * with {@link #setOutputWriter(Writer)} or {@link #setFilePath(String)}
	 */
	public FreemarkerOutput(){
		super(); 
	}
	
	/**
	 * Output into the specified file using Utf-8 encoding
	 * @param filePath	the path of the file
	 */
	public FreemarkerOutput(String filePath){
		super(filePath);
	}
	
	/**
	 * Output into the specified file using the provided freemarker configuration
	 * 
	 * @param filePath			the path of the output file
	 * @param freemarkerConfig	the freemarker configuration 
	 */
	public FreemarkerOutput(String filePath, Configuration freemarkerConfig){
		super(filePath); 
		setFreemarkerConfig(freemarkerConfig); 
	}
	
	/**
	 * output into the specified writer
	 * 
	 * @param writer	the output writer
	 */
	public FreemarkerOutput(Writer writer){
		super(writer); 
	}
	
	/**
	 * output into the specified writer using the configuration provided
	 * 
	 * @param writer			the output writer
	 * @param freemarkerConfig	the freemarker configuration
	 */
	public FreemarkerOutput(Writer writer, Configuration freemarkerConfig){
		super(writer); 
		setFreemarkerConfig(freemarkerConfig); 
	}
	
	
	/**
	 * initializer for the default html freemarker configuration
	 */
	protected Configuration initFreemarkerDefaultConfig(){
		Configuration freemarkerConfig = new Configuration(); 
		freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		freemarkerConfig.setTemplateLoader(
				new ClassTemplateLoader(getClass(), 
										DEFAULT_HTML_TEMPLATES_CLASS_PATH)); 
		return freemarkerConfig; 
	}
	
	/**
	 * init for root freemarker data
	 */
	protected void initRootFreemarkerData(){
		//init root data
		rootDataForReportTemplate = new HashMap<String, ReportProps>();
		rootDataForCellTemplate = new HashMap<String, CellProps>(); 
		rootDataForRowTemplate = new HashMap<String, RowProps>();
	}
	
	/**
	 * opens the output by : 
	 * <ol>
	 * 		<li>creating the default freemarker configuration (if none was provided)</li>
	 * 		<li>creating the root data for the templates</li>
	 * 		<li>calling the startReport template</li>
	 * </ol>
	 */
	public void open() {
		super.open(); 
		try {
			if(freemarkerConfig == null){
				freemarkerConfig = initFreemarkerDefaultConfig(); 
			}
			
			//init root hash maps
			initRootFreemarkerData(); 
			
			//get the templates from configuration
			initRootTemplateData();
			
			//process the start report template
			ReportProps reportProps = new ReportProps(); 
			rootDataForReportTemplate.put("reportProps", reportProps);
			startReportTemplate.process(rootDataForReportTemplate, getOutputWriter()); 
			
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} 
	}

	/**
	 * initializes the root data for all freemarker templates
	 * @throws IOException
	 */
	protected void initRootTemplateData() throws IOException {
		startReportTemplate = freemarkerConfig.getTemplate(START_REPORT_TEMPLATE); 
		endReportTemplate = freemarkerConfig.getTemplate(END_REPORT_TEMPLATE); 
		startRowTemplate = freemarkerConfig.getTemplate(START_ROW_TEMPLATE);
		endRowTemplate = freemarkerConfig.getTemplate(END_ROW_TEMPLATE); 
		cellTemplate = freemarkerConfig.getTemplate(CELL_TEMPLATE);
	}
	
	/**
	 * calls the start row template
	 */
	public void startRow(RowProps rowProperties) {
		super.startRow(rowProperties); 
		try {
			rootDataForRowTemplate.put("rowProps", rowProperties); 
			startRowTemplate.process(rootDataForRowTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}		
	}
	
	/**
	 * calls the end row template 
	 */
	public void endRow() {
		try {
			endRowTemplate.process(rootDataForRowTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	
	/**
	 * calls the cell.ftl template
	 */
	public void output(CellProps cellProps) {
		try {
			rootDataForCellTemplate.put("cellProps", cellProps); 
			cellTemplate.process(rootDataForCellTemplate, getOutputWriter());
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} 
	}
	
	/**
	 * calls the end report template and closes the writer
	 */
	public void close() {
		try {
			endReportTemplate.process(rootDataForReportTemplate, getOutputWriter());
			super.close(); //flush and close the writer
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} 
	}

	/**
	 * @return the freemarkerConfig
	 */
	public Configuration getFreemarkerConfig() {
		return freemarkerConfig;
	}

	/**
	 * @param freemarkerConfig the freemarkerConfig to set
	 */
	public void setFreemarkerConfig(Configuration freemarkerConfig) {
		this.freemarkerConfig = freemarkerConfig;
	}
}
