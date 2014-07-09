<?xml version="1.0"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>

<xsl:template match="report">
	<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
    
    	<fo:layout-master-set>
        	<fo:simple-page-master 	master-name="A3" 
        						page-height="59.4cm" 
        						page-width="42cm" 
        						margin-top="2cm" 
        						margin-bottom="2cm" 
        						margin-left="2cm" 
        						margin-right="2cm">
          		<fo:region-body/>
        	</fo:simple-page-master>
        
        	<fo:simple-page-master 	master-name="A4" 
        						page-width="297mm"
								page-height="210mm" 
								margin-top="1cm" 
								margin-bottom="1cm"
								margin-left="1cm" 
								margin-right="1cm">
  				<fo:region-body margin="3cm"/>
  				<fo:region-before extent="2cm"/>
  				<fo:region-after extent="2cm"/>
  				<fo:region-start extent="2cm"/>
  				<fo:region-end extent="2cm"/>
			</fo:simple-page-master>
		</fo:layout-master-set>
      
      
      	<fo:page-sequence master-reference="A4">
      		<fo:flow flow-name="xsl-region-body">
        
			<fo:block 	font-family="ArialUnicodeMS" 
						font-size="24pt" 
						font-style="normal" 
						space-after="5mm"
						text-align="center">
				<xsl:value-of select="title" />
          	</fo:block> 
			
			<fo:block>
				 <fo:table 	border-top-style="solid"
				 			border-bottom-style="solid"
				 			border-left-style="solid"
				 			border-right-style="solid">
				 			<!-- border-top-color="#000000"
				 			border-bottom-color="#000000"
				 			border-left-color="#000000"
				 			border-right-color="#000000" -->
					<fo:table-header border-bottom-style="solid">
						<xsl:for-each select="table-header">
							<fo:table-row>
								<xsl:attribute name="background-color">#000000</xsl:attribute>
								<xsl:for-each select="cell">
									<xsl:variable name="colspan" select="@colspan" />
									<xsl:variable name="horizAlign" select="@horizAlign" />
									<fo:table-cell 	number-columns-spanned="{$colspan}"
													padding-left="3pt"
													padding-right="3pt"
													padding-top="1pt"
													padding-bottom="1pt">
		 								<fo:block 	color="white"
		 											font-family="ArialUnicodeMS" 
													font-size="14pt"
													font-style="normal" 
													font-weight="bold"
													margin-left="10pt" 
													text-align="{$horizAlign}" >													
											<xsl:value-of select="current()" />
										</fo:block>
									</fo:table-cell>
								</xsl:for-each>
							</fo:table-row>
						</xsl:for-each>			
					</fo:table-header>
					
					
					<fo:table-body>
						<xsl:for-each select="row">
							<fo:table-row>
								<xsl:choose>
									<xsl:when test="@rowNumber mod 2 = 0">
										<xsl:attribute name="background-color">#C0C0C0</xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="background-color">#FFFFFF</xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
								
								<xsl:for-each select="cell">
									<xsl:variable name="colspan" select="@colspan" />
									<xsl:variable name="horizAlign" select="@horizAlign" />
									<fo:table-cell 	number-columns-spanned="{$colspan}"
													padding-left="3pt"
													padding-right="3pt"
													padding-top="1pt"
													padding-bottom="1pt"
													border-top-style="solid"
				 									border-bottom-style="solid"
				 									border-left-style="solid"
				 									border-right-style="solid"
				 									>
				 									<!-- border-top-color="#000000"
				 									border-bottom-color="#000000"
				 									border-left-color="#000000"
				 									border-right-color="#000000" -->
		 								<fo:block 	color="black"
		 											font-family="ArialUnicodeMS" 
													font-size="12pt"
													font-style="normal" 
													font-weight="normal"
													margin-left="10pt"
													text-align="{$horizAlign}">
											<xsl:value-of select="current()" />
										</fo:block>
									</fo:table-cell>
								</xsl:for-each>
							</fo:table-row>
						</xsl:for-each>
					</fo:table-body>	
									
				</fo:table>	
			</fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
</xsl:template>
</xsl:stylesheet>
