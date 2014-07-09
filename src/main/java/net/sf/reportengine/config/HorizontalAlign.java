/**
 * 
 */
package net.sf.reportengine.config;

/**
 * enumeration of possible horizontal alignments
 * 
 * @author dragos balan
 * @since 0.6
 */
public enum HorizontalAlign {
	LEFT, 
	CENTER, 
	RIGHT; 
	
	@Override public String toString(){
		return this.name().toLowerCase(); 
	}
}
