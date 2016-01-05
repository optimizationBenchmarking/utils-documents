package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentBuilder;
import org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML10DefaultFontPalette;
import org.optimizationBenchmarking.utils.graphics.style.impl.DefaultPalettes;
import org.optimizationBenchmarking.utils.graphics.style.impl.Styles;

/**
 * The export document builder
 */
public final class ExportDocumentBuilder extends DocumentBuilder {

  /**
   * the export document builder
   *
   * @param driver
   *          the driver to use
   */
  ExportDocumentBuilder(final ExportDriver driver) {
    super(driver);
  }

  /** {@inheritDoc} */
  @Override
  protected final Styles createStyles() {
    return DefaultPalettes.createDefaultStyles(//
        XHTML10DefaultFontPalette.getInstance(), //
        this.getColorModel());
  }

  /** {@inheritDoc} */
  @Override
  protected final Document doCreateDocument() {
    return new _ExportDocument(this);
  }
}
