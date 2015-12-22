<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:fox="http://xml.apache.org/fop/extensions" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:saxon="http://icl.com/saxon" extension-element-prefixes="saxon">
	<xsl:template match="/Invoice">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="pagemaster1" page-height="845.0pt" page-width="598.0pt" margin-top="10.0pt" margin-left="10.0pt" margin-bottom="10.0pt" margin-right="10.0pt">
					<xsl:variable name="backgroundImageRepeat">
						no-repeat
					</xsl:variable>
					<xsl:variable name="pageBackgroundColor">
						#ffffff
					</xsl:variable>
					<xsl:variable name="uriBackgroundImage"></xsl:variable>
					<xsl:variable name="uriLeftBackgroundImage"></xsl:variable>
					<xsl:variable name="uriRightBackgroundImage"></xsl:variable>
					<xsl:variable name="uriBottomBackgroundImage"></xsl:variable>
					<xsl:variable name="uriTopBackgroundImage"></xsl:variable>
					<fo:region-body margin-left="28.0pt" margin-top="28.0pt" margin-bottom="28.0pt" margin-right="28.0pt" background-image="url({$uriBackgroundImage})" background-repeat="{$backgroundImageRepeat}" background-position-horizontal="left" background-position-vertical="top" />
					<fo:region-before extent="28.0pt" precedence="true" background-image="url({$uriTopBackgroundImage})" background-repeat="no-repeat" />
					<fo:region-after extent="28.0pt" precedence="true" background-image="url({$uriBottomBackgroundImage})" background-repeat="no-repeat" />
					<fo:region-start extent="28.0pt" background-image="url({$uriLeftBackgroundImage})" background-repeat="no-repeat" />
					<fo:region-end extent="28.0pt" background-image="url({$uriRightBackgroundImage})" background-repeat="no-repeat" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:bookmark-tree>
			</fo:bookmark-tree>
			<fo:page-sequence master-reference="pagemaster1">
				<xsl:attribute name="force-page-count">no-force</xsl:attribute>
				<fo:static-content flow-name="xsl-region-before">
					<xsl:variable name="backgroundColorVariable0">
						#000000
					</xsl:variable>
					<fo:block-container position="absolute" top="-10.0pt" left="-10.0pt" height="10.0pt" width="598.0pt" border-left-style="solid" border-left-width="1.0pt" border-left-color="#000000" border-right-style="solid" border-right-width="1.0pt" border-right-color="#000000" border-top-style="solid" border-top-width="1.0pt" border-top-color="#000000" border-bottom-style="solid" border-bottom-width="1.0pt" border-bottom-color="#000000" background-color="{$backgroundColorVariable0}">
						<fo:block />
					</fo:block-container>
					<xsl:variable name="backgroundColorVariable1">
						#000000
					</xsl:variable>
					<fo:block-container position="absolute" top="-2.0pt" left="-10.0pt" height="840.0pt" width="38.0pt" border-left-style="solid" border-left-width="1.0pt" border-left-color="#000000" border-right-style="solid" border-right-width="1.0pt" border-right-color="#000000" border-top-style="solid" border-top-width="1.0pt" border-top-color="#000000" border-bottom-style="solid" border-bottom-width="1.0pt" border-bottom-color="#000000" background-color="{$backgroundColorVariable1}">
						<fo:block />
					</fo:block-container>
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-after">
					<fo:block />
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-start">
					<fo:block />
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-end">
					<fo:block />
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block />
					<fo:block-container position="absolute" top="4pt" left="2pt" height="752.0pt" width="536.0pt" border-width="1.0pt">
						<fo:block span="none" white-space-collapse="false" font-family="Helvetica" font-size="12pt" text-align="start" position="relative" top="44pt" left="27pt" height="752.0pt" width="536.0pt">
							<fo:instream-foreign-object xmlns:xlink="http://www.w3.org/1999/xlink">
								<svg xmlns="http://www.w3.org/2000/svg" xml:space="preserve" width="600" height="600" viewBox="0 0 1000 1000"> <g id="test-body-content"><text x="0" y="100" style="opacity:.25; font-family:Arial; font-size:100pt; fill:rgb(200,200,200)">XSLfast</text><text x="50" y="200" style="opacity:.25; font-family:Arial; font-size:100pt; fill:rgb(200,200,200)">DEMO</text></g></svg>
							</fo:instream-foreign-object>
						</fo:block>
					</fo:block-container>
					<xsl:variable name="backgroundColorVariable2">
						transparent
					</xsl:variable>
					<xsl:variable name="backgroundImageTextFlowVariable0"></xsl:variable>
					<fo:block-container position="absolute" top="6.0pt" left="7.0pt" height="39.0pt" width="509.0pt" border-left-style="double" border-left-width="1.37pt" border-left-color="#000000" border-right-style="double" border-right-width="1.37pt" border-right-color="#000000" border-top-style="double" border-top-width="1.37pt" border-top-color="#000000" border-bottom-style="double" border-bottom-width="1.37pt" border-bottom-color="#000000" background-color="{$backgroundColorVariable2}" display-align="center" reference-orientation="0">
						<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="24.0pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="center" position="relative" top="6.0pt" left="7.0pt" height="39.0pt" width="509.0pt" keep-together="auto" color="#000000" font-family="Times New Roman" font-size="20.0pt" font-weight="bold" letter-spacing="normal" word-spacing="normal">
							<fo:block hyphenate="true" language="en">
								<fo:inline>
									<xsl:text>Facture n°</xsl:text>
								</fo:inline>
								<fo:inline>
									<xsl:value-of disable-output-escaping="no" select="@number" />
								</fo:inline>
							</fo:block>
						</fo:block>
					</fo:block-container>
					<xsl:variable name="backgroundColorVariable5">
						#ffffff
					</xsl:variable>
					<xsl:variable name="backgroundImageTextFlowVariable1"></xsl:variable>
					<fo:block-container position="absolute" top="53.0pt" left="7.0pt" height="77.0pt" width="254.0pt" display-align="before" reference-orientation="0">
						<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="14.399999999999999pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="start" position="relative" top="53.0pt" left="7.0pt" height="77.0pt" width="254.0pt" keep-together="auto" color="#000000" font-family="Arial" font-size="12.0pt" letter-spacing="normal" word-spacing="normal">
							<fo:block hyphenate="true" language="en">
								<fo:inline>
									<xsl:text>msdjfklsdjflsdfjlm</xsl:text>
								</fo:inline>
							</fo:block>
							<fo:inline>
								<xsl:text>sdfsdlfjsdilmfjsdklm</xsl:text>
							</fo:inline>
							<fo:block hyphenate="true" language="en">
								<fo:inline>
								</fo:inline>
								<fo:inline>
									<xsl:text>sfsfjsdlfjslmdjfsd</xsl:text>
								</fo:inline>
							</fo:block>
							<fo:inline>
								<xsl:text>fsdlmfkjsdlfjsdklmj</xsl:text>
							</fo:inline>
						</fo:block>
					</fo:block-container>
					<xsl:variable name="backgroundColorVariable10">
						#ffffff
					</xsl:variable>
					<xsl:variable name="backgroundImageTextFlowVariable2"></xsl:variable>
					<fo:block-container position="absolute" top="58.0pt" left="352.0pt" height="30.0pt" width="158.0pt" display-align="before" reference-orientation="0">
						<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="14.399999999999999pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="start" position="relative" top="58.0pt" left="352.0pt" height="30.0pt" width="158.0pt" keep-together="auto" letter-spacing="normal" word-spacing="normal">
							<fo:block hyphenate="true" language="en">
								<fo:inline color="#000000" font-family="Arial" font-size="12.0pt" font-weight="bold">
									<xsl:text>Faite le</xsl:text>
								</fo:inline>
								<fo:inline color="#000000" font-family="Arial" font-size="12.0pt">
									<xsl:text> </xsl:text>
								</fo:inline>
								<fo:inline color="#000000" font-family="Arial" font-size="12.0pt">
									<xsl:value-of disable-output-escaping="no" select="@date" />
								</fo:inline>
							</fo:block>
						</fo:block>
					</fo:block-container>
					<xsl:variable name="backgroundColorVariable14">
						#ffffff
					</xsl:variable>
					<xsl:variable name="backgroundImageTextFlowVariable3"></xsl:variable>
					<fo:block-container position="absolute" top="130.0pt" left="261.0pt" height="88.0pt" width="255.0pt" display-align="before" reference-orientation="0">
						<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="14.399999999999999pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="end" position="relative" top="130.0pt" left="261.0pt" height="88.0pt" width="255.0pt" keep-together="auto" color="#000000" font-family="Arial" font-size="12.0pt" letter-spacing="normal" word-spacing="normal">
							<fo:block hyphenate="true" language="en">
								<fo:inline>
									<xsl:value-of disable-output-escaping="no" select="@customerName" />
								</fo:inline>
							</fo:block>
							<fo:inline>
								<xsl:value-of disable-output-escaping="no" select="address/Company" />
							</fo:inline>
							<fo:block hyphenate="true" language="en">
								<fo:inline>
								</fo:inline>
								<fo:inline>
									<xsl:value-of disable-output-escaping="no" select="address/Country" />
								</fo:inline>
							</fo:block>
							<fo:inline>
								<xsl:value-of disable-output-escaping="no" select="address/City" />
							</fo:inline>
							<fo:inline>
								<xsl:text> </xsl:text>
							</fo:inline>
							<fo:inline>
								<xsl:value-of disable-output-escaping="no" select="address/Street" />
							</fo:inline>
							<fo:block hyphenate="true" language="en">
								<fo:inline>
								</fo:inline>
								<fo:inline>
									<xsl:text>+689 87 69 85 23</xsl:text>
								</fo:inline>
							</fo:block>
						</fo:block>
					</fo:block-container>
					<fo:block-container position="absolute" top="250.0pt" left="2.0pt" height="110.0pt" width="522.0pt" reference-orientation="0">
						<fo:block keep-together="auto"><!-- GENERATE TABLE START -->
							<xsl:if test="InvoiceLines/InvoiceLine">
								<xsl:variable name="tableIfEmptyVariable0"></xsl:variable>
								<xsl:variable name="backgroundColorVariable22"></xsl:variable>
								<fo:table table-layout="fixed" width="100%" background-image="url({$tableIfEmptyVariable0})" background-repeat="no-repeat" background-position-horizontal="0%" background-position-vertical="0%" background-color="{$backgroundColorVariable22}">
									<fo:table-column column-width="85.0pt" />
									<fo:table-column column-width="112.0pt" />
									<fo:table-column column-width="80.0pt" />
									<fo:table-column column-width="80.0pt" />
									<fo:table-column column-width="80.0pt" />
									<fo:table-column column-width="80.0pt" />
									<fo:table-body>
										<xsl:for-each select="InvoiceLines/InvoiceLine">
											<xsl:variable name="rowIfEmptyVariable0"></xsl:variable>
											<xsl:variable name="backgroundColorRowVariable0"></xsl:variable>
											<fo:table-row background-color="{$backgroundColorRowVariable0}" background-image="url({$rowIfEmptyVariable0})" background-repeat="no-repeat" background-position-horizontal="0%" background-position-vertical="0%">
												<xsl:variable name="cellIfEmptyVariable0"></xsl:variable>
												<xsl:variable name="backgroundColorVariable23">
													#ffffff
												</xsl:variable>
												<xsl:variable name="backgroundColorCellVariable0"></xsl:variable>
												<fo:table-cell padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" display-align="before" background-image="url({$cellIfEmptyVariable0})" background-repeat="no-repeat" background-position-horizontal="0%" background-position-vertical="0%" background-color="{$backgroundColorCellVariable0}" width="85.0pt" reference-orientation="0">
													<fo:block break-before="auto" />
													<xsl:variable name="backgroundColorVariable24">
														#ffffff
													</xsl:variable>
													<fo:block-container>
														<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="14.399999999999999pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="start" keep-together="auto" color="#000000" font-family="Arial" font-size="12.0pt" letter-spacing="normal" word-spacing="normal">
															<fo:inline>
																<xsl:value-of disable-output-escaping="no" select="@bar_code" />
															</fo:inline>
														</fo:block>
													</fo:block-container>
													<fo:block break-after="auto" />
												</fo:table-cell>
												<xsl:variable name="cellIfEmptyVariable1"></xsl:variable>
												<xsl:variable name="backgroundColorVariable26">
													#ffffff
												</xsl:variable>
												<xsl:variable name="backgroundColorCellVariable1"></xsl:variable>
												<fo:table-cell padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" display-align="before" background-image="url({$cellIfEmptyVariable1})" background-repeat="no-repeat" background-position-horizontal="0%" background-position-vertical="0%" background-color="{$backgroundColorCellVariable1}" width="112.0pt" reference-orientation="0">
													<fo:block break-before="auto" />
													<xsl:variable name="backgroundColorVariable27">
														#ffffff
													</xsl:variable>
													<fo:block-container>
														<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="14.399999999999999pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="start" keep-together="auto" color="#000000" font-family="Arial" font-size="12.0pt" letter-spacing="normal" word-spacing="normal">
															<fo:inline>
																<xsl:value-of disable-output-escaping="no" select="@label" />
															</fo:inline>
														</fo:block>
													</fo:block-container>
													<fo:block break-after="auto" />
												</fo:table-cell>
												<xsl:variable name="cellIfEmptyVariable2"></xsl:variable>
												<xsl:variable name="backgroundColorVariable29">
													#ffffff
												</xsl:variable>
												<xsl:variable name="backgroundColorCellVariable2"></xsl:variable>
												<fo:table-cell padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" display-align="before" background-image="url({$cellIfEmptyVariable2})" background-repeat="no-repeat" background-position-horizontal="0%" background-position-vertical="0%" background-color="{$backgroundColorCellVariable2}" width="80.0pt" reference-orientation="0">
													<fo:block break-before="auto" />
													<xsl:variable name="backgroundColorVariable30">
														#ffffff
													</xsl:variable>
													<fo:block-container>
														<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="14.399999999999999pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="start" keep-together="auto" color="#000000" font-family="Arial" font-size="12.0pt" letter-spacing="normal" word-spacing="normal">
															<fo:inline>
																<xsl:value-of disable-output-escaping="no" select="@quantity" />
															</fo:inline>
														</fo:block>
													</fo:block-container>
													<fo:block break-after="auto" />
												</fo:table-cell>
												<xsl:variable name="cellIfEmptyVariable3"></xsl:variable>
												<xsl:variable name="backgroundColorVariable32">
													#ffffff
												</xsl:variable>
												<xsl:variable name="backgroundColorCellVariable3"></xsl:variable>
												<fo:table-cell padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" display-align="before" background-image="url({$cellIfEmptyVariable3})" background-repeat="no-repeat" background-position-horizontal="0%" background-position-vertical="0%" background-color="{$backgroundColorCellVariable3}" width="80.0pt" reference-orientation="0">
													<fo:block break-before="auto" />
													<xsl:variable name="backgroundColorVariable33">
														#ffffff
													</xsl:variable>
													<fo:block-container>
														<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="14.399999999999999pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="start" keep-together="auto" color="#000000" font-family="Arial" font-size="12.0pt" letter-spacing="normal" word-spacing="normal">
															<fo:inline>
																<xsl:value-of disable-output-escaping="no" select="@sellingPrice" />
															</fo:inline>
														</fo:block>
													</fo:block-container>
													<fo:block break-after="auto" />
												</fo:table-cell>
												<xsl:variable name="cellIfEmptyVariable4"></xsl:variable>
												<xsl:variable name="backgroundColorVariable35">
													#ffffff
												</xsl:variable>
												<xsl:variable name="backgroundColorCellVariable4"></xsl:variable>
												<fo:table-cell padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" display-align="before" background-image="url({$cellIfEmptyVariable4})" background-repeat="no-repeat" background-position-horizontal="0%" background-position-vertical="0%" background-color="{$backgroundColorCellVariable4}" width="80.0pt" reference-orientation="0">
													<fo:block break-before="auto" />
													<xsl:variable name="backgroundColorVariable36">
														#ffffff
													</xsl:variable>
													<fo:block-container>
														<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="14.399999999999999pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="start" keep-together="auto" color="#000000" font-family="Arial" font-size="12.0pt" letter-spacing="normal" word-spacing="normal">
															<fo:inline>
																<xsl:value-of disable-output-escaping="no" select="@discountRate" />
															</fo:inline>
														</fo:block>
													</fo:block-container>
													<fo:block break-after="auto" />
												</fo:table-cell>
												<xsl:variable name="cellIfEmptyVariable5"></xsl:variable>
												<xsl:variable name="backgroundColorVariable38">
													#ffffff
												</xsl:variable>
												<xsl:variable name="backgroundColorCellVariable5"></xsl:variable>
												<fo:table-cell padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" display-align="before" background-image="url({$cellIfEmptyVariable5})" background-repeat="no-repeat" background-position-horizontal="0%" background-position-vertical="0%" background-color="{$backgroundColorCellVariable5}" width="80.0pt" reference-orientation="0">
													<fo:block break-before="auto" />
													<xsl:variable name="backgroundColorVariable39">
														#ffffff
													</xsl:variable>
													<fo:block-container>
														<fo:block linefeed-treatment="preserve" white-space-treatment="ignore-if-surrounding-linefeed" padding-bottom="0.0pt" start-indent="0.0pt" end-indent="0.0pt" padding-top="0.0pt" padding="0.0pt" line-height="14.399999999999999pt" line-stacking-strategy="max-height" white-space-collapse="false" hyphenate="true" language="en" text-align="start" keep-together="auto" color="#000000" font-family="Arial" font-size="12.0pt" letter-spacing="normal" word-spacing="normal">
															<fo:inline>
																<xsl:value-of disable-output-escaping="no" select="@tvaRate" />
															</fo:inline>
														</fo:block>
													</fo:block-container>
													<fo:block break-after="auto" />
												</fo:table-cell>

											</fo:table-row>
											<xsl:variable name="rowIfEmptyVariable1"></xsl:variable>
											<xsl:variable name="backgroundColorRowVariable1"></xsl:variable>
											<fo:table-row background-color="{$backgroundColorRowVariable1}" background-image="url({$rowIfEmptyVariable1})" background-repeat="no-repeat" background-position-horizontal="0%" background-position-vertical="0%">
												<fo:table-cell>
													<fo:block />
												</fo:table-cell>
												<fo:table-cell>
													<fo:block />
												</fo:table-cell>
												<fo:table-cell>
													<fo:block />
												</fo:table-cell>
												<fo:table-cell>
													<fo:block />
												</fo:table-cell>
												<fo:table-cell>
													<fo:block />
												</fo:table-cell>
												<fo:table-cell>
													<fo:block />
												</fo:table-cell>

											</fo:table-row>
										</xsl:for-each>
									</fo:table-body>
								</fo:table>
							</xsl:if>
						</fo:block>
					</fo:block-container>
					<xsl:if test='position()=last()'>
						<fo:block id="lastPage" />
					</xsl:if>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="include-xsl-fo">
		<xsl:copy-of select="@*" />
	</xsl:template>
</xsl:stylesheet>
