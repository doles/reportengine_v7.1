/*
 * Created on Sep 8, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.reportengine.util.ReportIoUtils;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * Use this class for PDF, SVG, Postscript output. 
 * 
 * The Xsl-Fo report is done in two steps: <br/>
 * 
 * 1. Create temporary xml report (Stax) 
 * 2. Transform temporary xml according to the given xsl-fo template
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class XslFoOutput extends AbstractByteOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = Logger.getLogger(XslFoOutput.class);
	
	/**
	 * 
	 */
	private static final String DEFAULT_XSLFO_CLASSPATH = "net/sf/reportengine/fop/default-xml2fo.xslt";
	
	/**
	 * 
	 */
	private static final String DEFAULT_FO_CONF_PATH = "net/sf/reportengine/fop/fop.xconf";
	
	/**
	 * the mime type of the report
	 */
	private String mimeType;
	
	/**
	 * holds the FOP configuration 
	 */
	private InputStream configInputStream; 
	
	/**
     * stax report output to create the xml file 
     */
    private StaxReportOutput staxReportOutput = new StaxReportOutput();
    
    /**
     * the reader of the template
     */
    private Reader templateReader;
    
    /**
     * temporary xml file path
     */
    private String tempXmlFilePath;
    
	/**
	 * 
	 */
	public XslFoOutput(){
		super(); 
	}
	
	/**
	 * 
	 * @param filePath	the path of the result file
	 */
	public XslFoOutput(String filePath){
		super(filePath);
	}
	
	/**
	 * 
	 * @param filePath
	 * @param mimeType
	 */
	public XslFoOutput(String filePath, String mimeType){
		super(filePath); 
		setMimeType(mimeType); 
	}
	
	/**
	 * 
	 * @param outStream
	 */
    public XslFoOutput(OutputStream outStream) {
        super(outStream);
    }
    
    /**
     * 
     * @param outStream
     * @param mimeType
     */
    public XslFoOutput(OutputStream outStream, String mimeType){
    	super(outStream); 
    	setMimeType(mimeType); 
    }
    
    /**
     * 
     * @param outStream
     * @param mimeType
     * @param templateReader
     */
    public XslFoOutput(OutputStream outStream, String mimeType, Reader templateReader){
    	super(outStream); 
    	setMimeType(mimeType); 
    	setTemplateReader(templateReader); 
    }
    
    /**
     * 
     * @param outStream
     * @param mimeType
     * @param xsltInputStream
     * @param configInputStream
     */
    public XslFoOutput(	OutputStream outStream, 
    					String mimeType,
    					Reader templateReader, 
    					InputStream configInputStream) {
    	super(outStream); 
    	setMimeType(mimeType); 
    	setTemplateReader(templateReader); 
    	setFopConfigInputStream(configInputStream);
    }
    
    /**
     * 
     */
    public void open(){
    	try {
	    	super.open(); 
	    	if(mimeType == null){
	    		setMimeType(MimeConstants.MIME_PDF);
	    	}
	    	if(configInputStream == null){
	    		setFopConfigInputStream(ClassLoader.getSystemResourceAsStream(DEFAULT_FO_CONF_PATH)); 
	    	}
	    	if(getTemplateReader() == null){
				setTemplateReader(ReportIoUtils.createReaderFromClassPath(DEFAULT_XSLFO_CLASSPATH));
	    	}
	    	if(tempXmlFilePath== null){
	    		File tempXmlFile = File.createTempFile("report", ".tmp");
	    		if(LOGGER.isInfoEnabled())
	    			LOGGER.info("creating temporary xml file "+tempXmlFile.getAbsolutePath());
	    		setTempXmlFilePath(tempXmlFile.getAbsolutePath());
	    	}
	    	staxReportOutput.setFilePath(getTempXmlFilePath());
	    	staxReportOutput.open();
    	} catch (IOException e) {
    		throw new ReportOutputException(e); 
    	}
    }
    
    /**
     * 
     */
    public void close(){
    	try{
	    	staxReportOutput.close(); 
	    	transform();
	    	getTemplateReader().close(); 
	    	getFopConfigInputStream().close(); 
    	}catch(IOException ioExc){
    		throw new ReportOutputException(ioExc); 
    	}
    	super.close(); 
    }	
    
    public void transform(){
    	try {
    		DefaultConfigurationBuilder configBuilder = new DefaultConfigurationBuilder();
    		Configuration configuration  = configBuilder.build(configInputStream); 
    	
    		FopFactory fopFactory = FopFactory.newInstance();
    		fopFactory.setUserConfig(configuration); 
    		
    		//unfortunately fop requires an outputStream 
    		Fop fop = fopFactory.newFop(mimeType, getOutputStream());
		
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer(new StreamSource(getTemplateReader()));
			
			Source xmlSource = constructXmlSourceFromTempFile(); 
			Result result = new SAXResult(fop.getDefaultHandler());
			transformer.transform(xmlSource, result); 
			
		}catch(TransformerConfigurationException e){
			throw new ReportOutputException(e);
		}catch(TransformerException e){
			throw new ReportOutputException(e);
    	}catch (FOPException e) {
    		throw new ReportOutputException(e);
		} catch (ConfigurationException e) {
			throw new ReportOutputException(e);
		} catch (SAXException e) {
			throw new ReportOutputException(e);
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
    }
    
    
    
    
	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	/**
	 * 
	 */
	public void setFopConfigInputStream(InputStream fopConfigStream){
		this.configInputStream = fopConfigStream; 
	}
	
	/**
	 * 
	 * @return
	 */
	public InputStream getFopConfigInputStream(){
		return configInputStream; 
	}
	
	
	/**
     * 
     */
    public void startRow(RowProps rowProperties){
    	staxReportOutput.startRow(rowProperties);
    }
    
    /**
     * 
     */
    public void endRow(){
    	staxReportOutput.endRow();
    }
    
    /**
     * 
     */
    public void output(CellProps cellProps){
    	staxReportOutput.output(cellProps);
    }

	/**
	 * @return the templateReader
	 */
	public Reader getTemplateReader() {
		return templateReader;
	}

	/**
	 * @param templateReader the templateReader to set
	 */
	public void setTemplateReader(Reader templateReader) {
		this.templateReader = templateReader;
	}

	/**
	 * @return the tempXmlFileName
	 */
	public String getTempXmlFilePath() {
		return tempXmlFilePath;
	}

	/**
	 * @param tempXmlFileName the tempXmlFileName to set
	 */
	public void setTempXmlFilePath(String tempXmlFilePath) {
		this.tempXmlFilePath = tempXmlFilePath;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Source constructXmlSourceFromTempFile(){
		Source result = null; 
		if(tempXmlFilePath != null){
			result = new StreamSource(ReportIoUtils.createReaderFromPath(tempXmlFilePath));
		}else{
			LOGGER.warn("the construction of the xml source failed. No temporary xml file path was found"); 
		}
		return result; 
	}
}
