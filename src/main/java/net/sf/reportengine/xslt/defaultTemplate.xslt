<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
<xsl:output method="html" />

<xsl:template match="report">
<html>
<head>
	<title>
		<xsl:value-of select="title" />
	</title>
</head>
<body>
<table>
	<xsl:for-each select="table-header">
			<tr>
				<xsl:for-each select="cell">
					<xsl:variable name="colspan" select="@colspan" />
					<xsl:variable name="horizAlign" select="horizAlign" />
					<td colspan="{$colspan}" 
						style="	font-family='ArialUnicodeMS'  
							   	font-size='14pt'
								font-style='normal' 
								font-weight='bold'
								margin-left='10pt'
								text-align='{$horizAlign}' ">
						<xsl:value-of select="current()" />										
					</td>
				</xsl:for-each>
			</tr>
	</xsl:for-each>			
					
					
					
	<tbody>
		<xsl:for-each select="row">
				<tr>
					<xsl:choose>
						<xsl:when test="@rowNumber mod 2 = 0">
							<xsl:attribute name="bgcolor">#F5F5F5</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="bgcolor">#FFFFFF</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
								
					<xsl:for-each select="cell">
						<xsl:variable name="colspan" select="@colspan" />
						<td colspan="{$colspan}"
		 					style="	font-family='ArialUnicodeMS' 
									font-size='12pt'
									font-style='normal' 
									margin-left='10pt'">
											<xsl:value-of select="current()" />
						</td>						
					</xsl:for-each>
				</tr>
		</xsl:for-each>
	</tbody>	
</table>	
</body>	
</html>		
</xsl:template>
</xsl:stylesheet>
