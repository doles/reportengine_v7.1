/**
 * 
 */
package net.sf.reportengine.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.in.ReportInputException;
import net.sf.reportengine.out.ReportOutputException;

/**
 * @author dragos balan
 * @since 0.7
 */
public final class ReportIoUtils {
	
	/**
	 * system file encoding 
	 */
	public final static String SYSTEM_FILE_ENCODING = System.getProperty("file.encoding"); 
	
	/**
	 * utf-8 encoding 
	 */
	public final static String UTF8_ENCODING =  "UTF-8"; 
	
	/**
	 * the default system line separator
	 */
	public final static String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/**
	 * 
	 */
	private ReportIoUtils(){
		
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static Reader createReaderFromPath(String filePath){
		return createReaderFromPath(filePath, UTF8_ENCODING);
	}
	
	
	/**
	 * 
	 * @param filePath
	 * @param encoding
	 * @return
	 */
	public static Reader createReaderFromPath(String filePath, String encoding){
		try{
			return new InputStreamReader(	new FileInputStream(filePath), 
											encoding);
		}catch(FileNotFoundException fnf){
			throw new ReportInputException(fnf);
		}catch(UnsupportedEncodingException uee){
			throw new ReportInputException(uee);
		}
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static Writer createWriterFromPath(String filePath){
		return createWriterFromPath(filePath, UTF8_ENCODING); 
	}
	
	/**
	 * 
	 * @param filePath
	 * @param encoding
	 * @return
	 */
	public static Writer createWriterFromPath(String filePath, String encoding){
		try {
			return new OutputStreamWriter(	new FileOutputStream(filePath), 
											encoding);
		} catch (FileNotFoundException e) {
			throw new ReportOutputException(e); 
		} catch(UnsupportedEncodingException e){
			throw new ReportOutputException(e); 
		}
	}
	
	/**
	 * 
	 * @param classpath
	 * @return
	 */
	public static Reader createReaderFromClassPath(String classpath){
		return createReaderFromClassPath(classpath, UTF8_ENCODING);
	}
	
	/**
	 * 
	 * @param classPath
	 * @param encoding
	 * @return
	 */
	public static Reader createReaderFromClassPath(String classPath, String encoding){
		try {
			return new InputStreamReader(
					ClassLoader.getSystemResourceAsStream(classPath), 
					encoding);
		} catch (UnsupportedEncodingException e) {
			throw new ReportEngineRuntimeException(e); 
		}
	}
	
	/**
	 * 
	 * @param classPath
	 * @return
	 */
	public static InputStream createInputStreamFromClassPath(String classPath){
		return ClassLoader.getSystemResourceAsStream(classPath); 
	}
}
