/*
 * Created on 29.01.2005
 * 
 */
package net.sf.reportengine.util;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * class containing methods usefull for working with matrixes
 *  
 * @author dragosh
 */
public class MatrixUtils {
    
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(MatrixUtils.class);
	
    /**
     * empty implementation 
     *
     */
    private MatrixUtils(){
        
    }
	
	public static void logMatrix(Object[][] m) {
		logMatrix(null, m);
	}
    
    public static void logMatrix(String comment, Object[][] m){
        if(comment != null){
            logger.debug(comment);
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < m.length; i++) {
        	buffer.append("line : "+i+" length : "+m[i].length +" : ");
            for (int j = 0; j < m[i].length; j++) {
                buffer.append(m[i][j] + "\t");
            }
            logger.debug(buffer);
            buffer.delete(0, buffer.length());
        }
    }
    
    /**
	 * logs an ArrayList to the standars output
	 * @param list a list of Object[] array (i.e. a variable row matrix)
	 */
	public static void logMatrix(List list) {
		int index = 0;
		StringBuffer buffer = new StringBuffer();
		while (index < list.size()) {
		    Object[] row = (Object[]) list.get(index++);
		    buffer.append("line : "+index+" length : "+row.length +" : ");
			for (int i = 0; i < (row.length - 1); i++) {
			    buffer.append(" " + row[i]);
			}
			buffer.append(" " + row[row.length - 1]);
			logger.debug(buffer);
			buffer.delete(0, buffer.length());
		}
	}
	
	
	/**
	 * compares the objects whithin two matrixes
	 * @param matrix1
	 * @param matrix2
	 * @return
	 */
	public static boolean compareMatrices(Object[][] matrix1, Object[][] matrix2){
		boolean result = true;
		if(matrix1.length != matrix2.length){
			throw new IllegalArgumentException("the two matrixes don't have the same number of lines "+matrix1.length+" != "+matrix2.length);
		}
		
		for(int i=0; i<matrix1.length && result; i++){
			if(matrix1[i].length != matrix2[i].length){
				throw new IllegalArgumentException("the two matrixes don't have the same number of columns on line "+i+". "+matrix1[i].length+" <> "+matrix2[i].length);
			}else{
				for(int j=0; j<matrix1[i].length && result; j++){
					if(!matrix1[i][j].equals(matrix2[i][j])){
						result = false;
						logger.debug("row="+i+",col="+j+" the object "+matrix1[i][j]+" is not equal to "+matrix2[i][j]+" stopping iteration in matrix");
					}
				}
			}
		}
		return result;
	}
	
	
	/**
	 * compares all values whithin the two matrixes but before comparison it calls the toString method
	 * 
	 * @param matrix1
	 * @param matrix2
	 * 
	 * @return	
	 */
    public static boolean compareMatricesAsStrings(Object[][] matrix1, Object[][] matrix2){
        boolean result = true;
        if(matrix1.length != matrix2.length){
            throw new IllegalArgumentException("the two matrixes doesn't have the same number of lines");
        }
        
        for(int i=0; i<matrix1.length && result; i++){
            if(matrix1[i].length != matrix2[i].length){
                throw new IllegalArgumentException("the two matrixes doesn't have the same number of columns on line "+i);
            }else{
                for(int j=0; j<matrix1[i].length && result; j++){
                    if(matrix1[i][j] != null && matrix2[i][j] != null){
                    	String value1 = matrix1[i][j].toString(); 
                    	String value2 = matrix2[i][j].toString(); 
                    	
                        if(!value1.equals(value2)){
                            result = false;
                            //System.out.println(" values "+value1+" and "+value2+" are different");
                        }
                    }else{
                        if( (matrix1[i][j] != null && matrix2[i][j] == null)
                            || 
                            (matrix1[i][j] != null && matrix2[i][j] == null)
                          )
                        {
                            result = false;
                        }else{
                            //both null just continue
                        }
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * multiplies and inserts the values specified in the 
     * additionalArray inside the resulting matrix<br>
     * <b>Just a simple example :</b><br>
     * calling this method with the following source : 
     *  <code>South   Italy   Rome    Catholics   Males   100 200 300</code> 
     *  and the additionalArray : <code>Count, Avg, Max</code>
     *  the result will be as follows:<br>
     *  <code>
     *      South   Italy   Rome    Catholics   Males   Count   100 <br>
     *      South   Italy   Rome    Catholics   Males   Avg     200 <br>
     *      South   Italy   Rome    Catholics   Males   Max     300
     *  </code>     
     *
     * @param source            the source
     * @param additionalArray
     * @return                  a matrix of objects
     */
    public static Object[][] multiplyLines(Object[] source, String[] additionalArray) {
        //          y x Count Max Avg (indicator names)
        // Sud Arges Pitesti | Ortdox Masc | 100 300 200
        //
        // will become :
        // Sud Arges Pitesti Ortodox Masc Count 100
        // Sud Arges Pitesti Ortodox Masc Max 300
        // Sud Arges Pitesti Ortodox Masc Avg 200

        Object[][] toReturn = new Object[additionalArray.length][];
        for (int i = 0; i < additionalArray.length; i++) {
            toReturn[i] = new Object[source.length
                    - additionalArray.length + 2];
            //primele valori pana la offset se vor copia
            int countValuesToCopy = source.length - additionalArray.length;
            System.arraycopy(source, 0,
                            toReturn[i], 0,
                            countValuesToCopy);
            // urmatorele valori dupa offset se vor genera
            toReturn[i][countValuesToCopy] = additionalArray[i];
            toReturn[i][countValuesToCopy + 1] = source[countValuesToCopy
                    + i];

        }
        return toReturn;
    }
}
